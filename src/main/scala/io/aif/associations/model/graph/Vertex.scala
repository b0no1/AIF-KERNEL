package io.aif.associations.model.graph


trait Vertex[V, E] {

  def value: V

  def weight: Double
  
  def neigbors: Iterable[V]

  def edge(vertex: V): Option[E]

}