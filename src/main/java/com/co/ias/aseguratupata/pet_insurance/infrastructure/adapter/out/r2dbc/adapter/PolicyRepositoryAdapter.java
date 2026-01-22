package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.r2dbc.adapter;

import com.co.ias.aseguratupata.pet_insurance.application.port.out.PolicyRepository;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Policy;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.entity.PolicyEntity;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.mapper.PolicyMapper;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.r2dbc.repository.R2dbcPolicyRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PolicyRepositoryAdapter implements PolicyRepository {
    private final R2dbcPolicyRepository r2dbcRepository;
    private final PolicyMapper mapper;

    public PolicyRepositoryAdapter(R2dbcPolicyRepository r2dbcRepository, PolicyMapper mapper) {
        this.r2dbcRepository = r2dbcRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Policy> save(Policy policy) {
        PolicyEntity entity = mapper.toEntity(policy);
        return r2dbcRepository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Policy> findById(String id) {
        return r2dbcRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Policy> findByQuoteId(String quoteId) {
        return r2dbcRepository.findByQuoteId(quoteId)
                .map(mapper::toDomain);
    }
}
