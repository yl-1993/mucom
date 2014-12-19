package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

public class VerySlowHarmonyGenerator implements HarmonyGenerator {

	@Override
	public void generateHarmony(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		HashMap<Integer, Integer> nextHashMap = new HashMap<>();
		nextHashMap.put(1, 4);
		nextHashMap.put(4, 5);
		nextHashMap.put(5, 1);
		uniquePart.addHarmonic(new Time(0, 0), 1, "135", 1);
		int current = 1;
		int repeat = 1;
		for (int i = 2; i < uniquePart.getBarsCount() - 2; i += 2) {
			int variation = parameter.get(Constant.VARIATION_STRING);
			double coefficient = 0.5 * Common.getCoefficient(variation, 0, 100);
			boolean increase = test(repeat * (repeat + 1) * coefficient);
			if (increase) {
				current = nextHashMap.get(current);
				repeat = 0;
			}
			repeat++;
			uniquePart.addHarmonic(new Time(i, 0), current, "135", 1);
		}
		if (uniquePart.getBarsCount() == 4) {
			uniquePart.addHarmonic(new Time(1, 0), 4, "135", 1);
			uniquePart.addHarmonic(new Time(2, 0), 5, "135", 1);
			uniquePart.addHarmonic(new Time(3, 0), 1, "135", 1);
			return;
		} else {
			uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 2, 0), 1, "135", 1);
			return;
		}
	}

	private boolean test(double probability) {
		return Common.getRandomDouble(0, 1) < probability;
	}

}
