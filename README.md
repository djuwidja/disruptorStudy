# lmax.disruptor study

- ByteBuffer instances are passed directly from producer to handler.

- A new event instance is created every time producer publishEvent is called in EventProducer.

- producer.onData() is thread-safe.

- calling producer.onData() in multiple threads will slightly reduce performance. (Average 0.274ms over 90 tests on single thread vs average 0.339 over 90 tests on multiple threads)

- Start up of the disruptor causes performance hit. It is resolved after the first 1 second Thread.sleep() in this test.

