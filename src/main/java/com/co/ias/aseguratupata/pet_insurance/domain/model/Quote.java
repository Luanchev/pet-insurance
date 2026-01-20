package com.co.ias.aseguratupata.pet_insurance.domain.model;

import com.co.ias.aseguratupata.pet_insurance.domain.exception.QuoteExpiredException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Quote {
    private final String id;
    private final Pet pet;
    private final Plan plan;
    private final BigDecimal monthlyPrice;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;

    // las cotizaciones expiran en 30 días
    private static final int EXPIRATION_DAYS = 30;

    private Quote(String id, Pet pet, Plan plan, BigDecimal monthlyPrice,
                  LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.id = id;
        this.pet = pet;
        this.plan = plan;
        this.monthlyPrice = monthlyPrice;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    //metodo para crear una nueva cotización
    public static Quote create(Pet pet, Plan plan, BigDecimal monthlyPrice) {
        LocalDateTime now = LocalDateTime.now();
        return new Quote(
                UUID.randomUUID().toString(),
                pet,
                plan,
                monthlyPrice,
                now,
                now.plusDays(EXPIRATION_DAYS)
        );
    }


    //se reconstruye cotizacion existente
    public static Quote reconstruct(String id, Pet pet, Plan plan, BigDecimal monthlyPrice,
                                    LocalDateTime createdAt, LocalDateTime expiresAt) {
        return new Quote(id, pet, plan, monthlyPrice, createdAt, expiresAt);
    }

    //se verifica que la cotizacion es valida
    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiresAt);
    }

    //se valida antes de emitir
    public void validateForIssuance() {
        if (!isValid()) {
            throw new QuoteExpiredException(this.id);
        }
    }

    public String getId() {
        return id;
    }

    public Pet getPet() {
        return pet;
    }

    public Plan getPlan() {
        return plan;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
