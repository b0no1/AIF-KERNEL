package io.aif.associations.model;


@FunctionalInterface
public interface IDistanceMultiplierIncrementCalculator<T> {
    
    public double calculateMultiplierIncrement(final T vertex);
    
    public static <T>IDistanceMultiplierIncrementCalculator<T> createDefaultCalculator() {
        return vertex -> 0;
    }
    
}
