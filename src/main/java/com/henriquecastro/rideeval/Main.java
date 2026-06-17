package com.henriquecastro.rideeval;

import com.henriquecastro.rideeval.cli.RidePrinter;
import com.henriquecastro.rideeval.model.Ride;
import com.henriquecastro.rideeval.model.RideAnalysis;
import com.henriquecastro.rideeval.service.RideEvaluator;

import java.util.List;

/**
 * Application entry point: builds the input, runs the evaluator and prints the result.
 */
public class Main {

    public static void main(String[] args) {
        // Sample data for the demo run.
        List<Ride> rides = List.of(
                new Ride(12.50, 3.0, 10),
                new Ride(8.00, 5.0, 25),
                new Ride(30.00, 12.0, 35),
                new Ride(6.50, 2.0, 8),
                new Ride(18.00, 9.0, 40)
        );

        RideEvaluator evaluator = new RideEvaluator();
        List<RideAnalysis> ranking = evaluator.analyzeAll(rides);

        new RidePrinter().printRanking(ranking);
    }
}
