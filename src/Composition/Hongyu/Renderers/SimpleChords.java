package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
public class SimpleChords implements Renderer {

	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		for (int i = 0; i < renderPart.getHarmonics().size(); i++) {
			for (int j = 0; j < renderPart.getHarmonicOffsetCount(i); j++) {
				int pitch = renderPart.getHarmonicEventPitch(i, j);
				while (pitch >= 80) { pitch -= 12; }
				while (pitch <= 40) { pitch += 12; }
				renderPart.addNote(renderPart.getHarmonicEventStart(i), renderPart.getHarmonicEventEnd(i),
						pitch, (int)(renderPart.getHarmonicEventVolume(i) * 120));
			}
		}
	}
}
