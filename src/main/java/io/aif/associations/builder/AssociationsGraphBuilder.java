package io.aif.associations.builder;


import io.aif.associations.calculators.vertex.CompositeWeightCalculator;
import io.aif.associations.calculators.vertex.ConnectionBasedWeightCalculator;
import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.associations.graph.AssociationGraph;
import io.aif.associations.graph.INodeWithCount;
import io.aif.associations.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssociationsGraphBuilder<T> implements IAssociationsGraphBuilder<T> {
    
    private final ExperimentsConnectionsGraphBuilder<T> experimentsConnectionsGraphBuilder;

    private final Map<IVertexWeightCalculator<T>, Double> calculators;

    public AssociationsGraphBuilder(final IDistanceMultiplierIncrementCalculator<T> distanceMultiplierIncrementCalculator,
                                    final Map<IVertexWeightCalculator<T>, Double> calculators) {
        this.experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder<>(distanceMultiplierIncrementCalculator);
        this.calculators = calculators;
    }

    public AssociationsGraphBuilder(final Map<IVertexWeightCalculator<T>, Double> calculators) {
        this(IDistanceMultiplierIncrementCalculator.<T>createDefaultCalculator(), calculators);
    }

    public AssociationsGraphBuilder() {
        this(IDistanceMultiplierIncrementCalculator.<T>createDefaultCalculator(), new HashMap<>());
    }

    @Override
    public IGraph<IAssociationVertex<T>, IAssociationEdge> buildGraph(final List<T> experiments) {

        final IGraph<INodeWithCount<T>, Double> experimentsConnectionsGraph = experimentsConnectionsGraphBuilder.build(experiments);

        final CompositeWeightCalculator<T> compositeWeightCalculator = new CompositeWeightCalculator<>(calculators);
        compositeWeightCalculator.add(new ConnectionBasedWeightCalculator<>(), 1.);

        return AssociationGraph.generateGraph(experimentsConnectionsGraph, compositeWeightCalculator);
    }
    
}
