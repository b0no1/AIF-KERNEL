package io.aif.associations.builder;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExperimentsConnectionsGraphBuilderTest {

    @Test
    public void testBuildWithEmptyExperimentsList() throws Exception {
        final List<Integer> experiments = Collections.emptyList();

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, List<Double>>> result = experimentsConnectionsGraphBuilder.build(experiments);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testBuildWithOneExperiment() throws Exception {
        final List<Integer> experiments = Arrays.asList(1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, List<Double>>> result = experimentsConnectionsGraphBuilder.build(experiments);

        assertTrue(result.containsKey(1));
        assertTrue(result.get(1).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperiments() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, List<Double>>> result = experimentsConnectionsGraphBuilder.build(experiments);

        assertTrue(result.containsKey(1));
        assertTrue(result.get(1).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheEnd() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1, 2);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, List<Double>>> result = experimentsConnectionsGraphBuilder.build(experiments);

        assertTrue(result.containsKey(1));
        assertTrue(result.containsKey(2));

        assertTrue(result.get(1).containsKey(2));
        assertEquals(1, result.get(1).get(2).size());
        assertTrue(result.get(1).get(2).contains(1.));

        assertTrue(result.get(2).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheBegining() throws Exception {
        final List<Integer> experiments = Arrays.asList(2, 1, 1);

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, List<Double>>> result = experimentsConnectionsGraphBuilder.build(experiments);

        assertTrue(result.containsKey(1));
        assertTrue(result.containsKey(2));

        assertTrue(result.get(1).isEmpty());

        assertEquals(2, result.get(2).get(1).size());
        assertTrue(result.get(2).get(1).contains(1.));
        System.out.println(result.get(2).get(1));
        assertTrue(result.get(2).get(1).contains(2.));
    }

}