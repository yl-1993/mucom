package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * SimpleRandomMelody
 */
public class ExtremeMonotonousMelodyGenerator implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		
		// TODO Auto-generated method stub
		int basis = uniquePart.getEventBasis(0);
		// 根据参数调整基准
		int pitchParameter = parameter.get(Constant.PITCH_STRING);
		if (pitchParameter >= 100) pitchParameter = 99;
		if (pitchParameter <= 0) pitchParameter = 0;
		int[] offsets = {-4, -4, -2, -2, 0, 0, 2, 2, 4, 4};
		basis = basis + offsets[pitchParameter / 10];
		
		int note = basis;
		int SingleNote = note;
		
		int delta = 0;
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			note = uniquePart.alignPitchToHarmonic(i, note);
			SingleNote = uniquePart.alignPitchToHarmonic(i, SingleNote);
			if (Common.getRandomInteger(0, 5) == 0)
				uniquePart.setEventPitch(i, SingleNote);
			else
				uniquePart.setEventPitch(i, note);
			if (i < uniquePart.getEventsCount() - 2)
			{
				delta = Common.getRandomInteger(-2, 3);
				SingleNote += delta;
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
