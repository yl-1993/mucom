package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * 产生和弦的接口
 */
public interface HarmonyGenerator {
	
	/**
	 * 产生和弦
	 * @param uniquePart 独立的一段音乐
	 * @param parameter 音乐参数
	 */
	public void generateHarmony(UniquePart uniquePart, HashMap<String, Integer> parameter);
	
}
