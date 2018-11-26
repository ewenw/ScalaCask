package scalacask.engine

trait IOManager {

  /**
    * Set the key-value pair.
    *
    * @param key   the key.
    * @param value the byte-array storing the value.
    */
  def set(key: String, value: Array[Byte]): Unit

  /**
    * Retrieve the value of the corresponding key.
    *
    * @param key the key.
    * @return the value in a byte array.
    */
  def read(key: String): Array[Byte]

  /**
    * Delete the value of the corresponding key.
    *
    * @param key the key.
    */
  def delete(key: String)

  /**
    * Destroy the database's content.
    */
  def nuke(): Unit

  /**
    * Close the IO and persist the in-memory data.
    */
  def close(): Unit

}
