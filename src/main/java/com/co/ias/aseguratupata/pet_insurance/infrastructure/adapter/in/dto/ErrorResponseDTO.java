package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
    public ErrorResponseDTO(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now());
    }
}
