package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

public class ChordMapHarmony implements HarmonyGenerator {
	
	int rndInt(int i, int j) {
		return Common.getRandomInteger(i, j+1);
	}

	@Override
	public void generateHarmony(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int[][] map = new int[][] { { 2, 3, 4, 5, 6 }, { 3, 5 }, { 4, 6 },
				{ 5, 2, 1 }, { 3, 6, 1 }, { 4, 2 } };

		int reset = 4;
		if (rndInt(0, 2) == 0)
			reset = 8;
		if (rndInt(0, 2) == 0)
			reset = 2;

		int cur_chord = 1;
		if (rndInt(0, 3) == 0)
			cur_chord = rndInt(1, 6);

		int r = 0;

		for (int i = 0; i < uniquePart.getBarsCount() - 2; i++) {
			if (r == 0 || rndInt(0, 2) != 0) {
				uniquePart.addHarmonic(new Time(i, 0), cur_chord, "135", 1);

				if (rndInt(0, 1) == 0) {
					if (rndInt(0, 1) == 0) {
						uniquePart.addHarmonic(new Time(i, rndInt(1,
								uniquePart.getMeter() - 1)), cur_chord, "135"
								+ rndInt(6, 7), 1);
						cur_chord = map[cur_chord - 1][rndInt(0,
								map[cur_chord - 1].length - 1)];
					} else {
						cur_chord = map[cur_chord - 1][rndInt(0,
								map[cur_chord - 1].length - 1)];
						uniquePart.addHarmonic(new Time(i, rndInt(1,
								uniquePart.getMeter() - 1)), cur_chord, "135", 1);
						cur_chord = map[cur_chord - 1][rndInt(0,
								map[cur_chord - 1].length - 1)];
					}
				} else
					cur_chord = map[cur_chord - 1][rndInt(0,
							map[cur_chord - 1].length - 1)];
			}

			r++;
			if (r >= reset) {
				r = 0;
				cur_chord = 1;
				if (rndInt(0, 3) == 0)
					cur_chord = rndInt(1, 6);
			}
		}

		if (rndInt(0, 1) == 0)
			uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 2, 0), 5, "135", 1);
		else
			uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 2, 0), 5, "1357", 1);

		uniquePart.addHarmonic(new Time(uniquePart.getBarsCount() - 1, 0), 1, "135", 1);
	}
	
}
