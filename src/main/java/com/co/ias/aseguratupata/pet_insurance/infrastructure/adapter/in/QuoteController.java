package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in;

import com.co.ias.aseguratupata.pet_insurance.application.dto.QuoteRequest;
import com.co.ias.aseguratupata.pet_insurance.application.port.in.GenerateQuoteUseCase;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto.CreateQuoteRequestDTO;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto.QuoteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final GenerateQuoteUseCase generateQuoteUseCase;

    public QuoteController(GenerateQuoteUseCase generateQuoteUseCase) {
        this.generateQuoteUseCase = generateQuoteUseCase;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<QuoteResponseDTO> createQuote(@Valid @RequestBody CreateQuoteRequestDTO requestDTO) {
        // Mapear de DTO REST a DTO de aplicación
        QuoteRequest request = new QuoteRequest(
                requestDTO.petName(),
                requestDTO.species(),
                requestDTO.breed(),
                requestDTO.ageInYears(),
                requestDTO.plan()
        );

        // Ejecutar caso de uso
        return generateQuoteUseCase.execute(request)
                // Mapear de DTO de aplicación a DTO REST
                .map(response -> new QuoteResponseDTO(
                        response.quoteId(),
                        response.monthlyPrice(),
                        response.expiresAt()
                ));
    }
}
