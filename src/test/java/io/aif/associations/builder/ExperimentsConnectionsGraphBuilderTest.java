package io.aif.associations.builder;

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

        final Map<Integer, Map<Integer, Double>> result = experimentsConnectionsGraphBuilder.build(experiments);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testBuildWithOneExperiment() throws Exception {
        final List<Integer> experiments = Arrays.asList(1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, Double>>  result = experimentsConnectionsGraphBuilder.build(experiments);

        final Integer element = result.keySet().iterator().next();

        assertEquals(result.keySet().size(), 1);
        assertTrue(result.get(element).keySet().isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperiments() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, Double>>  result = experimentsConnectionsGraphBuilder.build(experiments);

        final Integer element = result.keySet().iterator().next();

        assertEquals(result.keySet().size(), 1);
        assertTrue(result.get(element).keySet().isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheEnd() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1, 2);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, Double>>  result = experimentsConnectionsGraphBuilder.build(experiments);

        final Integer element1 = result.keySet().stream().filter(i -> i == 1).findFirst().get();
        final Integer element2 = result.keySet().stream().filter(i -> i == 2).findFirst().get();

        assertEquals(result.get(element1).get(element2), (Double)1.0);
        assertEquals(result.get(element1).keySet().size(), 1);
        assertEquals(result.get(element2).keySet().size(), 0);
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheBegining() throws Exception {
        final List<Integer> experiments = Arrays.asList(2, 1, 1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, Double>>  result = experimentsConnectionsGraphBuilder.build(experiments);

        final Integer element1 = result.keySet().stream().filter(i -> i == 1).findFirst().get();
        final Integer element2 = result.keySet().stream().filter(i -> i == 2).findFirst().get();

        assertTrue(result.get(element1).keySet().isEmpty());
        assertEquals(result.get(element2).get(element1), (Double)3.0);
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheBeginingWithNonDefaultMultiplexer() throws Exception {
        final List<Integer> experiments = Arrays.asList(2, 1, 1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>(d -> d == 1 ? 4. : 1.);

        final Map<Integer, Map<Integer, Double>>  result = experimentsConnectionsGraphBuilder.build(experiments);

        final Integer element1 = result.keySet().stream().filter(i -> i == 1).findFirst().get();
        final Integer element2 = result.keySet().stream().filter(i -> i == 2).findFirst().get();

        assertTrue(result.get(element1).keySet().isEmpty());
        assertEquals(result.get(element2).get(element1), (Double)11.0);
    }

}