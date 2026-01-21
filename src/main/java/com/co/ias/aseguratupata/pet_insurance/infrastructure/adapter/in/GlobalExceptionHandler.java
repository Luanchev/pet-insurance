package com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in;

import com.co.ias.aseguratupata.pet_insurance.domain.exception.InvalidPetAgeException;
import com.co.ias.aseguratupata.pet_insurance.domain.exception.PetNotInsurableException;
import com.co.ias.aseguratupata.pet_insurance.domain.exception.QuoteExpiredException;
import com.co.ias.aseguratupata.pet_insurance.domain.exception.QuoteNotFoundException;
import com.co.ias.aseguratupata.pet_insurance.infrastructure.adapter.in.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    //Maneja excepciones de mascota demasiado mayor
    @ExceptionHandler(PetNotInsurableException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handlePetTooOldException(PetNotInsurableException ex) {
        logger.warn("Pet too old: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Pet Too Old",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    //Maneja excepciones de edad inválida <0
    @ExceptionHandler(InvalidPetAgeException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleInvalidPetAgeException(InvalidPetAgeException ex) {
        logger.warn("Invalid pet age: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Pet Age",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    //Maneja excepciones de cotización no encontrada
    @ExceptionHandler(QuoteNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleQuoteNotFoundException(QuoteNotFoundException ex) {
        logger.warn("Quote not found: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Quote Not Found",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    //Maneja excepciones de cotización expirada
    @ExceptionHandler(QuoteExpiredException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleQuoteExpiredException(QuoteExpiredException ex) {
        logger.warn("Quote expired: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Quote Expired",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }


    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleValidationException(WebExchangeBindException ex) {
        logger.warn("Validation error: {}", ex.getMessage());
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Error de validación");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                message
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }


    //otras excepciones no capturadas
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponseDTO>> handleGenericException(Exception ex) {
        logger.error("Unexpected error", ex);
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ha ocurrido un error inesperado. Por favor contacte al administrador."
        );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
    }
}
