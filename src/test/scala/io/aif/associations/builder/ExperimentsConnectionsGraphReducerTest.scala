package io.aif.associations.builder

import org.scalatest.{BeforeAndAfter, FunSuite}


class ExperimentsConnectionsGraphReducerTest extends FunSuite with BeforeAndAfter {

  var experimentsConnectionsGraphReducer: ExperimentsConnectionsGraphReducer[Int] = _

  before {
    experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer[Int]()
  }

  test("verifying the reduce process on empty map") {
    assert (experimentsConnectionsGraphReducer reduce  Map() isEmpty)
  }

  test("verifying the reduce process on the map with 1 element") {
    val inputMap = Map(1 -> Map(2 -> List(1.0, 1.0)))
    val actualResult = experimentsConnectionsGraphReducer reduce inputMap
    assert ( eq (actualResult.get(1).get.get(2) get,  1.0))
  }

  test("verifying the reduce process on the map with 2 elements") {
    val inputMap = Map(1 -> Map(2 -> List(1.0, 1.0)), 2 -> Map(1 -> List(2.0, 2.0)))
    val expectedResult = Map(1 -> Map(2 -> 1.0), 2 -> Map(1 -> 0.0))
    assert (eq (experimentsConnectionsGraphReducer reduce inputMap, expectedResult))
  }

  test("verifying the reduce process on the map with 3 elements") {
    val inputMap = Map(1 -> Map(2 -> List(1.0, 1.0)), 2 -> Map(1 -> List(2.0, 2.0), 3 -> List(3.0, 2.0, 1.0)))
    val expectedResult = Map(1 -> Map(2 -> 1.0), 2 -> Map(1 -> 0.5), 3 -> 0.0)
    assert (eq (experimentsConnectionsGraphReducer reduce inputMap, expectedResult))
  }

}
