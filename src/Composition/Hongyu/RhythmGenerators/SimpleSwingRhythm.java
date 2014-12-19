package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePhrase;

public class SimpleSwingRhythm implements RhythmGenerator {

	@Override
	public void generateRhythm(UniquePhrase uniquePhrase,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int bars = uniquePhrase.getBars();

		if (uniquePhrase.isEndsSentence())
			bars--;

		boolean must_follow = false;

		if (Common.getRandomInteger(0, 3) != 0) {
			if (Common.getRandomInteger(0, 2) == 0) {
				uniquePhrase.addEvent(
						new Time(-1, uniquePhrase.getMeter() - 0.25),
						new Time(-1, uniquePhrase.getMeter()),
						1);
				must_follow = true;
			} else {
				uniquePhrase.addEvent(
						new Time(-1, uniquePhrase.getMeter() - 0.5),
						new Time(-1, uniquePhrase.getMeter()),
						1);
				if (Common.getRandomInteger(0, 2) == 0)
					must_follow = true;
			}
		}

		for (int i = 0; i < bars; i++) {
			int met = uniquePhrase.getMeter();

			if (i == bars - 1)
				met--;

			for (int b = 0; b < met; b += 1) {
				int t = Common.getRandomInteger(0, 4);

				if (must_follow)
					t = Common.getRandomInteger(0, 3);

				if (b == met - 1)
					t = Common.getRandomInteger(1, 3);

				if (t == 0) {
					uniquePhrase.addEvent(new Time(i, b), new Time(i, b + 0.75), 1);
					uniquePhrase.addEvent(new Time(i, b + 0.75), new Time(i, b + 1), 0.7);
					must_follow = true;
				}
				if (t == 1) {
					uniquePhrase.addEvent(new Time(i, b), new Time(i, b + 1), 1);
					must_follow = false;
				}
				if (t == 2) {
					uniquePhrase.addEvent(new Time(i, b), new Time(i, b + 0.5), 0.7);
					uniquePhrase.addEvent(new Time(i, b + 0.5), new Time(i, b + 1), 0.7);
					must_follow = false;
				}
				if (t == 3) {
					if (Common.getRandomInteger(0, 2) == 0)
						uniquePhrase.addEvent(new Time(i, b + 0.5), new Time(i, b + 1), 0.7);
					else {
						uniquePhrase.addEvent(new Time(i, b + 0.75), new Time(i, b + 1), 0.7);
						must_follow = true;
					}
				}

			}
		}

		if (uniquePhrase.isEndsSentence())
			uniquePhrase.addEvent(
					new Time(uniquePhrase.getBars() - 1, 0),
					new Time(uniquePhrase.getBars() - 1,
							uniquePhrase.getMeter()),
							1);
	}
}
