package com.co.ias.aseguratupata.pet_insurance.infrastructure.configuration;

import com.co.ias.aseguratupata.pet_insurance.application.port.in.GenerateQuoteUseCase;
import com.co.ias.aseguratupata.pet_insurance.application.port.in.IssuePolicyUseCase;
import com.co.ias.aseguratupata.pet_insurance.application.port.out.EventPublisher;
import com.co.ias.aseguratupata.pet_insurance.application.port.out.PolicyRepository;
import com.co.ias.aseguratupata.pet_insurance.application.port.out.QuoteRepository;
import com.co.ias.aseguratupata.pet_insurance.application.useCase.GenerateQuoteUseCaseImpl;
import com.co.ias.aseguratupata.pet_insurance.application.useCase.IssuePolicyUseCaseImpl;
import com.co.ias.aseguratupata.pet_insurance.domain.service.PricingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public PricingService pricingService() {
        return new PricingService();
    }

    @Bean
    public GenerateQuoteUseCase generateQuoteUseCase(
            QuoteRepository quoteRepository,
            PricingService pricingService) {
        return new GenerateQuoteUseCaseImpl(quoteRepository, pricingService);
    }

    @Bean
    public IssuePolicyUseCase issuePolicyUseCase(
            QuoteRepository quoteRepository,
            PolicyRepository policyRepository,
            EventPublisher eventPublisher) {
        return new IssuePolicyUseCaseImpl(quoteRepository, policyRepository, eventPublisher);
    }
}
