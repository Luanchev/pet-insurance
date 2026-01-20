package com.co.ias.aseguratupata.pet_insurance.domain.service;

import com.co.ias.aseguratupata.pet_insurance.domain.exception.InvalidPetAgeException;
import com.co.ias.aseguratupata.pet_insurance.domain.exception.PetNotInsurableException;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Pet;
import com.co.ias.aseguratupata.pet_insurance.domain.model.Species;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pet - Tests del Value Object Mascota")
public class PetTest {
    @Test
    @DisplayName("Debe crear una mascota válida")
    void shouldCreateValidPet() {
        // When
        Pet pet = new Pet("Firulais", Species.DOG, "Labrador", 3);

        // Then
        assertNotNull(pet);
        assertEquals("Firulais", pet.name());
        assertEquals(Species.DOG, pet.species());
        assertEquals("Labrador", pet.breed());
        assertEquals(3, pet.ageInYears());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la mascota es mayor a 10 años")
    void shouldThrowExceptionWhenPetTooOld() {
        // When & Then
        PetNotInsurableException exception = assertThrows(
                PetNotInsurableException.class,
                () -> new Pet("Abuelo", Species.DOG, "Poodle", 11)
        );

        assertTrue(exception.getMessage().contains("11"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si la edad es 0 o negativa")
    void shouldThrowExceptionWhenAgeInvalid() {
        // When & Then
        assertThrows(
                InvalidPetAgeException.class,
                () -> new Pet("Cachorro", Species.DOG, "Chihuahua", 0)
        );

        assertThrows(
                InvalidPetAgeException.class,
                () -> new Pet("Negativo", Species.CAT, "Persa", -1)
        );
    }

    @Test
    @DisplayName("Debe permitir mascota de exactamente 10 años")
    void shouldAllowPetAtMaxAge() {
        // When
        Pet pet = new Pet("Viejo", Species.CAT, "Siamés", 10);

        // Then
        assertNotNull(pet);
        assertEquals(10, pet.ageInYears());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nombre está vacío")
    void shouldThrowExceptionWhenNameIsBlank() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Pet("", Species.DOG, "Beagle", 5)
        );

        assertTrue(exception.getMessage().contains("nombre"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si la raza está vacía")
    void shouldThrowExceptionWhenBreedIsBlank() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Pet("Bobby", Species.DOG, "", 5)
        );

        assertTrue(exception.getMessage().contains("raza"));
    }

    @Test
    @DisplayName("Debe identificar mascota senior (> 5 años)")
    void shouldIdentifySeniorPet() {
        // Given
        Pet youngPet = new Pet("Junior", Species.DOG, "Bulldog", 3);
        Pet seniorPet = new Pet("Senior", Species.DOG, "Bulldog", 7);
        Pet limitPet = new Pet("Limit", Species.CAT, "Persa", 5);

        // Then
        assertFalse(youngPet.isSenior());
        assertTrue(seniorPet.isSenior());
        assertFalse(limitPet.isSenior()); // 5 años NO es senior
    }
}
