package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto;

import com.co.ias.aseguratupata.pet_insurance.domain.model.Plan;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Species;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateQuoteRequestDTO(
    @NotBlank(message = "El nombre de la mascota es requerido")
    String petName,

    @NotNull(message = "La especie es requerida")
    Species species,

    @NotBlank(message = "La raza es requerida")
    String breed,

    @Min(value = 1)
    @Max(value = 10)
    int ageInYears,

    @NotNull(message = "El plan es requerido")
    Plan plan
) {}





