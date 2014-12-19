package Composition.Hongyu.Ornamenters;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.Part;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

/**
 * TwoOrnamentation
 * 功能：对二分音符进行修饰。
 * 效果：加快节奏。 
 */

public class TwoOrnamentation implements Ornamenter {
	
	double getEventLen(UniquePart up, int i) {
		return (up.getEventEnd(i).startBar - up.getEventStart(i).startBar)
				* up.getMeter()
				+ (up.getEventEnd(i).position - up.getEventStart(i).position);
	}

	@Override
	public void ornament(Part part, UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < uniquePart.getEventsCount(); i++) {
			Time t1 = uniquePart.getEventStart(i);
			Time t2 = uniquePart.getEventEnd(i);
			
			int Modified = 0;
			if (i < uniquePart.getEventsCount() - 1)
			{
				if (rndInt(0, 0) == 0 && getEventLen(uniquePart, i) == 2) 
				{
					Modified = 1;
					int mode = rndInt(0, 3);
					if (mode == 0)
					{
						part.addEvent(t1, new Time(t1.startBar, t1.position + 1), part
								.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i) * 0.8);
						part.addEvent(new Time(t1.startBar, t1.position + 1), 
								new Time(t1.startBar, t1.position + 2), 
								part.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i) * 0.8);	
					}
					if (mode == 1)
					{
						part.addEvent(t1, new Time(t1.startBar, t1.position + 1.5), part
								.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i) * 1);
						part.addEvent(new Time(t1.startBar, t1.position + 1.5), 
								new Time(t1.startBar, t1.position + 2), 
								part.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i) * 0.8);	
					}
					if (mode == 2)
					{
						int delta = rndInt(-1,1);
						part.addEvent(t1, new Time(t1.startBar, t1.position + 1), part
								.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i) * 1);
						part.addEvent(new Time(t1.startBar, t1.position + 1), 
								new Time(t1.startBar, t1.position + 1.5), 
								part.getAbsolutePitch(uniquePart.getEventPitch(i) + delta), uniquePart.getEventVolume(i) * 0.8);
						part.addEvent(new Time(t1.startBar, t1.position + 1.5), 
								new Time(t1.startBar, t1.position + 2), 
								part.getAbsolutePitch(uniquePart.getEventPitch(i) + delta), uniquePart.getEventVolume(i) * 0.8);	
					}
					if (mode == 3)
					{
						int delta = rndInt(-1,1);
						part.addEvent(t1, new Time(t1.startBar, t1.position + 0.5), part
								.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i) * 1);
						part.addEvent(new Time(t1.startBar, t1.position + 0.5), 
								new Time(t1.startBar, t1.position + 1), 
								part.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i) * 0.8);
						part.addEvent(new Time(t1.startBar, t1.position + 1), 
								new Time(t1.startBar, t1.position + 1.5), 
								part.getAbsolutePitch(uniquePart.getEventPitch(i) + delta), uniquePart.getEventVolume(i) * 0.6);
						part.addEvent(new Time(t1.startBar, t1.position + 1.5), 
								new Time(t1.startBar, t1.position + 2), 
								part.getAbsolutePitch(uniquePart.getEventPitch(i) + delta + 1), uniquePart.getEventVolume(i) * 0.6);
					}
				}
			}
			if (Modified == 0) {
				part.addEvent(t1, t2, part.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i));
			}
		}
	}

	private int rndInt(int i, int j) {
		return Common.getRandomInteger(i, j + 1);
	}

}
