package io.aif.associations.model


trait DistanceMultiplierIncrementCalculator[T] {
  
  def calculateMultiplierIncrement(vertex: T): Double

}
