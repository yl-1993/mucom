package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;

public class AccentedMelody implements Renderer {

	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		double max_len = 0.25;

		for (int i = 0; i < renderPart.getEventsCount(); i++) {
			Time t = renderPart.getEventStart(i);

			int poz = (int) t.position;

			if ((poz == 0 || poz == 2) && t.position - poz < 0.1) {
				// accent
				renderPart.addNote(renderPart.getEventStart(i), renderPart.getEventEnd(i), renderPart
						.getEventPitch(i), (int) (120 * renderPart.getEventVolume(i)));
			} else {
				double len = (renderPart.getEventEnd(i).startBar - renderPart.getEventStart(i).startBar)
						* renderPart.getUniquePart().getMeter()
						+ (renderPart.getEventEnd(i).position - renderPart.getEventStart(i).position);

				if (len > max_len) {
					renderPart.addNote(renderPart.getEventStart(i), new Time(
							renderPart.getEventStart(i).startBar, renderPart.getEventStart(i).position
									+ max_len), renderPart.getEventPitch(i), (int) (Common.getRandomInteger(84, 108) * renderPart.getEventVolume(i)));
				} else
					renderPart.addNote(renderPart.getEventStart(i), renderPart.getEventEnd(i), renderPart
							.getEventPitch(i), (int) (Common.getRandomInteger(84, 108) * renderPart.getEventVolume(i)));
			}
		}
	}

}
