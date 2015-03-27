package io.aif.associations.builder;


import java.util.*;
import java.util.stream.Collectors;

class ExperimentsConnectionsGraphReducer<T> {
    
    public Map<T, Map<T, Double>> reduce(final Map<T, Map<T, List<Double>>> originalGraph) {
        final Map<T, Map<T, Double>> resultsGraph = convertGraph(originalGraph);

        normalize(resultsGraph);
        
        return resultsGraph;
    }
    
    private void normalize(final Map<T, Map<T, Double>> graph) {
        if (graph.isEmpty()) return;

        final Double maxEdge = graph.entrySet().stream().flatMap(entry -> entry.getValue().entrySet().stream()).mapToDouble(Map.Entry::getValue).max().getAsDouble();
        final Double minEdge = graph.entrySet().stream().flatMap(entry -> entry.getValue().entrySet().stream()).mapToDouble(Map.Entry::getValue).min().getAsDouble();
        final Double newMax = maxEdge - minEdge > 0 ? maxEdge - minEdge : maxEdge;
        graph.keySet().forEach(from ->
            graph.get(from).keySet().forEach(to -> {
                        final Double weight = graph.get(from).get(to);
                        graph.get(from).put(to, ((newMax - (weight - minEdge)) / newMax));
                    }
            )
        );
    }
    
    private Map<T, Map<T, Double>> convertGraph(final Map<T, Map<T, List<Double>>> originalGraph) {
        return originalGraph.entrySet().stream().map(globalEntry ->
                        new HashMap.SimpleEntry<T, Map<T, Double>>(globalEntry.getKey(),
                                convertVertexEdges(globalEntry.getValue())
                        )
        ).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (v1, v2) -> v1
        ));
    }

    private Map<T, Double> convertVertexEdges(final Map<T, List<Double>> vertexEdges) {
        return vertexEdges.entrySet().stream()
                .map(entry ->
                    new HashMap.SimpleEntry<>(entry.getKey(), entry.getValue().stream().mapToDouble(x -> x).sum()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (v1, v2) -> v1
                ));
    }
    
}
