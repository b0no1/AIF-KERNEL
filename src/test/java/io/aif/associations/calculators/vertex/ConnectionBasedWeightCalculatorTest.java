package io.aif.associations.calculators.vertex;

import io.aif.associations.calculators.edge.IEdgeWeightCalculator;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConnectionBasedWeightCalculatorTest {

    @Test
    public void testCalculateWeightWithEmptyGraph() throws Exception {
        final IEdgeWeightCalculator<Integer> mockEdgeWeightCalculator = mock(IEdgeWeightCalculator.class);

        final Map<Integer, Map<Integer, Double>> graph = Collections.emptyMap();

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>(graph, mockEdgeWeightCalculator);
        assertEquals((Double)connectionBasedWeightCalculator.calculate(1), (Double).0);
    }

    @Test
    public void testCalculateWeightWith1ElementInGraph() throws Exception {
        final IEdgeWeightCalculator<Integer> mockEdgeWeightCalculator = mock(IEdgeWeightCalculator.class);

        final Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
        graph.put(1, new HashMap<>());

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>(graph, mockEdgeWeightCalculator);
        assertEquals((Double)connectionBasedWeightCalculator.calculate(1), (Double).0);
    }

    @Test
    public void testCalculateWeightWith2ElementsInGraph() throws Exception {
        final IEdgeWeightCalculator<Integer> mockEdgeWeightCalculator = mock(IEdgeWeightCalculator.class);
        when(mockEdgeWeightCalculator.calculate(1, 2)).thenReturn(.8);

        final Map<Integer, Double> connections = new HashMap<>();
        connections.put(2, .8);

        final Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
        graph.put(1, connections);
        graph.put(2, new HashMap<>());

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>(graph, mockEdgeWeightCalculator);
        assertEquals((Double)connectionBasedWeightCalculator.calculate(1), (Double)0.8999999999999999);

        verify(mockEdgeWeightCalculator, times(1)).calculate(1, 2);
    }

}