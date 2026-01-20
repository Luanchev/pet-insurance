package com.co.ias.aseguratupata.pet_insurance.application.port.out;

import com.co.ias.aseguratupata.pet_insurance.domain.event.PolicyIssuedEvent;
import reactor.core.publisher.Mono;

public interface EventPublisher {
    Mono<Void> publishPolicyIssued(PolicyIssuedEvent event);
}
