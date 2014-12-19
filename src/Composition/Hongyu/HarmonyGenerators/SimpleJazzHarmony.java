package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

/**
 * SimpleRandomHarmony
 */
public class SimpleJazzHarmony implements HarmonyGenerator {

	@Override
	public void generateHarmony(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int mode = Common.getRandomInteger(1, 4);
		if (mode == 3)
			mode = 1;
		int sec_placement = Common.getRandomInteger(1, uniquePart.getMeter());

		if (uniquePart.getMeter() == 2)
			sec_placement = 1;
		if (uniquePart.getMeter() == 4)
			sec_placement = 2;
		if (uniquePart.getMeter() == 3)
			sec_placement = 2;
		if (uniquePart.getMeter() == 5)
			sec_placement = Common.getRandomInteger(2, 4);
		if (uniquePart.getMeter() == 6)
			sec_placement = 3;
		if (uniquePart.getMeter() == 7)
			sec_placement = Common.getRandomInteger(3, 5);
		if (uniquePart.getMeter() == 8)
			sec_placement = 4;

		if (mode == 1) {
			if (Common.getRandomInteger(0, 2) == 0)
				uniquePart.addHarmonic(new Time(0, 0), 1, "1357", 1);
			else
				uniquePart.addHarmonic(new Time(0, 0), 3, "1357", 1);

			for (int i = 1; i < uniquePart.getBarsCount() - 2; i++) {
				int basis = Common.getRandomInteger(1, 8);
				if (basis == 4) {
					if (Common.getRandomInteger(0, 2) == 0)
						uniquePart.addHarmonic(new Time(i, 0), basis, "1357", 1);
					else
						uniquePart.addHarmonic(new Time(i, 0), basis, "1356", 1);
				} else
					uniquePart.addHarmonic(new Time(i, 0), basis, "1357", 1);
			}

			if (Common.getRandomInteger(0, 2) == 0)
				uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 2, 0), 5, "1357", 1);
			else
				uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 2, 0), 7, "1357", 1);

			if (Common.getRandomInteger(0, 2) == 0)
				uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 1, 0), 1, "135", 1);
			else
				uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 1, 0), 1, "1356", 1);
		} else {
			if (Common.getRandomInteger(0, 2) == 0)
				uniquePart.addHarmonic(new Time(0, 0), 1, "1357", 1);
			else
				uniquePart.addHarmonic(new Time(0, 0), 3, "1357", 1);

			for (int i = 0; i < uniquePart.getBarsCount() - 1; i++) {
				if (i != 0) {
					int basis = Common.getRandomInteger(1, 8);
					if (basis == 4) {
						if (Common.getRandomInteger(0, 2) == 0)
							uniquePart.addHarmonic(new Time(i, 0), basis, "1357", 1);
						else
							uniquePart.addHarmonic(new Time(i, 0), basis, "1356", 1);
					} else
						uniquePart.addHarmonic(new Time(i, 0), basis, "1357", 1);
				}
				if (i != uniquePart.getBarsCount() - 2) {
					int basis = Common.getRandomInteger(1, 8);
					if (basis == 4) {
						if (Common.getRandomInteger(0, 2) == 0)
							uniquePart.addHarmonic(new Time(i, sec_placement), basis,
									"1357", 1);
						else
							uniquePart.addHarmonic(new Time(i, sec_placement), basis,
									"1356", 1);
					} else
						uniquePart.addHarmonic(new Time(i, sec_placement), basis,
								"1357", 1);
				}
			}

			if (Common.getRandomInteger(0, 2) == 0)
				uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 1, 0), 1, "135",1);
			else
				uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 1, 0), 3, "1357",1);
		}

	}


}
