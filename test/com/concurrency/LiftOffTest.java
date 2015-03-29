package com.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.concurrency.LiftOff;

public class LiftOffTest {
	private static int threads;

	@BeforeClass
	public static void beforeClass() {
		threads = 5;
	}
	
	@Test
	public void testRun() {
		LiftOff luanch = new LiftOff();
		luanch.run();
	}

	@Test
	public void testThread() {
		Thread t = new Thread(new LiftOff());
		t.start();
	}

	@Test
	public void testThreads() {
		for (int i = 0; i < threads; i++) {
			new Thread(new LiftOff()).start();
			System.out.println("Waiting for LiftOff");
		}
	}
	
	@Test
	public void testCachedThreadPool() {
		ExecutorService extx = Executors.newCachedThreadPool();
		for (int i = 1; i < threads; i++) {
			extx.execute(new SleepingTask());
		}
		extx.shutdown();
	}
}
