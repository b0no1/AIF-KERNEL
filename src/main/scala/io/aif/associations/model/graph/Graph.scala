package io.aif.associations.model.graph


class Graph[V, E](raw: Map[V, Map[V, E]], vertexWeightCalculator: V => Double = (v) => 0.0) {

  def isEmpty: Boolean = raw isEmpty

  def vertexes: List[V] = raw.keys toList

  def neigbors(vertex: V): List[V] = raw.get(vertex).get.keys toList

  def edge(from: V, to: V): Option[E] = raw.get(from).get get(to)

  def rawData = raw

  def weight(vertex: V) = vertexWeightCalculator(vertex)

  def size = raw.size

}
