package io.aif.graph;


public interface ISimpleGraphBuilder<V> extends IGraphBuilder<V, Boolean> {

    default public void connect(final V from, final V to) {
        this.connect(from, to, true);
    }

    public static <V>ISimpleGraphBuilder<V> defaultBuilder() {
        return new MapBasedSimpleGraphBuilder<>();
    }

}
