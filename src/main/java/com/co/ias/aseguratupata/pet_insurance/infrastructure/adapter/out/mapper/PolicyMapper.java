package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.mapper;

import com.co.ias.aseguratupata.pet_insurance.domain.model.*;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.entity.PolicyEntity;
import org.springframework.stereotype.Component;

@Component
public class PolicyMapper {
    //convertimos esta entidad al modelo de dominio
    public Policy toDomain(PolicyEntity entity) {
        if (entity == null) {
            return null;
        }

        Owner owner = new Owner(
                entity.getOwnerName(),
                entity.getOwnerIdentificationId(),
                entity.getOwnerEmail()
        );
        Pet pet = new Pet(
                entity.getPetName(),
                Species.valueOf(entity.getPetSpecies()),
                entity.getPetBreed(),
                entity.getPetAgeInYears()
        );

        return Policy.reconstruct(
                entity.getId(),
                entity.getQuoteId(),
                owner,
                pet,
                Plan.valueOf(entity.getPlan()),
                entity.getMonthlyPrice(),
                PolicyStatus.valueOf(entity.getStatus()),
                entity.getIssuedAt()
        );
    }

    //Creamos una entidad desde el modelo de dominio
    public PolicyEntity toEntity(Policy policy) {
        if (policy == null) {
            return null;
        }
        PolicyEntity entity = new PolicyEntity();

        entity.setId(policy.getId());
        entity.setQuoteId(policy.getQuoteId());

        entity.setOwnerName(policy.getOwner().name());
        entity.setOwnerIdentificationId(policy.getOwner().identificationId());
        entity.setOwnerEmail(policy.getOwner().email());

        entity.setPetName(policy.getPet().name());
        entity.setPetSpecies(policy.getPet().species().name());
        entity.setPetBreed(policy.getPet().breed());
        entity.setPetAgeInYears(policy.getPet().ageInYears());

        entity.setPlan(policy.getPlan().name());
        entity.setMonthlyPrice(policy.getMonthlyPrice());
        entity.setStatus(policy.getStatus().name());
        entity.setIssuedAt(policy.getIssuedAt());

        return entity;
    }
}
