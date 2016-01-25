package io.aif.algorithms.graph.fuzzy.similarity;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.graph.IFuzzyGraph;

import java.util.Optional;

public class NodesOnTheGraphSimilarityCalculator<T> implements ISimilarityCalculator<T> {

    private final IFuzzyGraph<T> left;

    private final IFuzzyGraph<T> right;

    private final double similarityThreshold;

    public NodesOnTheGraphSimilarityCalculator(
            final IFuzzyGraph<T> left,
            final IFuzzyGraph<T> right,
            double similarityThreshold) {
        this.left = left;
        this.right = right;
        this.similarityThreshold = similarityThreshold;
    }

    @Override
    public FuzzyBoolean similar(final T from, final T to) {
        final Optional<FuzzyBoolean> leftEdge = left.getEdge(from, to);
        final Optional<FuzzyBoolean> rightEdge = right.getEdge(from, to);
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
