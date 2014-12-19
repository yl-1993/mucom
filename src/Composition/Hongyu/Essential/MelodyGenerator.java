package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * 产生旋律的接口
 */
public interface MelodyGenerator {
	
	/**
	 * 产生旋律
	 * @param uniquePart 独立的一段音乐
	 * @param parameter 音乐参数
	 */
	public void generateMelody(UniquePart uniquePart, HashMap<String, Integer> parameter);

}
