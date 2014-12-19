package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

/**
 * 基于SimpleRandomHarmony的快速和弦部分，处理了重复性与参数
 */
public class NormalHarmonyGenerator implements HarmonyGenerator {
	
	int repeat = 1;
	
	@Override
	public void generateHarmony(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		uniquePart.addHarmonic(new Time(0, 0), 1, "135", 1);
		int[] baseNotes = new int[] {1, 4, 5};
		int index = 0;
		for (int i = 0; i < uniquePart.getBarsCount() - 1; i++) {
			if (i != 0) {
				index = getIndex(index, parameter);
				if (i == uniquePart.getBarsCount() - 2 && index == 2 && repeat >= 2)
					index--;
				if (i != uniquePart.getBarsCount() / 2 - 1)
					uniquePart.addHarmonic(new Time(i, 0), baseNotes[index], "135", 0.9);
				else
					uniquePart.addHarmonic(new Time(i, 0), baseNotes[index], "135", 0.95);
			}
			if (i != uniquePart.getBarsCount() - 2 &&
					i != uniquePart.getBarsCount() / 2 - 1) {
				index = getIndex(index, parameter);
				uniquePart.addHarmonic(new Time(i, 2), baseNotes[index], "135", 0.8);
			}
		}
		uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 2, 2), 5, "135", 0.9);
		uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 1, 0), 1, "135", 1);
	}
	
	/**
	 * 获取下一个基准音的索引
	 * @param index 当前基准音的索引
	 * @param parameter 音乐参数
	 * @return
	 */
	private int getIndex(int index, HashMap<String, Integer> parameter) {
		int variation = parameter.get(Constant.VARIATION_STRING);
		double coefficient = 0.16 * Common.getCoefficient(variation, 0, 100);
		if (test(coefficient * repeat * (repeat + 1))) {
			if (test(0.25)) {
				index = (index - 1 + 3) % 3;
			}
			else {
				index = (index + 1) % 3;
			}
			repeat = 1;
		}
		else {
			repeat = repeat + 1;
		}
		return index;
	}

	private boolean test(double probability) {
		if (probability > Common.getRandomDouble(0, 1))
			return true;
		else
			return false;
	}
}
