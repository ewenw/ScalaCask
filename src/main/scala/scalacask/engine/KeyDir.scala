package scalacask.engine

@SerialVersionUID(123L)
trait KeyDir extends Serializable {

  /**
    * Get the value entry from the key directory.
    *
    * @param key the key.
    * @return the entry of that key.
    */
  def get(key: String): Entry

  /**
    * Set the entry of the corresponding key.
    *
    * @param key   the key.
    * @param entry the entry of that key.
    */
  def put(key: String, entry: Entry): Unit

  /**
    * Delte the entry of the corresponding key.
    *
    * @param key   the key.
    */
  def delete(key: String): Unit

  /**
    * Whether the key exists in the datastore.
    * @param key the key.
    * @return exists?
    */
  def containsKey(key: String): Boolean

}
