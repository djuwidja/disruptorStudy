package com.djuwidja.disruptorTest.event;

import com.lmax.disruptor.EventHandler;

public class UserChangeEventHandler implements EventHandler<UserChangeEvent>{
	
	public void onEvent(UserChangeEvent event, long sequence, boolean endOfBatch) {
		long now = System.nanoTime();
		double processTime = (now - event.getEventTime()) * 0.000001f;
//		System.out.println(String.format("Event: %d %s. Process time = %fms", event.getUserId(), event.getName(), processTime));
		System.out.println(String.format("%f", processTime));
	}
}
