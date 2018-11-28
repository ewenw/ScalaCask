package scalacask.api

import org.scalatest.FunSuite


class ScalaCaskAPITest extends FunSuite {

  test("Values can be added to list and retrieved") {
    val cask: ScalaCask = new ScalaCaskAPI()
    cask.addToList("alphabet", "a")
    cask.addToList("alphabet2", "alpha")
    cask.addToList("alphabet", "b")
    assert(cask.getList("alphabet")(0).equals("a"))
    assert(cask.getList("alphabet2")(0).equals("alpha"))
    assert(cask.getList("alphabet")(1).equals("b"))
    cask.nuke
  }

  test("All value types can be stored independently") {
    val cask: ScalaCask = new ScalaCaskAPI()

    cask.addToList("key", "value1")
    cask.addToList("key", "value2")
    cask.setString("key", "string value")

    assert(cask.getList("key")(0).equals("value1"))
    assert(cask.getList("key")(1).equals("value2"))
    assert(cask.getString("key").equals("string value"))

    cask.nuke
  }
}

