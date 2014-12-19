package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePhrase;

/**
 * Name: FixedSlowRhythm
 * Style: 2 + 1 + 1 mixed with 2 + 2 
 * Author:Zhf 
 * Date: 2/24/2014
 */

public class FixedSlowRhythm implements RhythmGenerator {

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
			if (!uniquePhrase.isEndsSentence())
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 2),1);
				time.position += 2;
				for (int i = 1; i <= 2; i++)
				{	
					uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 1), 0.9);
					time.position += 1;
				}
			}
			if (Common.getRandomInteger(0, 3) == 0 || uniquePhrase.isEndsSentence())
			{
				for (int i = 1; i <= 2; i++)
				{	
					uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 2), 1);
					time.position += 2;
				}
			}
			else
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 2),1);
				time.position += 2;
				for (int i = 1; i <= 2; i++)
				{	
					uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 1), 0.9);
					time.position += 1;
				}
			}
			time.position = 0;
			time.startBar += 2;
		}
		
		if (uniquePhrase.isEndsSentence())
			uniquePhrase.addEvent(new Time(uniquePhrase.getBars() - 1, 0),
				new Time(uniquePhrase.getBars() - 1, uniquePhrase.getMeter()),1);
	}
}
