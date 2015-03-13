package io.aif.associations.model.graph


trait Vertex[V, E] {

  def value: V
  
  def neigbors: List[V]

  def edge(vertex: V): E

}