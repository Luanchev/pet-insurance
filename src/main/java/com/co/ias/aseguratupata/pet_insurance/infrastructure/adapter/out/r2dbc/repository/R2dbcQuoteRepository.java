package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.r2dbc.repository;

import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.entity.QuoteEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R2dbcQuoteRepository extends ReactiveCrudRepository<QuoteEntity, String> {

}
