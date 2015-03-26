package io.aif.associations.builder;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ExperimentsConnectionsGraphBuilderTest {

    @Test
    public void testBuildWithEmptyExperimentsList() throws Exception {
        final List<Integer> experiments = Collections.emptyList();

        final ExperimentsConnectionsGraphBuilder<Integer> experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>();

        final Map<Integer, Map<Integer, List<Double>>> result = experimentsConnectionsGraphBuilder.build(experiments);

        assertTrue(result.isEmpty());
    }

}