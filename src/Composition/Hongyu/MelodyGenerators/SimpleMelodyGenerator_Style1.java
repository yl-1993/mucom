package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * SimpleRandomMelody
 */
public class SimpleMelodyGenerator_Style1 implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int note = uniquePart.getEventBasis(0);
		int delta = 0;
		int mode;
		int count = 0;
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			note = uniquePart.alignPitchToHarmonic(i, note);
			uniquePart.setEventPitch(i, note);
			if (i < uniquePart.getEventsCount() - 2)
			{
				mode = Common.getRandomInteger(0, 6);
				if (mode == 0)
					delta = 3;
				if (mode == 1)
					delta = -2;
				if (mode == 2)
					delta = 3;
				if (mode == 3)
					delta = 0;
				if (mode == 4)
					delta = 2; 
				if (mode == 5)
					delta = -1;
				if (delta >= 0)
					count ++;
				else
					count = 0;
				if (count >= 5)
				{
					delta = -3;
					count = 0;
				}
				note += delta;
			}
		}

		int last_note = 1;

		while (note > 5) {
			note -= 7;
			last_note += 7;
		}

		while (note < -3) {
			note += 7;
			last_note -= 7;
		}

		if (note == 5 && Common.getRandomInteger(0, 2) == 0)
			last_note += 7;

		uniquePart.setEventPitch(uniquePart.getEventsCount() - 1, last_note);
	}

}
