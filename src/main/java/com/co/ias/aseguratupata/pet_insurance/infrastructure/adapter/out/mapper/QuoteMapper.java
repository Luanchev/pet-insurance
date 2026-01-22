package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.mapper;

import com.co.ias.aseguratupata.pet_insurance.domain.model.Pet;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Plan;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Quote;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Species;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.entity.QuoteEntity;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapper {

    //Creamos una entidad desde el modelo de dominio
    public QuoteEntity toEntity(Quote quote) {
        if (quote == null) {
            return null;
        }

        QuoteEntity entity = new QuoteEntity();

        entity.setId(quote.getId());
        entity.setPetName(quote.getPet().name());
        entity.setPetSpecies(quote.getPet().species().name());
        entity.setPetBreed(quote.getPet().breed());
        entity.setPetAgeInYears(quote.getPet().ageInYears());
        entity.setPlan(quote.getPlan().name());
        entity.setMonthlyPrice(quote.getMonthlyPrice());
        entity.setCreatedAt(quote.getCreatedAt());
        entity.setExpiresAt(quote.getExpiresAt());

        // isNew = true ya lo hace el constructor vacío
        return entity;
    }

    public Quote toDomain(QuoteEntity entity) {
        if (entity == null) {
            return null;
        }

        // Reconstruir Pet
        Pet pet = new Pet(
                entity.getPetName(),
                Species.valueOf(entity.getPetSpecies()),
                entity.getPetBreed(),
                entity.getPetAgeInYears()
        );


        return Quote.reconstruct(
                entity.getId(),
                pet,
                Plan.valueOf(entity.getPlan()),
                entity.getMonthlyPrice(),
                entity.getCreatedAt(),
                entity.getExpiresAt()
        );
    }
}
