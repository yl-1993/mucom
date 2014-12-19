package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

/**
 * 基于SimpleRandomHarmony的慢速和弦部分，处理了重复性与参数
 */
public class SlowHarmonyGenerator implements HarmonyGenerator {

	@Override
	public void generateHarmony(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		HashMap<Integer, Integer> nextHashMap = new HashMap<>();
		nextHashMap.put(1, 4);
		nextHashMap.put(4, 5);
		nextHashMap.put(5, 1);
		
		int current = 1;
		int repeat = 1;
		uniquePart.addHarmonic(new Time(0, 0), 1, "135", 1);
		for (int i = 1; i < uniquePart.getBarsCount() - 2; i++) {
			int variation = parameter.get(Constant.VARIATION_STRING);
			double coefficient = 0.2 * Common.getCoefficient(variation, 0, 100);
			boolean increase = test(repeat * (repeat + 1) * coefficient);
			if (increase) {
				current = nextHashMap.get(current);
				repeat = 1;
			} else {
				repeat = repeat + 1;
			}
			if (i == uniquePart.getBarsCount() - 3 && current != 4 && repeat >= 2)
				current = 4;
			uniquePart.addHarmonic(new Time(i, 0), current, "135", 1 - 0.2 * (i % 2));
		}
		uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 2, 0), 5, "135", 1);
		uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 1, 0), 1, "135", 1);
	}
	
	private boolean test(double probability) {
		if (probability > Common.getRandomDouble(0, 1))
			return true;
		else
			return false;
	}
}
