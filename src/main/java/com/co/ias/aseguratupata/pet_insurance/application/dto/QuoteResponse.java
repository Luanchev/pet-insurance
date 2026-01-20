package com.co.ias.aseguratupata.pet_insurance.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record QuoteResponse(
        String quoteId,
        BigDecimal monthlyPrice,
        LocalDateTime expiresAt
) {
}
