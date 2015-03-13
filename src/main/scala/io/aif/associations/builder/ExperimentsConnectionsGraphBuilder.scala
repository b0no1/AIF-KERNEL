package io.aif.associations.builder


class ExperimentsConnectionsGraphBuilder[T] {

  def build(experiments: List[T]): Map[T, Map[T, List[Double]]] = {
    experiments
  }

}
