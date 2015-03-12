package io.aif.associations.graph;


import io.aif.associations.model.IAssociationVertex;

class AssociationVertex<T> implements IAssociationVertex<T> {
    
    private final T value;

    private final double weight;
    
    public AssociationVertex(final T value, final double weight) {
        this.value = value;
        this.weight = weight;
    }

    @Override
    public double weight() {
        return weight;
    }

    @Override
    public T item() {
        return value;
    }
    
}
