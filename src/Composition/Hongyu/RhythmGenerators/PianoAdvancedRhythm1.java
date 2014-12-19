package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePhrase;

/**
 * Name: PianoAdvancedRhythm1
 * Style: 1.5 0.5 1 1 + £¨NULL£©
 * Author:Zhf 
 * Date: 2/24/2014
 */

public class PianoAdvancedRhythm1 implements RhythmGenerator {

	@Override
	public void generateRhythm(UniquePhrase uniquePhrase,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		
		int bars = uniquePhrase.getBars();

		if (uniquePhrase.isEndsSentence())
			bars--;

		Time time = new Time(0, 0);

		if (Common.getRandomInteger(0, 2) == 0)
		{
			uniquePhrase.addEvent(new Time(time.startBar, time.position - 0.5), new Time(time.startBar, time.position),0.6);
		}
		while (time.startBar < bars) 
		{
			uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 1.5),1);
			time.position += 1.5;
			uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.5),0.7);
			time.position += 0.5;
			for (int i = 1; i <= 2; i++)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 1),1);
				time.position += 1;
			}
			time.position = 0;
			time.startBar += 2;
		}
		
		if (uniquePhrase.isEndsSentence())
			uniquePhrase.addEvent(new Time(uniquePhrase.getBars() - 1, 0),
				new Time(uniquePhrase.getBars() - 1, uniquePhrase.getMeter()),1);
	}
}
