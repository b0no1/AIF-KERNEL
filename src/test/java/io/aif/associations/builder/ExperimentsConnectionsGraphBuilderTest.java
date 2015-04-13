package io.aif.associations.builder;

import io.aif.associations.graph.INodeWithCount;
import io.aif.associations.model.IGraph;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExperimentsConnectionsGraphBuilderTest {

    @Test
    public void testBuildWithEmptyExperimentsList() throws Exception {
        final List<Integer> experiments = Collections.emptyList();

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final IGraph<INodeWithCount<Integer>, Double> result = experimentsConnectionsGraphBuilder.build(experiments);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testBuildWithOneExperiment() throws Exception {
        final List<Integer> experiments = Arrays.asList(1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final IGraph<INodeWithCount<Integer>, Double> result = experimentsConnectionsGraphBuilder.build(experiments);

        final INodeWithCount<Integer> element = result.getVertex().iterator().next();

        assertEquals(result.getVertex().size(), 1);
        assertTrue(result.getNeighbors(element).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperiments() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final IGraph<INodeWithCount<Integer>, Double> result = experimentsConnectionsGraphBuilder.build(experiments);

        final INodeWithCount<Integer> element = result.getVertex().iterator().next();

        assertEquals(result.getVertex().size(), 1);
        assertTrue(result.getNeighbors(element).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheEnd() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1, 2);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final IGraph<INodeWithCount<Integer>, Double> result = experimentsConnectionsGraphBuilder.build(experiments);

        final INodeWithCount<Integer> element1 = result.getVertex().stream().filter(i -> i.item() == 1).findFirst().get();
        final INodeWithCount<Integer> element2 = result.getVertex().stream().filter(i -> i.item() == 2).findFirst().get();

        assertEquals(result.getEdge(element1, element2), Optional.of(1.0));
        assertEquals(result.getNeighbors(element1).size(), 1);
        assertEquals(result.getNeighbors(element2).size(), 0);
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheBegining() throws Exception {
        final List<Integer> experiments = Arrays.asList(2, 1, 1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final IGraph<INodeWithCount<Integer>, Double> result = experimentsConnectionsGraphBuilder.build(experiments);

        final INodeWithCount<Integer> element1 = result.getVertex().stream().filter(i -> i.item() == 1).findFirst().get();
        final INodeWithCount<Integer> element2 = result.getVertex().stream().filter(i -> i.item() == 2).findFirst().get();

        assertTrue(result.getNeighbors(element1).isEmpty());
        assertEquals(result.getEdge(element2, element1), Optional.of(3.0));
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheBeginingWithNonDefaultMultiplexer() throws Exception {
        final List<Integer> experiments = Arrays.asList(2, 1, 1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>(d -> d == 1 ? 4. : 1.);

        final IGraph<INodeWithCount<Integer>, Double> result = experimentsConnectionsGraphBuilder.build(experiments);

        final INodeWithCount<Integer> element1 = result.getVertex().stream().filter(i -> i.item() == 1).findFirst().get();
        final INodeWithCount<Integer> element2 = result.getVertex().stream().filter(i -> i.item() == 2).findFirst().get();

        assertTrue(result.getNeighbors(element1).isEmpty());
        assertEquals(result.getEdge(element2, element1), Optional.of(11.0));
    }

}