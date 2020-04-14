package com.djuwidja.disruptorTest.event;

import com.lmax.disruptor.EventFactory;

public class UserChangeEventFactory implements EventFactory<UserChangeEvent>{
	public UserChangeEvent newInstance() {
		return new UserChangeEvent();
	}
}
