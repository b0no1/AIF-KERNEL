package io.aif.fuzzy.set;


import io.aif.fuzzy.bool.FuzzyBoolean;

public interface IFuzzySet<T> {

    public FuzzyBoolean contains(final T element);

    default public IFuzzySet<T> union(final IFuzzySet<T> that) {
        return FuzzySetUnion.union(this, that);
    }

}
