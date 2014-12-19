package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;
/**
 * SimpleMelody
 */
public class SimpleRenderer_Style2 implements Renderer {

	@Override
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < renderPart.getHarmonics().size() - 1; i++) 
		{
			if (count % 4 == 0 || count % 4 == 1)
			{
				for (int j = 0; j < renderPart.getHarmonicOffsetCount(i); j++) {		
					Time start = renderPart.getHarmonicEventStart(i);
					Time end = renderPart.getHarmonicEventEnd(i);
					Time mid1 = new Time(start.startBar, start.position + 2);
					Time mid2 = new Time(start.startBar, start.position + 3);
					renderPart.addNote(mid1, mid2, renderPart.getHarmonicEventPitch(i, j) - 12, (int)(renderPart.getHarmonicEventVolume(i) * 120));
					renderPart.addNote(mid2, end, renderPart.getHarmonicEventPitch(i, j) - 12, (int)(renderPart.getHarmonicEventVolume(i) * 120));
				}
			}
			else
			{
				for (int j = 0; j < renderPart.getHarmonicOffsetCount(i); j++) {		
					Time start = renderPart.getHarmonicEventStart(i);
					Time end = renderPart.getHarmonicEventEnd(i);
					Time mid0 = new Time(start.startBar, start.position + 1);
					//Time mid1 = new Time(start.startBar, start.position + 2);
					Time mid2 = new Time(start.startBar, start.position + 3);
					renderPart.addNote(mid0, mid2, renderPart.getHarmonicEventPitch(i, j) - 12, (int)(renderPart.getHarmonicEventVolume(i) * 120));
					renderPart.addNote(mid2, end, renderPart.getHarmonicEventPitch(i, j) - 12, (int)(renderPart.getHarmonicEventVolume(i) * 120));
				}
			}
			count ++;
		}
	}

}
