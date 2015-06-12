package io.aif.graph;

import io.aif.graph.simple.ISimpleGraph;
import io.aif.graph.simple.ISimpleGraphBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class DFSTest {

    @Test
    public void testFindPath() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.connect(1, 2);
        graphBuilder.connect(1, 3);
        graphBuilder.connect(2, 4);
        graphBuilder.connect(3, 4);

        final List<List<Integer>> expected = new ArrayList<List<Integer>>() {{
            add(Arrays.asList(1, 3, 4));
            add(Arrays.asList(1, 2, 4));
        }};
        runAndCompare(graphBuilder, expected, 1, 4);
    }

    @Test
    public void testFindPath2() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.connect(1, 2);
        graphBuilder.connect(1, 3);
        graphBuilder.connect(2, 3);
        graphBuilder.add(4);

        final List<List<Integer>> expected = Collections.emptyList();
        runAndCompare(graphBuilder, expected, 1, 4);
    }

    @Test
    public void testFindPath3() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.add(1);
        graphBuilder.add(2);

        final List<List<Integer>> expected = Collections.emptyList();
        runAndCompare(graphBuilder, expected, 1, 4);
    }

    @Test
    public void testFindPath4() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.connect(1, 3);
        graphBuilder.connect(3, 4);
        graphBuilder.connect(4, 5);
        graphBuilder.connect(5, 2);
        graphBuilder.connect(1, 2);

        final List<List<Integer>> expected = new ArrayList<List<Integer>>() {{
            add(Arrays.asList(1, 2));
            add(Arrays.asList(1, 3, 4, 5, 2));
        }};
        runAndCompare(graphBuilder, expected, 1, 2);
    }

    @Test
    public void testFindPathForCircularTraversal() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.connect(1, 2);
        graphBuilder.connect(1, 3);
        graphBuilder.connect(1, 4);
        graphBuilder.connect(2, 3);
        graphBuilder.connect(2, 4);
        graphBuilder.connect(2, 5);
        graphBuilder.connect(3, 4);
        graphBuilder.connect(3, 5);
        graphBuilder.connect(4, 5);

        final DFS<Integer> traverser =  new DFS<>(graphBuilder.build());
        final List<List<Integer>> actual = traverser.findPath(1, 5);
        assertTrue(actual.size() > 0);
    }

    private void runAndCompare(final ISimpleGraphBuilder<Integer> graphBuilder,
                               final List<List<Integer>> expected,
                               final Integer src,
                               final Integer dst) {
        final ISimpleGraph<Integer> graph = graphBuilder.build();

        final DFS<Integer> traverser =  new DFS<>(graph);
        final List<List<Integer>> actual = traverser.findPath(src, dst);
        actual.forEach(item -> assertTrue(item.toString(), expected.contains(item)));
        assertEquals(expected.size(), actual.size());
    }
}