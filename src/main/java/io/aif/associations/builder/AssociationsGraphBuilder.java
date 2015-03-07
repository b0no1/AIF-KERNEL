package io.aif.associations.builder;


import edu.uci.ics.jung.graph.Graph;
import io.aif.associations.model.IAssociationEdge;
import io.aif.associations.model.IAssociationVertex;
import io.aif.associations.model.IAssociationsGraphBuilder;
import io.aif.associations.model.IDistanceMultiplierIncrementCalculator;

import java.util.List;

public class AssociationsGraphBuilder<T> implements IAssociationsGraphBuilder<T> {
    
    private final ExperimentsConnectionsGraphBuilder<T> experimentsConnectionsGraphBuilder;

    public AssociationsGraphBuilder(final IDistanceMultiplierIncrementCalculator<T> distanceMultiplierIncrementCalculator) {
        this.experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>(distanceMultiplierIncrementCalculator);
    }

    public AssociationsGraphBuilder() {
        this(IDistanceMultiplierIncrementCalculator.<T>createDefaultCalculator());
    }

    @Override
    public Graph<IAssociationVertex<T>, IAssociationEdge> buildGraph(final List<T> experiments) {
        final Graph<T, List<Double>> experimentsConnectionsGraph = experimentsConnectionsGraphBuilder.build(experiments);
        
        return null;
    }
    
}
