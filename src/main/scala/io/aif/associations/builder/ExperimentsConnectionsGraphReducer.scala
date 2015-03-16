package io.aif.associations.builder


class ExperimentsConnectionsGraphReducer[T] {
  
  def reduce(originalGraph: Map[T, Map[T, List[Double]]]): Map[T, Map[T, Double]] = {
    
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

    if (! originalGraph.isEmpty ) {
      val recalculateGraph = originalGraph.mapValues(convertVertexConnections)
      normalize(recalculateGraph, max(recalculateGraph), min(recalculateGraph))
    } else {
      Map()
    }
  }

}
