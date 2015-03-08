package io.aif.associations.builder;


import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import io.aif.associations.model.IAssociationEdge;
import io.aif.associations.model.IAssociationVertex;

import java.awt.geom.Arc2D;

public class ToResultGraphMapper {
    
    public static <T>Graph<IAssociationVertex<T>, IAssociationEdge> map(final Graph<T, Double> connections) {
        final Graph<IAssociationVertex<T>, IAssociationEdge> resultGraph = new SparseGraph<>();
        connections.getVertices().forEach(vertex -> {
            // TODO
        });
    }
    
}
