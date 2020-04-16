package com.djuwidja.disruptorTest.util;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import com.djuwidja.disruptorTest.event.UserChangeEvent;

import org.junit.Assert;

public class UserChangeEventByteBufferUtilTest {
	
	@Test
	public void testUserNameChangeUtil() throws UserChangeEventException {
		ByteBuffer bb = UserChangeEventByteBufferUtil.createByteBuffer();
		
		long eventTime = System.nanoTime();
		int userId = 10000;
		String userName = String.format("userName_%d", userId);	
		 UserChangeEventByteBufferUtil.writeByteBuffer(bb, userId, userName, eventTime);
		
		//reset position as if the the ByteBuffer is brand new.		
		UserChangeEvent event = new UserChangeEvent();
		UserChangeEventByteBufferUtil.readByteBuffer(event, bb);
		
		Assert.assertEquals(userId, event.getUserId());
		Assert.assertEquals(userName, event.getName());
		
		// Exception case - user name is null
		boolean isExceptionThrown = false;
		try {
			UserChangeEventByteBufferUtil.writeByteBuffer(bb, 1000000, null, eventTime);
		}
		catch (final UserNameIsNullException e) {
			isExceptionThrown = true;
		}
		
		if (!isExceptionThrown) {
			Assert.fail("UserNameIsNullException was not thrown.");
		}
		
		// Exception case = user name length
		UserChangeEventByteBufferUtil.writeByteBuffer(bb, 1000000, "01234567890123456789012345678901234567890123456789", eventTime); // success case
		
		isExceptionThrown = false;
		try {
			UserChangeEventByteBufferUtil.writeByteBuffer(bb, 1000000, "012345678901234567890123456789012345678901234567891", eventTime);
		}
		catch (final UserNameOverflowException e) {
			isExceptionThrown = true;
		}
		
		if (!isExceptionThrown) {
			Assert.fail("UserNameLengthException was not thrown.");
		}
		
	}

}
