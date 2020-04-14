package com.djuwidja.disruptorTest.factory;
import java.lang.reflect.InvocationTargetException;

import com.djuwidja.disruptorTest.producer.EventProducer;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class DisruptorFactory {
	public static <T> EventProducer<T> createProducer(
			int bufferSize, 
			Class<? extends EventFactory<T>> factoryCls, 
			Class<? extends EventHandler<T>> handlerCls, 
			Class<? extends EventProducer<T>> producerCls) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		EventFactory<T> factory = factoryCls.getDeclaredConstructor().newInstance();
		
		Disruptor<T> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE);
		
		EventHandler<T> handler = handlerCls.getDeclaredConstructor().newInstance();	
		disruptor.handleEventsWith(handler);
				
		RingBuffer<T> ringBuffer = disruptor.start();
		EventProducer<T> producer = producerCls.getDeclaredConstructor(ringBuffer.getClass()).newInstance(ringBuffer);
		return producer;
	}
}
