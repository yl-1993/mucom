package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;

/**
 * SimpleMelody
 */
public class DefaultRenderer implements Renderer {

	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		for (int i = 0; i < renderPart.getEventsCount(); i++) {
			renderPart.addNote(renderPart.getEventStart(i), renderPart.getEventEnd(i), renderPart.getEventPitch(i), (int)(120 * renderPart.getEventVolume(i)));
		}
	}

}
