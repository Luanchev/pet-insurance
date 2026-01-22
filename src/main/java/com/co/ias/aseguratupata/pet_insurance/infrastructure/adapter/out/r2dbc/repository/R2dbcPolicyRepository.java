package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.r2dbc.repository;

import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.entity.PolicyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface R2dbcPolicyRepository extends ReactiveCrudRepository<PolicyEntity, String> {
    @Query("SELECT * FROM policies WHERE quote_id = :quoteId")
    Mono<PolicyEntity> findByQuoteId(String quoteId);
}
