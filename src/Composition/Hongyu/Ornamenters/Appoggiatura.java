package Composition.Hongyu.Ornamenters;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.Part;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

/**
 * 音符之前的倚音
 */
public class Appoggiatura implements Ornamenter {

	double getEventLen(UniquePart up, int i) {
		if (i < 0 || i >= up.getEventsCount()) {
			return 0;
		} else {
			return (up.getEventEnd(i).startBar - up.getEventStart(i).startBar)
					* up.getMeter()
					+ (up.getEventEnd(i).position - up.getEventStart(i).position);
		}
	}
	
	private boolean test(double probability) {
		if (Common.getRandomDouble(0, 1) < probability)
			return true;
		return false;
	}

	@Override
	public void ornament(Part part, UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub

		double speed = parameter.get(Constant.SPEED_STRING);
		
		for (int i = 0; i < uniquePart.getEventsCount(); i++) {
			Time t1 = uniquePart.getEventStart(i);
			Time t2 = uniquePart.getEventEnd(i);
			if (i < uniquePart.getEventsCount() - 1) {
				
				double currLen = getEventLen(uniquePart, i);
				double nextLen = getEventLen(uniquePart, i + 1);
				int currNote = uniquePart.getEventPitch(i);
				int nextNote = uniquePart.getEventPitch(i + 1);
				int mode = Common.getRandomInteger(0, 2);
				
				if (mode == 0 && currLen >= 4 && nextLen >= 1 && test(speed / 100 + 0.4)) {
					//在nextNode之前补充+2,+1或者-2,-1
					int orientation = Common.getRandomInteger(0, 2);
					if (currNote == nextNote - 2)
						orientation = 1;
					if (currNote == nextNote + 2)
						orientation = 0;
					if (orientation == 0) {
						part.addEvent(t1, new Time(t2.startBar, t2.position - 2), part.getAbsolutePitch(currNote),
								uniquePart.getEventVolume(i) * 1);
						part.addEvent(new Time(t2.startBar, t2.position - 2), new Time(t2.startBar, t2.position - 1.5), part.getAbsolutePitch(nextNote - 2),
								uniquePart.getEventVolume(i + 1) * 0.8);
						part.addEvent(new Time(t2.startBar, t2.position - 1), new Time(t2.startBar, t2.position - 0.5), part.getAbsolutePitch(nextNote - 1),
								uniquePart.getEventVolume(i + 1) * 0.9);
					}
					if (orientation == 1) {
						part.addEvent(t1, new Time(t2.startBar, t2.position - 2), part.getAbsolutePitch(currNote),
								uniquePart.getEventVolume(i) * 1);
						part.addEvent(new Time(t2.startBar, t2.position - 2), new Time(t2.startBar, t2.position - 1.5), part.getAbsolutePitch(nextNote + 2),
								uniquePart.getEventVolume(i + 1) * 0.8);
						part.addEvent(new Time(t2.startBar, t2.position - 1), new Time(t2.startBar, t2.position - 0.5), part.getAbsolutePitch(nextNote + 1),
								uniquePart.getEventVolume(i + 1) * 0.9);
					}
					continue;
				}
				
				if (mode == 1 && currLen >= 2 && nextLen >= 1 && test(speed / 200 + 0.2)) {
					//如果音符差距为奇数，则贴近nextNote
					int newNote = nextNote - (nextNote - currNote) / 2;
					if (nextNote == currNote)
						newNote = currNote + Common.getRandomElement(new Integer[] {-1, 1});
					if (nextNote == currNote + 1)
						newNote = currNote - 1;
					if (nextNote == currNote - 1)
						newNote = currNote + 1;
					part.addEvent(t1, new Time(t2.startBar, t2.position - 1), part.getAbsolutePitch(uniquePart.getEventPitch(i)),
							uniquePart.getEventVolume(i) * 1);
					part.addEvent(new Time(t2.startBar, t2.position - 1), t2, part.getAbsolutePitch(newNote),
							uniquePart.getEventVolume(i + 1) * 0.8);
					continue;
				}
			}
			part.addEvent(t1, t2, part.getAbsolutePitch(uniquePart.getEventPitch(i)),
					uniquePart.getEventVolume(i));
		}
	}

}
