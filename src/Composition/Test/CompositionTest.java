package Composition.Test;

import java.util.HashMap;

import Generate.Composition;
import MIDI.Music;
import MIDI.NoteTiming;

public class CompositionTest implements Composition {

	@Override
	public void train(Music music, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		System.out.println("composition train");
		System.out.println(music);
		System.out.println(parameter);
	}

	@Override
	public void tidy() {
		// TODO Auto-generated method stub
		System.out.println("composition tidy");
	}

	@Override
	public Music generate(HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		System.out.println("composition generate");
		System.out.println(parameter);
		Music music = new Music();
		music.pushNote("c1", NoteTiming.succession, "whole", "fff");
		return music;
	}

}
