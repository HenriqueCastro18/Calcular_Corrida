package com.henriquecastro.rideeval.model;

/**
 * Raw ride offer (input only, no calculations).
 * Immutable value type; the compact constructor enforces the input invariants.
 *
 * Monetary amounts use double because this app only computes comparison ratios,
 * not financial bookkeeping. BigDecimal would be the right choice for accounting.
 */
public record Ride(double value, double distanceKm, int durationMin) {

    public Ride {
        if (value < 0) {
            throw new IllegalArgumentException("value must be >= 0");
        }
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("distanceKm must be > 0");
        }
        if (durationMin <= 0) {
            throw new IllegalArgumentException("durationMin must be > 0");
        }
    }
}
