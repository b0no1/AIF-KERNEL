package io.aif.associations.builder

import io.aif.associations.calculators.edge.{PredefinedEdgeWeightCalculator, EdgeWeightCalculator}
import io.aif.associations.calculators.vertex.ConnectionBasedWeightCalculator
import io.aif.associations.model.graph.{Graph, Vertex, GraphBuilder}


class AssociationsGraphBuilder[V](experimentsConnectionsGraphBuilder: ExperimentsConnectionsGraphBuilder[V] = new ExperimentsConnectionsGraphBuilder[V](),
                                  experimentsConnectionsGraphReducer: ExperimentsConnectionsGraphReducer[V] = new ExperimentsConnectionsGraphReducer[V]()) extends GraphBuilder[V, Double] {

  override def build(experiments: List[V]): Graph[Vertex[V], Double] = {


    val build: List[V] => Graph[V, List[Double]] = experimentsConnectionsGraphBuilder.build
    val reduce: Graph[V,List[Double]] => Graph[V, Double] = experimentsConnectionsGraphReducer.reduce
    val reducedGraph = (build andThen reduce) (experiments)

    val edgeWeightCalculator = new PredefinedEdgeWeightCalculator[V](reducedGraph)
    val vertexWeightCalculator = new ConnectionBasedWeightCalculator[V](reducedGraph, edgeWeightCalculator)

    def convert(graph: Graph[V, Double]): Graph[Vertex[V], Double] = {
      graph.rawData.keys.map(key => new Vertex[V] {
        override def value: V = key

        override def weight: Double = vertexWeightCalculator.calculate(key)
      }).toList
    }

    convert(reducedGraph)
  }

}