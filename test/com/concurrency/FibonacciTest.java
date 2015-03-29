package com.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.BeforeClass;
import org.junit.Test;

public class FibonacciTest {
	private static int threads;

	@BeforeClass
	public static void beforeClass() {
		threads = 10;
	}

	@Test
	public void testRun() {
		long startMillis = System.currentTimeMillis();
		for (int i = 0; i < threads; i++) {
			new Fibonacci(i).run();
		}
		long millis = System.currentTimeMillis() - startMillis;
		System.out.println("Time\t" + millis);
	}

	@Test
	public void testThread() {
		long startMillis = System.currentTimeMillis();
		for (int i = 1; i < threads; i++) {
			new Thread(new Fibonacci(i)).start();
		}
		long millis = System.currentTimeMillis() - startMillis;
		System.out.println("Thread Time\t" + millis);
	}

	/**
	 * 使用 Executor
	 */
	@Test
	public void testCachedThreadPool() {
		long startMillis = System.currentTimeMillis();
		ExecutorService extx = Executors.newCachedThreadPool();
		for (int i = 1; i < threads; i++) {
			extx.execute(new Fibonacci(i));
		}
		extx.shutdown();
		long millis = System.currentTimeMillis() - startMillis;
		System.out.println("Thread Time\t" + millis);
	}

	/**
	 * 预先分配线程池
	 */
	@Test
	public void testFixedThreadPool() {
		long startMillis = System.currentTimeMillis();
		ExecutorService extx = Executors.newFixedThreadPool(threads);
		for (int i = 1; i < threads; i++) {
			extx.execute(new Fibonacci(i));
		}
		extx.shutdown();
		long millis = System.currentTimeMillis() - startMillis;
		System.out.println("Thread Time\t" + millis);
	}

	/**
	 * 使用单线程来执行任务,任务会排队执行.
	 */
	@Test
	public void testSingleThreadExecutor() {
		long startMillis = System.currentTimeMillis();
		ExecutorService extx = Executors.newSingleThreadExecutor();
		for (int i = 1; i < threads; i++) {
			extx.execute(new Fibonacci(i));
		}
		// shutdown 可以防止新任务提交给 Executor
		extx.shutdown();
		long millis = System.currentTimeMillis() - startMillis;
		System.out.println("Thread Time\t" + millis);
	}

	/**
	 * 使用单线程来执行任务,任务会排队执行.
	 */
	@Test
	public void testCall() {
		ExecutorService extx = Executors.newCachedThreadPool();
		for (int i = 1; i < threads; i++) {
			Future<Integer> future = extx.submit((Callable) new Fibonacci(i));
			try {
				System.out.println(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		extx.shutdown();

	}
}
