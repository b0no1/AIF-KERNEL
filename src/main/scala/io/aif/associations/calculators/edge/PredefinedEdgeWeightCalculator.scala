package io.aif.associations.calculators.edge

import io.aif.associations.model.graph.Graph


class PredefinedEdgeWeightCalculator[T](graph: Graph[T, Double]) extends EdgeWeightCalculator[T] {

  override def calculate(from: T, to: T): Option[Double] = graph.edge(from, to)

}
