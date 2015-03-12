package io.aif.associations.builder;


import io.aif.associations.graph.AssociationGraph;
import io.aif.associations.model.IAssociationEdge;
import io.aif.associations.model.IAssociationVertex;
import io.aif.associations.model.IDistanceMultiplierIncrementCalculator;
import io.aif.associations.model.IGraph;

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
    
    public Map<T, Map<T, List<Double>>> build(final List<T> experiments) {
        final Map<T, Map<T, List<Double>>> graph = new HashMap<>();

        final int connectAheadNormalized = calculateNormalizedConnectAhead(experiments);

        IntStream.range(0, experiments.size()).forEach(index -> {
            addDataToGraph(graph, experiments, index, connectAheadNormalized);
        });

        return graph;
    }
    
    private void addDataToGraph(final Map<T, Map<T, List<Double>>> graph,
                                final List<T> experiments,
                                final int startPosition,
                                final int connectAhead) {
        final T srcVertex = experiments.get(startPosition);
        double multiplier = 1;
        if (!graph.containsKey(srcVertex)) graph.put(srcVertex, new HashMap<>());
        for (int index = 0; index < connectAhead; index++) {
            final int position = startPosition + index + 1;
            if (position >= experiments.size()) return;
            
            final T destVertex = experiments.get(position);
            if (!graph.get(srcVertex).containsKey(destVertex)) graph.get(srcVertex).put(destVertex, new ArrayList<>());
            
            final double distance = (index + 1) * multiplier;
            final List<Double> edge = graph.get(srcVertex).get(destVertex);
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
