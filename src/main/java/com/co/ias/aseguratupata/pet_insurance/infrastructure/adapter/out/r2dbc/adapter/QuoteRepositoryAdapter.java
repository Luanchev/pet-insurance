package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.r2dbc.adapter;

import com.co.ias.aseguratupata.pet_insurance.application.port.out.QuoteRepository;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Quote;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.r2dbc.repository.R2dbcQuoteRepository;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.entity.QuoteEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class QuoteRepositoryAdapter implements QuoteRepository {
    private final R2dbcQuoteRepository r2dbcRepository;

    public QuoteRepositoryAdapter(R2dbcQuoteRepository r2dbcRepository) {
        this.r2dbcRepository = r2dbcRepository;
    }

    @Override
    public Mono<Quote> save(Quote quote) {
        QuoteEntity entity = QuoteEntity.fromDomain(quote);
        return r2dbcRepository.save(entity)
                .map(QuoteEntity::toDomain);
    }

    @Override
    public Mono<Quote> findById(String id) {
        return r2dbcRepository.findById(id)
                .map(QuoteEntity::toDomain);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return r2dbcRepository.existsById(id);
    }
}
