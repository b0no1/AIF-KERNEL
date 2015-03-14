package io.aif.associations.builder;


import io.aif.associations.graph.AssociationGraph;
import io.aif.associations.model.*;

import java.util.List;
import java.util.Map;

public class AssociationsGraphBuilderOld<T> implements IAssociationsGraphBuilder<T> {
    
    private final ExperimentsConnectionsGraphReducer<T> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
    
    private final ExperimentsConnectionsGraphBuilderOld<T> experimentsConnectionsGraphBuilder;

    public AssociationsGraphBuilderOld(final IDistanceMultiplierIncrementCalculator<T> distanceMultiplierIncrementCalculator) {
        this.experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilderOld<>(distanceMultiplierIncrementCalculator);
    }

    public AssociationsGraphBuilderOld() {
        this(IDistanceMultiplierIncrementCalculator.<T>createDefaultCalculator());
    }

    @Override
    public IGraph<IAssociationVertex<T>, IAssociationEdge> buildGraph(final List<T> experiments) {
        final Map<T, Map<T, List<Double>>> experimentsConnectionsGraph = experimentsConnectionsGraphBuilder.build(experiments);
        final Map<T, Map<T, Double>> reducedExperimentsConnectionsGraph = experimentsConnectionsGraphReducer.reduce(experimentsConnectionsGraph);
        
        return AssociationGraph.generateGraph(reducedExperimentsConnectionsGraph);
    }
    
}
