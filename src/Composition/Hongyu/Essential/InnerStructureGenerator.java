package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * 产生内部结构的接口
 */
public interface InnerStructureGenerator {
	
	/**
	 * 产生内部结构
	 * @param uniquePart 独立的一段音乐
	 * @param parameter 音乐参数
	 */
	public void generateInnerStructure(UniquePart uniquePart, HashMap<String, Integer> parameter);

}
