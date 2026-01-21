package com.co.ias.aseguratupata.pet_insurance.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Policy {
    private final String id;
    private final String quoteId;
    private final Owner owner;
    private final Pet pet;
    private final Plan plan;
    private final BigDecimal monthlyPrice;
    private PolicyStatus status;
    private final LocalDateTime issuedAt;


    private Policy(String id, String quoteId, Owner owner, Pet pet, Plan plan,
                   BigDecimal monthlyPrice, PolicyStatus status, LocalDateTime issuedAt) {
        this.id = id;
        this.quoteId = quoteId;
        this.owner = owner;
        this.pet = pet;
        this.plan = plan;
        this.monthlyPrice = monthlyPrice;
        this.status = status;
        this.issuedAt = issuedAt;
    }

    //se emite una poliza desde la cotización
    public static Policy issue(Quote quote, Owner owner) {
        quote.validateForIssuance(); // Valida que la cotización esté vigente

        return new Policy(
                UUID.randomUUID().toString(),
                quote.getId(),
                owner,
                quote.getPet(),
                quote.getPlan(),
                quote.getMonthlyPrice(),
                PolicyStatus.ACTIVE,
                LocalDateTime.now()
        );
    }

    //reconstruye desde la persistencia
    public static Policy reconstruct(String id, String quoteId, Owner owner, Pet pet,
                                     Plan plan, BigDecimal monthlyPrice,
                                     PolicyStatus status, LocalDateTime issuedAt) {
        return new Policy(id, quoteId, owner, pet, plan, monthlyPrice, status, issuedAt);
    }

   //cancela poliza
    public void cancel() {
        if (this.status == PolicyStatus.CANCELLED) {
            throw new IllegalStateException("La póliza ya está cancelada");
        }
        this.status = PolicyStatus.CANCELLED;
    }

    //verifica que este activa
    public boolean isActive() {
        return this.status == PolicyStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public Owner getOwner() {
        return owner;
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

    public PolicyStatus getStatus() {
        return status;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}