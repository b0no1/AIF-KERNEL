package io.aif.associations.calculators.vertex


trait VertexWeightCalculator[T] {

  def calculate(vertex: T): Double

}
