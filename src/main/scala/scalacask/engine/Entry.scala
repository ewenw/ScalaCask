package scalacask.engine

import scala.beans.BeanProperty

/**
  * Resembles an entry of key-value pair on the disk. Used by KeyDir.
  * @param key the key of the pair.
  * @param fileId the file ID.
  * @param size the size of the value.
  * @param pos the position of the value on the file.
  * @param time the timestamp.
  */
@SerialVersionUID(123L)
class Entry(@BeanProperty var key: String,
            @BeanProperty var fileId: Int,
            @BeanProperty var size: Int,
            @BeanProperty var pos: Long,
            @BeanProperty var time: Long) extends Serializable
