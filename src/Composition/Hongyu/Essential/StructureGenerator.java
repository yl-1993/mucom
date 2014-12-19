package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * 产生结构的接口
 */
public interface StructureGenerator {
	
	/**
	 * 产生结构
	 * @param musicDescription 音乐描述对象
	 * @param parameter 音乐参数
	 */
	public void generateStructure(MusicDescription musicDescription, HashMap<String, Integer> parameter);
	
}
