package io.aif.algorithms.graph.fuzzy.similarity;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.graph.IFuzzyGraph;
import javaslang.Tuple2;

public interface ISimilarityCalculator<T> {

    public FuzzyBoolean similar(final T left, final T right);

    default public FuzzyBoolean similar(final Tuple2<T, T> tuple) {
        return this.similar(tuple._1, tuple._2);
    }

}
