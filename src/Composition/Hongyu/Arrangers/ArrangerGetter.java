package Composition.Hongyu.Arrangers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.Constant;

/**
 * 排布器获取类
 */
public class ArrangerGetter {
		
	/**
	 * 排布器枚举
	 */
	public static enum GeneratorsEnum {
		SimpleArranger,//简单的排布
		DefaultArranger,//较简单的排布
		NormalArranger,//普通的排布
		ComplexArranger,//较复杂的排布
		PianoAdvancedClassical,//复杂丰富的排布
		DefaultArrangerOrnament,//原TestArranger
		TestMelody,//测试旋律
		TestHarmony,//测试和弦
	}
	
	/**
	 * 排布器表
	 */
	public static final HashMap<GeneratorsEnum, Arranger> ARRANGERS = new HashMap<>();
	
	static {
		ARRANGERS.put(GeneratorsEnum.SimpleArranger, new SimpleArranger());
		ARRANGERS.put(GeneratorsEnum.DefaultArranger, new DefaultArranger());
		ARRANGERS.put(GeneratorsEnum.NormalArranger, new NormalArranger());
		ARRANGERS.put(GeneratorsEnum.ComplexArranger, new ComplexArranger());
		ARRANGERS.put(GeneratorsEnum.PianoAdvancedClassical, new PianoAdvancedClassical());
		ARRANGERS.put(GeneratorsEnum.DefaultArrangerOrnament, new DefaultArrangerOrnament());
		ARRANGERS.put(GeneratorsEnum.TestMelody, new TestMelody());
		ARRANGERS.put(GeneratorsEnum.TestHarmony, new TestHarmony());
	}
	
	/**
	 * 选取排布器
	 * @param 音乐参数
	 * @return 排布器
	 */
	public static Arranger getArranger(HashMap<String, Integer> parameter) {
		int speed = parameter.get(Constant.SPEED_STRING);
		if (speed <= 20)
			return ARRANGERS.get(GeneratorsEnum.SimpleArranger);
		if (speed <= 40)
			return ARRANGERS.get(GeneratorsEnum.DefaultArranger);
		if (speed <= 60)
			return ARRANGERS.get(GeneratorsEnum.NormalArranger);
		if (speed <= 80)
			return ARRANGERS.get(GeneratorsEnum.ComplexArranger);
		return ARRANGERS.get(GeneratorsEnum.PianoAdvancedClassical);
	}
	
}
