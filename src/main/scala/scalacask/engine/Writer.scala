package scalacask.engine

import java.io.{File, FileNotFoundException, FileOutputStream}
import java.nio.ByteBuffer
import java.nio.channels.FileChannel


class Writer(file: File) {

  private var channel: FileChannel = _

  private var fileId: Int = _

  setActiveFile(file, java.lang.Integer.parseInt(file.getName))

  def setActiveFile(file: File, fileId: Int): Unit = {
    try channel = new FileOutputStream(file, true).getChannel
    catch {
      case e: FileNotFoundException => e.printStackTrace()

    }
    this.fileId = fileId
  }

  def write(key: String, value: Array[Byte]): Entry = synchronized {
    channel.write(toByteBufferPair(key.getBytes))
    channel.write(toByteBufferPair(value))
    val entry: Entry = new Entry(key,
      fileId,
      value.length,
      channel.position() - value.length,
      System.currentTimeMillis())
    entry
  }

  private def toByteBufferPair(bytes: Array[Byte]): ByteBuffer = {
    val bufferSize: Int = bytes.length + java.lang.Long.BYTES
    val buffer: ByteBuffer = ByteBuffer.allocate(bufferSize)
    buffer.putLong(bytes.length)
    buffer.put(bytes)
    buffer.flip()
    buffer
  }

  def close(): Unit = {
    channel.close()
  }

}
