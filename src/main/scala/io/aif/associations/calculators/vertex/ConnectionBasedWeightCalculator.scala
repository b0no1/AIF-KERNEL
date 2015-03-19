package io.aif.associations.calculators.vertex

import io.aif.associations.calculators.edge.EdgeWeightCalculator
import io.aif.associations.model.graph.Graph


class ConnectionBasedWeightCalculator[T](graph: Graph[T, Double],
                                         edgeWeightCalculator: EdgeWeightCalculator[T],
                                         targetWeight: Double = .7) extends VertexWeightCalculator[T] {

  override def calculate(vertex: T): Double = {
    val neigbors = graph.vertexes.map(neigbor => graph.edge(vertex, neigbor)).filter(optDouble => optDouble.nonEmpty).map(optDouble => optDouble get)
    1.0 - math.abs(targetWeight - (neigbors sum) / (neigbors.size toDouble))
  }

}
