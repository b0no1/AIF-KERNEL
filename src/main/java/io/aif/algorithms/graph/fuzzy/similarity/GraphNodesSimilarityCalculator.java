package io.aif.algorithms.graph.fuzzy.similarity;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.graph.IFuzzyGraph;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class GraphNodesSimilarityCalculator<T> implements ISimilarityCalculator<T> {

    private final IFuzzyGraph<T> graph;

    private final double similarityThreshold;

    public GraphNodesSimilarityCalculator(
            final IFuzzyGraph<T> graph,
            double similarityThreshold) {
        this.graph = graph;
        this.similarityThreshold = similarityThreshold;
    }

    @Override
    public FuzzyBoolean similar(final T left, final T right) {
        if (graph.getNeighbors(left).size() > graph.getNeighbors(right).size()) {
            return similar(right, left);
        }
        final List<FuzzyBoolean> resutls = graph
                .getNeighbors(left)
                .stream()
                .map(v -> similarEdge(left, right, v))
                .collect(Collectors.toList());

        final OptionalDouble result = resutls
                .stream()
                .filter(FuzzyBoolean::isTrue)
                .distinct()
                .mapToDouble(FuzzyBoolean::getValue)
                .average();
        if (result.isPresent()) {
            return new FuzzyBoolean(result.getAsDouble(), similarityThreshold);
        }
        return new FuzzyBoolean(0, similarityThreshold);
    }

    private FuzzyBoolean similarEdge(final T from1, final T from2, final T to) {
        final Optional<FuzzyBoolean> leftEdge = graph.getEdge(from1, to);
        final Optional<FuzzyBoolean> rightEdge = graph.getEdge(from2, to);
        if (leftEdge.isPresent() && !rightEdge.isPresent()) {
            return new FuzzyBoolean(.0, similarityThreshold);
        }
        if (leftEdge.get().isTrue() && !rightEdge.get().isTrue()) {
            return new FuzzyBoolean(.0, similarityThreshold);
        }
        if (!leftEdge.get().isTrue() && rightEdge.get().isTrue()) {
            return new FuzzyBoolean(.0, similarityThreshold);
        }
        if (!leftEdge.get().isTrue() && !rightEdge.get().isTrue()) {
            return new FuzzyBoolean(1., similarityThreshold);
        }
        final double similarity = Math.min(leftEdge.get().getValue(), rightEdge.get().getValue()) /
                Math.max(leftEdge.get().getValue(), rightEdge.get().getValue());
        return new FuzzyBoolean(similarity, similarityThreshold);
    }

}
