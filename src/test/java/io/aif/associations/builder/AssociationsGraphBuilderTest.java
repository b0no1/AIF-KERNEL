package io.aif.associations.builder;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AssociationsGraphBuilderTest {

    @Test
    public void testBuildWithEmptyExperimentsList() throws Exception {
        final List<Integer> experiments = Collections.emptyList();

        final AssociationsGraphBuilder<Integer> associationsGraphBuilder = new AssociationsGraphBuilder<>();

        final AssociationGraph<Integer> result = associationsGraphBuilder.buildGraph(experiments);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testBuildWithOneExperiment() throws Exception {
        final List<Integer> experiments = Arrays.asList(1);

        final AssociationsGraphBuilder<Integer> associationsGraphBuilder = new AssociationsGraphBuilder<>();

        final AssociationGraph<Integer> result = associationsGraphBuilder.buildGraph(experiments);

        assertEquals(result.getVertices().size(), 1);
        assertEquals(result.getVertices().iterator().next(), (Integer)1);
        assertTrue(result.getNeighbors(result.getVertices().iterator().next()).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperiments() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1);

        final AssociationsGraphBuilder<Integer> associationsGraphBuilder = new AssociationsGraphBuilder<>();

        final AssociationGraph<Integer> result = associationsGraphBuilder.buildGraph(experiments);

        assertEquals(result.getVertices().size(), 1);
        assertEquals(result.getVertices().iterator().next(), (Integer) 1);
        assertTrue(result.getNeighbors(result.getVertices().iterator().next()).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheEnd() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1, 2);

        final AssociationsGraphBuilder<Integer> associationsGraphBuilder = new AssociationsGraphBuilder<>();

        final AssociationGraph<Integer> result = associationsGraphBuilder.buildGraph(experiments);

        assertEquals(result.getVertices().size(), 2);

        final List<Integer> iterator = new ArrayList<>(result.getVertices());
        final Integer firstVertex = iterator.get(0) == 1 ? iterator.get(0): iterator.get(1);
        final Integer secondVertex = iterator.get(0) == 1 ? iterator.get(1): iterator.get(0);

        assertTrue(result.getEdge(firstVertex, secondVertex).isPresent());
        assertFalse(result.getEdge(secondVertex, firstVertex).isPresent());
        assertEquals((Double) result.getEdge(firstVertex, secondVertex).get().getValue(), (Double) 1.);

    }

}