package io.aif.algorithms.graph.fuzzy.similarity;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.graph.IFuzzyGraph;

public interface ISimilarityCalculator<T> {

    public FuzzyBoolean similar(final IFuzzyGraph<T> left, final IFuzzyGraph<T> right);

}
