package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePhrase;

/**
 * Name: FixedSlightlyFastRhythm1
 * Style: 1 0.5 0.5 0.5 0.5 0.5 0.5
 * Author:Zhf 
 * Date: 2/24/2014
 */

public class FixedSlightlyFastRhythm1 implements RhythmGenerator {

	@Override
	public void generateRhythm(UniquePhrase uniquePhrase,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int bars = uniquePhrase.getBars();

		if (uniquePhrase.isEndsSentence())
			bars--;

		Time time = new Time(0, 0);

		while (time.startBar < bars) 
		{
			uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 1), 1);
			time.position += 1;
			for (int i = 1; i <= 2; i++)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.5),0.7);
				time.position += 0.5;
			}
			for (int i = 1; i <= 2; i++)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.5), 0.9);
				time.position += 0.5;
			}	
			for (int i = 1; i <= 2; i++)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.5),0.7);
				time.position += 0.5;
			}
			while (time.position >= uniquePhrase.getMeter())
			{
				time.position -= uniquePhrase.getMeter();
				time.startBar++;
			}
		}
		if (uniquePhrase.isEndsSentence())
			uniquePhrase.addEvent(new Time(uniquePhrase.getBars() - 1, 0),
					new Time(uniquePhrase.getBars() - 1, uniquePhrase.getMeter()),1);
	}

}
