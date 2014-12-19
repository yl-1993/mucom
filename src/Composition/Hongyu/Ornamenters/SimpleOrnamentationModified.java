package Composition.Hongyu.Ornamenters;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.Part;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

public class SimpleOrnamentationModified implements Ornamenter {
	
	double getEventLen(UniquePart up, int i) {
		return (up.getEventEnd(i).startBar - up.getEventStart(i).startBar)
				* up.getMeter()
				+ (up.getEventEnd(i).position - up.getEventStart(i).position);
	}

	@Override
	public void ornament(Part part, UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		
		double speed = parameter.get(Constant.SPEED_STRING);
		
		for (int i = 0; i < uniquePart.getEventsCount(); i++) {
			Time t1 = uniquePart.getEventStart(i);
			Time t2 = uniquePart.getEventEnd(i);
			boolean Modified = false;
			
			if (getEventLen(uniquePart, i) >= 2 && 
					(test(speed / 200.0 + 0.2) || i == uniquePart.getEventsCount() - 1)) {
				
				double len = 1;
				if (getEventLen(uniquePart, i) < 4) {
					len = 0.5;
				}
				int mode = rndInt(0, 3);
				
				if (mode == 0 && len == 1) {
					Modified = true;
					// 较平缓
					// 4个四分音符 音高0 2 1 0
					part.addEvent(t1, new Time(t1.startBar, t1.position + len), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) + 0), uniquePart.getEventVolume(i) * 1.0);
					part.addEvent(new Time(t1.startBar, t1.position + len), 
							new Time(t1.startBar, t1.position + len * 1.5), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) + 2), uniquePart.getEventVolume(i) * 0.7);
					part.addEvent(new Time(t1.startBar, t1.position + len * 2),
							new Time(t1.startBar, t1.position + len * 2.5), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) + 1), uniquePart.getEventVolume(i) * 0.8);
					part.addEvent(new Time(t1.startBar, t1.position + len * 3),
							new Time(t1.startBar, t1.position + len * 4), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) + 0), uniquePart.getEventVolume(i) * 0.9);			
				}
				if (mode == 1 && len == 1) {
					Modified = true;
					// 较平缓
					// 4个四分音符 音高0 -2 -1 0  
					part.addEvent(t1, new Time(t1.startBar, t1.position + len),
							part.getAbsolutePitch(uniquePart.getEventPitch(i) - 0), uniquePart.getEventVolume(i) * 1.0);
					part.addEvent(new Time(t1.startBar, t1.position + len), 
							new Time(t1.startBar, t1.position + len * 1.5), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) - 2), uniquePart.getEventVolume(i) * 0.7);
					part.addEvent(new Time(t1.startBar, t1.position + len * 2),
							new Time(t1.startBar, t1.position + len * 2.5), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) - 1), uniquePart.getEventVolume(i) * 0.8);
					part.addEvent(new Time(t1.startBar, t1.position + len * 3),
							new Time(t1.startBar, t1.position + len * 4), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) - 0), uniquePart.getEventVolume(i) * 0.9);			
				}
				if (mode == 2) {
					Modified = true;
					// 较平缓
					// 1个二分音符，2个四分音符， 音高0 -1 0  
					part.addEvent(t1, new Time(t1.startBar, t1.position + len * 2), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) - 0), uniquePart.getEventVolume(i) * 1.0);
					part.addEvent(new Time(t1.startBar, t1.position + len * 2),
							new Time(t1.startBar, t1.position + len * 2.5), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) - 1), uniquePart.getEventVolume(i) * 0.8);
					part.addEvent(new Time(t1.startBar, t1.position + len * 3),
							new Time(t1.startBar, t1.position + len * 3.5), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) - 0), uniquePart.getEventVolume(i) * 0.8);
				}
				if (mode == 3) {
					Modified = true;
					// 较平缓
					// 1个二分音符，2个四分音符， 音高0 1 0 
					part.addEvent(t1, new Time(t1.startBar, t1.position + len * 2), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) + 0), uniquePart.getEventVolume(i) * 1.0);
					part.addEvent(new Time(t1.startBar, t1.position + len * 2),
							new Time(t1.startBar, t1.position + len * 2.5), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) + 1), uniquePart.getEventVolume(i) * 0.8);
					part.addEvent(new Time(t1.startBar, t1.position + len * 3),
							new Time(t1.startBar, t1.position + len * 3.5), 
							part.getAbsolutePitch(uniquePart.getEventPitch(i) + 0), uniquePart.getEventVolume(i) * 0.8);
				}
			}
			if (!Modified) {
				part.addEvent(t1, t2, part.getAbsolutePitch(uniquePart.getEventPitch(i)),
						uniquePart.getEventVolume(i));
			}
		}
	}

	private int rndInt(int i, int j) {
		return Common.getRandomInteger(i, j + 1);
	}
	
	private boolean test(double probability) {
		if (Common.getRandomDouble(0, 1) < probability)
			return true;
		return false;
	}

}
