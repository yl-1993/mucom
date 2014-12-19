package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.UniquePhrase;

public class MixedSlowRhythm implements RhythmGenerator {

	@Override
	public void generateRhythm(UniquePhrase uniquePhrase,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		double speed = parameter.get(Constant.SPEED_STRING);
		boolean mode = test(2 - speed / 20);
		if (mode) {
			new FixedSlowRhythm().generateRhythm(uniquePhrase, parameter);
		} else {
			new RandomSlowRhythm().generateRhythm(uniquePhrase, parameter);
		}
		return;
	}
	
	public boolean test(double probability) {
		return Common.getRandomDouble(0, 1) < probability;
	}

}
