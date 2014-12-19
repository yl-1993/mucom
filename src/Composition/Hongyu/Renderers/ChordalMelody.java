package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;

public class ChordalMelody implements Renderer {

	double GetNoteLength(RenderPart p, int i) {
		Time t1 = p.getEventStart(i);
		Time t2 = p.getEventEnd(i);
		return (t2.startBar - t1.startBar) * p.getUniquePart().getMeter()
				+ (t2.position - t1.position);
	}

	int abs(int v) {
		if (v < 0)
			return -v;
		return v;
	}

	double fabs(double v) {
		if (v < 0)
			return -v;
		return v;
	}

	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		for (int i = 0; i < renderPart.getEventsCount(); i++) {
			int note_pitch = renderPart.getEventPitch(i);

			int poz = (int) renderPart.getEventStart(i).position;
			boolean strong = true;

			if (fabs(poz - renderPart.getEventStart(i).position) > 0.1)
				strong = false;

			if (GetNoteLength(renderPart, i) > 0.26) {
				Time t = renderPart.getEventEnd(i);
				t.position -= 0.1;
				int harm = renderPart.getHarmonic(t);

				for (int h = 0; h < renderPart.getHarmonicOffsetCount(harm); h++) {
					int harm_pitch = renderPart.getHarmonicEventPitch(harm, h);

					while (harm_pitch + 12 < note_pitch) {
						harm_pitch += 12;
					}

					while (harm_pitch > note_pitch) {
						harm_pitch -= 12;
					}

					int safe_dist = 2;

					if (note_pitch != renderPart.alignPitch(note_pitch, 0))
						safe_dist = 3;

					if (abs(harm_pitch - note_pitch) > safe_dist) {
						if (strong)
							renderPart.addNote(renderPart.getEventStart(i),
									renderPart.getEventEnd(i), harm_pitch,
									(int) (Common.getRandomInteger(96, 120) * renderPart.getEventVolume(i)));
						else
							renderPart.addNote(renderPart.getEventStart(i),
									renderPart.getEventEnd(i), harm_pitch,
									(int) (Common.getRandomInteger(84, 108) * renderPart.getEventVolume(i)));
					}
				}
			}

			if (strong)
				renderPart.addNote(renderPart.getEventStart(i),
						renderPart.getEventEnd(i), note_pitch, (int) (120 * renderPart.getEventVolume(i)));
			else
				renderPart.addNote(renderPart.getEventStart(i),
						renderPart.getEventEnd(i), note_pitch, (int) (108 * renderPart.getEventVolume(i)));

		}
	}

}
