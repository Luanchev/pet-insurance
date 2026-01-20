package com.co.ias.aseguratupata.pet_insurance.domain.service;

import com.co.ias.aseguratupata.pet_insurance.domain.exception.QuoteExpiredException;
import com.co.ias.aseguratupata.pet_insurance.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Policy - Tests de la Entidad Póliza")
public class PolicyTest {
    @Test
    @DisplayName("Debe emitir póliza desde cotización válida")
    void shouldIssuePolicyFromValidQuote() {
        // Given: Cotización válida
        Pet pet = new Pet("Max", Species.DOG, "Labrador", 4);
        Plan plan = Plan.BASIC;
        BigDecimal price = new BigDecimal("12.00");
        Quote quote = Quote.create(pet, plan, price);

        Owner owner = new Owner("Juan Pérez", "12345678", "juan@example.com");

        // When
        Policy policy = Policy.issue(quote, owner);

        // Then
        assertNotNull(policy);
        assertNotNull(policy.getId());
        assertEquals(quote.getId(), policy.getQuoteId());
        assertEquals(owner, policy.getOwner());
        assertEquals(pet, policy.getPet());
        assertEquals(plan, policy.getPlan());
        assertEquals(price, policy.getMonthlyPrice());
        assertEquals(PolicyStatus.ACTIVE, policy.getStatus());
        assertTrue(policy.isActive());
    }

    @Test
    @DisplayName("Debe generar IDs únicos para cada póliza")
    void shouldGenerateUniqueIdsForPolicies() {
        // Given
        Pet pet = new Pet("Luna", Species.CAT, "Siamés", 3);
        Quote quote = Quote.create(pet, Plan.PREMIUM, new BigDecimal("22.00"));
        Owner owner = new Owner("María García", "87654321", "maria@example.com");

        // When
        Policy policy1 = Policy.issue(quote, owner);
        Policy policy2 = Policy.issue(quote, owner);

        // Then
        assertNotEquals(policy1.getId(), policy2.getId());
    }

    @Test
    @DisplayName("No debe emitir póliza desde cotización expirada")
    void shouldNotIssuePolicyFromExpiredQuote() {
        // Given: Cotización expirada
        Pet pet = new Pet("Rex", Species.DOG, "Pastor", 7);
        Plan plan = Plan.BASIC;
        BigDecimal price = new BigDecimal("18.00");

        LocalDateTime pastDate = LocalDateTime.now().minusDays(40);
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(5);

        Quote expiredQuote = Quote.reconstruct(
                "expired-quote",
                pet,
                plan,
                price,
                pastDate,
                expirationDate
        );

        Owner owner = new Owner("Carlos López", "11223344", "carlos@example.com");

        // When & Then
        assertThrows(
                QuoteExpiredException.class,
                () -> Policy.issue(expiredQuote, owner)
        );
    }

    @Test
    @DisplayName("Debe permitir cancelar póliza activa")
    void shouldAllowCancellingActivePolicy() {
        // Given: Póliza activa
        Pet pet = new Pet("Bobby", Species.DOG, "Bulldog", 5);
        Quote quote = Quote.create(pet, Plan.PREMIUM, new BigDecimal("24.00"));
        Owner owner = new Owner("Ana Torres", "55667788", "ana@example.com");
        Policy policy = Policy.issue(quote, owner);

        // When
        policy.cancel();

        // Then
        assertEquals(PolicyStatus.CANCELLED, policy.getStatus());
        assertFalse(policy.isActive());
    }

    @Test
    @DisplayName("No debe permitir cancelar póliza ya cancelada")
    void shouldNotAllowCancellingAlreadyCancelledPolicy() {
        // Given: Póliza ya cancelada
        Pet pet = new Pet("Michi", Species.CAT, "Persa", 4);
        Quote quote = Quote.create(pet, Plan.BASIC, new BigDecimal("11.00"));
        Owner owner = new Owner("Pedro Ramírez", "99887766", "pedro@example.com");
        Policy policy = Policy.issue(quote, owner);
        policy.cancel();

        // When & Then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                policy::cancel
        );

        assertTrue(exception.getMessage().contains("ya está cancelada"));
    }

    @Test
    @DisplayName("Debe copiar datos de la cotización a la póliza")
    void shouldCopyQuoteDataToPolicy() {
        // Given
        Pet pet = new Pet("Garfield", Species.CAT, "Naranja", 6);
        Plan plan = Plan.PREMIUM;
        BigDecimal price = new BigDecimal("33.00");
        Quote quote = Quote.create(pet, plan, price);
        Owner owner = new Owner("Sofia Méndez", "44556677", "sofia@example.com");

        // When
        Policy policy = Policy.issue(quote, owner);

        // Then
        assertEquals(quote.getPet().name(), policy.getPet().name());
        assertEquals(quote.getPet().species(), policy.getPet().species());
        assertEquals(quote.getPet().breed(), policy.getPet().breed());
        assertEquals(quote.getPet().ageInYears(), policy.getPet().ageInYears());
        assertEquals(quote.getPlan(), policy.getPlan());
        assertEquals(quote.getMonthlyPrice(), policy.getMonthlyPrice());
    }
}
