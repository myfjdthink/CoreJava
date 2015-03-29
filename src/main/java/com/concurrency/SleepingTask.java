package com.concurrency;

import java.util.concurrent.TimeUnit;

public class SleepingTask extends LiftOff {

	@Override
	public void run() {
		while (countDown-- > 0) {
			System.out.println(status());
			try {
				// Ë¯Ãß0.1s
				// Thread.sleep(100);
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("uh Interrupted!");
			}
		}
	}
}
