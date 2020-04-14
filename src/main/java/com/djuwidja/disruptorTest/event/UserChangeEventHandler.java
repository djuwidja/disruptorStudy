package com.djuwidja.disruptorTest.event;

import com.lmax.disruptor.EventHandler;

public class UserChangeEventHandler implements EventHandler<UserChangeEvent>{
	
	public void onEvent(UserChangeEvent event, long sequence, boolean endOfBatch) {
		System.out.println(String.format("Event: %d %s", event.getUserId(), event.getName()));
	}
}
