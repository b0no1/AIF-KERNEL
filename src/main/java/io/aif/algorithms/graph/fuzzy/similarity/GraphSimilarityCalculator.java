package io.aif.algorithms.graph.fuzzy.similarity;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.graph.IFuzzyGraph;
import javaslang.Tuple2;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class GraphSimilarityCalculator<T> implements ISimilarityCalculator<IFuzzyGraph<T>> {

    private final double similarityThreshold;

    public GraphSimilarityCalculator(final double similarityThreshold) {
        this.similarityThreshold = similarityThreshold;
    }

    @Override
    public FuzzyBoolean similar(final IFuzzyGraph<T> left, final IFuzzyGraph<T> right) {
        if (left.getVertices().size() > right.getVertices().size()) {
            return similar(right, left);
        }
        final ISimilarityCalculator<T> nodeSimilarityCalculator =
                new NodesOnTheGraphSimilarityCalculator<>(left, right, similarityThreshold);
        final List<FuzzyBoolean> allCombinations = left
                .getVertices()
                .stream()
                .flatMap(v ->
                    left.getNeighbors(v).stream().map(n -> new Tuple2<T, T>(v, n))
                )
                .distinct()
                .map(nodeSimilarityCalculator::similar)
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

}
