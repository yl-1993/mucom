package Composition.Hongyu.GeneratorSelections;

import java.util.HashMap;

import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.GeneratorSelection;

public class GeneratorSelectionGetter {

	public static GeneratorSelection getGeneratorSelection(HashMap<String, Integer> parameter) {
		int variation = parameter.get(Constant.VARIATION_STRING);
		//起伏较大的组合
		if (inRange(variation, 80, Integer.MAX_VALUE)) {
			return new VariousSelection(parameter);
		}
		return new CustomSelection(parameter);
	}
	
	/**
	 * 检查value的数值是否大于等于minimum且小于maximum
	 * @param value 输入数值
	 * @param minimum 最小值（包含）
	 * @param maximum 最大值（不包含）
	 * @return 是否满足上述条件
	 */
	public static boolean inRange(int value, int minimum, int maximum) {
		return (value >= minimum && value < maximum);
	}
	
}
