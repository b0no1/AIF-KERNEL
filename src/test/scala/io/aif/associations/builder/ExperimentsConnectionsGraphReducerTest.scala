package io.aif.associations.builder

import org.scalatest.{BeforeAndAfter, FunSuite}
import io.aif.associations.model.graph.Graph


class ExperimentsConnectionsGraphReducerTest extends FunSuite with BeforeAndAfter {

  var experimentsConnectionsGraphReducer: ExperimentsConnectionsGraphReducer[Int] = _

  before {
    experimentsConnectionsGraphReducer = new ExperimentsConnectionsGraphReducer[Int]()
  }

  test("verifying the reduce process on empty map") {
    val inputGraph = new Graph[Int, List[Double]](Map())
    assert (experimentsConnectionsGraphReducer reduce inputGraph isEmpty)
  }

  test("verifying the reduce process on the map with 1 element") {
    val inputMap = Map(1 -> Map(2 -> List(1.0, 1.0)))
    val inputGraph = new Graph[Int, List[Double]](inputMap)
    val actualResult = experimentsConnectionsGraphReducer reduce inputGraph
    assert ( eq (actualResult.edge(1, 2) get,  1.0))
  }

  test("verifying the reduce process on the map with 2 elements") {
    val inputMap = Map(1 -> Map(2 -> List(1.0, 1.0)), 2 -> Map(1 -> List(2.0, 2.0)))
    val inputGraph = new Graph[Int, List[Double]](inputMap)

    val resultGraph = experimentsConnectionsGraphReducer reduce inputGraph

    assert (eq (resultGraph.edge(1, 2) get, 1.0))
    assert (eq (resultGraph.edge(2, 1) get, 0.0))
  }

  test("verifying the reduce process on the map with 3 elements") {
    val inputMap = Map(1 -> Map(2 -> List(1.0, 1.0)), 2 -> Map(1 -> List(2.0, 2.0), 3 -> List(3.0, 2.0, 1.0)))
    val inputGraph = new Graph[Int, List[Double]](inputMap)

    val resultGraph = experimentsConnectionsGraphReducer reduce inputGraph

    assert (eq (resultGraph.edge(1, 2) get, 1.0))
    assert (eq (resultGraph.edge(2, 1) get, 0.5))
  }

}
