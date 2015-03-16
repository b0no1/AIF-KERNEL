package io.aif.associations.builder

import io.aif.associations.calculators.edge.{PredefinedEdgeWeightCalculator, EdgeWeightCalculator}
import io.aif.associations.calculators.vertex.ConnectionBasedWeightCalculator
import io.aif.associations.model.graph.{Vertex, GraphBuilder}


class AssociationsGraphBuilder[V](experimentsConnectionsGraphBuilder: ExperimentsConnectionsGraphBuilder[V] = new ExperimentsConnectionsGraphBuilder[V](),
                                  experimentsConnectionsGraphReducer: ExperimentsConnectionsGraphReducer[V] = new ExperimentsConnectionsGraphReducer[V]()) extends GraphBuilder[V, Double] {

  override def build(experiments: List[V]): List[Vertex[V, Double]] = {



    val graph = experimentsConnectionsGraphBuilder.build(experiments)
    val reducedGraph = experimentsConnectionsGraphReducer.reduce(graph)

    val edgeWeightCalculator = new PredefinedEdgeWeightCalculator[V](reducedGraph)
    val vertexWeightCalculator = new ConnectionBasedWeightCalculator[V](reducedGraph, edgeWeightCalculator)

    def convert(graph: Map[V, Map[V, Double]]): List[Vertex[V, Double]] = {
      graph.keys.map(key => new Vertex[V, Double] {
        override def value: V = key

        override def edge(vertex: V): Option[Double] = graph.get(key).get.get(vertex)

        override def neigbors: Iterable[V] = graph.get(key).get.keys

        override def weight: Double = vertexWeightCalculator.calculate(key)
      }).toList
    }

    convert(reducedGraph)
  }

}
