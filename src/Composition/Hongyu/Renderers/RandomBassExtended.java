package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;

public class RandomBassExtended implements Renderer {
	
	int first_pitch = 0;
	int last_pitch = 0;

	void SetFirstPitch(int f) {
		first_pitch = f;
		last_pitch = f;
	}

	int GetNextPitch(int p) {
		int cur_pitch = p;
		if (cur_pitch - 7 > last_pitch)
			cur_pitch -= 12;
		last_pitch = cur_pitch + rndInt(-3, 3);
		return cur_pitch;
	}
	
	int rndInt(int i, int j) {
		return Common.getRandomInteger(i, j + 1);
	}

	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		SetFirstPitch(renderPart.getHarmonicEventPitch(0, 0));

		int mode = rndInt(0, 1);

		if (mode == 0) {
			double delta = 0.5;
			for (int i = 0; i < renderPart.getHarmonics().size(); i++) {
				if (rndInt(0, 5) == 0 || i == renderPart.getHarmonics().size() - 1) {
					renderPart.addNote(renderPart.getHarmonicEventStart(i), renderPart
							.getHarmonicEventEnd(i), GetNextPitch(renderPart
							.getHarmonicEventPitch(i, 0)), (int) (120 * renderPart.getHarmonicEventVolume(i)));
				} else {
					renderPart.addNote(renderPart.getHarmonicEventStart(i), new Time(renderPart
							.getHarmonicEventEnd(i).startBar, renderPart
							.getHarmonicEventEnd(i).position
							- delta), GetNextPitch(renderPart
							.getHarmonicEventPitch(i, 0)), (int) (120 * renderPart.getHarmonicEventVolume(i)));
					renderPart.addNote(new Time(renderPart.getHarmonicEventEnd(i).startBar, renderPart
							.getHarmonicEventEnd(i).position
							- delta), renderPart.getHarmonicEventEnd(i), GetNextPitch(renderPart
							.getHarmonicEventPitch(i, rndInt(0, 2))
							- 12 * rndInt(0, 1)), (int) (120 * renderPart.getHarmonicEventVolume(i)));
				}
			}
		} else {

			double len = Common.getRandomDouble(0.5, 1.0);
			if (rndInt(0, 1) == 0)
				len = 0.98;

			int len_mode = rndInt(0, 1);

			double speed = 1;

			for (int i = 0; i < renderPart.getUniquePart().getBarsCount(); i++) {
				for (double m = 0; m < renderPart.getUniquePart().getMeter(); m += speed) {
					Time t = new Time(i, m);
					int vel = 127;
					if (m % 2 == 1)
						vel = 122;

					int harm = renderPart.getHarmonic(t);

					if (harm == renderPart.getHarmonics().size() - 1) {
						renderPart.addNote(
								renderPart
										.getHarmonicEventStart(renderPart
												.getHarmonics().size() - 1), renderPart
										.getHarmonicEventEnd(renderPart
												.getHarmonics().size() - 1),
								GetNextPitch(renderPart.getHarmonicEventPitch(renderPart
										.getHarmonics().size() - 1, 0)), (int) (120 * renderPart.getHarmonicEventVolume(renderPart
												.getHarmonics().size() - 1)));
						return;
					}

					Time t2 = t.clone();
					if (len_mode == 0)
						t2.position += len * speed;
					else
						t2.position += Common.getRandomDouble(0.5, 1) * speed;

					if (rndInt(0, 2) != 0)
						renderPart.addNote(t, t2, GetNextPitch(renderPart.getHarmonicEventPitch(
								harm, 0)), rndInt(vel - 15, vel));
					else
						renderPart.addNote(t, t2, GetNextPitch(renderPart.getHarmonicEventPitch(
								harm, rndInt(-2, 2))), rndInt(vel - 15, vel));
				}
			}
		}
	}
}
