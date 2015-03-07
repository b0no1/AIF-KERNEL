package io.aif.associations.model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@FunctionalInterface
public interface IAssociationEdge {

    @Max(value = 1)
    @Min(value = 0)
    public double weight();
    
}
