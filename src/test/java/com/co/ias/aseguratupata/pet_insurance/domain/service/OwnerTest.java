package com.co.ias.aseguratupata.pet_insurance.domain.service;

import com.co.ias.aseguratupata.pet_insurance.domain.model.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Owner - Tests del Value Object Dueño")
public class OwnerTest {
    @Test
    @DisplayName("Debe crear un dueño válido")
    void shouldCreateValidOwner() {
        // When
        Owner owner = new Owner("Juan Pérez", "12345678", "juan@example.com");

        // Then
        assertNotNull(owner);
        assertEquals("Juan Pérez", owner.name());
        assertEquals("12345678", owner.identificationId());
        assertEquals("juan@example.com", owner.email());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nombre está vacío")
    void shouldThrowExceptionWhenNameIsBlank() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Owner("", "12345678", "juan@example.com")
        );

        assertTrue(exception.getMessage().contains("nombre"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el ID está vacío")
    void shouldThrowExceptionWhenIdIsBlank() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Owner("Juan Pérez", "", "juan@example.com")
        );

        assertTrue(exception.getMessage().contains("identificación"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el email está vacío")
    void shouldThrowExceptionWhenEmailIsBlank() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Owner("Juan Pérez", "12345678", "")
        );

        assertTrue(exception.getMessage().contains("email"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "@example.com",
            "user@",
            "user@.com",
            "user name@example.com"
    })
    @DisplayName("Debe rechazar emails con formato inválido")
    void shouldRejectInvalidEmailFormats(String invalidEmail) {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Owner("Juan Pérez", "12345678", invalidEmail)
        );

        assertTrue(exception.getMessage().contains("Email inválido"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user@example.com",
            "user.name@example.com",
            "user+tag@example.co.uk",
            "user_123@sub.example.com"
    })
    @DisplayName("Debe aceptar emails con formato válido")
    void shouldAcceptValidEmailFormats(String validEmail) {
        // When & Then
        assertDoesNotThrow(
                () -> new Owner("Juan Pérez", "12345678", validEmail)
        );
    }
}
