package io.aif.associations.builder;


import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import io.aif.associations.model.IDistanceMultiplierIncrementCalculator;

import java.util.*;
import java.util.stream.IntStream;

class ExperimentsConnectionsGraphBuilder<T> {
    
    private static final int DEFAULT_CONNECT_AHEAD = 5;
    
    private final int connectAhead;

    private final IDistanceMultiplierIncrementCalculator<T> distanceMultiplierIncrementCalculator;

    public ExperimentsConnectionsGraphBuilder(final IDistanceMultiplierIncrementCalculator<T> distanceMultiplierIncrementCalculator,
                                              final int connectAhead) {
        this.distanceMultiplierIncrementCalculator = distanceMultiplierIncrementCalculator;
        if (connectAhead <= 0)
            this.connectAhead = DEFAULT_CONNECT_AHEAD;
        else
            this.connectAhead = connectAhead;
    }

    public ExperimentsConnectionsGraphBuilder(final IDistanceMultiplierIncrementCalculator<T> distanceMultiplierIncrementCalculator) {
        this(distanceMultiplierIncrementCalculator, DEFAULT_CONNECT_AHEAD);
    }


    public ExperimentsConnectionsGraphBuilder() {
        this(IDistanceMultiplierIncrementCalculator.<T>createDefaultCalculator(), 
                DEFAULT_CONNECT_AHEAD);
    }
    
    public Graph<T, List<Double>> build(final List<T> experiments) {
        final Graph<T, List<Double>> graph = new SparseGraph<>();

        final int connectAheadNormalized = calculateNormalizedConnectAhead(experiments);

        IntStream.range(0, experiments.size()).forEach(index -> {
            addDataToGraph(graph, experiments, index, connectAheadNormalized);
        });

        return graph;
    }
    
    private void addDataToGraph(final Graph<T, List<Double>> graph, 
                                final List<T> experiments,
                                final int startPosition,
                                final int connectAhead) {
        final T srcVertex = experiments.get(startPosition);
        double multiplier = 1;
        if (!graph.containsVertex(srcVertex)) graph.addVertex(srcVertex);
        for (int index = 0; index < connectAhead; index++) {
            final int position = startPosition + index + 1;
            if (position >= experiments.size()) return;
            
            final T destVertex = experiments.get(position);
            if (!graph.containsVertex(destVertex)) graph.addVertex(destVertex);
            
            final double distance = (index + 1) * multiplier;
            if (graph.findEdge(srcVertex, destVertex) == null) {
                graph.addEdge(new ArrayList<>(), srcVertex, destVertex);
            }
            final List<Double> edge = graph.findEdge(srcVertex, destVertex);
            edge.add(distance);
            
            multiplier += distanceMultiplierIncrementCalculator.calculateMultiplierIncrement(destVertex);
        } 
    }
    
    private int calculateNormalizedConnectAhead(final Collection<T> experiments) {
        return (connectAhead > experiments.size())
                ? experiments.size()
                : connectAhead;
    }
    
}
