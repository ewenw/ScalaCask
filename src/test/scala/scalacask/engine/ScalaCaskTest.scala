package scalacask.engine

import java.io.{File, IOException}
import java.util.Random

import org.scalatest.FunSuite

class ScalaCaskTest extends FunSuite {
  val rand: Random = new Random(20)

  var count: Int = 0

  def getRandUniqueString(bound: Int): String = {
    var s: String = "" + count
    for (_ <- 0 until rand.nextInt(bound) + 2) {
      s += (rand.nextInt(26) + 'a').toChar
    }
    s
  }

  test("Values can be deleted") {
    val manager: IOManager = new CaskManager(new File("data"), 100)
    var thrown = false
    manager.set("key", "value".getBytes)
    manager.set("key2", "efg".getBytes)
    manager.delete("key2")
    assert("value".equals(new String(manager.read("key"))))
    try {
      manager.read("key2")
    } catch {
      case e: IllegalArgumentException => {
        thrown = true
      }
        manager.nuke()
        assert(thrown)
    }
  }
  test("A CaskManager be able to get values from keys that it has written to") {
    val manager: IOManager = new CaskManager(new File("data"), 100)
    var s: String = ""
    var s2: String = ""
    try {
      manager.set("key1", "test".getBytes)
      manager.set("key2", "test2".getBytes)
      s = new String(manager.read("key1"))
      s2 = new String(manager.read("key2"))
      manager.nuke()
    } catch {
      case e: IOException => e.printStackTrace()
    }
    assert("test".equals(s))
    assert("test2".equals(s2))
  }

  test("it should store and retrieve when using multiple files too") {
    val manager: IOManager = new CaskManager(new File("data"), 100)
    val keys: Array[String] = Array.ofDim[String](1000)
    val values: Array[String] = Array.ofDim[String](1000)
    try {
      for (i <- 0 until keys.length) {
        keys(i) = getRandUniqueString(50)
        values(i) = getRandUniqueString(100)
        manager.set(keys(i), values(i).getBytes)
        if (i > 0) {
          assert(values(i - 1).equals(new String(manager.read(keys(i - 1)))))
        }
      }
      for (i <- 0 until keys.length) {
        assert(new String(manager.read(keys(i))).equals(values(i)))
      }
      manager.nuke()
    } catch {
      case e: IOException => {
        assert(false)
        e.printStackTrace()
      }

    }
  }


  test("Another session should load up the KeyDir with the same data") {
    var manager: IOManager = new CaskManager(new File("data"), 100)
    manager.set("key", "value".getBytes)
    manager.set("key2", "efg".getBytes)
    manager.set("key2", "value".getBytes)
    assert("value".equals(new String(manager.read("key"))))
    assert("value".equals(new String(manager.read("key2"))))
    manager.close()
    manager = new CaskManager(new File("data"), 100)
    assert("value".equals(new String(manager.read("key"))))
    assert("value".equals(new String(manager.read("key2"))))
    manager.nuke()
  }
}

