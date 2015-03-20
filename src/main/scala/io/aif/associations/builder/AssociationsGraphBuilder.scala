package io.aif.associations.builder

import io.aif.associations.calculators.edge.PredefinedEdgeWeightCalculator
import io.aif.associations.calculators.vertex.ConnectionBasedWeightCalculator
import io.aif.associations.model.graph.{Graph, GraphBuilder}


class AssociationsGraphBuilder[V](experimentsConnectionsGraphBuilder: ExperimentsConnectionsGraphBuilder[V] = new ExperimentsConnectionsGraphBuilder[V](),
                                  experimentsConnectionsGraphReducer: ExperimentsConnectionsGraphReducer[V] = new ExperimentsConnectionsGraphReducer[V]()) {

  def build(experiments: List[V]): Graph[V, Double] = {

    val build: List[V] => Graph[V, List[Double]] = experimentsConnectionsGraphBuilder.build
    val reduce: Graph[V,List[Double]] => Graph[V, Double] = experimentsConnectionsGraphReducer.reduce
    val reducedGraph = (build andThen reduce) (experiments)

    val edgeWeightCalculator = new PredefinedEdgeWeightCalculator[V](reducedGraph)
    val vertexWeightCalculator = new ConnectionBasedWeightCalculator[V](reducedGraph, edgeWeightCalculator.calculate)

    new Graph[V, Double](reducedGraph.rawData, vertexWeightCalculator.calculate)
  }

}
