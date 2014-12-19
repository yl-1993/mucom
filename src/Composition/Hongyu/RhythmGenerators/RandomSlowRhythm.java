package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePhrase;

/**
 * Name: RandomSlowRhythm
 * Style: Random Combination of Four Meters 
 * Author:Zhf 
 * Date: 2/24/2014
 */

public class RandomSlowRhythm implements RhythmGenerator {

	@Override
	public void generateRhythm(UniquePhrase uniquePhrase,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		
		int bars = uniquePhrase.getBars();

		if (uniquePhrase.isEndsSentence())
			bars--;

		Time time = new Time(0, 0);

		int count = 4;
		while (time.startBar < bars) 
		{
			int mode = 0;
			if (count % 4 == 0)
				mode = Common.getRandomInteger(0, 5);
			if (count % 4 == 3)
				mode = Common.getRandomInteger(0, 3);
			if (count % 4 == 2)
				mode = Common.getRandomInteger(0, 2);
			if (count % 4 == 1)
				mode = Common.getRandomInteger(0, 1);
			
			if (mode == 4)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 4),1);
				time.position += 4;
			}
			if (mode == 3)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 3),1);
				time.position += 3;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 1),1);
				time.position += 1;
			}
			if (mode == 2)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 3),1);
				time.position += 3;
				count -= 3;
			}
			if (mode == 1)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 2),1);
				time.position += 2;
				count -= 2;
			}
			if (mode == 0)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 1),1);
				time.position += 1;
				count -= 1;
			}
			if (time.position >= uniquePhrase.getMeter()) 
			{
				time.position = 0;
				time.startBar++;
			}
		}
		
		if (uniquePhrase.isEndsSentence())
			uniquePhrase.addEvent(new Time(uniquePhrase.getBars() - 1, 0),
				new Time(uniquePhrase.getBars() - 1, uniquePhrase.getMeter()),1);
	}
}
