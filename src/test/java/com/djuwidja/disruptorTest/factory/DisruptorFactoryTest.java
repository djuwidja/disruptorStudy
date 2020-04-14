package com.djuwidja.disruptorTest.factory;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.djuwidja.disruptorTest.event.UserChangeEvent;
import com.djuwidja.disruptorTest.event.UserChangeEventFactory;
import com.djuwidja.disruptorTest.event.UserChangeEventHandler;
import com.djuwidja.disruptorTest.event.UserChangeEventProducer;
import com.djuwidja.disruptorTest.producer.EventProducer;

public class DisruptorFactoryTest {
	@Test
	public void testCreateProducer() throws Exception {
		EventProducer<UserChangeEvent> producer = DisruptorFactory.createProducer(
				1024,
				UserChangeEventFactory.class, 
				UserChangeEventHandler.class,
				UserChangeEventProducer.class);
		Assert.assertNotNull(producer);
	}
	
	@Test
	public void testCreateProducerNoFactory() {
		boolean isExceptionThrown = false;
		try {
			DisruptorFactory.createProducer(
					1024,
					null, 
					UserChangeEventHandler.class,
					UserChangeEventProducer.class);
		}
		catch (final Exception e) {
			isExceptionThrown = true;
		}
		
		if (!isExceptionThrown) {
			Assert.fail("Exception is not thrown");
		}
	}
	
	@Test
	public void testCreateProducerNoHandlerFactory() {
		boolean isExceptionThrown = false;
		try {
			DisruptorFactory.createProducer(
					1024,
					UserChangeEventFactory.class, 
					null,
					UserChangeEventProducer.class);
		}
		catch (final Exception e) {
			isExceptionThrown = true;
		}
		
		if (!isExceptionThrown) {
			Assert.fail("Exception is not thrown");
		}
	}
	
	@Test
	public void testCreateProducerNoProducerFactory() {
		boolean isExceptionThrown = false;
		try {
			DisruptorFactory.createProducer(
					1024,
					UserChangeEventFactory.class, 
					UserChangeEventHandler.class,
					null);
		}
		catch (final Exception e) {
			isExceptionThrown = true;
		}
		
		if (!isExceptionThrown) {
			Assert.fail("Exception is not thrown");
		}
	}
}
