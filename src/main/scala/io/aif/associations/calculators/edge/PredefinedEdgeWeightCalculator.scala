package io.aif.associations.calculators.edge


class PredefinedEdgeWeightCalculator[T](graph: Map[T, Map[T, Double]]) extends EdgeWeightCalculator[T] {

  override def calculate(from: T, to: T): Option[Double] = graph.get(from).get get(to)

}
