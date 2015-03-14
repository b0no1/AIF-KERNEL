package io.aif.associations.builder

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter


class ExperimentsConnectionsGraphBuilderTest extends FunSuite with BeforeAndAfter {

  var experimentsConnectionsGraphBuilder: ExperimentsConnectionsGraphBuilder[Int] = _

  before {
    experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder[Int](exp => 1)
  }

  test("verifying the build process on empty list") {
    assert (experimentsConnectionsGraphBuilder build List[Int]() isEmpty)
  }

  test("verifying the build process with one element") {
    val result = experimentsConnectionsGraphBuilder build 1 :: Nil
    assert (result.size == 1)
  }

  test("verifying the build process with two same elements") {
    val result = experimentsConnectionsGraphBuilder build 1 :: 1 :: Nil
    assert (result.size == 1)
    assert (result.get(1).get isEmpty)
  }

  test("verifying the build process with two different elements") {
    val result = experimentsConnectionsGraphBuilder build 1 :: 2 :: Nil
    assert (result.size == 2)
    assert (result.get(1).get.get(2).get(0) == 2.0)
    assert (result.get(2).get isEmpty)
  }

  test("verifying the build process with three elements") {
    val result = experimentsConnectionsGraphBuilder build 1 :: 2 :: 2 :: Nil
    assert (result.size == 2)
    assert (result.get(1).get.get(2).get(0) == 2.0)
    assert (result.get(1).get.get(2).get(1) == 3.0)
    assert (result.get(2).get isEmpty)
  }

  test("verifying the build process with three elements and non default multilayer") {
    experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder[Int](exp => if (exp == 1) 1.0 else 2.0)
    val result = experimentsConnectionsGraphBuilder build 1 :: 2 :: 2 :: Nil
    assert (result.size == 2)
    assert (result.get(1).get.get(2).get(0) == 4.0)
    assert (result.get(1).get.get(2).get(1) == 10.0)
    assert (result.get(2).get isEmpty)
  }

  test("verifying the build process with fore elements and non default multilayer") {
    experimentsConnectionsGraphBuilder = new ExperimentsConnectionsGraphBuilder[Int](exp => if (exp == 1) 1.0 else 2.0)
    val result = experimentsConnectionsGraphBuilder build 1 :: 2 :: 2 :: 2 :: Nil
    assert (result.size == 2)
    assert (result.get(1).get.get(2).get(0) == 4.0)
    assert (result.get(1).get.get(2).get(1) == 10.0)
    assert (result.get(2).get isEmpty)
  }
  
}
