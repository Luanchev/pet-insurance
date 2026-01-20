package com.co.ias.aseguratupata.pet_insurance.domain.exception;

public class QuoteNotFoundException extends DomainException{
    public QuoteNotFoundException(String quoteId) {
        super("Cotización no encontrada: " + quoteId);
    }
}
