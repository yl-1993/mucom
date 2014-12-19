package Composition.Hongyu.Essential;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * 通用的函数库
 */
public class Common {
	
	/**
	 * 随机种子
	 */
	private static Random random = new Random(new Date().getTime());
	
	/**
	 * 返回一个大于等于minimum，小于maximum的随机整数
	 * @param minimum 最小值（含）
	 * @param maximum 最大值（不含）
	 * @return 随机数
	 */
	public static int getRandomInteger(int minimum, int maximum) {
		return minimum + random.nextInt(maximum - minimum);
	}
	
	/**
	 * 返回一个大于等于minimum，小于maximum的随机小数
	 * @param minimun 最小值（含）
	 * @param maximum 最大值（不含）
	 * @return 随机数
	 */
	public static double getRandomDouble(double minimun, double maximum) {
		return minimun + (maximum - minimun) * random.nextDouble();
	}
	
	/**
	 * 设定到指定大小
	 * @param count 指定大小
	 * @param arrayList 待设定的自增长数组
	 * @param elementClass 元素类型
	 */
	public static <E> void setSize(int count, ArrayList<E> arrayList, Class<E> elementClass) {
		try {
			while (arrayList.size() < count) {
				arrayList.add(elementClass.newInstance());
			}
			while (arrayList.size() > count) {
				arrayList.remove(arrayList.size() - 1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据索引下标，在必要的时候扩张自增长数组的大小
	 * @param index 索引下标
	 * @param arrayList 待扩展的自增长数组
	 * @param elementClass 元素类型
	 */
	public static <E> void expandIfNecessary(int index, ArrayList<E> arrayList, Class<E> elementClass) {
		if (index >= arrayList.size()) {
			setSize(index + 1, arrayList, elementClass);
		}
	}
	
	/**
	 * 安全的获得自增长数组指定索引下标的内容
	 * @param index 索引下标
	 * @param arrayList 自增长数组
	 * @param elementClass 元素类型
	 * @return 获取的元素
	 */
	public static <E> E getElementSafe(int index, ArrayList<E> arrayList, Class<E> elementClass) {
		expandIfNecessary(index, arrayList, elementClass);
		return arrayList.get(index);
	}
	
	/**
	 * 判断数组是否包含指定元素
	 * @param array 数组
	 * @param element 元素
	 * @return 判断结果
	 */
	public static <E> boolean arrayContains(E[] array, E element) {
		if (array == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (element == array[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 从数组中随机获得一个元素
	 * @param array 数组
	 * @return 返回的元素
	 */
	public static <E> E getRandomElement(E[] array) {
		int index = Common.getRandomInteger(0, array.length);
		return array[index];
	}
	
	/**
	 * 返回a%b的非负数值
	 * @param a 数字1
	 * @param b 数字2
	 * @return 计算结果
	 */
	public static int positiveMod(int a, int b) {
		int result = 0;
		if (a >= 0) {
			result = a % b;
		} else {
			result = (b + a % b) % b;
		}
		return result;
	}
	
	/**
	 * 获取参数相对系数，小于中值时为线性，否则为平方
	 * @param value 参数
	 * @param min 最小值
	 * @param max 最大值
	 * @return 相对系数
	 */
	public static double getCoefficient(int value, int min, int max) {
		double position = (double)(value - min) / (double)(max - min);
		if (position <= 0)
			return 0;
		if (position <= 0.5)
			return position * 2;
		return (position * position) * 4;
	}
}
