package io.aif.associations.builder;


import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.associations.model.IAssociationEdge;
import io.aif.associations.model.IAssociationVertex;

import java.awt.geom.Arc2D;
import java.util.HashMap;
import java.util.Map;

class ToResultGraphMapper<T> {
    
    private final IVertexWeightCalculator<T> vertexWeightCalculator;

    public ToResultGraphMapper(final IVertexWeightCalculator<T> vertexWeightCalculator) {
        this.vertexWeightCalculator = vertexWeightCalculator;
    }

    public Graph<IAssociationVertex<T>, IAssociationEdge> map(final Graph<T, Double> connections) {
        final Graph<IAssociationVertex<T>, IAssociationEdge> resultGraph = new SparseGraph<>();
        final Map<T, IAssociationVertex<T>> oldToNewVertexMapping = new HashMap<>();
        connections.getVertices().forEach(vertex -> {
            oldToNewVertexMapping.put(vertex, new AssociationVertex<>(vertex, vertexWeightCalculator.calculate(vertex)));
        });
        connections.getVertices().forEach(vertex -> {
            mapVertexConnections(oldToNewVertexMapping.get(vertex), oldToNewVertexMapping, resultGraph, connections);
        });
        return resultGraph;
    }
    
    private void mapVertexConnections(final IAssociationVertex<T> current, 
                                      final Map<T, IAssociationVertex<T>> oldToNewVertexMapping, 
                                      final Graph<IAssociationVertex<T>, IAssociationEdge> resultGraph,
                                      final Graph<T, Double> connections) {
        connections.getNeighbors(current.item()).forEach(neighbor -> {
            final IAssociationVertex<T> newNeighbor = oldToNewVertexMapping.get(neighbor);
            resultGraph.addEdge(new AssociationEdge(connections.findEdge(current.item(), neighbor)), current, newNeighbor);
        }); 
    }
    
}
