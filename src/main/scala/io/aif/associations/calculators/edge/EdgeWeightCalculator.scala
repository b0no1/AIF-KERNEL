package io.aif.associations.calculators.edge


trait EdgeWeightCalculator[T] {

  def calculate(from: T, to: T): Option[Double]

}
