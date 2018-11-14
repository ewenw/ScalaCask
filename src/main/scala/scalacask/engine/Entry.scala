package scalacask.engine

import scala.beans.BeanProperty

class Entry(@BeanProperty var key: String,
            @BeanProperty var fileId: Int,
            @BeanProperty var size: Int,
            @BeanProperty var pos: Long,
            @BeanProperty var time: Long)
