package com.henriquecastro.rideeval.service;

import com.henriquecastro.rideeval.model.Rating;
import com.henriquecastro.rideeval.model.Ride;
import com.henriquecastro.rideeval.model.RideAnalysis;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Unit tests for the evaluation logic: metrics, classification, inclusive boundaries and ranking. */
class RideEvaluatorTest {

    private final RideEvaluator evaluator = new RideEvaluator(); // defaults: 30 / 18

    @Test
    void computesCostPerKmAndPerHour() {
        // 20 / 4km = 5 R$/km ; 30 min = 0.5h -> 20 / 0.5 = 40 R$/h
        RideAnalysis a = evaluator.analyze(new Ride(20.0, 4.0, 30));
        assertEquals(5.0, a.costPerKm(), 0.0001);
        assertEquals(40.0, a.costPerHour(), 0.0001);
    }

    @Test
    void classifiesGoodRideAsWorthIt() {
        assertEquals(Rating.WORTH_IT, evaluator.analyze(new Ride(20.0, 4.0, 30)).rating());
    }

    @Test
    void classifiesBadRideAsBad() {
        // 10 over 60 min (1h) -> 10 R$/h, below 18
        assertEquals(Rating.BAD, evaluator.analyze(new Ride(10.0, 5.0, 60)).rating());
    }

    @Test
    void worthItBoundaryIsInclusive() {
        // 15 over 30 min -> exactly 30 R$/h
        assertEquals(Rating.WORTH_IT, evaluator.analyze(new Ride(15.0, 3.0, 30)).rating());
    }

    @Test
    void fairBoundaryIsInclusive() {
        // 9 over 30 min -> exactly 18 R$/h
        assertEquals(Rating.FAIR, evaluator.analyze(new Ride(9.0, 3.0, 30)).rating());
    }

    @Test
    void ranksBestRideFirst() {
        Ride good = new Ride(20.0, 4.0, 30); // 40 R$/h
        Ride bad = new Ride(10.0, 5.0, 60);  // 10 R$/h
        List<RideAnalysis> ranking = evaluator.analyzeAll(List.of(bad, good));
        assertEquals(good, ranking.get(0).ride());
        assertEquals(bad, ranking.get(1).ride());
    }
}
