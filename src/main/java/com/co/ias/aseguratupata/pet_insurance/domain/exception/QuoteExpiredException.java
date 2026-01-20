package com.co.ias.aseguratupata.pet_insurance.domain.exception;

public class QuoteExpiredException extends DomainException{
    public QuoteExpiredException(String quoteId){
        super("La cotización " + quoteId +" ha expirado");
    }
}

