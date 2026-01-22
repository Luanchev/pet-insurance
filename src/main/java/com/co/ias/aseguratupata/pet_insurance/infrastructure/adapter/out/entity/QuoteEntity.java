package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("quotes")
public class QuoteEntity implements Persistable<String> {
    @Id
    private String id;

    @Column("pet_name")
    private String petName;

    @Column("pet_species")
    private String petSpecies;

    @Column("pet_breed")
    private String petBreed;

    @Column("pet_age_in_years")
    private Integer petAgeInYears;

    @Column("plan")
    private String plan;

    @Column("monthly_price")
    private BigDecimal monthlyPrice;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("expires_at")
    private LocalDateTime expiresAt;

    @Transient
    private boolean isNew = true;

    // Constructor completo
    @PersistenceCreator
    public QuoteEntity(String id, String petName, String petSpecies,
                       String petBreed, Integer petAgeInYears, String plan,
                       BigDecimal monthlyPrice, LocalDateTime createdAt,
                       LocalDateTime expiresAt) {
        this.id = id;
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petAgeInYears = petAgeInYears;
        this.plan = plan;
        this.monthlyPrice = monthlyPrice;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isNew = false; //cuando viene desde la DB
    }

    public QuoteEntity() {
        this.isNew = true;
    }



    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public Integer getPetAgeInYears() {
        return petAgeInYears;
    }

    public void setPetAgeInYears(Integer petAgeInYears) {
        this.petAgeInYears = petAgeInYears;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

}
