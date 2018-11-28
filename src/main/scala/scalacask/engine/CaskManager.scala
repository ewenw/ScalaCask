package scalacask.engine

import java.io._

class CaskManager(private var directory: File, private var sizeLimit: Long)
  extends IOManager {

  private val keyDirFile = new File(directory.getAbsolutePath + "/keyDir")
  private val keyDir = loadKeyDir
  private var files: List[File] = getFilesFromFolder(directory)
  if (files.size == 0) {
    directory.mkdir()
    createFile
  }
  private val writer: Writer = new Writer(files(activeFile))
  private var activeFile: Int = files.size - 1
  private val reader: Reader = new Reader(files)

  def this(directory: File) = this(directory, Math.pow(10, 8).toLong)

  override def set(key: String, value: Array[Byte]): Unit = {
    val entry = writer.write(key, value)
    keyDir.put(key, entry)
    if (entry.getPos > sizeLimit) {
      {
        activeFile += 1
      }
      createFile
    }
  }

  private def createFile(): Unit = {
    val file: File = new File(directory.getName + "/" + activeFile)
    try {
      file.createNewFile
      files = file :: files
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  override def read(key: String): Array[Byte] = {
    val entry = keyDir.get(key)
    if (entry == null) {
      throw new IllegalArgumentException("Could not find key " + key + ".");
    }
    reader.read(entry.getFileId, entry.getPos, entry.getSize)
  }

  override def nuke(): Unit = {
    close
    for (file <- directory.listFiles() if !file.delete) {
      throw new IOException("Couldn't delete file " + file.getName)
    }

  }

  override def close(): Unit = {
    persistKeyDir
    reader.close
    writer.close
  }

  def persistKeyDir(): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream(keyDirFile))
    oos.writeObject(keyDir)
    oos.close
  }

  def loadKeyDir(): KeyDir = {
    if (keyDirFile.exists() && !keyDirFile.isDirectory) {
      val ois = new ObjectInputStream(new FileInputStream(keyDirFile))
      val keyDir = ois.readObject.asInstanceOf[BucketKeyDir]
      ois.close
      return keyDir
    }
    return new BucketKeyDir
  }

  /**
    * Delete the value of the corresponding key.
    *
    * @param key the key.
    */
  override def delete(key: String): Unit = {
    keyDir.delete(key)
  }

  private def getFilesFromFolder(folder: File): List[File] = {
    var files = List[File]()
    if (!folder.exists) {
      return files
    }
    for (file <- folder.listFiles()) {
      if (!file.isDirectory && !file.getName.equals("keyDir")) {
        files = file :: files
      }
    }
    files
  }
}