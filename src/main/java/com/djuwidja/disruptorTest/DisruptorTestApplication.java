package com.djuwidja.disruptorTest;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.djuwidja.disruptorTest.event.UserChangeEvent;
import com.djuwidja.disruptorTest.event.UserChangeEventFactory;
import com.djuwidja.disruptorTest.event.UserChangeEventHandler;
import com.djuwidja.disruptorTest.event.UserChangeEventProducer;
import com.djuwidja.disruptorTest.factory.DisruptorFactory;
import com.djuwidja.disruptorTest.producer.EventProducer;
import com.djuwidja.disruptorTest.util.UserChangeEventByteBufferUtil;
import com.djuwidja.disruptorTest.util.UserNameIsNullException;
import com.djuwidja.disruptorTest.util.UserNameOverflowException;

public class DisruptorTestApplication {
	private static int TEST_ITERATION = 10;
	private static int TEST_PER_ITERATION =10;

	public static void main(String[] args) throws Exception {	
		EventProducer<UserChangeEvent> producer = DisruptorFactory.createProducer(
				1024, UserChangeEventFactory.class, UserChangeEventHandler.class, UserChangeEventProducer.class);
		
		Random rand = new Random();
		System.out.println("Single thread event production test.");
		ByteBuffer sbb = UserChangeEventByteBufferUtil.createByteBuffer();
		for (int j = 0; j < TEST_ITERATION; j++) {
			for (int i = 0; i < TEST_PER_ITERATION; i++) {
				emitEvent(producer, rand, sbb);
			}	
			Thread.sleep(1000);
		}
		System.out.println("==============================================");
		
		System.out.println("Multithreaded event production test.");
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int j = 0; j < TEST_PER_ITERATION; j++) {
			es.submit(() -> {
				try {
					ByteBuffer mbb = UserChangeEventByteBufferUtil.createByteBuffer(); //ByteBuffer cannot be shared across threads.
					for (int i = 0; i < TEST_ITERATION; i++) {
						emitEvent(producer, rand, mbb);
						Thread.sleep(1000);
					}			
				}
				catch (final Exception e) {
					// something wrong has happened;
				}
			});
		}
		es.shutdown();
	}

	private static void emitEvent(EventProducer<UserChangeEvent> producer, Random rand, ByteBuffer bb)
			throws UserNameIsNullException, UserNameOverflowException {
		int userId = 10000 + rand.nextInt(89999);
		String userName = String.format("userName_%d", userId);
		
		long now = System.nanoTime();
		UserChangeEventByteBufferUtil.writeByteBuffer(bb, userId, userName, now);
		producer.onData(bb);
	}

}
