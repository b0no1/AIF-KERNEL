package io.aif.associations.model;


import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface IAssociationsGraphBuilder<T> {
    
    public IGraph<T> buildGraph(final List<T> experiments);
    
}
