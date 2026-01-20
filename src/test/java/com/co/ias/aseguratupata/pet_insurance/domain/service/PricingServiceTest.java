package com.co.ias.aseguratupata.pet_insurance.domain.service;

import com.co.ias.aseguratupata.pet_insurance.domain.model.Pet;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Plan;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Species;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PricingService - Tests de Cálculo de Precios")
public class PricingServiceTest {
    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService();
    }

    @Test
    @DisplayName("Debe calcular precio base para gato joven con plan básico")
    void shouldCalculateBasePriceForYoungCatBasicPlan() {
        // Given: Gato de 3 años, plan básico
        Pet cat = new Pet("Michi", Species.CAT, "Siamés", 3);
        Plan plan = Plan.BASIC;

        // When: Calcular precio
        BigDecimal price = pricingService.calculateMonthlyPrice(cat, plan);

        // Then: $10 + 10% (gato) = $11.00
        assertEquals(new BigDecimal("11.00"), price);
    }

    @Test
    @DisplayName("Debe calcular precio para perro joven con plan básico")
    void shouldCalculateBasePriceForYoungDogBasicPlan() {
        // Given: Perro de 4 años, plan básico
        Pet dog = new Pet("Firulais", Species.DOG, "Labrador", 4);
        Plan plan = Plan.BASIC;

        // When: Calcular precio
        BigDecimal price = pricingService.calculateMonthlyPrice(dog, plan);

        // Then: $10 + 20% (perro) = $12.00
        assertEquals(new BigDecimal("12.00"), price);
    }

    @Test
    @DisplayName("Debe aplicar recargo por edad senior (> 5 años)")
    void shouldApplySeniorSurchage() {
        // Given: Perro de 7 años (senior), plan básico
        Pet seniorDog = new Pet("Rex", Species.DOG, "Pastor Alemán", 7);
        Plan plan = Plan.BASIC;

        // When: Calcular precio
        BigDecimal price = pricingService.calculateMonthlyPrice(seniorDog, plan);

        // Then: $10 + 20% (perro) = $12 → $12 + 50% (senior) = $18.00
        assertEquals(new BigDecimal("18.00"), price);
    }

    @Test
    @DisplayName("Debe duplicar precio con plan Premium")
    void shouldDoublePriceForPremiumPlan() {
        // Given: Gato de 3 años, plan Premium
        Pet cat = new Pet("Luna", Species.CAT, "Persa", 3);
        Plan plan = Plan.PREMIUM;

        // When: Calcular precio
        BigDecimal price = pricingService.calculateMonthlyPrice(cat, plan);

        // Then: $10 + 10% (gato) = $11 → $11 x 2 (Premium) = $22.00
        assertEquals(new BigDecimal("22.00"), price);
    }

    @Test
    @DisplayName("Caso complejo: Perro senior con plan Premium")
    void shouldCalculateComplexCase() {
        // Given: Perro de 8 años (senior), plan Premium
        Pet seniorDog = new Pet("Max", Species.DOG, "Golden Retriever", 8);
        Plan plan = Plan.PREMIUM;

        // When: Calcular precio
        BigDecimal price = pricingService.calculateMonthlyPrice(seniorDog, plan);

        // Then:
        // $10 base
        // + 20% (perro) = $12
        // + 50% (senior) = $18
        // x 2 (Premium) = $36.00
        assertEquals(new BigDecimal("36.00"), price);
    }

    @Test
    @DisplayName("Debe calcular precio exacto en el límite de edad senior (5 años)")
    void shouldNotApplySeniorAt5Years() {
        // Given: Gato de exactamente 5 años
        Pet cat = new Pet("Whiskers", Species.CAT, "Angora", 5);
        Plan plan = Plan.BASIC;

        // When: Calcular precio
        BigDecimal price = pricingService.calculateMonthlyPrice(cat, plan);

        // Then: $10 + 10% = $11.00 (sin recargo senior)
        assertEquals(new BigDecimal("11.00"), price);
    }

    @Test
    @DisplayName("Debe aplicar senior a partir de 6 años")
    void shouldApplySeniorAt6Years() {
        // Given: Gato de 6 años
        Pet cat = new Pet("Garfield", Species.CAT, "Naranja", 6);
        Plan plan = Plan.BASIC;

        // When: Calcular precio
        BigDecimal price = pricingService.calculateMonthlyPrice(cat, plan);

        // Then: $10 + 10% = $11 → $11 + 50% = $16.50
        assertEquals(new BigDecimal("16.50"), price);
    }
}
