package io.aif.associations.calculators.vertex;


import io.aif.associations.graph.INodeWithCount;
import io.aif.associations.model.IGraph;

import java.util.Map;
import java.util.stream.Collectors;

public class CompositeWeightCalculator<T> implements IVertexWeightCalculator<T> {

    private final Map<IVertexWeightCalculator<T>, Double> calculators;

    public CompositeWeightCalculator(final Map<IVertexWeightCalculator<T>, Double> calculators) {
        this.calculators = calculators;
    }

    public void add(final IVertexWeightCalculator<T> calculator, final Double weight) {
        calculators.put(calculator, weight);
    }

    @Override
    public Map<T, Double> calculate(final IGraph<INodeWithCount<T>, Double> vertexes) {
        final Map<T, Double> results = calculators.keySet().stream().map(calculator -> {
            final Map<T, Double> result = calculator.calculate(vertexes);
            return result.keySet()
                    .stream()
                    .collect(Collectors.toMap(k -> k, k -> result.get(k) * calculators.get(calculator)));
        }).flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(k -> k.getKey(), k -> k.getValue(), (k1, k2) -> k1 + k2));
        return results.keySet().stream().collect(Collectors.toMap(k -> k, k -> results.get(k) / calculators.size()));
    }

}
