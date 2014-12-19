package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

public class RandomRiffHarmony implements HarmonyGenerator {

	class Offset {
		int bar;
		double pos;
	};
	
	@Override
	public void generateHarmony(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int chords = rndInt(2, 3);
		int[] basis = new int[chords];

		if (rndInt(0, 1) == 0) // tonic on the end
		{
			basis[chords - 1] = 1;

			if (chords == 2) {
				basis[0] = rndInt(3, 6);
			} else {
				basis[0] = rndInt(2, 6);
				do {
					basis[1] = rndInt(3, 6);
				} while (basis[0] == basis[1]);
			}
		} else // tonic on the front
		{
			basis[0] = 1;

			if (chords == 2) {
				basis[1] = rndInt(3, 6);
			} else {
				basis[2] = rndInt(3, 6);
				do {
					basis[1] = rndInt(2, 6);
				} while (basis[1] == basis[2]);
			}
		}

		Offset[] pattern = new Offset[chords];
		for (int i=0; i<chords; i++) {
			pattern[i] = new Offset();
		}
		
		pattern[0].bar = 0;
		pattern[0].pos = 0;

		if (chords == 2) {
			if (rndInt(0, 1) == 0) {
				pattern[1].bar = 1;
				pattern[1].pos = 0;
			} else {
				pattern[1].bar = 0;
				pattern[1].pos = uniquePart.getMeter() - 1;
			}
		} else {
			if (rndInt(0, 1) == 0) {
				pattern[1].bar = 0;
				if (uniquePart.getMeter() == 2)
					pattern[1].pos = 1;
				else if (uniquePart.getMeter() == 3)
					pattern[1].pos = 2;
				else if (uniquePart.getMeter() == 4)
					pattern[1].pos = 2;
				else if (uniquePart.getMeter() == 5)
					pattern[1].pos = 3;
				else
					pattern[1].pos = uniquePart.getMeter() - 2;
				pattern[2].bar = 1;
				pattern[2].pos = 0;
			} else {
				pattern[1].bar = 0;
				if (uniquePart.getMeter() < 5)
					pattern[1].pos = uniquePart.getMeter() - 1;
				else
					pattern[1].pos = uniquePart.getMeter() - 2;
				pattern[2].bar = 1;
				pattern[2].pos = 1;
			}
		}

		for (int i = 0; i < uniquePart.getBarsCount(); i += 2) {
			for (int c = 0; c < chords; c++) {
				uniquePart.addHarmonic(new Time(i + pattern[c].bar, pattern[c].pos),
						basis[c], "135", 1);
			}
		}
	}

	private int rndInt(int i, int j) {
		return Common.getRandomInteger(i, j + 1);
	}

}
