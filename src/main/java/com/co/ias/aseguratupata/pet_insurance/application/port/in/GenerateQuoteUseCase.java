package com.co.ias.aseguratupata.pet_insurance.application.port.in;

import com.co.ias.aseguratupata.pet_insurance.application.dto.QuoteRequest;
import com.co.ias.aseguratupata.pet_insurance.application.dto.QuoteResponse;
import reactor.core.publisher.Mono;

public interface GenerateQuoteUseCase {

    Mono<QuoteResponse> execute(QuoteRequest request);
}
