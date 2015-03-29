package com.concurrency;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * 打印斐波那契数列
 * 
 * @author Jude
 * 
 */
public class Fibonacci implements Runnable, Callable<Integer> {
	private int count;
	private final int n;

	public Fibonacci(int n) {
		this.n = n;
	}

	public Integer next() {
		return fib(count++);
	}

	private int fib(int n) {
		if (n < 2)
			return 1;
		return fib(n - 2) + fib(n - 1);
	}

	public void run() {
		Integer[] sequence = new Integer[n];
		for (int i = 0; i < n; i++)
			sequence[i] = next();
		System.out.println("Seq. of " + n + ": " + Arrays.toString(sequence));
	}

	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += next();
		}
		return sum;
	}
}
