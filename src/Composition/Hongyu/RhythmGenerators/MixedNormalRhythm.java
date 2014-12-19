package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.UniquePhrase;


public class MixedNormalRhythm implements RhythmGenerator {

	@Override
	public void generateRhythm(UniquePhrase uniquePhrase,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int flag = Common.getRandomInteger(0, 3);
		switch (flag) {
		case 0:
			new FixedNormalRhythm1().generateRhythm(uniquePhrase, parameter);
			return;
		case 1:
			new FixedNormalRhythm2().generateRhythm(uniquePhrase, parameter);
			return;
		default:
			new FixedNormalRhythm3().generateRhythm(uniquePhrase, parameter);
			return;
		}
	}
}
