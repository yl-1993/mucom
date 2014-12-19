package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

public class AdvancedRandomHarmony implements HarmonyGenerator {

	@Override
	public void generateHarmony(UniquePart up,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int mode = Common.getRandomInteger(1, 4);
		if (mode == 3)
			mode = 1;
		int sec_placement = Common.getRandomInteger(1, up.getMeter());

		if (up.getMeter() == 2)
			sec_placement = 1;
		if (up.getMeter() == 4)
			sec_placement = 2;
		if (up.getMeter() == 3)
			sec_placement = 2;
		if (up.getMeter() == 5)
			sec_placement = Common.getRandomInteger(2, 4);
		if (up.getMeter() == 6)
			sec_placement = 3;
		if (up.getMeter() == 7)
			sec_placement = Common.getRandomInteger(3, 5);
		if (up.getMeter() == 8)
			sec_placement = 4;

		if (mode == 1) {
			up.addHarmonic(new Time(0, 0), 1, "135", 1);

			for (int i = 1; i < up.getBarsCount() - 2; i++) {
				int basis = Common.getRandomInteger(1, 8);
				if (basis == 4)
					up.addHarmonic(new Time(i, 0), basis, "1356", 1);
				else if (basis == 5)
					up.addHarmonic(new Time(i, 0), basis, "1357", 1);
				else
					up.addHarmonic(new Time(i, 0), basis, "135", 1);
			}

			up.addHarmonic(new Time(up.getBarsCount() - 2, 0), 5, "1357", 1);
			up.addHarmonic(new Time(up.getBarsCount() - 1, 0), 1, "135", 1);
		} else {
			up.addHarmonic(new Time(0, 0), 1, "135", 1);

			for (int i = 0; i < up.getBarsCount() - 1; i++) {
				if (i != 0) {
					int basis = Common.getRandomInteger(1, 8);
					if (basis == 4)
						up.addHarmonic(new Time(i, 0), basis, "1356", 1);
					else if (basis == 5)
						up.addHarmonic(new Time(i, 0), basis, "1357", 1);
					else
						up.addHarmonic(new Time(i, 0), basis, "135", 1);
				}
				if (i != up.getBarsCount() - 2) {
					int basis = Common.getRandomInteger(1, 8);
					if (basis == 4)
						up.addHarmonic(new Time(i, sec_placement), basis,
								"1356", 1);
					else if (basis == 5)
						up.addHarmonic(new Time(i, sec_placement), basis,
								"1357", 1);
					else
						up.addHarmonic(new Time(i, sec_placement), basis,
								"135", 1);
				}
			}

			up.addHarmonic(new Time(up.getBarsCount() - 2, sec_placement), 5,
					"1357", 1);
			up.addHarmonic(new Time(up.getBarsCount() - 1, 0), 1, "135", 1);
		}
	}
	
}
