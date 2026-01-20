package com.co.ias.aseguratupata.pet_insurance.application.port.in;

import com.co.ias.aseguratupata.pet_insurance.application.dto.IssuePolicyRequest;
import com.co.ias.aseguratupata.pet_insurance.application.dto.IssuePolicyResponse;
import reactor.core.publisher.Mono;

public interface IssuePolicyUseCase {

    Mono<IssuePolicyResponse> execute(IssuePolicyRequest request);
}
