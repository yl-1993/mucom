package Composition.Hongyu.RhythmGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.RhythmGenerator;

/**
 * 节奏生成器获取类
 */
public class RhythmGeneratorGetter {
	
	/**
	 * 节奏生成器枚举
	 */
	public static enum GeneratorsEnum {
		RandomSlowRhythm,//Slow
		FixedSlowRhythm,//Slightly Slow
		MixedSlowRhythm,//Slow
		MixedNormalRhythm,//Normal
		MixedSlightlyFastRhythm,//Slightly Fast
		RandomFastRhythm,//Fast
		PianoAdvancedRhythm1,//Only used in PianoAdvanced & NormalArranger
		PianoAdvancedRhythm2,//Only used in PianoAdvanced & NormalArranger
		ExtremeFastRhythmGenerator,
		ExtremeSlowRhythmGenerator,
	}
	
	/**
	 * 节奏生成器表
	 */
	public static final HashMap<GeneratorsEnum, RhythmGenerator> RHYTHM_GENERATORS = new HashMap<>();
	
	static {
		RHYTHM_GENERATORS.put(GeneratorsEnum.RandomSlowRhythm, new RandomSlowRhythm());
		RHYTHM_GENERATORS.put(GeneratorsEnum.FixedSlowRhythm, new FixedSlowRhythm());
		RHYTHM_GENERATORS.put(GeneratorsEnum.MixedSlowRhythm, new MixedSlowRhythm());
		RHYTHM_GENERATORS.put(GeneratorsEnum.MixedNormalRhythm, new MixedNormalRhythm());
		RHYTHM_GENERATORS.put(GeneratorsEnum.MixedSlightlyFastRhythm, new MixedSlightlyFastRhythm());
		RHYTHM_GENERATORS.put(GeneratorsEnum.RandomFastRhythm, new RandomFastRhythm());
		RHYTHM_GENERATORS.put(GeneratorsEnum.PianoAdvancedRhythm1, new PianoAdvancedRhythm1());
		RHYTHM_GENERATORS.put(GeneratorsEnum.PianoAdvancedRhythm2, new PianoAdvancedRhythm2());
		RHYTHM_GENERATORS.put(GeneratorsEnum.ExtremeFastRhythmGenerator, new ExtremeFastRhythmGenerator());
		RHYTHM_GENERATORS.put(GeneratorsEnum.ExtremeSlowRhythmGenerator, new ExtremeSlowRhythmGenerator());
	}
	
	/**
	 * 选取节奏生成器
	 * @param 音乐参数
	 * @return 节奏生成器
	 */
	public static RhythmGenerator getRhythmGenerator(HashMap<String, Integer> parameter) {
		int speed = parameter.get(Constant.SPEED_STRING);
		if (speed <= 10) {
			return RHYTHM_GENERATORS.get(GeneratorsEnum.ExtremeSlowRhythmGenerator);
		}
		if (speed <= 30) {
			return RHYTHM_GENERATORS.get(GeneratorsEnum.MixedSlowRhythm);
		}
		if (speed <= 50) {
			if (Common.getRandomInteger(0, 5) != 2) 
				return RHYTHM_GENERATORS.get(GeneratorsEnum.MixedNormalRhythm);
			else
				return RHYTHM_GENERATORS.get(GeneratorsEnum.PianoAdvancedRhythm1);
		}
		if (speed <= 70) {
			if (Common.getRandomInteger(0, 5) != 2) 
				return RHYTHM_GENERATORS.get(GeneratorsEnum.MixedSlightlyFastRhythm);
			else
				return RHYTHM_GENERATORS.get(GeneratorsEnum.PianoAdvancedRhythm2);
		}
		if (speed <= 90) {
			return RHYTHM_GENERATORS.get(GeneratorsEnum.RandomFastRhythm);
		}
		return RHYTHM_GENERATORS.get(GeneratorsEnum.ExtremeFastRhythmGenerator);
	}
}
