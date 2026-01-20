package com.co.ias.aseguratupata.pet_insurance.domain.service;

import com.co.ias.aseguratupata.pet_insurance.domain.exception.QuoteExpiredException;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Pet;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Plan;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Quote;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Species;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Quote - Tests de la Entidad Cotización")
public class QuoteTest {
    @Test
    @DisplayName("Debe crear una cotización con ID único")
    void shouldCreateQuoteWithUniqueId() {
        // Given
        Pet pet = new Pet("Max", Species.DOG, "Labrador", 4);
        Plan plan = Plan.BASIC;
        BigDecimal price = new BigDecimal("12.00");

        // When
        Quote quote = Quote.create(pet, plan, price);

        // Then
        assertNotNull(quote.getId());
        assertFalse(quote.getId().isBlank());
        assertEquals(pet, quote.getPet());
        assertEquals(plan, quote.getPlan());
        assertEquals(price, quote.getMonthlyPrice());
    }

    @Test
    @DisplayName("Debe generar dos IDs diferentes para dos cotizaciones")
    void shouldGenerateDifferentIdsForDifferentQuotes() {
        // Given
        Pet pet = new Pet("Luna", Species.CAT, "Siamés", 3);
        Plan plan = Plan.PREMIUM;
        BigDecimal price = new BigDecimal("22.00");

        // When
        Quote quote1 = Quote.create(pet, plan, price);
        Quote quote2 = Quote.create(pet, plan, price);

        // Then
        assertNotEquals(quote1.getId(), quote2.getId());
    }

    @Test
    @DisplayName("Debe establecer fecha de expiración a 30 días")
    void shouldSetExpirationTo30Days() {
        // Given
        Pet pet = new Pet("Rex", Species.DOG, "Pastor", 5);
        Plan plan = Plan.BASIC;
        BigDecimal price = new BigDecimal("12.00");

        // When
        Quote quote = Quote.create(pet, plan, price);

        // Then
        LocalDateTime expectedExpiration = quote.getCreatedAt().plusDays(30);
        assertEquals(expectedExpiration, quote.getExpiresAt());
    }

    @Test
    @DisplayName("Una cotización recién creada debe ser válida")
    void shouldBeValidWhenJustCreated() {
        // Given
        Pet pet = new Pet("Michi", Species.CAT, "Persa", 2);
        Plan plan = Plan.BASIC;
        BigDecimal price = new BigDecimal("11.00");

        // When
        Quote quote = Quote.create(pet, plan, price);

        // Then
        assertTrue(quote.isValid());
    }

    @Test
    @DisplayName("Una cotización expirada no debe ser válida")
    void shouldBeInvalidWhenExpired() {
        // Given: Cotización que expiró hace 1 día
        Pet pet = new Pet("Firulais", Species.DOG, "Beagle", 4);
        Plan plan = Plan.PREMIUM;
        BigDecimal price = new BigDecimal("24.00");

        LocalDateTime pastDate = LocalDateTime.now().minusDays(31);
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(1);

        Quote quote = Quote.reconstruct(
                "test-id-123",
                pet,
                plan,
                price,
                pastDate,
                expirationDate
        );

        // Then
        assertFalse(quote.isValid());
    }

    @Test
    @DisplayName("Debe lanzar excepción al validar cotización expirada")
    void shouldThrowExceptionWhenValidatingExpiredQuote() {
        // Given: Cotización expirada
        Pet pet = new Pet("Bobby", Species.DOG, "Bulldog", 6);
        Plan plan = Plan.BASIC;
        BigDecimal price = new BigDecimal("18.00");

        LocalDateTime pastDate = LocalDateTime.now().minusDays(40);
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(10);

        Quote expiredQuote = Quote.reconstruct(
                "expired-123",
                pet,
                plan,
                price,
                pastDate,
                expirationDate
        );

        // When & Then
        QuoteExpiredException exception = assertThrows(
                QuoteExpiredException.class,
                expiredQuote::validateForIssuance
        );

        assertTrue(exception.getMessage().contains("expired-123"));
    }

    @Test
    @DisplayName("No debe lanzar excepción al validar cotización vigente")
    void shouldNotThrowExceptionWhenValidatingValidQuote() {
        // Given: Cotización vigente
        Pet pet = new Pet("Whiskers", Species.CAT, "Angora", 4);
        Plan plan = Plan.PREMIUM;
        BigDecimal price = new BigDecimal("22.00");

        Quote quote = Quote.create(pet, plan, price);

        // When & Then
        assertDoesNotThrow(quote::validateForIssuance);
    }
}
