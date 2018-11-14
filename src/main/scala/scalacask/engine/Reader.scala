package scalacask.engine

import java.io.{File, RandomAccessFile}

class Reader(private var files: List[File]) {
  private var readFiles = List[RandomAccessFile]()

  setFiles(files)


  def setFiles(files: List[File]): Unit = {
    this.readFiles = files.map(new RandomAccessFile(_, "r"))
  }

  def read(fileId: Int, pos: Long, size: Int): Array[Byte] = synchronized {
    val buffer: Array[Byte] = Array.ofDim[Byte](size)
    readFiles(fileId).seek(pos)
    readFiles(fileId).read(buffer)
    buffer
  }

  def close(): Unit = {
    for (file <- readFiles) {
      file.close()
    }
  }

}
