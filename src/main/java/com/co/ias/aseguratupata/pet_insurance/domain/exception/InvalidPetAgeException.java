package com.co.ias.aseguratupata.pet_insurance.domain.exception;

public class InvalidPetAgeException extends DomainException{
    public InvalidPetAgeException(int age) {
        super("Edad de mascota inválida: " + age + ". Debe ser mayor a 0");
    }
}
