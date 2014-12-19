package Composition.Hongyu.StructureGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.StructureGenerator;

/**
 * 结构生成器获取类
 */
public class StructureGeneratorGetter {

	/**
	 * 结构生成器枚举
	 */
	public static enum GeneratorsEnum {
		DefaultStructureGenerator,
		StructureGeneratorConsiderTime,
	}

	/**
	 * 结构生成器表
	 */
	public static final HashMap<GeneratorsEnum, StructureGenerator> MELODY_GENERATORS = new HashMap<>();
	
	static {
		MELODY_GENERATORS.put(GeneratorsEnum.DefaultStructureGenerator, new DefaultStructureGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.StructureGeneratorConsiderTime, new StructureGeneratorConsiderTime());
	}
	
	/**
	 * 选取结构生成器
	 * @param 音乐参数
	 * @return 结构生成器
	 */
	public static StructureGenerator getStructureGenerator(HashMap<String, Integer> parameter) {
		return MELODY_GENERATORS.get(GeneratorsEnum.StructureGeneratorConsiderTime);
	}
}
