package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePhrase;

/**
 * RandomStaticRhythm
 */
public class DefaultRhythmGenerator implements RhythmGenerator {

	@Override
	public void generateRhythm(UniquePhrase uniquePhrase,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int bars = uniquePhrase.getBars();

		if (uniquePhrase.isEndsSentence())
			bars--;

		Time time = new Time(0, 0);

		int start = Common.getRandomInteger(0, 5);

		if (start == 0) {
			time.startBar = 0;
			time.position = -1;
		}

		if (start == 1) {
			time.startBar = 0;
			time.position = -0.5;
		}

		if (start == 2) {
			time.startBar = 0;
			time.position = 0;
		}

		if (start == 3) {
			time.startBar = 0;
			time.position = 0.5;
		}

		if (start == 4) {
			time.startBar = 0;
			time.position = 1;
		}

		double delta;

		int mode = Common.getRandomInteger(0, 4);
		if (mode == 0)
			delta = 2;
		if (mode == 1)
			delta = 1;
		if (mode == 2)
			delta = 0.5;
		if (mode == 3)
			delta = 0.25;
		else
			delta = 0.5;

		while (time.startBar < bars) 
		{
			Time t2 = time.clone();
			t2.position += delta;
			if (t2.position >= uniquePhrase.getMeter())
				t2.position = uniquePhrase.getMeter();

			uniquePhrase.addEvent(time.clone(), t2.clone(), 1);

			time.position += delta;
			
			if (time.position >= uniquePhrase.getMeter()) 
			{
				time.position = 0;
				time.startBar++;
			}
		}

		if (uniquePhrase.isEndsSentence())
			uniquePhrase.addEvent(
					new Time(uniquePhrase.getBars() - 1, 0),
					new Time(uniquePhrase.getBars() - 1, uniquePhrase.getMeter()), 
					1);
	}

}
