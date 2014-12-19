package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * SimpleRandomMelody
 */
public class SimpleMelodyGenerator_Style3 implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		//曲调整体向上
		int note = uniquePart.getEventBasis(0);
		int delta, count = 0;
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			note = uniquePart.alignPitchToHarmonic(i, note);
			uniquePart.setEventPitch(i, note);
			if (i < uniquePart.getEventsCount() - 2)
			{
				delta = Common.getRandomInteger(0, 3);
				count++;
				if (count % 8 == 7)
					delta = -5;
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
