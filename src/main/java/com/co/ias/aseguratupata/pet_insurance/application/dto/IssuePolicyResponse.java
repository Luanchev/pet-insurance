package com.co.ias.aseguratupata.pet_insurance.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IssuePolicyResponse(
        String policyId,
        String quoteId,
        String ownerName,
        BigDecimal monthlyPrice,
        LocalDateTime issuedAt
) {
}
