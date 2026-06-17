package com.henriquecastro.rideeval.model;

/**
 * Quality classification for a ride offer.
 * Each constant carries its own display label used by the CLI.
 */
public enum Rating {
    WORTH_IT("Worth it"),
    FAIR("Fair"),
    BAD("Bad");

    private final String label;

    Rating(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
