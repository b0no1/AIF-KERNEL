package io.aif.associations.calculators.vertex;


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
    public Map<T, Double> calculate(final Map<T, Map<T, Double>>  graph, final Map<T, Long> count) {
        final Map<T, Double> results = calculators.keySet().stream().map(calculator -> {
            final Map<T, Double> result = calculator.calculate(graph, count);
            return result.keySet()
                    .stream()
                    .collect(Collectors.toMap(k -> k, k -> result.get(k) * calculators.get(calculator)));
        }).flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(k -> k.getKey(), k -> k.getValue(), (k1, k2) -> k1 + k2));
        return results.keySet().stream().collect(Collectors.toMap(k -> k, k -> results.get(k) / calculators.size()));
    }

}
