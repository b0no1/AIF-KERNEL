package io.aif.associations.model;


import edu.uci.ics.jung.graph.Graph;

import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface IAssociationsGraphBuilder<T> {
    
    public Graph<IAssociationVertex<T>, IAssociationEdge> buildGraph(final List<T> experiments);
    
}
