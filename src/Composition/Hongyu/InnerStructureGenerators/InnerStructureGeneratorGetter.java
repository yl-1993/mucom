package Composition.Hongyu.InnerStructureGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.InnerStructureGenerator;

/**
 * 内部结构生成器获取类
 */
public class InnerStructureGeneratorGetter {
	
	/**
	 * 内部结构生成器枚举
	 */
	public static enum GeneratorsEnum {
		DefaultInnerStructureGenerator,
		ShortInnerStructureGenerator,
	}
	
	/**
	 * 内部结构生成器表
	 */
	public static final HashMap<GeneratorsEnum, InnerStructureGenerator> INNER_STRUCTURE_GENERATORS = new HashMap<>();
	
	static {
		INNER_STRUCTURE_GENERATORS.put(GeneratorsEnum.DefaultInnerStructureGenerator, new DefaultInnerStructureGenerator());
		INNER_STRUCTURE_GENERATORS.put(GeneratorsEnum.ShortInnerStructureGenerator, new ShortInnerStructureGenerator());
	}
	
	/**
	 * 选取内部结构生成器
	 * @param barsCount 小节数量
	 * @return 内部结构生成器
	 */
	public static InnerStructureGenerator getInnerStructureGenerator(int barsCount) {
		if (barsCount >= 6)
			return INNER_STRUCTURE_GENERATORS.get(GeneratorsEnum.DefaultInnerStructureGenerator);
		if (2 <= barsCount && barsCount < 6)
			return INNER_STRUCTURE_GENERATORS.get(GeneratorsEnum.ShortInnerStructureGenerator);
		throw new IllegalArgumentException();
	}
}
