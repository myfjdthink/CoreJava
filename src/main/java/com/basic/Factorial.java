package com.basic;

/**
 * N �Ľ׳�
 * 
 * @author Jude
 * 
 */
public class Factorial {
	/**
	 * ����ͨ�� for ѭ��
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
	 * ��β�ݹ�
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
