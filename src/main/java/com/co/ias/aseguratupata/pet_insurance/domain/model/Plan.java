package com.co.ias.aseguratupata.pet_insurance.domain.model;

public enum Plan {
    BASIC(1.0),PREMIUM(2.0);

    private final double priceMultiplier;

    Plan(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }
    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}
