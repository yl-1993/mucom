package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

public class QuickHarmonyGenerator implements HarmonyGenerator {

	@Override
	public void generateHarmony(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		
		int repeat = 1;
		int basis = 1;
		uniquePart.addHarmonic(new Time(0, 0), basis, "135", 1);
		
		//每一小节的每一拍都有可能被添加和弦
		for (int i = 0; i < uniquePart.getBarsCount() - 1; i++) {
			for (int j = 0; j < uniquePart.getMeter(); j++) {
				//两种特殊时间和某些1、3拍不添加音符
				if ((i == 0 && j == 0) || (j % 2 != 0 && i % 2 != 0) ||
						(i == uniquePart.getBarsCount() - 2 && j == 2)) {
					continue;
				}
				//是否变化，如何变化
				int variation = parameter.get(Constant.VARIATION_STRING);
				double coefficient = 0.16 * Common.getCoefficient(variation, 0, 100);
				boolean change = test(repeat * (repeat + 1) * coefficient);
				if (change) {
					double[] downProbability = new double[] {0, 0.3, 0.5, 0.7, 1};
					boolean down = test(downProbability[basis - 1]);
					if (down)
						if (test(0.5) && basis >= 3)
							basis -= 2;
						else
							basis -= 1;
					else
						if (test(0.5) && basis <= 3)
							basis += 2;
						else
							basis += 1;
					repeat = 0;
				}
				repeat++;
				if (j == 0 || j == 2)
					uniquePart.addHarmonic(new Time(i, j), basis, "135", 1);
				else
					uniquePart.addHarmonic(new Time(i, j), basis, "135", 0.8);
			}
		}
		uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 2, 2), 5,
				"135", 1);
		uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 1, 0), 1,
				"135", 1);
	}

	private boolean test(double probability) {
		return (probability > Common.getRandomDouble(0, 1));
	}
}
