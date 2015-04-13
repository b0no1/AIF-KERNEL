package io.aif.associations.calculators.vertex;
import io.aif.associations.graph.INodeWithCount;
import io.aif.associations.model.IGraph;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConnectionBasedWeightCalculatorTest {

    @Test
    public void testCalculateWeightWithEmptyGraph() throws Exception {

        final IGraph<INodeWithCount<Integer>, Double> graph = mock(IGraph.class);
        when(graph.getVertex()).thenReturn(Collections.emptySet());

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>();
        assertTrue(connectionBasedWeightCalculator.calculate(graph).isEmpty());
    }

    @Test
    public void testCalculateWeightWith1ElementInGraph() throws Exception {
        final IGraph<INodeWithCount<Integer>, Double> graph = mock(IGraph.class);
        final INodeWithCount<Integer> element = mock(INodeWithCount.class);
        when(graph.getVertex()).thenReturn(new HashSet<INodeWithCount<Integer>>(){{add(element);}});
        when(graph.getNeighbors(element)).thenReturn(Collections.emptySet());

        final Map<INodeWithCount<Integer>, Double> expectedResult = new HashMap<INodeWithCount<Integer>, Double>(){{
            put(element, .0);
        }};

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>();


        assertEquals(connectionBasedWeightCalculator.calculate(graph), expectedResult);
    }

    @Test
    public void testCalculateWeightWith2ElementsInGraph() throws Exception {
        final INodeWithCount<Integer> element = mock(INodeWithCount.class);
        final INodeWithCount<Integer> element2 = mock(INodeWithCount.class);
        when(element.item()).thenReturn(1);
        when(element2.item()).thenReturn(2);

        final IGraph<INodeWithCount<Integer>, Double> graph = mock(IGraph.class);

        when(graph.getVertex()).thenReturn(new HashSet<INodeWithCount<Integer>>(){{add(element); add(element2);}});
        when(graph.getNeighbors(element)).thenReturn(new HashSet<INodeWithCount<Integer>>(){{add(element2);}});
        when(graph.getEdge(element, element2)).thenReturn(Optional.of(.8));

        final Map<INodeWithCount<Integer>, Double> expectedResult = new HashMap<INodeWithCount<Integer>, Double>(){{
            put(element, 0.8999999999999999);
            put(element2, .0);
        }};

        final ConnectionBasedWeightCalculator<Integer> connectionBasedWeightCalculator = new ConnectionBasedWeightCalculator<>();


        assertEquals(connectionBasedWeightCalculator.calculate(graph), expectedResult);
    }

}