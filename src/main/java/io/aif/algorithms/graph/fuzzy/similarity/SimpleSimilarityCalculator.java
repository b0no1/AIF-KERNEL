package io.aif.algorithms.graph.fuzzy.similarity;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.graph.IFuzzyGraph;
import javaslang.Tuple2;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class SimpleSimilarityCalculator<T> implements ISimilarityCalculator<T> {

    private final double similarityThreshold;

    public SimpleSimilarityCalculator(final double similarityThreshold) {
        this.similarityThreshold = similarityThreshold;
    }

    @Override
    public FuzzyBoolean similar(final IFuzzyGraph<T> left, final IFuzzyGraph<T> right) {
        if (left.getVertices().size() > right.getVertices().size()) {
            return similar(right, left);
        }
        final List<FuzzyBoolean> allCombinations = left
                .getVertices()
                .stream()
                .flatMap(v ->
                    left.getNeighbors(v).stream().map(n -> new Tuple2<T, T>(v, n))
                )
                .distinct()
                .map(p -> combain(left, right, p._1, p._2))
                .collect(Collectors.toList());
        final OptionalDouble result = allCombinations
                .stream()
                .mapToDouble(FuzzyBoolean::getValue)
                .average();
        if (result.isPresent()) {
            return new FuzzyBoolean(result.getAsDouble(), similarityThreshold);
        }
        return new FuzzyBoolean(.0, similarityThreshold);
    }

    public FuzzyBoolean combain(final IFuzzyGraph<T> left, final IFuzzyGraph<T> right, final T from, final T to) {
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
