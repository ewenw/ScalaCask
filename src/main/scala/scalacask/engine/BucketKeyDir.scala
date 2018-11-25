package scalacask.engine

import java.io.File

import scala.collection.mutable._


class BucketKeyDir extends KeyDir {

  var buckets: Array[Buffer[Entry]] = _

  private var bucketCount: Int = _

  private var items: Long = 0

  initBuckets(1000000)

  override def put(key: String, entry: Entry): Unit = {
    val index = getIndex(key)
    val bucket = buckets(index)
    val existing = get(key)
    if (existing == null) {
      bucket.insert(0, entry)
    }
    else {
      bucket(index) = entry
    }
    items += 1
  }

  private def getIndex(key: String): Int = Math.abs(key.hashCode) % bucketCount

  override def get(key: String): Entry = {
    val bucket = buckets(getIndex(key))

    for (e <- bucket) {
      if (e.getKey.equals(key)) {
        return e
      }
    }
    null
  }

  override def containsKey(key: String): Boolean = get(key) == null

  override def load(directory: File): Unit = {}

  override def persist(directory: File): Unit = {}

  private def initBuckets(bucketCount: Int): Unit = {
    this.bucketCount = bucketCount
    buckets = Array.ofDim(bucketCount)
    for (i <- 0 until bucketCount) {
      buckets(i) = ArrayBuffer()
    }
  }

}
