package scalacask.api

import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File}

import javax.imageio.ImageIO
import scalacask.engine.{CaskManager, IOManager}

/**
  * Constructs a ScalaCask API.
  *
  * @param directory the default data directory.
  * @param sizeLimit the size limit of an individual file.
  */
class ScalaCaskAPI(private val directory: File = new File("data"), private val sizeLimit: Int = Math.pow(10, 8).toInt) extends ScalaCask {

  private val manager: IOManager = new CaskManager(directory, sizeLimit)

  private val typePadding = Map("Image" -> "i", "String" -> "s")

  /**
    * Store an Image value.
    *
    * @param key   the key.
    * @param image the image to store.
    */
  override def setImage(key: String, image: BufferedImage): Unit = {
    val byteArray = new ByteArrayOutputStream
    ImageIO.write(image, "jpg", byteArray)
    byteArray.flush()
    manager.set(typePadding("Image") + key, byteArray.toByteArray);
  }

  /**
    * Store a String value.
    *
    * @param key the key.
    * @param str the String to store.
    */
  override def setString(key: String, str: String): Unit = {
    manager.set(typePadding("String") + key, str.getBytes);
  }

  /**
    * Retrieve the stored Image.
    *
    * @param key the key to retrieve the value from.
    * @return the Image value.
    */
  override def getImage(key: String): BufferedImage = {
    val byteStream = new ByteArrayInputStream(manager.read(typePadding("Image") + key))
    ImageIO.read(byteStream)
  }

  /**
    * Retrieve the stored String.
    *
    * @param key the key to retrieve the value from.
    * @return the String value.
    */
  override def getString(key: String): String = {
    new String(manager.read(typePadding("Image") + key))
  }

  /**
    * Closes the ScalaCask data store and persists memory to disk.
    */
  override def close(): Unit = {
    manager.close()
  }

  /**
    * Delete the stored Image.
    *
    * @param key the key to delete the value from.
    */
  override def deleteImage(key: String): Unit = {
    manager.read(typePadding("Image") + key)
  }

  /**
    * Delete the stored String.
    *
    * @param key the key to delete the value from.
    */
  override def deleteString(key: String): Unit = {
    manager.read(typePadding("String") + key)
  }
}
