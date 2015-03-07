package io.aif.associations.builder;


import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

class ExperimentsConnectionsGraphReducer<T> {
    
    public Graph<T, Double> reduce(final Graph<T, List<Double>> originalGraph) {
        final Graph<T, Double> resultsGraph = new SparseGraph<>();

        originalGraph.getVertices().stream().forEach(vertex -> {
            
        });
        
        distancesGraph.keySet().stream().forEach(key -> {
            results.put(key, new HashMap<>());
            distancesGraph
                    .get(key)
                    .keySet()
                    .forEach(innerKey -> {
                        results.get(key).put(innerKey, distancesGraph.get(key).get(innerKey).stream().mapToDouble(x -> x).sum());
                    });
        });
        final Double maxConnection = results.keySet().stream().mapToDouble(key -> {
            final OptionalDouble optMax = results.get(key).keySet().stream().mapToDouble(results.get(key)::get).max();
            if (optMax.isPresent()) return optMax.getAsDouble();
            return .0;
        }).max().getAsDouble();

        results.keySet().forEach(key -> {
            results.get(key).keySet().forEach(innerKey -> {
                results.get(key).put(innerKey, results.get(key).get(innerKey) / maxConnection);
            });
        });

        return results;
        
    }
    
}
