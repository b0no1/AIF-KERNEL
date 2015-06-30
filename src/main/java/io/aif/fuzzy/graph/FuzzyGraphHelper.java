package io.aif.fuzzy.graph;


import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.graph.normal.IGraph;
import javaslang.Tuple2;
import javaslang.control.Try;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

abstract public class FuzzyGraphHelper {

    /**
     * return the probability that any random vertex from the graph is connected to the vertx
     * @param vertex
     * @return
     */
    @Max(value = 1)
    @Min(value = 0)
    public static <T>FuzzyBoolean connectedTo(final IGraph<T, FuzzyBoolean> graph, final T vertex) {
        final List<T> vertices = graph
                .getVertices()
                .stream()
                .filter(v -> !v.equals(vertex))
                .map(v -> new Tuple2<>(v, Try.of(() -> graph.getEdge(v, vertex)).get()))
                .filter(t -> t._2.isPresent())
                .map(t -> t.map((_1) -> _1, _2 -> _2.get()))
                .filter(t -> t._2.isTrue())
                .map(t -> t._1)
                .collect(Collectors.toList());

        final OptionalDouble optionalAverage = vertices
                .stream()
                .mapToDouble(v -> Try.of(() -> graph.getEdge(v, vertex)).get().get().getThreshold())
                .average();

        if (!optionalAverage.isPresent()) return FuzzyBoolean.FALSE;

        final double probabilityThatVertexHasConnection = (double)vertices.size() / (double)graph.getVertices().size();
        return new FuzzyBoolean(probabilityThatVertexHasConnection * optionalAverage.getAsDouble());
    }

}
