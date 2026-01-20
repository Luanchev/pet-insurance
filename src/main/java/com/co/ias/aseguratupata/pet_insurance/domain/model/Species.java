package com.co.ias.aseguratupata.pet_insurance.domain.model;

public enum Species {
    DOG(0.20),  // 20% de incremento
    CAT(0.10);  // 10% de incremento

    private final double riskMultiplier;

    Species(double riskMultiplier) {
        this.riskMultiplier = riskMultiplier;
    }

    public double getRiskMultiplier() {
        return riskMultiplier;
    }
}
