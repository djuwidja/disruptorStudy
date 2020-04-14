package com.djuwidja.disruptorTest.event;

import lombok.Getter;
import lombok.Setter;

public class UserChangeEvent {
	@Getter @Setter private int userId;
	@Getter @Setter private String name;
}
