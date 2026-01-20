package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in;

import com.co.ias.aseguratupata.pet_insurance.application.dto.IssuePolicyRequest;
import com.co.ias.aseguratupata.pet_insurance.application.port.in.IssuePolicyUseCase;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto.IssuePolicyRequestDTO;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto.PolicyResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {
    private final IssuePolicyUseCase issuePolicyUseCase;

    public PolicyController(IssuePolicyUseCase issuePolicyUseCase) {
        this.issuePolicyUseCase = issuePolicyUseCase;
    }

    /**
     * POST /api/policies
     * Emite una nueva póliza desde una cotización
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PolicyResponseDTO> issuePolicy(@Valid @RequestBody IssuePolicyRequestDTO requestDTO) {
        // Mapear de DTO REST a DTO de aplicación
        IssuePolicyRequest request = new IssuePolicyRequest(
                requestDTO.quoteId(),
                requestDTO.ownerName(),
                requestDTO.ownerId(),
                requestDTO.ownerEmail()
        );

        // Ejecutar caso de uso
        return issuePolicyUseCase.execute(request)
                // Mapear de DTO de aplicación a DTO REST
                .map(response -> new PolicyResponseDTO(
                        response.policyId(),
                        response.quoteId(),
                        response.ownerName(),
                        response.monthlyPrice(),
                        response.issuedAt()
                ));
    }

}
