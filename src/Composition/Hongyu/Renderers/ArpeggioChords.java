package Composition.Hongyu.Renderers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;

public class ArpeggioChords implements Renderer {

	private int[][] arp = { { 0, 2, 1, 2 }, { 0, 1, 2, 1 }, { 0, 1, 2, 3 },
			{ 0, 1, 2, 3 }, { 2, 0, 3, 0 }, { 2, 0, 1, 0 },
			{ 0, 1, 2, 3, 2, 1 }, { 0, 1, 2 }, { 0, 2, 3 }, { 0, 2, 4 },
			{ 0, 1, 2, 3, 4, 5, 6, 7 } };
	
	@Override
	public void render(RenderPart p, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method
		Time t = new Time(0, 0);

		double basic_tempo = 0.5;
		if (parameter.get(Constant.SPEED_STRING) <= 60)
			basic_tempo = 1;
		if (parameter.get(Constant.SPEED_STRING) <= 40)
			basic_tempo = 2;

		double len = basic_tempo;

		int len_mode = Common.getRandomInteger(0, 4);

		if (len_mode == 0)
			len = basic_tempo;
		if (len_mode == 1)
			len = basic_tempo * 1.5;
		if (len_mode == 2)
			len = basic_tempo * 0.95;
		if (len_mode == 3)
			len = basic_tempo * 0.85;

		int a = Common.getRandomInteger(0, arp.length);
		int note = 0;
		int old_nhp = -1;

		int reset_on_bar = Common.getRandomInteger(0, 2);
		int reset_on_chord_change = Common.getRandomInteger(0, 2);

		while (t.startBar < p.getUniquePart().getBarsCount()) {
			Time t2 = new Time(0, 0);
			t2.startBar = t.startBar;
			t2.position = t.position + len;

			int nnhp = p.getHarmonicEventPitch(p.getHarmonic(t), 0);
			if (nnhp != old_nhp && reset_on_chord_change == 1) {
				note = 0;
				old_nhp = nnhp;
			}

			p.addNote(t, t2, p.getHarmonicEventPitch(p.getHarmonic(t),
					arp[a][note]), (int) (120 * p.getHarmonicEventVolume(p.getHarmonic(t))));

			note = (note + 1) % arp[a].length;

			t.position += basic_tempo;
			if (t.position >= p.getUniquePart().getMeter()) {
				t.startBar++;
				t.position = 0;
				if (reset_on_bar == 1)
					note = 0;
			}
		}
	}

}
