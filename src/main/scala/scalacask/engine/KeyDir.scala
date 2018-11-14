package scalacask.engine

import java.io.File

trait KeyDir {

  def get(key: String): Entry

  def put(key: String, entry: Entry): Unit

  def containsKey(key: String): Boolean

  def load(directory: File): Unit

  def persist(directory: File): Unit

}
