package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;

/**
 * SimpleMelody
 */
public class SimpleRenderer_Style1 implements Renderer {

	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		for (int i = 0; i < renderPart.getHarmonics().size() - 1; i++) {
			for (int j = 0; j < renderPart.getHarmonicOffsetCount(i); j++) {
				Time start = renderPart.getHarmonicEventStart(i);
				Time end = renderPart.getHarmonicEventEnd(i);
				Time mid = new Time(start.startBar, start.position + 2);
				renderPart.addNote(start, mid, renderPart.getHarmonicEventPitch(i, j), (int)(renderPart.getHarmonicEventVolume(i) * 120));
				renderPart.addNote(mid, end, renderPart.getHarmonicEventPitch(i, j), (int)(renderPart.getHarmonicEventVolume(i) * 120));
			}
		}
	}

}
