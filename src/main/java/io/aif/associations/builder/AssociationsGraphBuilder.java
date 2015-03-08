package io.aif.associations.builder;


import edu.uci.ics.jung.graph.Graph;
import io.aif.associations.calculators.edge.PredefinedEdgeWeightCalculator;
import io.aif.associations.calculators.vertex.ConnectionBasedWeightCalculator;
import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.associations.model.IAssociationEdge;
import io.aif.associations.model.IAssociationVertex;
import io.aif.associations.model.IAssociationsGraphBuilder;
import io.aif.associations.model.IDistanceMultiplierIncrementCalculator;

import java.util.List;

public class AssociationsGraphBuilder<T> implements IAssociationsGraphBuilder<T> {
    
    private final ExperimentsConnectionsGraphReducer<T> experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer<>();
    
    private final ExperimentsConnectionsGraphBuilder<T> experimentsConnectionsGraphBuilder;

    public AssociationsGraphBuilder(final IDistanceMultiplierIncrementCalculator<T> distanceMultiplierIncrementCalculator) {
        this.experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>(distanceMultiplierIncrementCalculator);
    }

    public AssociationsGraphBuilder(final IVertexWeightCalculator<T> vertexWeightCalculator) {
        this(IDistanceMultiplierIncrementCalculator.<T>createDefaultCalculator());
    }

    @Override
    public Graph<IAssociationVertex<T>, IAssociationEdge> buildGraph(final List<T> experiments) {
        final Graph<T, List<Double>> experimentsConnectionsGraph = experimentsConnectionsGraphBuilder.build(experiments);
        final Graph<T, Double> reducedExperimentsConnectionsGraph = experimentsConnectionsGraphReducer.reduce(experimentsConnectionsGraph);
        
        final PredefinedEdgeWeightCalculator<T> predefinedEdgeWeightCalculator = new PredefinedEdgeWeightCalculator<>(reducedExperimentsConnectionsGraph);
        
        final IVertexWeightCalculator<T> vertexWeightCalculator = new ConnectionBasedWeightCalculator<>(reducedExperimentsConnectionsGraph, predefinedEdgeWeightCalculator);
        
        final ToResultGraphMapper<T> toResultGraphMapper = new ToResultGraphMapper<>(vertexWeightCalculator);
        
        return toResultGraphMapper.map(reducedExperimentsConnectionsGraph);
    }
    
}
