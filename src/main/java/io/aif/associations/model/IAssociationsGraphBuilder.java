package io.aif.associations.model;


import io.aif.associations.builder.AssociationGraph;

import java.util.List;

@FunctionalInterface
public interface IAssociationsGraphBuilder<T> {
    
    public AssociationGraph<T> buildGraph(final List<T> experiments);
    
}
