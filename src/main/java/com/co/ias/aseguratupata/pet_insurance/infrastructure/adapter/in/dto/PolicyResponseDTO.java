package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PolicyResponseDTO(
        String policyId,
        String quoteId,
        String ownerName,
        BigDecimal monthlyPrice,
        LocalDateTime issuedAt
) {}
