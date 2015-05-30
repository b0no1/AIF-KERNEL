package io.aif.graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class GraphTest {

    @Test
    public void testConnectAndGetNeighbours() throws Exception {
        final Object vertex1 = mock(Object.class);
        final Object vertex2 = mock(Object.class);
        final Object vertex3 = mock(Object.class);

        final Set<Object> expected = new HashSet<>(Arrays.asList(vertex2, vertex3));

        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        graphBuilder.connect(vertex1, vertex2, true);
        graphBuilder.connect(vertex1, vertex3, true);
        final IGraph<Object, Boolean> graph = graphBuilder.build();

        final Set<Object> actual = graph.getNeighbors(vertex1);

        assertEquals(actual, expected);
    }

    @Test
    public void testConnectToNull() throws Exception {
        final Object vertex1 = mock(Object.class);

        final Set<Object> expected = new HashSet<>();
        expected.add(null);

        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        graphBuilder.connect(vertex1, null, true);
        Set<Object> actual = graphBuilder.build().getNeighbors(vertex1);

        assertEquals(actual, expected);
    }

    @Test
    public void testAdd() throws Exception {
        final Object vertex1 = mock(Object.class);

        final Set<Object> expected = new HashSet<>(Arrays.asList(vertex1));

        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        graphBuilder.add(vertex1);
        final Set<Object> actual = graphBuilder.build().getVertices();

        assertEquals(actual, expected);
    }

    @Test
    public void testAddNull() throws Exception {
        Set<Object> expected = new HashSet<>();
        expected.add(null);

        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        graphBuilder.add(null);
        final Set<Object> actual = graphBuilder.build().getVertices();

        assertEquals(actual, expected);
    }

    @Test
    public void testGetNeighboursEmptyGraph() throws Exception {
        final Object vertex1 = mock(Object.class);
        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        final IGraph<Object, Boolean> graph = graphBuilder.build();
        try {
            graph.getNeighbors(vertex1);
            fail();
        } catch (final VertexNotFoundException e) {}
    }

    @Test
    public void testGetNeighboursForNonExistentVertex() throws Exception {
        final Object vertex1 = mock(Object.class);
        final Object vertex2 = mock(Object.class);
        final Object vertex3 = mock(Object.class);
        final Object vertex4 = mock(Object.class);

        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        graphBuilder.connect(vertex1, vertex2, true);
        graphBuilder.connect(vertex1, vertex3, true);
        final IGraph<Object, Boolean> graph = graphBuilder.build();
        try {
            graph.getNeighbors(vertex4);
            fail();
        } catch (final VertexNotFoundException e) {}
    }

    @Test
    public void testGetNeighboursForVertexWithoutConnections() throws Exception {
        final Object vertex1 = mock(Object.class);
        final Object vertex2 = mock(Object.class);
        final Object vertex3 = mock(Object.class);
        final Object vertex4 = mock(Object.class);

        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        graphBuilder.connect(vertex1, vertex2, true);
        graphBuilder.connect(vertex1, vertex3, true);
        graphBuilder.add(vertex4);

        assertTrue(graphBuilder.build().getNeighbors(vertex4).isEmpty());
    }

    @Test
    public void testGetAll() throws Exception {
        final Object vertex1 = mock(Object.class);
        final Object vertex2 = mock(Object.class);
        final Object vertex3 = mock(Object.class);
        final Object vertex4 = mock(Object.class);

        final Set<Object> expected = new HashSet<>(Arrays.asList(vertex1, vertex2, vertex3, vertex4));

        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        graphBuilder.connect(vertex1, vertex2, true);
        graphBuilder.connect(vertex1, vertex3, true);
        graphBuilder.add(vertex4);
        Set<Object> actual = graphBuilder.build().getVertices();

        assertEquals(actual, expected);
    }

    @Test
    public void testGetAllEmptyGraph() throws Exception {
        final IGraphBuilder<Object, Boolean> graphBuilder = IGraphBuilder.defaultBuilder();
        assertEquals(graphBuilder.build().isEmpty(), true);
    }
    
}