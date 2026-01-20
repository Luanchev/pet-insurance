package com.co.ias.aseguratupata.pet_insurance.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("quotes")
public class QuoteEntity {
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


    public QuoteEntity() {}

    // Constructor completo
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
    }

    /**
     * Convierte esta entidad de persistencia al modelo de dominio
     */
    public Quote toDomain() {
        Pet pet = new Pet(
                this.petName,
                Species.valueOf(this.petSpecies),
                this.petBreed,
                this.petAgeInYears
        );

        return Quote.reconstruct(
                this.id,
                pet,
                Plan.valueOf(this.plan),
                this.monthlyPrice,
                this.createdAt,
                this.expiresAt
        );
    }

    /**
     * Crea una entidad de persistencia desde el modelo de dominio
     */
    public static QuoteEntity fromDomain(Quote quote) {
        return new QuoteEntity(
                quote.getId(),
                quote.getPet().name(),
                quote.getPet().species().name(),
                quote.getPet().breed(),
                quote.getPet().ageInYears(),
                quote.getPlan().name(),
                quote.getMonthlyPrice(),
                quote.getCreatedAt(),
                quote.getExpiresAt()
        );
    }

}
