package com.basic;

/**
 * @author judefeng
 * @date 2015-3-30 下午04:37:54 打印回旋矩阵
 */
public class Tangle {
	public static void main(String[] args) {

		printNums(initNums(10));
	}

	public static int[][] initNums(int len) {
		int[][] nums = new int[len][len];
		// 标记那些未知已经有数字了
		int num = 1;
		int all = len * len;
		int i = len, j = len;
		// 数字增长的方向
		int direction = 0;
		while (all > 0) {
			if (nums[i % len][j % len] == 0) {
				nums[i % len][j % len] = num++;
				all--;
			}

			// 根据方向改变下标
			switch (direction) {
			case 0:
				i++;
				break;
			case 1:
				j++;
				break;
			case 2:
				i--;
				break;
			case 3:
				j--;
				break;
			}

			// 如果下一个位置已经有值了, 就改变方向。还要回退i和j的值。
			if (nums[i % len][j % len] > 0) {
				switch (direction) {
				case 0:
					i--;
					break;
				case 1:
					j--;
					break;
				case 2:
					i++;
					break;
				case 3:
					j++;
					break;
				}
				direction = (direction + 1) % len;
			}
			// System.out.println(String.format("d:%s \t i: %s \t j: %s \t num:%s",
			// direction, i, j, num));
		}
		return nums;
	}
	
	// 打印数组
	public static void printNums(int[][] nums) {
		for (int i = 0; i < nums.length; i++) {
			for (int j : nums[i]) {
				System.out.print(j + "\t ");
			}
			System.out.println();
		}

	}
}
