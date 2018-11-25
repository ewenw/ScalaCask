package scalacask.engine.experiments

import java.io.{FileOutputStream, RandomAccessFile}
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.util.Random

//remove if not needed

object TestWrite {

  var rand: Random = new Random()

  def main(args: Array[String]): Unit = {
    readTest()
  }

  def writeTest(): Unit = {
    val active: FileChannel = new FileOutputStream("test", true).getChannel
    var i: Long = 0
    while (i < 1000) {
      val randStr: String = getRandString
      println("key: " + i + " val: " + randStr)
      val buffer: ByteBuffer = toBuffer(longToBytes(i), randStr.getBytes)
      println("Position before: " + active.position())
      active.write(buffer)
      println("Position after: " + active.position())
    }
    active.close()
  }

  def readTest(): Unit = {
    val key: Array[Byte] = read(35492 + 16, 8)
    println(ByteBuffer.wrap(key).getLong)
  }

  def read(pos: Long, size: Int): Array[Byte] = {
    val buffer: Array[Byte] = Array.ofDim[Byte](size)
    val raf: RandomAccessFile = new RandomAccessFile("test", "r")
    raf.seek(pos)
    raf.read(buffer)
    buffer
  }

  def getRandString(): String = {
    var s: String = ""
    for (i <- 0 until rand.nextInt(40) + 5) {
      s += (rand.nextInt(26) + 'a').toChar
    }
    s
  }

  def toBuffer(key: Array[Byte], `val`: Array[Byte]): ByteBuffer = {
    val buffer: ByteBuffer =
      ByteBuffer.allocate(key.length + `val`.length + 3 * java.lang.Long.BYTES)
    buffer.putLong(key.length).put(key).putLong(`val`.length).put(`val`)
    buffer.flip()
    buffer
  }

  def write(ch: FileChannel, content: ByteBuffer): Unit = {
    ch.write(content)
  }

  def longToBytes(x: Long): Array[Byte] = {
    val buffer: ByteBuffer = ByteBuffer.allocate(java.lang.Long.BYTES)
    buffer.putLong(x)
    buffer.array()
  }

}
