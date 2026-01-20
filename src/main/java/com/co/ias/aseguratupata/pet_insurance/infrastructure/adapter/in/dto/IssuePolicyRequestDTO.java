package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record IssuePolicyRequestDTO(
        @NotBlank(message = "El ID de la cotización es requerido")
        String quoteId,

        @NotBlank(message = "El nombre del dueño es requerido")
        String ownerName,

        @NotBlank(message = "El ID del dueño es requerido")
        String ownerId,

        @NotBlank(message = "El email es requerido")
        @Email(message = "El email debe ser válido")
        String ownerEmail
) {}