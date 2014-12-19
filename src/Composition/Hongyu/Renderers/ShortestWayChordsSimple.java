package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;

public class ShortestWayChordsSimple implements Renderer {
	
	int przew;
	int low;
	int high;

	void SetFirstChord(RenderPart p, int h) {
		przew = Common.getRandomInteger(-1, 2);
		low = p.getHarmonicEventPitch(h, przew);
		high = p.getHarmonicEventPitch(h, przew + p.getHarmonicOffsetCount(h)
				- 1);
	}

	int GetDist(RenderPart p, int harm, int pr) {
		int l = p.getHarmonicEventPitch(harm, pr);
		int h = p.getHarmonicEventPitch(harm, pr
				+ p.getHarmonicOffsetCount(harm) - 1);

		int dist = 0;

		if (l < low)
			dist += low - l;
		else
			dist += l - low;

		if (h < high)
			dist += high - h;
		else
			dist += h - high;

		return dist;
	}

	void PlaceNextChord(RenderPart p, Time t1, Time t2) {
		int harm = p.getHarmonic(t1);

		int pr = 0;
		int dist = GetDist(p, harm, pr);

		while (GetDist(p, harm, pr - 1) < dist) {
			dist = GetDist(p, harm, pr - 1);
			pr--;
		}

		while (GetDist(p, harm, pr + 1) <= dist) {
			dist = GetDist(p, harm, pr + 1);
			pr++;
		}

		przew = pr;
		low = p.getHarmonicEventPitch(harm, przew);
		high = p.getHarmonicEventPitch(harm, przew
				+ p.getHarmonicOffsetCount(harm) - 1);
		
		for (int f = 0; f < p.getHarmonicOffsetCount(harm); f++) {
			int pitch = p.getHarmonicEventPitch(harm, przew + f);
			while (pitch > 84) { pitch -= 12; }
			while (pitch < 36) { pitch += 12; }
			p.addNote(t1, t2, pitch, (int) (Common.getRandomInteger(
					108, 120) * p.getHarmonicEventVolume(harm)));
		}
	}
	
	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		SetFirstChord(renderPart, 0);
		for (int i = 0; i < renderPart.getHarmonics().size(); i++) {
			Time t1 = renderPart.getHarmonicEventStart(i);
			Time t2 = renderPart.getHarmonicEventEnd(i);
			PlaceNextChord(renderPart, t1, t2);
		}
	}
}
