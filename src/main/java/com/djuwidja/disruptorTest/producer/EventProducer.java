package com.djuwidja.disruptorTest.producer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.EventTranslatorOneArg;

import java.nio.ByteBuffer;

public abstract class EventProducer<T> {
	private final RingBuffer<T> ringBuffer;
	
	public EventProducer(RingBuffer<T> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
		
	protected abstract void readEventByteBuffer(T event, ByteBuffer bb);
	
	private EventTranslatorOneArg<T, ByteBuffer> TRANSLATOR = 
		new EventTranslatorOneArg<T, ByteBuffer>(){
			public void translateTo(T event, long sequence, ByteBuffer bb) {
//				System.out.println(String.format("Read ByteBuffer instance %s, event instance %s", bb, event));
				readEventByteBuffer(event, bb);
			}
		};
	
	public void onData(ByteBuffer bb) {
		ringBuffer.publishEvent(TRANSLATOR, bb);
	}
}
