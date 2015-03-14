package io.aif.associations.builder


class ExperimentsConnectionsGraphBuilder[T](distanceMultiplierIncrementCalculator: T => Double,
                                            connectAhead: Int = 5) {

  def build(experiments: List[T]): Map[T, Map[T, List[Double]]] = {
    
    def process(experiments: List[T], result: Map[T, Map[T, List[Double]]]): Map[T, Map[T, List[Double]]] = {
      if (experiments isEmpty) {
        result
        
      } else {
        val currentExperiment = experiments.head
        process(experiments tail, result updated(currentExperiment, addValueToMap(experiments head, experiments tail, 1, connectAhead, result.getOrElse(currentExperiment, Map()))))
        
      }
    }
    
    def addValueToMap(currentExp: T, experiments: List[T], distance: Double, end: Int, results: Map[T, List[Double]]): Map[T, List[Double]] = {
      if ((distance >= end) || (experiments isEmpty) || currentExp == experiments.head) {
        results
      } else {
        val newDistance = (distance + 1.0) * distanceMultiplierIncrementCalculator(experiments head)
        addValueToMap(currentExp, experiments tail, newDistance, end, (results.updated(experiments head, results.getOrElse(experiments head, List()) ::: newDistance :: Nil)))
      }
      
    }

    process(experiments, Map())
  }

}
