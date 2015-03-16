package io.aif.associations.calculators.vertex

import io.aif.associations.calculators.edge.EdgeWeightCalculator


class ConnectionBasedWeightCalculator[T](graph: Map[T, Map[T, Double]],
                                         edgeWeightCalculator: EdgeWeightCalculator[T],
                                         targetWeight: Double = .7) extends VertexWeightCalculator[T] {

  override def calculate(vertex: T): Double = {
    val neigbors = graph.keys.map(graph.get(vertex).get.get).filter(optDouble => optDouble.nonEmpty).map(optDouble => optDouble get)
    1.0 - math.abs(targetWeight - (neigbors sum) / (neigbors.size toDouble))
  }

}
