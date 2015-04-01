package io.aif.associations.builder;

import io.aif.associations.model.IAssociationEdge;
import io.aif.associations.model.IAssociationVertex;
import io.aif.associations.model.IGraph;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AssociationsGraphBuilderTest {

    @Test
    public void testBuildWithEmptyExperimentsList() throws Exception {
        final List<Integer> experiments = Collections.emptyList();

        final AssociationsGraphBuilder<Integer> associationsGraphBuilder = new AssociationsGraphBuilder<>(null);

        final IGraph<IAssociationVertex<Integer>, IAssociationEdge> result = associationsGraphBuilder.buildGraph(experiments);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testBuildWithOneExperiment() throws Exception {
        final List<Integer> experiments = Arrays.asList(1);

        final AssociationsGraphBuilder<Integer> associationsGraphBuilder = new AssociationsGraphBuilder<>(null);

        final IGraph<IAssociationVertex<Integer>, IAssociationEdge> result = associationsGraphBuilder.buildGraph(experiments);

        assertEquals(result.getVertex().size(), 1);
        assertEquals(result.getVertex().iterator().next().item(), (Integer)1);
        assertTrue(result.getNeighbors(result.getVertex().iterator().next()).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperiments() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1);

        final AssociationsGraphBuilder<Integer> associationsGraphBuilder = new AssociationsGraphBuilder<>(null);

        final IGraph<IAssociationVertex<Integer>, IAssociationEdge> result = associationsGraphBuilder.buildGraph(experiments);

        assertEquals(result.getVertex().size(), 1);
        assertEquals(result.getVertex().iterator().next().item(), (Integer) 1);
        assertTrue(result.getNeighbors(result.getVertex().iterator().next()).isEmpty());
    }

    @Test
    public void testBuildWithTwoSameExperimentsAmdOneNotSameAtTheEnd() throws Exception {
        final List<Integer> experiments = Arrays.asList(1, 1, 2);

        final AssociationsGraphBuilder<Integer> associationsGraphBuilder = new AssociationsGraphBuilder<>(null);

        final IGraph<IAssociationVertex<Integer>, IAssociationEdge> result = associationsGraphBuilder.buildGraph(experiments);

        assertEquals(result.getVertex().size(), 2);

        final List<IAssociationVertex<Integer>> iterator = new ArrayList<>(result.getVertex());
        final IAssociationVertex<Integer> firstVertex = iterator.get(0).item() == 1 ? iterator.get(0): iterator.get(1);
        final IAssociationVertex<Integer> secondVertex = iterator.get(0).item() == 1 ? iterator.get(1): iterator.get(0);

        assertTrue(result.getEdge(firstVertex, secondVertex).isPresent());
        assertFalse(result.getEdge(secondVertex, firstVertex).isPresent());
        assertEquals((Double) result.getEdge(firstVertex, secondVertex).get().weight(), (Double) 1.);

    }

}