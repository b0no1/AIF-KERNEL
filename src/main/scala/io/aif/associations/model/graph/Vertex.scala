package io.aif.associations.model.graph


trait Vertex[V] {

  def value: V

  def weight: Double

}