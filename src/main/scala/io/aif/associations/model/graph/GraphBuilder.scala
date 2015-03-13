package io.aif.associations.model.graph


trait GraphBuilder[V, E] {

  def build(experiments: List[V]): List[Vertex[V, E]]

}
