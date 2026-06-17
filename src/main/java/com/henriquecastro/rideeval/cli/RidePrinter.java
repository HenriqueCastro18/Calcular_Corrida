package com.henriquecastro.rideeval.cli;

import com.henriquecastro.rideeval.model.RideAnalysis;

import java.util.List;
import java.util.Locale;

/**
 * Renders the ranking to the terminal. Isolated from the logic so the output
 * format (or the whole UI) can change without touching the evaluator.
 */
public class RidePrinter {

    // Fixed locale so numbers format consistently regardless of the host machine.
    private static final Locale LOCALE = Locale.US;

    public void printRanking(List<RideAnalysis> ranking) {
        System.out.println("=== Ride ranking (best to worst) ===");
        System.out.printf(LOCALE, "%-4s %-10s %-10s %-11s %-10s %-10s %s%n",
                "#", "Value", "Dist(km)", "Time(min)", "R$/km", "R$/hour", "Rating");

        int position = 1;
        for (RideAnalysis a : ranking) {
            System.out.printf(LOCALE, "%-4d %-10.2f %-10.2f %-11d %-10.2f %-10.2f %s%n",
                    position++,
                    a.ride().value(),
                    a.ride().distanceKm(),
                    a.ride().durationMin(),
                    a.costPerKm(),
                    a.costPerHour(),
                    a.rating().label());
        }
    }
}
