package com.djuwidja.disruptorTest.event;

import java.nio.ByteBuffer;

import com.djuwidja.disruptorTest.producer.EventProducer;
import com.djuwidja.disruptorTest.util.UserChangeEventByteBufferUtil;
import com.lmax.disruptor.RingBuffer;

public class UserChangeEventProducer extends EventProducer<UserChangeEvent>{

	public UserChangeEventProducer(RingBuffer<UserChangeEvent> ringBuffer) {
		super(ringBuffer);
	}

	@Override
	protected void readEventByteBuffer(UserChangeEvent event, ByteBuffer bb) {
		UserChangeEventByteBufferUtil.readByteBuffer(event, bb);
	}
}
