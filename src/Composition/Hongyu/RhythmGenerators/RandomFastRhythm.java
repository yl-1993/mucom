package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePhrase;

/**
 * Name: RandomFastRhythm
 * Style: Random Combination of One Meter 
 * Author:Zhf 
 * Date: 2/24/2014
 */

public class RandomFastRhythm implements RhythmGenerator {

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
			int mode = 0;
			mode = Common.getRandomInteger(1, 6);
			if (mode == 1)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.5),0.8);
				time.position += 0.5;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.5),0.8);
				time.position += 0.5;
			}
			if (mode == 2)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.5),0.8);
				time.position += 0.5;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.6);
				time.position += 0.25;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.6);
				time.position += 0.25;
			}
			if (mode == 3)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.6);
				time.position += 0.25;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.6);
				time.position += 0.25;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.5),0.8);
				time.position += 0.5;
			}
			if (mode == 4)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.7);
				time.position += 0.25;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.5);
				time.position += 0.25;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.7);
				time.position += 0.25;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.5);
				time.position += 0.25;
			}
			if (mode == 5)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.75),0.8);
				time.position += 0.75;
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 0.25),0.8);
				time.position += 0.25;
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
