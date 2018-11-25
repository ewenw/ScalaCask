package scalacask.engine

import java.io.{File, IOException}

class CaskManager(private var directory: File, private var sizeLimit: Long)
  extends IOManager {

  private var files: List[File] = getFilesFromFolder(directory)

  if (files.size == 0) {
    directory.mkdir()
    createFile()
  }

  private var activeFile: Int = files.size - 1

  private val keyDir: KeyDir = new BucketKeyDir()

  private val reader: Reader = new Reader(files)

  private val writer: Writer = new Writer(files(activeFile))

  def this(directory: File) = this(directory, Math.pow(10, 8).toLong)

  private def createFile(): Unit = {
    val file: File = new File(directory.getName + "/" + activeFile)
    try {
      file.createNewFile()
      files = file :: files
    } catch {
      case e: IOException => e.printStackTrace()

    }
  }

  override def set(key: String, value: Array[Byte]): Unit = {
    val entry = writer.write(key, value)
    keyDir.put(key, entry)
    if (entry.getPos > sizeLimit) {
      {
        activeFile += 1
      }
      createFile()
    }
  }

  override def read(key: String): Array[Byte] = {
    val entry = keyDir.get(key)
    if (entry == null) {
      throw new IllegalArgumentException("Could not find key.");
    }
    reader.read(entry.getFileId, entry.getPos, entry.getSize)
  }

  override def nuke(): Unit = {
    close()
    for (file <- files if !file.delete()) {
      throw new IOException("Couldn't delete file " + file.getName)
    }
  }

  override def close(): Unit = {
    keyDir.persist(new File(directory.getAbsolutePath + "keydir"))
    reader.close()
    writer.close()
  }

  private def getFilesFromFolder(folder: File): List[File] = {
    var files = List[File]()
    if (!folder.exists) {
      return files
    }
    for (file <- folder.listFiles()) {
      if (!file.isDirectory) {
        files = file :: files
      }
    }
    files
  }

}