package io.aif.associations.builder;


import io.aif.associations.model.IAssociationEdge;

class AssociationEdge implements IAssociationEdge {
    
    private final Double weight;

    public AssociationEdge(final Double weight) {
        this.weight = weight;
    }

    @Override
    public double weight() {
        return weight;
    }
    
}
