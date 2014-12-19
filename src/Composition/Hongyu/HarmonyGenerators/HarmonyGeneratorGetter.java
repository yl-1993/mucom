package Composition.Hongyu.HarmonyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.HarmonyGenerator;

/**
 * 和弦生成器获取类
 */
public class HarmonyGeneratorGetter {

	/**
	 * 和弦生成器枚举
	 */
	public static enum GeneratorsEnum {
		DefaultHarmonyGenerator,//随机性有点大
		ChordMapHarmony,//一般般
		RandomRiffHarmony,//稍稍单调，个别地方不太合适
		AdvancedRandomHarmony,//略单调
		VerySlowHarmonyGenerator,//非常慢速的和弦
		SlowHarmonyGenerator,//慢速的和弦
		NormalHarmonyGenerator,//中速的和弦
		QuickHarmonyGenerator,//快速的和弦
		SimpleJazzHarmony,
	}
	
	/**
	 * 和弦生成器表
	 */
	public static final HashMap<GeneratorsEnum, HarmonyGenerator> HARMONY_GENERATORS = new HashMap<>();
	
	static {
		HARMONY_GENERATORS.put(GeneratorsEnum.DefaultHarmonyGenerator, new DefaultHarmonyGenerator());
		HARMONY_GENERATORS.put(GeneratorsEnum.ChordMapHarmony, new ChordMapHarmony());
		HARMONY_GENERATORS.put(GeneratorsEnum.RandomRiffHarmony, new RandomRiffHarmony());
		HARMONY_GENERATORS.put(GeneratorsEnum.AdvancedRandomHarmony, new AdvancedRandomHarmony());
		HARMONY_GENERATORS.put(GeneratorsEnum.VerySlowHarmonyGenerator, new VerySlowHarmonyGenerator());
		HARMONY_GENERATORS.put(GeneratorsEnum.SlowHarmonyGenerator, new SlowHarmonyGenerator());
		HARMONY_GENERATORS.put(GeneratorsEnum.NormalHarmonyGenerator, new NormalHarmonyGenerator());
		HARMONY_GENERATORS.put(GeneratorsEnum.QuickHarmonyGenerator, new QuickHarmonyGenerator());
		HARMONY_GENERATORS.put(GeneratorsEnum.SimpleJazzHarmony, new SimpleJazzHarmony());
	}
	
	/**
	 * 选取和弦生成器
	 * @param 音乐参数
	 * @return 和弦生成器
	 */
	public static HarmonyGenerator getHarmonyGenerator(HashMap<String, Integer> parameter) {
		int speed = parameter.get(Constant.SPEED_STRING);
		/*
		int variation = parameter.get(Constant.VARIATION_STRING);
		if (variation >= 40)
		{
			int mode = variation % 5;
			if (mode == 0)
				return HARMONY_GENERATORS.get(GeneratorsEnum.RandomRiffHarmony);
			if (mode == 1)
				return HARMONY_GENERATORS.get(GeneratorsEnum.AdvancedRandomHarmony);
			if (mode == 2)
				return HARMONY_GENERATORS.get(GeneratorsEnum.ChordMapHarmony);
			if (mode == 3)
				return HARMONY_GENERATORS.get(GeneratorsEnum.SimpleJazzHarmony);
			if (speed <= 20)
				return HARMONY_GENERATORS.get(GeneratorsEnum.VerySlowHarmonyGenerator);
			if (speed <= 50)
				return HARMONY_GENERATORS.get(GeneratorsEnum.SlowHarmonyGenerator);
			if (speed <= 80)
				return HARMONY_GENERATORS.get(GeneratorsEnum.NormalHarmonyGenerator);
			return HARMONY_GENERATORS.get(GeneratorsEnum.QuickHarmonyGenerator);
		}
		*/
		if (speed <= 20)
			return HARMONY_GENERATORS.get(GeneratorsEnum.VerySlowHarmonyGenerator);
		if (speed <= 50)
			return HARMONY_GENERATORS.get(GeneratorsEnum.SlowHarmonyGenerator);
		if (speed <= 80)
			return HARMONY_GENERATORS.get(GeneratorsEnum.NormalHarmonyGenerator);
		return HARMONY_GENERATORS.get(GeneratorsEnum.QuickHarmonyGenerator);
		
	}
}
