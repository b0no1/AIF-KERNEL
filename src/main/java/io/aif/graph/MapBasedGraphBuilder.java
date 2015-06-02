package io.aif.graph;


import java.util.HashMap;
import java.util.Map;

class MapBasedGraphBuilder<V, E> implements IGraphBuilder<V, E>{

    private final Map<V, Map<V, E>> g = new HashMap<>();

    public synchronized void connect(final V from, final V to, final E edge) {
        if (!g.containsKey(from)) {
            add(from);
        }
        if (!g.containsKey(to)) {
            add(to);
        }
        g.get(from).put(to, edge);
        g.get(to).put(from, edge);
    }

    /**
     * Add disconnected single components to the graph.
     *
     * @param vertex
     */
    public synchronized void add(final V vertex) {
        if (g.containsKey(vertex)) {
            return;
        }
        g.put(vertex, new HashMap<>());
    }

    @Override
    public IGraph<V, E> build() {
        return new MapBasedGraph<>(g);
    }

}
