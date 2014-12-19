package Composition.Hongyu.Ornamenters;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Ornamenter;

/**
 * 修饰器获取类
 */
public class OrnamenterGetter {
	
	/**
	 * 修饰器枚举
	 */
	public static enum GeneratorsEnum {
		DefaultOrnamenter,
		SimpleOrnamentationModified,
		Appoggiatura,
	}
	
	/**
	 * 修饰器表
	 */
	public static final HashMap<GeneratorsEnum, Ornamenter> ORNAMENTERS = new HashMap<>();
	
	static {
		ORNAMENTERS.put(GeneratorsEnum.DefaultOrnamenter, new DefaultOrnamenter());
		ORNAMENTERS.put(GeneratorsEnum.SimpleOrnamentationModified, new SimpleOrnamentationModified());
		ORNAMENTERS.put(GeneratorsEnum.Appoggiatura, new Appoggiatura());
	}
	
	/**
	 * 选取修饰器
	 * @param 音乐参数
	 * @return 修饰器
	 */
	public static Ornamenter getOrnamenter(HashMap<String, Integer> parameter) {
		return (Ornamenter) Common.getRandomElement(ORNAMENTERS.values().toArray());
	}
	
}
