package io.aif.associations.model.graph


trait GraphBuilder[V, E] {

  def build(experiments: List[V]): Graph[V, E]

}
