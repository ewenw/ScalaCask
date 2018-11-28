# ScalaCask
Lightweight, high throughput key-value store.

An implementation of the BitCask concept in Scala.
http://basho.com/wp-content/uploads/2015/05/bitcask-intro.pdf

Preliminary performance results:
Redis: 3451 inserts / sec
MySQL: 668 inserts / sec
ScalaCask: 27346 inserts / sec

Redis: 7448 refreshes / sec
MySQL: 0.381 refreshes / sec
ScalaCask: 806 refreshes / sec
