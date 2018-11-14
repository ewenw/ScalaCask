package scalacask.api

import java.awt.Image

trait SCask {

  def setImage(key: String, image: Image): Unit

  def setString(key: String, str: String): Unit

  def getImage(key: String): Image

  def getString(key: String): String

}
