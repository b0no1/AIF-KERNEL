package io.aif.associations.calculators.edge;


public interface IEdgeWeightCalculator<T> {

    public double calculate(final T from, final T to);
    
}
