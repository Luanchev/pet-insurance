package com.co.ias.aseguratupata.pet_insurance.application.dto;

public record IssuePolicyRequest(
        String quoteId,
        String ownerName,
        String ownerId,
        String ownerEmail
) {
}
