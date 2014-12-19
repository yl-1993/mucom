package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * SimpleRandomMelody
 */
public class ExtremeLowMelodyGenerator implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int note = uniquePart.getEventBasis(0);
		int pitch = parameter.get(Constant.PITCH_STRING);
		
		if (pitch >= 8) note -= 7;
		else if (pitch >= 4) note -= 10;
		else note -= 14;
		
		int baseNote = note;
		int delta = 0;
		
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			note = uniquePart.alignPitchToHarmonic(i, note);
			uniquePart.setEventPitch(i, note);
			if (i < uniquePart.getEventsCount() - 2)
			{
				delta = Common.getRandomInteger(-3, 4);
				note += delta;
				while (note > baseNote + 7)
					note -= 3;
				while (note < baseNote - 10)
					note += 3;
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
