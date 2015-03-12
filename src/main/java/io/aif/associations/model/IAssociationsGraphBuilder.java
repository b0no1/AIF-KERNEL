package io.aif.associations.model;


import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface IAssociationsGraphBuilder<T> {
    
    public IGraph<IAssociationVertex<T>, IAssociationEdge> buildGraph(final List<T> experiments);
    
}
