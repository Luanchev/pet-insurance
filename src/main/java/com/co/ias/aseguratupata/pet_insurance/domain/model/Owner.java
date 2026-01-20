package com.co.ias.aseguratupata.pet_insurance.domain.model;

import java.util.regex.Pattern;

public record Owner(
        String name,
        String identificationId,
        String email

) {
    private static final Pattern EMAIL_PATTERN =
            //expresion regular
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");


    public Owner {
        validateName(name);
        validateIdentificationId(identificationId);
        validateEmail(email);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del dueño es requerido");
        }
    }


    private void validateIdentificationId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("La identificación del dueño es requerida");
        }
    }

    //validamos si es correcto el email
    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email del dueño es requerido");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
    }
}
