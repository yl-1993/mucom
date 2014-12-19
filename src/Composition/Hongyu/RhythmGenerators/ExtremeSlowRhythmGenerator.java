package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePhrase;

/**
 * RandomStaticRhythm
 */
public class ExtremeSlowRhythmGenerator implements RhythmGenerator {

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
			int mode = Common.getRandomInteger(0, 5);
			
			if (mode == 4)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 4),1);
				time.position += 4;
			}
			if (mode == 3)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 3),1);
				time.position += 3;
			}
			if (mode == 2)
			{
				uniquePhrase.addEvent(time.clone(), new Time(time.startBar, time.position + 2),1);
				time.position += 2;
			}
			if (mode == 1)
			{
				time.position += 1;
			}
			if (mode == 0)
			{
				time.position += 2;
			}
			if (time.position >= uniquePhrase.getMeter()) 
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
