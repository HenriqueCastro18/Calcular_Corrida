package com.henriquecastro.rideeval.model;

/**
 * Computed result of evaluating a {@link Ride}.
 * Kept separate from Ride so the input stays untouched and each type has a single responsibility.
 */
public record RideAnalysis(Ride ride, double costPerKm, double costPerHour, Rating rating) {
}
