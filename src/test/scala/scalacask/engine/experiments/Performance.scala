package scalacask.engine.experiments

import java.io.{File, IOException}
import java.util.Random

import scalacask.engine.CaskManager


object Performance {

  var rand: Random = new Random(20)

  /**
    * TRIAL 1: 1 million inserts - 16244346005ns. 1 million retrieves - 5581567039ns
    */
  def main(args: Array[String]): Unit = {
    var cask: CaskManager =
      new CaskManager(new File("data"), Math.pow(10, 8).toLong)
    println("Starting performance experiment...")
    try {
      cask.nuke()
      cask = new CaskManager(new File("data"), Math.pow(10, 8).toLong)
      val values: Array[String] = Array.ofDim[String](1000000)
      for (i <- 0.until(1000000)) {
        values(i) = getRandUniqueString(120)
      }
      var before: Long = System.nanoTime()
      for (i <- 0.until(1000000)) {
        cask.set("item " + i, values(i).getBytes)
        if (i % 10000 == 0) {
          println(i)
        }
      }
      println("Insertions: " + (System.nanoTime() - before) + "ns")
      before = System.nanoTime()
      for (i <- 0.until(1000000)) {
        cask.read("item " + i)
        if (i % 10000 == 0) {
          println(i + ", " + new String(cask.read("item " + i)))
        }
      }
      println("Retrievals: " + (System.nanoTime() - before) + "ns")
    } catch {
      case e: IOException => e.printStackTrace()

    }
  }

  var count: Int = 0

  def getRandUniqueString(bound: Int): String = {
    var s: String = "" + count
    for (i <- 0 until rand.nextInt(bound) + 2) {
      s += (rand.nextInt(26) + 'a').toChar
    }
    {
      count += 1;
      count - 1
    }
    s
  }

}
