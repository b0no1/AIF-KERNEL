package io.aif.associations.builder;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ExperimentsConnectionsGraphReducerTest {

    @Test
    public void testReduceWithEmptyMap() throws Exception {
        final ExperimentsConnectionsGraphReducer<Integer> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
        final Map<Integer, Map<Integer, Double>> actualResult = experimentsConnectionsGraphReducer.reduce(Collections.emptyMap());

        assertTrue(actualResult.isEmpty());
    }

    @Test
     public void testReduceWithOneElementMap() throws Exception {
        final Map<Integer, Map<Integer, List<Double>>> inputValues = new HashMap<>();
        inputValues.put(1, Collections.emptyMap());

        final ExperimentsConnectionsGraphReducer<Integer> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
        final Map<Integer, Map<Integer, Double>> actualResult = experimentsConnectionsGraphReducer.reduce(inputValues);

        assertEquals(actualResult.keySet().size(), 1);
        assertTrue(actualResult.get(1).isEmpty());
    }

    @Test
    public void testReduceWithTwoElementsMapAndOneConnection() throws Exception {
        final Map<Integer, List<Double>> connection = new HashMap<>();
        connection.put(2, Arrays.asList(1.));

        final Map<Integer, Map<Integer, List<Double>>> inputValues = new HashMap<>();
        inputValues.put(1, connection);
        inputValues.put(2, Collections.emptyMap());

        final ExperimentsConnectionsGraphReducer<Integer> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
        final Map<Integer, Map<Integer, Double>> actualResult = experimentsConnectionsGraphReducer.reduce(inputValues);

        assertEquals(actualResult.keySet().size(), 2);
        assertEquals(actualResult.get(1).get(2), (Double)1.);
        assertEquals(actualResult.get(1).keySet().size(), 1);

        assertTrue(actualResult.get(2).isEmpty());
    }

    @Test
      public void testReduceWithTwoElementsMapAndTwoConnections() throws Exception {
        final Map<Integer, List<Double>> connection = new HashMap<>();
        connection.put(2, Arrays.asList(1., 4.));
        connection.put(3, Arrays.asList(1.));

        final Map<Integer, Map<Integer, List<Double>>> inputValues = new HashMap<>();
        inputValues.put(1, connection);
        inputValues.put(2, Collections.emptyMap());
        inputValues.put(3, Collections.emptyMap());

        final ExperimentsConnectionsGraphReducer<Integer> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
        final Map<Integer, Map<Integer, Double>> actualResult = experimentsConnectionsGraphReducer.reduce(inputValues);

        assertEquals(actualResult.keySet().size(), 3);
        assertEquals(actualResult.get(1).get(2), (Double)0.);
        assertEquals(actualResult.get(1).get(3), (Double)1.);

        assertTrue(actualResult.get(2).isEmpty());
        assertTrue(actualResult.get(3).isEmpty());
    }

    @Test
    public void testReduceWithForeElementsMapAndThreeConnections() throws Exception {
        final Map<Integer, List<Double>> connection = new HashMap<>();
        connection.put(2, Arrays.asList(1., 1.));
        connection.put(3, Arrays.asList(1., 4.));
        connection.put(4, Arrays.asList(1., 7.));

        final Map<Integer, Map<Integer, List<Double>>> inputValues = new HashMap<>();
        inputValues.put(1, connection);
        inputValues.put(2, Collections.emptyMap());
        inputValues.put(3, Collections.emptyMap());
        inputValues.put(4, Collections.emptyMap());

        final ExperimentsConnectionsGraphReducer<Integer> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
        final Map<Integer, Map<Integer, Double>> actualResult = experimentsConnectionsGraphReducer.reduce(inputValues);

        assertEquals(actualResult.keySet().size(), 4);
        assertEquals(actualResult.get(1).get(2), (Double)1.);
        assertEquals(actualResult.get(1).get(3), (Double).5);
        assertEquals(actualResult.get(1).get(4), (Double).0);

        assertTrue(actualResult.get(2).isEmpty());
        assertTrue(actualResult.get(3).isEmpty());
        assertTrue(actualResult.get(4).isEmpty());
    }

}