package scalacask.engine

import scala.collection.mutable._

@SerialVersionUID(123L)
class BucketKeyDir extends KeyDir with Serializable {

  var buckets: Array[Buffer[Entry]] = Array.ofDim(bucketCount)
  private var bucketCount: Int = 1000000
  private var items: Long = 0

  initBuckets(bucketCount)

  override def put(key: String, entry: Entry): Unit = {
    val index = getIndex(key)
    val bucket = buckets(index)
    val existing = get(key)
    if (existing == null) {
      bucket.insert(0, entry)
    }
    else {
      var entryIndex = 0
      for (existingEntry <- bucket) {
        if (existing.getKey.equals(entry.getKey)) {
          bucket(entryIndex) = entry
        }
        entryIndex += 1
      }
    }
    items += 1
  }

  override def containsKey(key: String): Boolean = get(key) == null

  override def get(key: String): Entry = {
    val bucket = buckets(getIndex(key))
    for (e <- bucket) {
      if (e.getKey.equals(key)) {
        return e
      }
    }
    null
  }

  private def getIndex(key: String): Int = Math.abs(key.hashCode) % bucketCount

  override def delete(key: String): Unit = {
    val bucket = buckets(getIndex(key))
    var entryIndex = 0
    for (e <- bucket) {
      if (e.getKey.equals(key)) {
        bucket.remove(entryIndex)
      }
      entryIndex += 1
    }
  }

  private def initBuckets(bucketCount: Int): Unit = {
    this.bucketCount = bucketCount
    buckets = Array.ofDim(bucketCount)
    for (i <- 0 until bucketCount) {
      buckets(i) = ArrayBuffer()
    }
  }
}
