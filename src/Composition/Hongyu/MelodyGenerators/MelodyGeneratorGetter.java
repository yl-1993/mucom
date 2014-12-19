package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MelodyGenerator;

/**
 * 旋律生成器获取类
 */
public class MelodyGeneratorGetter {
	
	/**
	 * 旋律生成器枚举
	 */
	public static enum GeneratorsEnum {
		DefaultMelodyGenerator,
		RandomPhrasedMelody,
		WideRandomMelody,
		MarkovMelodyGenerator,
		MarkovMelodyGeneratorVariation,
		ExtremeHighMelodyGenerator,
		ExtremeLowMelodyGenerator,
		ExtremeVariousMelodyGenerator,
		ExtremeMonotonousMelodyGenerator,
		TestMelodyGenerator,
		SimpleMelodyGenerator_Style1,//趋势略高
		SimpleMelodyGenerator_Style2,//起伏可能很大，随机性强
		SimpleMelodyGenerator_Style3,//趋势过高
		SimpleMelodyGenerator_Style4,//起伏很小，旋律较舒缓，但变化少
	}
	
	/**
	 * 旋律生成器表
	 */
	public static final HashMap<GeneratorsEnum, MelodyGenerator> MELODY_GENERATORS = new HashMap<>();
	
	static {
		MELODY_GENERATORS.put(GeneratorsEnum.DefaultMelodyGenerator, new DefaultMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.RandomPhrasedMelody, new RandomPhrasedMelody());
		MELODY_GENERATORS.put(GeneratorsEnum.WideRandomMelody, new WideRandomMelody());
		MELODY_GENERATORS.put(GeneratorsEnum.MarkovMelodyGenerator, new MarkovMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.MarkovMelodyGeneratorVariation, new MarkovMelodyGeneratorVariation());
		MELODY_GENERATORS.put(GeneratorsEnum.ExtremeHighMelodyGenerator, new ExtremeHighMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.ExtremeLowMelodyGenerator, new ExtremeLowMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.ExtremeVariousMelodyGenerator, new ExtremeVariousMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.ExtremeMonotonousMelodyGenerator, new ExtremeMonotonousMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.TestMelodyGenerator, new TestMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.SimpleMelodyGenerator_Style1, new SimpleMelodyGenerator_Style1());
		MELODY_GENERATORS.put(GeneratorsEnum.SimpleMelodyGenerator_Style2, new SimpleMelodyGenerator_Style2());
		MELODY_GENERATORS.put(GeneratorsEnum.SimpleMelodyGenerator_Style3, new SimpleMelodyGenerator_Style3());
		MELODY_GENERATORS.put(GeneratorsEnum.SimpleMelodyGenerator_Style4, new SimpleMelodyGenerator_Style4());
	}
	
	/**
	 * 选取旋律生成器
	 * @param 音乐参数
	 * @return 旋律生成器
	 */
	public static MelodyGenerator getMelodyGenerator(HashMap<String, Integer> parameter) {
		int pitch = parameter.get(Constant.PITCH_STRING);
		int variation = parameter.get(Constant.VARIATION_STRING);
		if (variation <= 10) {
			return MELODY_GENERATORS.get(GeneratorsEnum.ExtremeMonotonousMelodyGenerator);
		}
		if (variation >= 90) {
			return MELODY_GENERATORS.get(GeneratorsEnum.ExtremeVariousMelodyGenerator);
		}
		if (pitch <= 10) {
			return MELODY_GENERATORS.get(GeneratorsEnum.ExtremeLowMelodyGenerator);
		}
		if (pitch >= 90) {
			return MELODY_GENERATORS.get(GeneratorsEnum.ExtremeHighMelodyGenerator);
		}
		return MELODY_GENERATORS.get(GeneratorsEnum.MarkovMelodyGenerator);
	}
}
