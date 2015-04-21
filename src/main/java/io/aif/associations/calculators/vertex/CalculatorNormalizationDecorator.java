package io.aif.associations.calculators.vertex;

import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class CalculatorNormalizationDecorator<T>  implements IVertexWeightCalculator<T> {

    private final IVertexWeightCalculator<T> vertexWeightCalculator;

    public CalculatorNormalizationDecorator(final IVertexWeightCalculator<T> vertexWeightCalculator) {
        this.vertexWeightCalculator = vertexWeightCalculator;
    }

    @Override
    public Map<T, Double> calculate(final Map<T, Map<T, Double>>  graph, final Map<T, Long> count) {
        final Map<T, Double> result = vertexWeightCalculator.calculate(graph, count);
        final OptionalDouble optMax = result.keySet().stream().mapToDouble(result::get).max();
        if (!optMax.isPresent()) return result;
        return result.entrySet()
                .stream()
                .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue() / optMax.getAsDouble()));
    }
}
