package Composition.Hongyu.Arrangers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Renderers.AccentedMelody;
import Composition.Hongyu.Renderers.ArpeggioChords;
import Composition.Hongyu.Renderers.DefaultRenderer;
import Composition.Hongyu.Renderers.RandomBassExtended;
import Composition.Hongyu.Renderers.ShortestWayChordsSimple;
import Composition.Hongyu.Renderers.SimpleChords;
import Composition.Hongyu.Renderers.SimpleChordsSmooth;

public class ComplexArranger implements Arranger {

	@Override
	public void arrange(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		musicDescription.addTrack("Melody");
		musicDescription.addTrack("Alt Voice");
		musicDescription.addTrack("Accomp1");
		musicDescription.addTrack("Bass");
		musicDescription.addTrack("Chords");
		musicDescription.addTrack("Accomp2");
		
		int[] off = new int[5];
		for (int i = 0; i < off.length; i++) {
			off[i] = rndInt(0, 1);
		}

		Renderer melody = new DefaultRenderer();
		if (rndInt(0, 2) == 0)
			melody = new AccentedMelody();

		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			if (musicDescription.getPart(i).getArrangeHint() == 0) {
				//简单的背景伴奏，音量较小
				musicDescription.addRenderEvent(new ShortestWayChordsSimple(),
						4, musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i) - 1, 0,
						new Time(0, 0), 0.6);
				//上行的琶音
				musicDescription.addRenderEvent(new ArpeggioChords(), 5,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i) - 1, off[0],
						new Time(0, 0), 0.5);
			} else if (musicDescription.getPart(i).getArrangeHint() == 1) {
				//普通旋律
				musicDescription
						.addRenderEvent(melody, 0, musicDescription
								.getPartStartBar(i), musicDescription
								.getPartEndBar(i), 1, new Time(0, 0), 0.8);
				//普通和弦
				musicDescription
						.addRenderEvent(new SimpleChords(), 4, musicDescription
								.getPartStartBar(i), musicDescription
								.getPartEndBar(i), 0, new Time(0, 0), 0.4);

			} else if (musicDescription.getPart(i).getArrangeHint() == 2) {
				//普通旋律
				musicDescription
						.addRenderEvent(melody, 1, musicDescription
								.getPartStartBar(i), musicDescription
								.getPartEndBar(i), 1, new Time(0, 0), 0.8);
				//普通和弦
				musicDescription
						.addRenderEvent(new SimpleChords(), 4, musicDescription
								.getPartStartBar(i), musicDescription
								.getPartEndBar(i), 0, new Time(0, 0), 0.4);

			} else if (musicDescription.getPart(i).getArrangeHint() == 3) {
				//普通的旋律
				musicDescription.addRenderEvent(melody, 1,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i), 1,
						new Time(0, 0), 1);
				//普通和弦
				musicDescription.addRenderEvent(new SimpleChords(), 4,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i) - 1, 0,
						new Time(0, 0), 0.5);
			}
			//每个Part的结尾部分的两个音
			musicDescription.addRenderEvent(new SimpleChords(), 4, 
					musicDescription.getPartEndBar(i) - 1, musicDescription
							.getPartEndBar(i), 1 + (off[musicDescription
							.getPart(i).getArrangeHint()] % 2), new Time(0, 0),
							0.6);
			if (musicDescription.getPart(i).getArrangeHint() == 3) {
				//稳定的单重音-低
				musicDescription.addRenderEvent(new RandomBassExtended(), 3,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i), -1, new Time(0, 0),
						0.7);
			} else {
				//上行的琶音
				musicDescription.addRenderEvent(new ArpeggioChords(), 2,
						musicDescription.getPartStartBar(i), musicDescription
								.getPartEndBar(i) - 1, off[musicDescription
								.getPart(i).getArrangeHint()], new Time(0, 0), 0.5);
			}
		}
		//乐曲结尾和弦
		int bars = musicDescription.getBarsCount();
		musicDescription.addRenderEvent(
				new SimpleChordsSmooth(), 2,
				bars - 1, bars, 3,
				new Time(0, musicDescription.getUniquePart(
						musicDescription.getPart(0).getUniquePartIndex())
						.getMeter() / 2), 0.7);
	}
	
	private int rndInt(int i, int j) {
		// TODO Auto-generated method stub
		return Common.getRandomInteger(i, j + 1);
	}

}
