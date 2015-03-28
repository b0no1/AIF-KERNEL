package io.aif.associations.calculators.vertex;


import java.util.Map;

public class CompositeWeightCalculator<T> implements IVertexWeightCalculator<T> {

    private final Map<IVertexWeightCalculator<T>, Double> calculators;

    public CompositeWeightCalculator(final Map<IVertexWeightCalculator<T>, Double> calculators) {
        this.calculators = calculators;
    }

    @Override
    public double calculate(final T vertex) {
        return calculators.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().calculate(vertex) * entry.getValue())
                .average()
                .getAsDouble();
    }

}
