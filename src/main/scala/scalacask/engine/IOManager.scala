package scalacask.engine

trait IOManager {

  def set(key: String, value: Array[Byte]): Unit

  def read(key: String): Array[Byte]

  def nuke(): Unit

  def close(): Unit

}
