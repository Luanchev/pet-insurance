package com.co.ias.aseguratupata.pet_insurance.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PolicyIssuedEvent(
        String policyId,
        String ownerName,
        String ownerEmail,
        String ownerId,
        String petName,
        BigDecimal monthlyPrice,
        LocalDateTime issuedAt
) {
    public PolicyIssuedEvent {
        if (policyId == null || policyId.isBlank()) {
            throw new IllegalArgumentException("El ID de la póliza es requerido");
        }
        if (monthlyPrice == null || monthlyPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio mensual debe ser mayor a cero");
        }
        if (issuedAt == null) {
            throw new IllegalArgumentException("La fecha de emisión es requerida");
        }
    }
}
