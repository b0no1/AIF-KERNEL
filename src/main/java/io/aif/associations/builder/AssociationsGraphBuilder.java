package io.aif.associations.builder;


import io.aif.associations.calculators.edge.PredefinedEdgeWeightCalculator;
import io.aif.associations.calculators.vertex.CompositeWeightCalculator;
import io.aif.associations.calculators.vertex.ConnectionBasedWeightCalculator;
import io.aif.associations.graph.AssociationGraph;
import io.aif.associations.model.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AssociationsGraphBuilder<T> implements IAssociationsGraphBuilder<T> {
    
    private final ExperimentsConnectionsGraphReducer<T> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
    
    private final ExperimentsConnectionsGraphBuilder<T> experimentsConnectionsGraphBuilder;

    private final CompositeWeightCalculator<T> compositeWeightCalculator;

    public AssociationsGraphBuilder(final IDistanceMultiplierIncrementCalculator<T> distanceMultiplierIncrementCalculator,
                                    final CompositeWeightCalculator<T> compositeWeightCalculator) {
        this.experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>(distanceMultiplierIncrementCalculator);
        this.compositeWeightCalculator = compositeWeightCalculator;
    }

    public AssociationsGraphBuilder(final CompositeWeightCalculator<T> compositeWeightCalculator) {
        this(IDistanceMultiplierIncrementCalculator.<T>createDefaultCalculator(), compositeWeightCalculator);
    }

    @Override
    public IGraph<IAssociationVertex<T>, IAssociationEdge> buildGraph(final List<T> experiments) {

        final Map<T, Map<T, List<Double>>> experimentsConnectionsGraph = experimentsConnectionsGraphBuilder.build(experiments);
        final Map<T, Map<T, Double>> reducedExperimentsConnectionsGraph = experimentsConnectionsGraphReducer.reduce(experimentsConnectionsGraph);

        final CompositeWeightCalculator<T> compositeWeightCalculator = new CompositeWeightCalculator<>(this.compositeWeightCalculator);
        compositeWeightCalculator.add(new ConnectionBasedWeightCalculator<>(reducedExperimentsConnectionsGraph, new PredefinedEdgeWeightCalculator<>(reducedExperimentsConnectionsGraph)), 1.);

        return AssociationGraph.generateGraph(reducedExperimentsConnectionsGraph, compositeWeightCalculator);
    }
    
}
