package Composition.Hongyu.MelodyGenerators;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import Composition.Hongyu.CompositionHongyu;
import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.MarkovChain;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

public class TestMelodyGenerator implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		// 恢复马尔科夫链
		MarkovChain markovChain = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new FileInputStream(CompositionHongyu.TRAINING_RESULT));
			markovChain = (MarkovChain) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		int note = uniquePart.getEventBasis(0);
		ArrayList<Integer> condition = new ArrayList<>();
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			note = uniquePart.alignPitchToHarmonic(i, note);
			uniquePart.setEventPitch(i, note);
			condition.add(note);
			System.out.print(note + " ");
			if (i < uniquePart.getEventsCount() - 2) {
				int backup = note;
				note = markovChain.getNote(condition);
				// 如果本次音符与上次相同，或本次音符与上次相差超过7个半音，或四选一选中，则重新获取
				if (note == backup || Math.abs(note - backup) > 4
						|| Common.getRandomInteger(0, 3) == 0) {
					note += Common.getRandomInteger(-2, 3);
				}
				if (note < uniquePart.getEventBasis(0) - 7 && Common.getRandomInteger(0, 3) == 0)
					note = uniquePart.getEventBasis(0) - Common.getRandomInteger(0, 5);
				if (note > uniquePart.getEventBasis(0) + 7 && Common.getRandomInteger(0, 3) == 0)
					note = uniquePart.getEventBasis(0) + Common.getRandomInteger(0, 5);
			}
		}
		System.out.println();
		
		// 设置最后一个音
		int last_note = 1;
		while (note > 5) {
			note -= 7;
			last_note += 7;
		}
		while (note < -3) {
			note += 7;
			last_note -= 7;
		}
		if (note == 5 && Common.getRandomInteger(0, 2) == 0) {
			last_note += 7;
		}
		uniquePart.setEventPitch(uniquePart.getEventsCount() - 1, last_note);
	}

}
