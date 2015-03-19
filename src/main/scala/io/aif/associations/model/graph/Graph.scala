package io.aif.associations.model.graph


class Graph[V, E](raw: Map[V, Map[V, E]]) {

  def isEmpty: Boolean = raw isEmpty

  def vertexes: List[V] = raw.keys toList

  def neigbors(vertex: V): List[V] = raw.get(vertex).get.keys toList

  def edge(from: V, to: V): Option[E] = raw.get(from).get get(to)

  def mutate[V2, E2](f: Map[V, Map[V, E]] => Map[V2, Map[V2, E2]]) = new Graph[V2, E2](f(raw))

  def rawData = raw

}
