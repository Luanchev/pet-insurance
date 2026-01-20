package com.co.ias.aseguratupata.pet_insurance.application.dto;

import com.co.ias.aseguratupata.pet_insurance.domain.model.Plan;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Species;

public record QuoteRequest(
        String petName,
        Species species,
        String breed,
        int ageInYears,
        Plan plan
) {

}
