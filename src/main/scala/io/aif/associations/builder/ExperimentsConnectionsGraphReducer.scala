package io.aif.associations.builder

import io.aif.associations.model.graph.Graph


class ExperimentsConnectionsGraphReducer[T] {
  
  def reduce(originalGraph: Graph[T, List[Double]]): Graph[T, Double] = {
    
    def convertVertexConnections(vertexConnections: Map[T, List[Double]]) = {
      vertexConnections.keys.map(key => (key, vertexConnections.get(key).get.sum )) toMap
    }
    
    def max(graph: Map[T, Map[T, Double]]) = {
      graph.values.map(connection => connection.values max) max
    }

    def min(graph: Map[T, Map[T, Double]]) = {
      graph.values.map(connection => connection.values min) min
    }

    def normalize(graph: Map[T, Map[T, Double]], max : Double, min : Double) = {
      val newMax = if (max - min > 0) max - min else max
      graph mapValues(vertexConnections => vertexConnections.mapValues(weight => ((newMax - (weight - min)) / newMax)))
    }

    val rawData = originalGraph.rawData

    rawData match {
      case rawData.isEmpty => new Graph[T, Double](Map())
      case _ => {
        val recalculateGraph = rawData.mapValues(convertVertexConnections)
        new Graph[T, Double](normalize(recalculateGraph, max(recalculateGraph), min(recalculateGraph)))
      }
    }
  }

}
