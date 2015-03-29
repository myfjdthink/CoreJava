package com.concurrency;

import org.junit.Test;

public class PrinterTest {

	@Test
	public void test() {
		for (int i = 0; i < 10; i++) {
			new Thread(new Printer()).start();
		}
	}

}
