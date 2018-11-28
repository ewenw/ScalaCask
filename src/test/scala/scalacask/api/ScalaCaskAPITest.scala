package scalacask.api

import org.scalatest.FunSuite


class ScalaCaskAPITest extends FunSuite {

  test("Values can be added to list and retrieved") {
    val cask: ScalaCask = new ScalaCaskAPI()
    cask.addToList("alphabet", "a")
    cask.addToList("alphabet", "b")
    assert(cask.getList("alphabet")(0).equals("a"))
    assert(cask.getList("alphabet")(1).equals("b"))
  }

}

