package Composition.Hongyu.Arrangers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Renderers.DefaultRenderer;
import Composition.Hongyu.Renderers.SimpleChords;
import Composition.Hongyu.Renderers.SimpleRenderer_Style2;
/**
 * PianoSimpleArrangement
 */
public class DefaultArrangerOrnament implements Arranger {

	@Override
	public void arrange(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
			
		musicDescription.addTrack("Melody");
		musicDescription.addTrack("Voice");
		musicDescription.addTrack("Chords");
		
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			if (musicDescription.getPart(i).getArrangeHint() == 1)
				musicDescription.addRenderEvent(new DefaultRenderer(), 0, musicDescription.getPartStartBar(i), musicDescription.getPartEndBar(i), 1, new Time(0, 0), 1.0);
			if (musicDescription.getPart(i).getArrangeHint() == 2)
				musicDescription.addRenderEvent(new DefaultRenderer(), 1, musicDescription.getPartStartBar(i), musicDescription.getPartEndBar(i), 1, new Time(0, 0), 1.0);
			if (musicDescription.getPart(i).getArrangeHint() == 3) {
				musicDescription.addRenderEvent(new DefaultRenderer(), 0, musicDescription.getPartStartBar(i), musicDescription.getPartEndBar(i), 1, new Time(0, 0), 1.0);
				musicDescription.addRenderEvent(new DefaultRenderer(), 1, musicDescription.getPartStartBar(i), musicDescription.getPartEndBar(i), 2, new Time(0, 0.05), 0.75);
			}
			musicDescription.addRenderEvent(new SimpleRenderer_Style2(), 2, musicDescription.getPartStartBar(i), musicDescription.getPartEndBar(i), 1, new Time(0, 0), 0.6);
		}
		musicDescription.addRenderEvent(new SimpleChords(), 2, 0, musicDescription.getBarsCount(), 0,
				new Time(0, 0), 0.75);
	}
}
