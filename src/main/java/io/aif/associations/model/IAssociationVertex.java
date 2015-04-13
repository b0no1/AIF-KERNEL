package io.aif.associations.model;


import io.aif.associations.graph.INode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface IAssociationVertex<T> extends INode<T> {

    @Max(value = 1)
    @Min(value = 0)
    public double weight();
    
}
