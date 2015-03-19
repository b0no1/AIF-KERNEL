package io.aif.associations.builder

import io.aif.associations.model.graph.Graph

import scala.annotation.tailrec


class ExperimentsConnectionsGraphBuilder[T](distanceMultiplierIncrementCalculator: T => Double = v => 1,
                                            connectAhead: Int = 5) {

  def build(experiments: List[T]): Graph[T, List[Double]] = {
    
    @tailrec
    def process(experiments: List[T], result: Map[T, Map[T, List[Double]]]): Map[T, Map[T, List[Double]]] = {
      experiments match {
        case Nil => result
        case currentExperiment :: rest =>
          process(experiments tail, result updated(currentExperiment, addValueToMap(experiments head, experiments tail, 1, connectAhead, result.getOrElse(currentExperiment, Map()))))
      }
    }
    
    @tailrec
    def addValueToMap(currentExp: T, experiments: List[T], distance: Double, end: Int, results: Map[T, List[Double]]): Map[T, List[Double]] = {
      if ((distance >= end) || (experiments isEmpty) || currentExp == experiments.head) {
        results
      } else {
        val head = experiments head
        val newDistance = (distance + 1.0) * distanceMultiplierIncrementCalculator(head)
        addValueToMap(currentExp, experiments tail, newDistance, end, (results.updated(head, newDistance :: results.getOrElse(head, List()))))
      }
      
    }

    new Graph[T, List[Double]](process(experiments, Map()))
  }

}
