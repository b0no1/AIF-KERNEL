package io.aif.associations.calculators.vertex;


import java.util.Map;

public interface IVertexWeightCalculator<T> {
    
    public Map<T, Double> calculate(final Map<T, Map<T, Double>> vertex, final Map<T, Long> count);
    
}
