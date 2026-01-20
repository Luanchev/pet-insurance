package com.co.ias.aseguratupata.pet_insurance.application.useCase;

import com.co.ias.aseguratupata.pet_insurance.application.dto.IssuePolicyRequest;
import com.co.ias.aseguratupata.pet_insurance.application.dto.IssuePolicyResponse;
import com.co.ias.aseguratupata.pet_insurance.application.port.in.IssuePolicyUseCase;
import com.co.ias.aseguratupata.pet_insurance.application.port.out.EventPublisher;
import com.co.ias.aseguratupata.pet_insurance.application.port.out.PolicyRepository;
import com.co.ias.aseguratupata.pet_insurance.application.port.out.QuoteRepository;
import com.co.ias.aseguratupata.pet_insurance.domain.event.PolicyIssuedEvent;
import com.co.ias.aseguratupata.pet_insurance.domain.exception.QuoteNotFoundException;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Owner;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Policy;
import reactor.core.publisher.Mono;

public class IssuePolicyUseCaseImpl implements IssuePolicyUseCase {
    private final QuoteRepository quoteRepository;
    private final PolicyRepository policyRepository;
    private final EventPublisher eventPublisher;

    public IssuePolicyUseCaseImpl(QuoteRepository quoteRepository,
                                  PolicyRepository policyRepository,
                                  EventPublisher eventPublisher) {
        this.quoteRepository = quoteRepository;
        this.policyRepository = policyRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Mono<IssuePolicyResponse> execute(IssuePolicyRequest request) {
        return quoteRepository.findById(request.quoteId())

                // 1. Se valida que la cotización existe
                .switchIfEmpty(Mono.error(
                        new QuoteNotFoundException(request.quoteId())
                ))

                // 2. Creamos Owner y emitimos Policy
                .flatMap(quote -> {

                    // Crear el Value Object Owner

                    Owner owner = new Owner(
                            request.ownerName(),
                            request.ownerId(),
                            request.ownerEmail()
                    );
                    // Emitir la póliza
                    Policy policy = Policy.issue(quote, owner);
                    return Mono.just(policy);
                })

                // 3. Persistir la póliza
                .flatMap(policyRepository::save)

                // 4. Publicar evento de dominio (sin bloquear la respuesta)
                .flatMap(policy -> {
                PolicyIssuedEvent event = new PolicyIssuedEvent(
                        policy.getId(),
                        policy.getOwner().name(),
                        policy.getOwner().email(),
                        policy.getOwner().identificationId(),
                        policy.getPet().name(),
                        policy.getMonthlyPrice(),
                        policy.getIssuedAt()
                );

                // Publicar el evento y retornar la póliza
                return eventPublisher.publishPolicyIssued(event)
                        .thenReturn(policy);
                })

                // 5. Mapear a DTO de respuesta
                .map(policy -> new IssuePolicyResponse(
                        policy.getId(),
                        policy.getQuoteId(),
                        policy.getOwner().name(),
                        policy.getMonthlyPrice(),
                        policy.getIssuedAt()
                ));
    }
}
