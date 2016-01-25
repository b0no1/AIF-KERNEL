package io.aif.algorithms.graph.fuzzy.similarity;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.graph.IFuzzyGraph;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class ISimilarityCalculatorTest {

    @Mock
    private IFuzzyGraph<String> left;

    @Mock
    private IFuzzyGraph<String> right;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final Optional<FuzzyBoolean> edge = Optional.of(new FuzzyBoolean(1.));

        final Set<String> leftVertices = new HashSet<>();
        leftVertices.add("1");
        leftVertices.add("2");
        leftVertices.add("3");
        final Set<String> rightVertices = new HashSet<>();
        rightVertices.add("2");
        rightVertices.add("3");
        rightVertices.add("4");

        when(left.getVertices()).thenReturn(leftVertices);
        when(left.getNeighbors("1")).thenReturn(new HashSet<String>(){{add("2");}});
        when(left.getNeighbors("2")).thenReturn(new HashSet<String>(){{add("1"); add("3");}});
        when(left.getNeighbors("3")).thenReturn(new HashSet<String>(){{add("2");}});
        when(left.getEdge("1", "2")).thenReturn(edge);
        when(left.getEdge("2", "1")).thenReturn(edge);
        when(left.getEdge("3", "2")).thenReturn(edge);
        when(left.getEdge("2", "3")).thenReturn(edge);

        when(right.getVertices()).thenReturn(rightVertices);
        when(right.getNeighbors("1")).thenReturn(Collections.emptySet());
        when(right.getNeighbors("2")).thenReturn(new HashSet<String>(){{add("3");}});
        when(right.getNeighbors("3")).thenReturn(new HashSet<String>(){{add("2"); add("4");}});
        when(right.getNeighbors("4")).thenReturn(new HashSet<String>(){{add("3");}});
        when(right.getEdge(eq("1"), any(String.class))).thenReturn(Optional.empty());
        when(right.getEdge(any(String.class), eq("1"))).thenReturn(Optional.empty());
        when(right.getEdge("2", "3")).thenReturn(edge);
        when(right.getEdge("3", "2")).thenReturn(edge);
        when(right.getEdge("3", "4")).thenReturn(edge);
        when(right.getEdge("4", "3")).thenReturn(edge);
    }

    @Test
    public void testSimpleSimilarityCalculator() {
        testGraphs(new GraphSimilarityCalculator<>(0.2));
    }

    private void testGraphs(final ISimilarityCalculator<IFuzzyGraph<String>> calculator) {
        final FuzzyBoolean result = calculator.similar(left, right);
        assertEquals(0.5, result.getValue(), .01);
    }

}
