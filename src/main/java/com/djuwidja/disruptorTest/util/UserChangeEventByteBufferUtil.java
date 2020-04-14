package com.djuwidja.disruptorTest.util;

import java.nio.ByteBuffer;

import com.djuwidja.disruptorTest.event.UserChangeEvent;

public class UserChangeEventByteBufferUtil {
	private static final int INTEGER_BYTE_SIZE = 4;
	private static final int NAME_BYTES_BUFFER_SIZE = 50;
	
	public static ByteBuffer createByteBuffer() {
		int size = INTEGER_BYTE_SIZE + INTEGER_BYTE_SIZE + NAME_BYTES_BUFFER_SIZE; // userId + string len + string contents
		ByteBuffer bb = ByteBuffer.allocate(size);
		
		return bb;
	}

	public static void writeByteBuffer(ByteBuffer bb, int userId, String userName) throws UserNameIsNullException, UserNameOverflowException {
		if (userName == null) {
			throw new UserNameIsNullException();
		}
		
		byte[] userNameBytes = userName.getBytes();
		if (userNameBytes.length > NAME_BYTES_BUFFER_SIZE) {
			throw new UserNameOverflowException();
		}
				
		bb.clear();		
		bb.putInt(userId);
		bb.putInt(userNameBytes.length);
		bb.put(userNameBytes);
	}
	
	public static void readByteBuffer(UserChangeEvent event, ByteBuffer bb) {
		bb.position(0);
		int userId = bb.getInt();
		
		int strLen = bb.getInt();		
		byte[] strByte = new byte[strLen];
		bb.get(bb.position(), strByte, 0, strByte.length);

		String updateName = new String(strByte);
		
		event.setUserId(userId);
		event.setName(updateName);
	}
	
}
