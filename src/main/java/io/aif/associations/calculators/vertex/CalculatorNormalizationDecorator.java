package io.aif.associations.calculators.vertex;


import io.aif.associations.graph.INodeWithCount;
import io.aif.associations.model.IGraph;

import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class CalculatorNormalizationDecorator<T>  implements IVertexWeightCalculator<T> {

    private final IVertexWeightCalculator<T> vertexWeightCalculator;

    public CalculatorNormalizationDecorator(final IVertexWeightCalculator<T> vertexWeightCalculator) {
        this.vertexWeightCalculator = vertexWeightCalculator;
    }

    @Override
    public Map<T, Double> calculate(final IGraph<INodeWithCount<T>, Double> vertex) {
        final Map<T, Double> result = vertexWeightCalculator.calculate(vertex);
        final OptionalDouble optMax = result.keySet().stream().mapToDouble(result::get).max();
        if (!optMax.isPresent()) return result;
        return result.entrySet()
                .stream()
                .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue() / optMax.getAsDouble()));
    }
}
