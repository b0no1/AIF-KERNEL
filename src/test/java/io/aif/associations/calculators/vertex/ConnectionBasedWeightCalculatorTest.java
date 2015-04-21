package io.aif.associations.calculators.vertex;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ConnectionBasedWeightCalculatorTest {

    @Test
    public void testCalculateWeightWithEmptyGraph() throws Exception {

        final Map<Integer, Map<Integer, Double>> graph = Collections.emptyMap();
        final Map<Integer, Long> count = Collections.emptyMap();

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>();
        assertTrue(connectionBasedWeightCalculator.calculate(graph, count).isEmpty());
    }

    @Test
    public void testCalculateWeightWith1ElementInGraph() throws Exception {
        final Integer inputElement = 1;

        final Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
        graph.put(inputElement, new HashMap<>());

        final Map<Integer, Long> count = new HashMap<>();
        count.put(1, 1l);

        final Map<Integer, Double> expectedResult = new HashMap<Integer, Double>(){{
            put(inputElement, .0);
        }};

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>();


        assertEquals(connectionBasedWeightCalculator.calculate(graph, count), expectedResult);
    }

    @Test
    public void testCalculateWeightWith2ElementsInGraph() throws Exception {
        final Integer element = 1;
        final Integer element2 = 2;

        final Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
        graph.put(1, new HashMap<Integer, Double>() {{
            put(2, .8);
        }});

        final Map<Integer, Long> count = new HashMap<>();
        count.put(1, 1l);
        count.put(2, 1l);

        final Map<Integer, Double> expectedResult = new HashMap<Integer, Double>(){{
            put(element, 0.8999999999999999);
            put(element2, .0);
        }};

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>();


        assertEquals(connectionBasedWeightCalculator.calculate(graph, count), expectedResult);
    }

}