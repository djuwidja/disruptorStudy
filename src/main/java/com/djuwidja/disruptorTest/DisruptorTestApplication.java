package com.djuwidja.disruptorTest;

import java.nio.ByteBuffer;
import java.util.Random;

import com.djuwidja.disruptorTest.event.UserChangeEvent;
import com.djuwidja.disruptorTest.event.UserChangeEventFactory;
import com.djuwidja.disruptorTest.event.UserChangeEventHandler;
import com.djuwidja.disruptorTest.event.UserChangeEventProducer;
import com.djuwidja.disruptorTest.factory.DisruptorFactory;
import com.djuwidja.disruptorTest.producer.EventProducer;
import com.djuwidja.disruptorTest.util.UserChangeEventByteBufferUtil;

public class DisruptorTestApplication {

	public static void main(String[] args) throws Exception {	
		EventProducer<UserChangeEvent> producer = DisruptorFactory.createProducer(
				1024, UserChangeEventFactory.class, UserChangeEventHandler.class, UserChangeEventProducer.class);
		
		Random rand = new Random();
		ByteBuffer bb = UserChangeEventByteBufferUtil.createByteBuffer();
		while (true) {
			int userId = 10000 + rand.nextInt(89999);
			String userName = String.format("userName_%d", userId);
			UserChangeEventByteBufferUtil.writeByteBuffer(bb, userId, userName);
			System.out.println(String.format("Write ByteBuffer instance %s", bb));
			producer.onData(bb);
			
			Thread.sleep(1000);
		}
	}

}
