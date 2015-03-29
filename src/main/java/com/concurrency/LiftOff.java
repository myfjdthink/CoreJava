package com.concurrency;

import java.util.concurrent.TimeUnit;

public class LiftOff implements Runnable {
	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;

	public LiftOff() {
	};

	public LiftOff(int countDown) {
		this.countDown = countDown;
	}

	public String status() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + ").";
	}

	public void run() {
		while (countDown-- > 0) {
			System.out.println(status());
			// yield告诉线程调度器 当前线程已经做完了最重要的事情了, 可以把CPU分配给别的线程了.
			Thread.yield();
		}
	}
}
