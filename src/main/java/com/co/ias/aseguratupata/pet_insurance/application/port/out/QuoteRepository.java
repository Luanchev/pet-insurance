package com.co.ias.aseguratupata.pet_insurance.application.port.out;

import com.co.ias.aseguratupata.pet_insurance.domain.model.Quote;
import reactor.core.publisher.Mono;

public interface QuoteRepository {

    Mono<Quote> save(Quote quote);

    Mono<Quote> findById(String id);

    Mono<Boolean> existsById(String id);
}
