package com.jy;

import java.util.Arrays;

public class Main {
	private static int COUNT = 5;

	public static void main(String[] args) {
		int[] numbers1 = new int[] { 1, 3, 4, 5, 0 };
		System.out.println("输入的代表扑克牌点数的数组:" + Arrays.toString(numbers1));
		System.out.println("这" + COUNT + "张扑克牌是否是顺子:" + isContinuous(numbers1));
		System.out.println();
		int[] numbers2 = new int[] { 12, 4, 0, 0, 5 };
		System.out.println("输入的代表扑克牌点数的数组:" + Arrays.toString(numbers2));
		System.out.println("这" + COUNT + "张扑克牌是否是顺子:" + isContinuous(numbers2));

	}

	/**
	 * 判断数组中代表的几张扑克牌是不是顺子
	 * 
	 * @param numbers
	 *            代表扑克牌点数的数组
	 * @return 如果是顺子则返回true,否则返回false
	 */
	public static boolean isContinuous(int[] numbers) {
		int length = numbers.length;
		if (numbers == null || length != COUNT)
			return false;
		// 先对数组进行排序
		// Arrays.sort(numbers);
		bucketSort(numbers);
		// 统计数组中0的个数和空缺的位置
		int numbersOfZero = 0;
		int numbersOfGap = 0;

		System.out.println("数组经过排序后:" + Arrays.toString(numbers));

		// 如果有0,进过排序后肯定是位于数组的前端，且从第0个元素开始
		for (int i = 0; i < length && numbers[i] == 0; i++)
			numbersOfZero++;
		// 因为一副牌最大只有2张大小王牌
		if (numbersOfZero >= 2)
			return false;
		// 第一个非0元素在数组中的位置
		int smallIndex = numbersOfZero;
		// 相邻的下一个数
		int bigIndex = smallIndex + 1;

		// 通过相邻两个数之间的差值，计算空缺的个数
		while (bigIndex < length) {
			// 如果存在相等的2个非0元素，表明在扑克牌中出现了对子，则不可能再是顺子
			if (numbers[smallIndex] == numbers[bigIndex])
				return false;
			numbersOfGap += numbers[bigIndex] - numbers[smallIndex] - 1;
			// 继续往后
			smallIndex = bigIndex;
			bigIndex++;
		}
		// 如果空缺数小于0的个数，则可以组成顺子
		return numbersOfGap <= numbersOfZero ? true : false;
	}

	/**
	 * 桶式排序
	 * 
	 * @param numbers
	 *            待排序的序列数组
	 */
	public static void bucketSort(int[] numbers) {
		// 序列中的最大、最小值
		int max = numbers[0];
		int min = numbers[0];
		// 遍历序列求出最大、最小值
		for (int number : numbers) {
			if (max < number)
				max = number;
			if (min > number)
				min = number;
		}
		// 调用重载的桶式排序
		bucketSort(numbers, min, max);
	}

	/**
	 * 桶式排序
	 * 
	 * @param numbers
	 *            待排序的序列数组
	 * @param min
	 *            序列的最小值
	 * @param max
	 *            序列的最大值
	 */
	public static void bucketSort(int[] numbers, int min, int max) {
		// 序列的长度
		int arrayLength = numbers.length;
		// 定义作为桶的数组
		// 数组的大小为max-min+1
		int[] buckets = new int[max - min + 1];
		// 计算每个元素在序列中出现的次数
		for (int i = 0; i < arrayLength; i++)
			buckets[numbers[i] - min]++;

		// 计算落入各个桶中的元素在序列中的位置
		// 根据buckets[i] = buckets[i-1] + buckets[i]计算新数组
		for (int i = 1; i < buckets.length; i++)
			buckets[i] += buckets[i - 1];

		// 定义一个临时数组，并将源数组的原样复制过去
		int[] temp = new int[arrayLength];
		System.arraycopy(numbers, 0, temp, 0, arrayLength);

		// 根据buckets中的信息，将temp中的元素插入到源数组的中的合适的位置
		// 必须从temp的最后一个元素往前取出元素来安排位置，因为buckets中的元素是递减的，即相同的两个元素是先从后往前摆放的，这样才能保证桶式排序是稳定的
		for (int i = arrayLength - 1; i >= 0; i--)
			// 考虑到序列中可能出现同一个数出现几次的情况，因此需要在取出buckets数组中某个位置的值后，需要递减
			// 桶中的数值时元素在数组中第几的位置，例如：1代表第1的位置，但最终在数组中的索引是0。所以这里先递减在赋值
			numbers[--buckets[temp[i] - min]] = temp[i];
	}

}
