package com.henriquecastro.rideeval.service;

import com.henriquecastro.rideeval.model.Rating;
import com.henriquecastro.rideeval.model.Ride;
import com.henriquecastro.rideeval.model.RideAnalysis;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Core evaluation logic: computes the metrics, classifies and ranks rides.
 * Free of I/O so it can be unit-tested in isolation.
 */
public class RideEvaluator {

    private static final int MINUTES_PER_HOUR = 60;

    // Best-first ordering: higher R$/hour wins, R$/km breaks ties.
    private static final Comparator<RideAnalysis> BEST_FIRST =
            Comparator.comparingDouble(RideAnalysis::costPerHour)
                    .thenComparingDouble(RideAnalysis::costPerKm)
                    .reversed();

    private final EvaluationThresholds thresholds;

    public RideEvaluator(EvaluationThresholds thresholds) {
        this.thresholds = thresholds;
    }

    public RideEvaluator() {
        this(EvaluationThresholds.defaults());
    }

    public RideAnalysis analyze(Ride ride) {
        double costPerKm = ride.value() / ride.distanceKm();
        double costPerHour = ride.value() / (ride.durationMin() / (double) MINUTES_PER_HOUR);
        return new RideAnalysis(ride, costPerKm, costPerHour, classify(costPerHour));
    }

    /** Evaluates every ride and returns them ranked best to worst. */
    public List<RideAnalysis> analyzeAll(List<Ride> rides) {
        return rides.stream()
                .map(this::analyze)
                .sorted(BEST_FIRST)
                .toList();
    }

    /** Returns the top-ranked ride, or empty when there are no rides. */
    public Optional<RideAnalysis> findBest(List<Ride> rides) {
        return analyzeAll(rides).stream().findFirst();
    }

    private Rating classify(double costPerHour) {
        if (costPerHour >= thresholds.worthItPerHour()) {
            return Rating.WORTH_IT;
        }
        if (costPerHour >= thresholds.fairPerHour()) {
            return Rating.FAIR;
        }
        return Rating.BAD;
    }
}
