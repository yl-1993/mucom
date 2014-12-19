package Composition.Hongyu.Arrangers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Renderers.ArpeggioChords;
import Composition.Hongyu.Renderers.DefaultRenderer;
import Composition.Hongyu.Renderers.SimpleChords;

/**
 * PianoSimpleArrangement
 */
public class DefaultArranger implements Arranger {

	@Override
	public void arrange(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		
		musicDescription.addTrack("Melody");
		musicDescription.addTrack("Voice");
		musicDescription.addTrack("Chords");
		
		int octiveOffsetCount = 0;
		if (parameter.get(Constant.PITCH_STRING) >= 70)
			octiveOffsetCount = 1;
		
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			if (musicDescription.getPart(i).getArrangeHint() == 0) {
				//上行的琶音
				musicDescription.addRenderEvent(new ArpeggioChords(), 2,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i) - 1, octiveOffsetCount,
						new Time(0, 0), 0.7);
			}
			if (musicDescription.getPart(i).getArrangeHint() == 1) {
				//普通旋律
				musicDescription.addRenderEvent(new DefaultRenderer(), 0, 
						musicDescription.getPartStartBar(i), 
						musicDescription.getPartEndBar(i), 
						1, new Time(0, 0), 0.85);
			}
			if (musicDescription.getPart(i).getArrangeHint() == 2) {
				//普通旋律
				musicDescription.addRenderEvent(new DefaultRenderer(), 1, 
						musicDescription.getPartStartBar(i), 
						musicDescription.getPartEndBar(i), 
						1, new Time(0, 0), 0.85);
			}
			if (musicDescription.getPart(i).getArrangeHint() == 3) {
				//普通旋律
				musicDescription.addRenderEvent(new DefaultRenderer(), 0, 
						musicDescription.getPartStartBar(i), 
						musicDescription.getPartEndBar(i), 
						1, new Time(0, 0), 1.0);
				//上行的琶音
				musicDescription.addRenderEvent(new ArpeggioChords(), 2,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i) - 1, octiveOffsetCount,
						new Time(0, 0), 0.7);
			}
		}
		//普通和弦
		musicDescription.addRenderEvent(new SimpleChords(), 2, 
				0, musicDescription.getBarsCount(), 
				0, new Time(0, 0), 0.7);
	}
}
