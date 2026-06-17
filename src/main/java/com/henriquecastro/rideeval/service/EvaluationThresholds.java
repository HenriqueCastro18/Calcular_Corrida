package com.henriquecastro.rideeval.service;

/**
 * Configurable R$/hour limits used to classify a ride.
 * Centralizing the rules here keeps magic numbers out of the evaluator and makes
 * boundary cases easy to test.
 */
public record EvaluationThresholds(double worthItPerHour, double fairPerHour) {

    public EvaluationThresholds {
        if (fairPerHour <= 0 || worthItPerHour <= fairPerHour) {
            throw new IllegalArgumentException("Expected 0 < fairPerHour < worthItPerHour");
        }
    }

    /** Default BRL limits: >= 30/h is worth it, >= 18/h is fair, anything below is bad. */
    public static EvaluationThresholds defaults() {
        return new EvaluationThresholds(30.0, 18.0);
    }
}
