package io.aif.associations.builder;


import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

import java.util.*;

class ExperimentsConnectionsGraphReducer<T> {
    
    public Graph<T, Double> reduce(final Graph<T, List<Double>> originalGraph) {
        final Graph<T, Double> resultsGraph = new SparseGraph<>();

        originalGraph.getVertices().stream().forEach(vertex -> convertVertex(vertex, originalGraph, resultsGraph));
        normalize(resultsGraph);
        
        return resultsGraph;
    }
    
    private void normalize(final Graph<T, Double> graph) {
        final Double maxEdge = graph.getEdges().stream().max(Double::compare).get();
        graph.getVertices().forEach(vertex -> {
            graph.getNeighbors(vertex).forEach(neighbor -> {
                final Double edge = graph.findEdge(vertex, neighbor);
                graph.removeEdge(edge);
                graph.addEdge(edge / maxEdge, vertex, neighbor);
            });
        });
    }
    
    private void convertVertex(final T vertex, final Graph<T, List<Double>> srcGraph, final Graph<T, Double> dstGraph) {
        if (!dstGraph.containsVertex(vertex)) dstGraph.addVertex(vertex);        
        
        srcGraph.getNeighbors(vertex).forEach(neighbor -> {
            if (!dstGraph.containsVertex(neighbor)) dstGraph.addVertex(neighbor);
            final Double edge = srcGraph.findEdge(vertex, neighbor).stream().mapToDouble(x -> x).sum();
            dstGraph.addEdge(edge, vertex, neighbor);
        });
    }
    
}
