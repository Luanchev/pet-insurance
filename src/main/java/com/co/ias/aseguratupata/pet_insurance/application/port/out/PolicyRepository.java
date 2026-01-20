package com.co.ias.aseguratupata.pet_insurance.application.port.out;

import com.co.ias.aseguratupata.pet_insurance.domain.model.Policy;
import reactor.core.publisher.Mono;

public interface PolicyRepository {

    Mono<Policy> save(Policy policy);

    Mono<Policy> findById(String id);

    Mono<Policy> findByQuoteId(String quoteId);
}
