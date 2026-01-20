package com.co.ias.aseguratupata.pet_insurance.domain.exception;

public class PetNotInsurableException extends DomainException {

    public PetNotInsurableException(int age) {
        super("Las mascotas mauor a 10 anios no pueden ser aseguradas. Edad de la mascota:" + age);
    }

}
