package io.aif.associations.model.graph


trait Graph[V, E] {

  def vertexes: List[V]

  def edge(from: V, to: V): Option[E]

  def addVertex(vertex: V): Graph[V, E]

  def addEdge(from: V, to: V, edge: E): Graph[V, E]

}


object EmptyGraph extends Graph[Any, Any] {

  override def vertexes = Nil

  override def edge(from: Any, to: Any) = Option.empty

  override def addEdge(from: Any, to: Any, edge: Any): Graph[Any, Any] = throw new NoSuchElementException()

  override def addVertex(vertex: Any): Graph[Any, Any] = return new NonEmptyGraph[Any, Any](vertex, Map(), EmptyGraph)

}


class NonEmptyGraph[V, E](root: V, edges: Map[V, E], nextSubGraph: Graph[V, E] = EmptyGraph) extends Graph[V, E] {

  override def vertexes: List[V] = root :: nextSubGraph.vertexes

  override def edge(from: V, to: V): Option[E] = from match {
    case root => edges get to
    case _ => nextSubGraph.edge(from, to)
  }

  override def addEdge(from: V, to: V, edge: E): Graph[V, E] = from match {
    case root => new NonEmptyGraph[V, E](root, edges updated(to, edge))
    case _ => new NonEmptyGraph[V, E](root, edges, nextSubGraph addEdge(from, to, edge))
  }

  override def addVertex(vertex: V): Graph[V, E] = vertex match {
    case root => this
    case _ => new NonEmptyGraph[V, E](root, edges, nextSubGraph addVertex(vertex))
  }

}
