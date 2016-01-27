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

    private final double percentile;

    public GraphNodesSimilarityCalculator(
            final IFuzzyGraph<T> graph,
            final double similarityThreshold,
            final double percentile) {
        this.graph = graph;
        this.similarityThreshold = similarityThreshold;
        this.percentile = percentile;
    }

    @Override
    public FuzzyBoolean similar(final T left, final T right) {
        if (graph.getNeighbors(left).size() > graph.getNeighbors(right).size()) {
            return similar(right, left);
        }
        final List<FuzzyBoolean> results = graph
                .getNeighbors(left)
                .stream()
                .sorted((v1, v2) -> Double.compare(graph.getEdge(left, v2).get().getValue(), graph.getEdge(left, v1).get().getValue()))
                .limit((int)((double)graph.getNeighbors(left).size() * percentile))
                .map(v -> similarEdge(left, right, v))
                .collect(Collectors.toList());

        final double sum = results
                .stream()
                .filter(FuzzyBoolean::isTrue)
                .distinct()
                .mapToDouble(FuzzyBoolean::getValue)
                .sum();
        final double result = sum / results.size();
        return new FuzzyBoolean(result, similarityThreshold);
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
