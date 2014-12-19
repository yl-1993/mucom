package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * SimpleRandomMelody
 */
public class ExtremeVariousMelodyGenerator implements MelodyGenerator {

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
		
		int noteHigh = basis;
		int noteLow = basis;
		int note = 1;
		
		int pitch = parameter.get(Constant.PITCH_STRING);
		if (pitch <= 93) noteHigh += 7;
		else if (pitch <= 96) noteHigh += 11;
		else noteHigh += 14;
		
		if (pitch >= 7) noteLow -= 7;
		else if (pitch >= 4) noteLow -= 11;
		else noteLow -= 14;
		
		int delta = 0;
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			noteHigh = uniquePart.alignPitchToHarmonic(i, noteHigh);
			noteLow = uniquePart.alignPitchToHarmonic(i, noteLow);
			if (Common.getRandomInteger(0, 2) == 0) {
				uniquePart.setEventPitch(i, noteHigh);
				note = noteHigh;
			}
			else {
				uniquePart.setEventPitch(i, noteLow);
				note = noteLow;
			}
			if (i < uniquePart.getEventsCount() - 2)
			{
				delta = Common.getRandomInteger(-1, 2);
				noteHigh += delta;
				delta = Common.getRandomInteger(-1, 2);
				noteLow += delta;
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
