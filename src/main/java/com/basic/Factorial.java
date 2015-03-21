package com.basic;

/**
 * N 的阶乘
 * 
 * @author Jude
 * 
 */
public class Factorial {
	/**
	 * 用普通的 for 循环
	 * 
	 * @param n
	 * @return
	 */
	public static int useFor(int n) {
		int sum = 1;
		for (int i = 1; i <= n; i++) {
			sum = sum * i;
		}
		return sum;
	}

	/**
	 * 用尾递归
	 * 
	 * @param n
	 * @return
	 */
	public static int useRecursion(int n, int sum) {
		if (n == 1)
			return sum;
		return useRecursion(n - 1, n * sum);
	}
}
