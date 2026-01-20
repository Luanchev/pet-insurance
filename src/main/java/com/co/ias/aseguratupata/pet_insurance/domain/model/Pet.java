package com.co.ias.aseguratupata.pet_insurance.domain.model;

import com.co.ias.aseguratupata.pet_insurance.domain.exception.InvalidPetAgeException;
import com.co.ias.aseguratupata.pet_insurance.domain.exception.PetNotInsurableException;

public record Pet(
        String name,
        Species species,
        String breed,
        int ageInYears

) {private static final int MAX_INSURABLE_AGE = 10;
    private static final int MIN_AGE = 0;

    public Pet {
        validateAge(ageInYears);
        validateName(name);
        validateBreed(breed);
    }

    private void validateAge(int age) {
        if (age <= MIN_AGE) {
            throw new InvalidPetAgeException(age);
        }
        if (age > MAX_INSURABLE_AGE) {
            throw new PetNotInsurableException(age);
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre de la mascota es requerido");
        }
    }


    private void validateBreed(String breed) {
        if (breed == null || breed.isBlank()) {
            throw new IllegalArgumentException("La raza de la mascota es requerida");
        }
    }

    //Validamos si la mascota tiene mas de 5 años
    public boolean isSenior() {
        return ageInYears > 5;
    }
}
