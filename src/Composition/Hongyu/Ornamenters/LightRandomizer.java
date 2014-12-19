package Composition.Hongyu.Ornamenters;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.Part;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

public class LightRandomizer implements Ornamenter {

	@Override
	public void ornament(Part part, UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		boolean skip = false;

		for (int i = 0; i < uniquePart.getEventsCount(); i++) {
			if (i < uniquePart.getEventsCount() - 1 && !skip) {
				Time t = uniquePart.getEventStart(i);

				int poz = (int) t.position;
				double tmp = t.position - poz;

				if (Math.abs(tmp) < 0.1) {
					part.addEvent(uniquePart.getEventStart(i),
							uniquePart.getEventEnd(i),
							part.getAbsolutePitch(uniquePart.getEventPitch(i)),
							uniquePart.getEventVolume(i));
				} else // the note starts on a week part of the bar
				{
					skip = true;

					if (i > 0 && i < uniquePart.getEventsCount() - 1) {
						if (uniquePart.getEventPitch(i) != uniquePart
								.getEventPitch(i + 1)
								&& uniquePart.getEventPitch(i) != uniquePart
										.getEventPitch(i - 1)) {
							part.addEvent(uniquePart.getEventStart(i),
									uniquePart.getEventEnd(i), part
											.getAbsolutePitch(uniquePart
													.getEventPitch(i)),
									uniquePart.getEventVolume(i));
							skip = false;
						}
					}

					if (skip) {
						if (uniquePart.getEventPitch(i) == uniquePart
								.getEventPitch(i + 1)) {
							if (Common.getRandomInteger(0, 2) == 0)
								part.addEvent(uniquePart.getEventStart(i),
										uniquePart.getEventEnd(i), part
												.getAbsolutePitch(uniquePart
														.getEventPitch(i) + 1),
										uniquePart.getEventVolume(i));
							else
								part.addEvent(uniquePart.getEventStart(i),
										uniquePart.getEventEnd(i), part
												.getAbsolutePitch(uniquePart
														.getEventPitch(i) - 1),
										uniquePart.getEventVolume(i));
						}

						if (uniquePart.getEventPitch(i) > uniquePart
								.getEventPitch(i + 1)) {
							if (Common.getRandomInteger(0, 2) == 0)
								part.addEvent(uniquePart.getEventStart(i),
										uniquePart.getEventEnd(i), part
												.getAbsolutePitch(uniquePart
														.getEventPitch(i) - 1),
										uniquePart.getEventVolume(i));
							else
								part.addEvent(uniquePart.getEventStart(i),
										uniquePart.getEventEnd(i),
										part.getAbsolutePitch(uniquePart
												.getEventPitch(i + 1) + 1),
										uniquePart.getEventVolume(i));
						}

						if (uniquePart.getEventPitch(i) < uniquePart
								.getEventPitch(i + 1)) {
							if (Common.getRandomInteger(0, 2) == 0)
								part.addEvent(uniquePart.getEventStart(i),
										uniquePart.getEventEnd(i), part
												.getAbsolutePitch(uniquePart
														.getEventPitch(i) + 1),
										uniquePart.getEventVolume(i));
							else
								part.addEvent(uniquePart.getEventStart(i),
										uniquePart.getEventEnd(i),
										part.getAbsolutePitch(uniquePart
												.getEventPitch(i + 1)) - 1,
										uniquePart.getEventVolume(i));
						}
					}

				}
			} else {
				part.addEvent(uniquePart.getEventStart(i),
						uniquePart.getEventEnd(i),
						part.getAbsolutePitch(uniquePart.getEventPitch(i)),
						uniquePart.getEventVolume(i));
				skip = false;
			}

		}
	}

}
