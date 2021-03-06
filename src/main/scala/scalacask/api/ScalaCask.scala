package scalacask.api

import java.awt.image.BufferedImage

/**
  * ScalaCask offers a simple interface to allows users to store and retrieve key-value pairs of different value data types.
  * All data types values are stored independently. For example, setImage("dog1", dogImg) and setString("dog1", "bark")
  * will allow the values to coexist.
  */
trait ScalaCask {

  /**
    * Store an Image value.
    *
    * @param key   the key.
    * @param image the image to store.
    */
  def setImage(key: String, image: BufferedImage): Unit

  /**
    * Store a String value.
    *
    * @param key the key.
    * @param str the String to store.
    */
  def setString(key: String, str: String): Unit


  /**
    * Add String to a list.
    *
    * @param key the key.
    */
  def addToList(key: String, element: String): Unit

  /**
    * Retrieve the stored Image.
    *
    * @param key the key to retrieve the value from.
    * @return the Image value.
    */
  def getImage(key: String): BufferedImage

  /**
    * Retrieve the stored String.
    *
    * @param key the key to retrieve the value from.
    * @return the String value.
    */
  def getString(key: String): String

  /**
    * Retrieve the entire list.
    *
    * @param key the key to retrieve the list from.
    * @return the list of String values.
    */
  def getList(key: String): List[String]

  /**
    * Delete the stored Image.
    *
    * @param key the key to delete the value from.
    */
  def deleteImage(key: String): Unit

  /**
    * Delete the stored String.
    *
    * @param key the key to delete the value from.
    */
  def deleteString(key: String): Unit


  /**
    * Closes the ScalaCask data store and persists memory to disk.
    */
  def close(): Unit

  /**
    * Nukes all of the data.
    */
  def nuke(): Unit

}
