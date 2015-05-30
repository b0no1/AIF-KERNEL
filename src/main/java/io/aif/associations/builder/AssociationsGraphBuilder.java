package io.aif.associations.builder;


import io.aif.associations.calculators.vertex.CompositeWeightCalculator;
import io.aif.associations.calculators.vertex.ConnectionBasedWeightCalculator;
import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.associations.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public AssociationGraph<T> buildGraph(final List<T> experiments) {

        final Map<T, Map<T, Double>> experimentsConnectionsGraph = experimentsConnectionsGraphBuilder.build(experiments);
        final Map<T, Long> count = experimentsConnectionsGraph.keySet().stream().collect(Collectors.toMap(
            k -> k, v -> experiments.stream().filter(v::equals).count()
        ));

        final CompositeWeightCalculator<T> compositeWeightCalculator = new CompositeWeightCalculator<>(calculators);
        compositeWeightCalculator.add(new ConnectionBasedWeightCalculator<>(), 1.);

        return AssociationGraph.generateGraph(experimentsConnectionsGraph, count, compositeWeightCalculator);
    }
    
}
