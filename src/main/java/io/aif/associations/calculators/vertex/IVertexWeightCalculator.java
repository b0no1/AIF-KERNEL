package io.aif.associations.calculators.vertex;


import io.aif.associations.graph.INodeWithCount;
import io.aif.associations.model.IGraph;

import java.util.Map;

public interface IVertexWeightCalculator<T> {
    
    public Map<T, Double> calculate(final IGraph<INodeWithCount<T>, Double> vertex);
    
}
