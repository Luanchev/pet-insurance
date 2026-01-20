package com.co.ias.aseguratupata.pet_insurance.domain.service;

import com.co.ias.aseguratupata.pet_insurance.domain.model.Pet;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Plan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class PricingService {
    private static final BigDecimal BASE_PRICE = new BigDecimal("10.00");
    private static final BigDecimal SENIOR_MULTIPLIER = new BigDecimal("1.50"); // +50%


    public BigDecimal calculateMonthlyPrice(Pet pet, Plan plan) {
        // 1. Precio base
        BigDecimal price = BASE_PRICE;

        // 2. Aplicar multiplicador por especie
        BigDecimal speciesMultiplier = BigDecimal.valueOf(1 + pet.species().getRiskMultiplier());
        price = price.multiply(speciesMultiplier);

        // 3. Aplicar recargo por edad
        if (pet.isSenior()) {
            price = price.multiply(SENIOR_MULTIPLIER);
        }

        // 4. Aplicar multiplicador del plan
        BigDecimal planMultiplier = BigDecimal.valueOf(plan.getPriceMultiplier());
        price = price.multiply(planMultiplier);

        // Redondear a 2 decimales
        return price.setScale(2, RoundingMode.HALF_UP);
    }
}
