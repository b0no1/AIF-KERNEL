package io.aif.fuzzy.set;


import io.aif.fuzzy.bool.FuzzyBoolean;

class FuzzySetUnion<T> implements IFuzzySet<T> {

    private final IFuzzySet<T> left;

    private final IFuzzySet<T> right;

    private FuzzySetUnion(final IFuzzySet<T> left, final IFuzzySet<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public FuzzyBoolean contains(final T element) {
        final FuzzyBoolean leftFB = left.contains(element);
        final FuzzyBoolean rightFB = right.contains(element);
        if (leftFB.getValue() > rightFB.getValue()) return leftFB;
        return rightFB;
    }

    public static <T>IFuzzySet<T> union(final IFuzzySet<T> left, final IFuzzySet<T> right) {
        return new FuzzySetUnion<>(left, right);
    }

}
