package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record QuoteResponseDTO(
        String quoteId,
        BigDecimal monthlyPrice,
        LocalDateTime expiresAt
) {}
