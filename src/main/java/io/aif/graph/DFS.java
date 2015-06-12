package io.aif.graph;

import io.aif.graph.normal.IGraph;

import java.util.*;
import java.util.stream.Collectors;

public class DFS<T> {

    private final IGraph<T, ?> graph;

    public DFS(final IGraph<T, ?> graph) {
        this.graph = graph;
    }

    public List<List<T>> findPath(final T source, final T dest) {
        final List<T> initialPath = new ArrayList<>();
        initialPath.add(source);
        return getPath(initialPath, dest);
    }

    public static <T>List<List<T>> getShortestPath(final List<List<T>> paths) {
        final OptionalInt minPathValue = paths.stream().mapToInt(List::size).min();
        if (!minPathValue.isPresent()) {
            return Collections.emptyList();
        }
        return paths
                .stream()
                .filter(path -> path.size() == minPathValue.getAsInt())
                .collect(Collectors.toList());
    }

    private List<List<T>> getPath(final List<T> path, final T dest) {
        final List<List<T>> result = new ArrayList<>();

        final T last = path.get(path.size() - 1);
        if (last == dest) {
            result.add(path);
            return result;
        }

        final Set<T> neighbours;
        try {
            neighbours = graph.getNeighbors(last);
            if (neighbours.isEmpty())
                return result;
        } catch (VertexNotFoundException e) {
            e.printStackTrace();
            return result;
        }

        return neighbours
                .stream()
                .filter(neighbour -> !path.contains(neighbour))
                .parallel()
                .map(neighbour -> {
                    final List<T> pathCopy = new ArrayList<>(path);
                    pathCopy.add(neighbour);
                    return getPath(pathCopy, dest);
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
