package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;

public class SimpleChordsSmooth implements Renderer {

	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		for (int i = 0; i < renderPart.getHarmonics().size(); i++) {
			for (int f = 0; f < 3; f++) {
				Time t1 = renderPart.getHarmonicEventStart(i);
				Time t2 = renderPart.getHarmonicEventEnd(i);

				t1.position -= (renderPart.getHarmonicOffsetCount(i) - f) / 20.0;
				t2.position -= (renderPart.getHarmonicOffsetCount(i) - f) / 20.0;

				renderPart.addNote(t1, t2, renderPart.getHarmonicEventPitch(i, f), (int) (120 * renderPart.getEventVolume(i)));
			}
		}
	}

}
