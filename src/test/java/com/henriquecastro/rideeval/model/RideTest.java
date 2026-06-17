package com.henriquecastro.rideeval.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/** Ensures the Ride compact constructor rejects invalid input. */
class RideTest {

    @Test
    void rejectsNonPositiveDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Ride(10.0, 0.0, 10));
    }

    @Test
    void rejectsNonPositiveDuration() {
        assertThrows(IllegalArgumentException.class, () -> new Ride(10.0, 5.0, 0));
    }

    @Test
    void rejectsNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new Ride(-1.0, 5.0, 10));
    }
}
