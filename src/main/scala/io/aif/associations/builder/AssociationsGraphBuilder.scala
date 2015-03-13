package io.aif.associations.builder

import io.aif.associations.model.graph.{Vertex, GraphBuilder}


class AssociationsGraphBuilder[V] extends GraphBuilder[V, Double] {

  override def build(experiments: List[V]): List[Vertex[V, Double]] = {
    return Nil
  }

}
