package Composition.Hongyu.Arrangers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Renderers.DefaultRenderer;

/**
 * ²âÊÔÐýÂÉ×¨ÓÃ
 */
public class TestMelody implements Arranger {

	@Override
	public void arrange(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		musicDescription.addTrack("Melody");
		musicDescription.addRenderEvent(new DefaultRenderer(), 0, 0, musicDescription.getBarsCount(), 0,
				new Time(0, 0), 1);
	}

}
