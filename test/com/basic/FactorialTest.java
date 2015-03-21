package com.basic;

import static org.junit.Assert.*;

import org.junit.Test;

public class FactorialTest {

	@Test
	public void testUseFor() {
		assertEquals(1, Factorial.useFor(1));
		assertEquals(6, Factorial.useFor(3));
		assertEquals(24, Factorial.useFor(4));
		assertEquals(120, Factorial.useFor(5));
	}

	@Test
	public void testUseRecursion() {
		assertEquals(1, Factorial.useRecursion(1, 1));
		assertEquals(6, Factorial.useRecursion(3, 1));
		assertEquals(24, Factorial.useRecursion(4, 1));
		assertEquals(120, Factorial.useRecursion(5, 1));
	}

}
