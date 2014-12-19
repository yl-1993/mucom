package Composition.Hongyu.Ornamenters;

import java.util.HashMap;

import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.Part;
import Composition.Hongyu.Essential.UniquePart;

/**
 * NoOrnamentation
 */
public class DefaultOrnamenter implements Ornamenter {

	@Override
	public void ornament(Part part, UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		for (int i = 0; i < uniquePart.getEventsCount(); i++)
			part.addEvent(uniquePart.getEventStart(i),
					uniquePart.getEventEnd(i),
					part.getAbsolutePitch(uniquePart.getEventPitch(i)),
					uniquePart.getEventVolume(i));
	}

}
