package com.co.ias.aseguratupata.pet_insurance.application.useCase;

import com.co.ias.aseguratupata.pet_insurance.application.dto.QuoteRequest;
import com.co.ias.aseguratupata.pet_insurance.application.dto.QuoteResponse;
import com.co.ias.aseguratupata.pet_insurance.application.port.in.GenerateQuoteUseCase;
import com.co.ias.aseguratupata.pet_insurance.application.port.out.QuoteRepository;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Pet;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Quote;
import com.co.ias.aseguratupata.pet_insurance.domain.service.PricingService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class GenerateQuoteUseCaseImpl implements GenerateQuoteUseCase {
    private final QuoteRepository quoteRepository;
    private final PricingService pricingService;

    public GenerateQuoteUseCaseImpl(QuoteRepository quoteRepository,
                                    PricingService pricingService) {
        this.quoteRepository = quoteRepository;
        this.pricingService = pricingService;
    }

    @Override
    public Mono<QuoteResponse> execute(QuoteRequest request) {
        return Mono.fromCallable(() -> {

            // 1. Crear el Value Object Pet
            Pet pet = new Pet(
                    request.petName(),
                    request.species(),
                    request.breed(),
                    request.ageInYears()
            );

            // 2. Calcular precio usando servicio de dominio
            BigDecimal monthlyPrice = pricingService.calculateMonthlyPrice(
                    pet,
                    request.plan()
            );

            // 3. Crear el agregado Quote
            Quote quote = Quote.create(pet, request.plan(), monthlyPrice);

            return quote;
        })
        // 4. Persistir la cotización (reactivo)
        .flatMap(quoteRepository::save)

        // 5. Mapeamos a DTO de respuesta
        .map(quote -> new QuoteResponse(
                quote.getId(),
                quote.getMonthlyPrice(),
                quote.getExpiresAt()
        ));
    }
}
