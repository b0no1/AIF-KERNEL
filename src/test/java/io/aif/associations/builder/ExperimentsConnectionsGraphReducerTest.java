package io.aif.associations.builder;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class ExperimentsConnectionsGraphReducerTest {

    @Test
    public void testReduceQithEmptyMap() throws Exception {
        final ExperimentsConnectionsGraphReducer<Integer> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
        final Map<Integer, Map<Integer, Double>> actualResult = experimentsConnectionsGraphReducer.reduce(Collections.emptyMap());

        assertTrue(actualResult.isEmpty());
    }

}