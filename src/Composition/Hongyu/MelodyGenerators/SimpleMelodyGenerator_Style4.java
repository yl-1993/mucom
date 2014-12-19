package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * SimpleRandomMelody
 */
public class SimpleMelodyGenerator_Style4 implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		//前后音上下相间，起伏小
		int note = uniquePart.getEventBasis(0);
		int delta, count = 0, zero = 0;
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			note = uniquePart.alignPitchToHarmonic(i, note);
			uniquePart.setEventPitch(i, note);
			if (i < uniquePart.getEventsCount() - 2)
			{
				count++;
				delta = Common.getRandomInteger(0, 3);
				if (delta == 0)
				{
					if (zero < 2)
						zero++;
					else
					{
						zero = 0;
						delta = Common.getRandomInteger(1, 3);
					}
				}
				else
					zero = 0;
				if (count % 2 == 0)
					note += delta;
				else
					note -= delta;
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
