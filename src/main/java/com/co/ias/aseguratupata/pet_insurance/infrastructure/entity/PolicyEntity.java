package com.co.ias.aseguratupata.pet_insurance.infrastructure.entity;

import com.co.ias.aseguratupata.pet_insurance.domain.model.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("policies")
public class PolicyEntity implements Persistable<String> {
    @Id
    private String id;

    @Column("quote_id")
    private String quoteId;

    @Column("owner_name")
    private String ownerName;

    @Column("owner_identification_id")
    private String ownerIdentificationId;

    @Column("owner_email")
    private String ownerEmail;

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

    @Column("status")
    private String status;

    @Column("issued_at")
    private LocalDateTime issuedAt;

    @Transient
    private boolean isNew = true;


    public PolicyEntity(String id, String quoteId, String ownerName,
                        String ownerIdentificationId, String ownerEmail,
                        String petName, String petSpecies, String petBreed,
                        Integer petAgeInYears, String plan, BigDecimal monthlyPrice,
                        String status, LocalDateTime issuedAt) {
        this.id = id;
        this.quoteId = quoteId;
        this.ownerName = ownerName;
        this.ownerIdentificationId = ownerIdentificationId;
        this.ownerEmail = ownerEmail;
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petAgeInYears = petAgeInYears;
        this.plan = plan;
        this.monthlyPrice = monthlyPrice;
        this.status = status;
        this.issuedAt = issuedAt;
        this.isNew = false;  //cuando viene desde la DB
    }

    // Constructor para crear nuevas entidades
    public PolicyEntity() {
        this.isNew = true;
    }

    //convertimos esta entidad al modelo de dominio
    public Policy toDomain() {
        Owner owner = new Owner(
                this.ownerName,
                this.ownerIdentificationId,
                this.ownerEmail
        );

        Pet pet = new Pet(
                this.petName,
                Species.valueOf(this.petSpecies),
                this.petBreed,
                this.petAgeInYears
        );

        return Policy.reconstruct(
                this.id,
                this.quoteId,
                owner,
                pet,
                Plan.valueOf(this.plan),
                this.monthlyPrice,
                PolicyStatus.valueOf(this.status),
                this.issuedAt
        );
    }

    //Creamos una entidad desde el modelo de dominio
    public static PolicyEntity fromDomain(Policy policy) {
        PolicyEntity entity = new PolicyEntity();
        entity.id = policy.getId();
        entity.quoteId = policy.getQuoteId();
        entity.ownerName = policy.getOwner().name();
        entity.ownerIdentificationId = policy.getOwner().identificationId();
        entity.ownerEmail = policy.getOwner().email();
        entity.petName = policy.getPet().name();
        entity.petSpecies = policy.getPet().species().name();
        entity.petBreed = policy.getPet().breed();
        entity.petAgeInYears = policy.getPet().ageInYears();
        entity.plan = policy.getPlan().name();
        entity.monthlyPrice = policy.getMonthlyPrice();
        entity.status = policy.getStatus().name();
        entity.issuedAt = policy.getIssuedAt();
        entity.isNew = true; // si la policy es nuevo hace un insert
        return entity;
    }

    // Métodos de Persistable
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

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdentificationId() {
        return ownerIdentificationId;
    }

    public void setOwnerIdentificationId(String ownerIdentificationId) {
        this.ownerIdentificationId = ownerIdentificationId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
}
