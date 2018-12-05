package scalacask.api

import java.util.Scanner

/**
  * Allows the user to interact with the database through REPL.
  */
object Console extends App {
  override def main(args: Array[String]): Unit = {
    val line = new Scanner(System.in)
    val cask: ScalaCask = new ScalaCaskAPI()
    print("> ")
    while (line.hasNext) {
      val args = line.nextLine.split(" ")
      if (args(0).equals("set")) {
        cask.setString(args(1), args(2))
      }
      else if (args(0).equals("ladd")) {
        for (i <- 2 until args.length) {
          cask.addToList(args(1), args(i))
        }
      }
      else if (args(0).equals("get")) {
        try {
          println(cask.getString(args(1)))
        }
        catch {
          case _: IllegalArgumentException => println("Key doesn't exist.")
        }
      }
      else if (args(0).equals("delete")) {
        cask.deleteString(args(1))
      }
      else if (args(0).equals("lget")) {
        cask.getList(args(1)).foreach(println _)
      }
      else if (args(0).equals("nuke")) {
        cask.nuke
        System.exit(-1)
      }
      else if (args(0).equals("close")) {
        cask.close
        System.exit(-1)
      }
      else {
        println("Invalid command.")
      }
      print("> ")
    }
  }
}
