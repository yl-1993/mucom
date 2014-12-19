package Composition.Hongyu.Arrangers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Renderers.SimpleChords;

/**
 * ≤‚ ‘∫Õœ“◊®”√
 */
public class TestHarmony implements Arranger {

	@Override
	public void arrange(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		musicDescription.addTrack("Chords");
		musicDescription.addRenderEvent(new SimpleChords(), 0, 0, musicDescription.getBarsCount(), 0,
				new Time(0, 0), 0.75);
	}
	
}
