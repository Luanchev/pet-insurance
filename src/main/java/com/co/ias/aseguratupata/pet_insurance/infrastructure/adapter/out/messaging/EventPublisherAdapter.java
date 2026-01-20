package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.out.messaging;

import com.co.ias.aseguratupata.pet_insurance.application.port.out.EventPublisher;
import com.co.ias.aseguratupata.pet_insurance.domain.event.PolicyIssuedEvent;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;

@Component
public class EventPublisherAdapter implements EventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(EventPublisherAdapter.class);

    @Override
    public Mono<Void> publishPolicyIssued(PolicyIssuedEvent event) {
        return Mono.fromRunnable(() -> {
            logger.info("=====================================");
            logger.info("EVENTO DE DOMINIO: PolicyIssuedEvent");
            logger.info("========================================");
            logger.info("Policy ID: {}", event.policyId());
            logger.info("Owner: {} ({})", event.ownerName(), event.ownerEmail());
            logger.info("Pet: {}", event.petName());
            logger.info("Monthly Price: ${}", event.monthlyPrice());
            logger.info("Issued At: {}", event.issuedAt());
            logger.info("========================================");
            logger.info("Este evento sería consumido por el sistema de facturación");
            logger.info("========================================");
        });
    }
}
