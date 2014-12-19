package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * 装饰音乐的接口
 */
public interface Ornamenter {
	
	/**
	 * 装饰音乐
	 * @param part 一段音乐
	 * @param uniquePart 独立的一段音乐
	 * @param parameter 音乐参数
	 */
	public void ornament(Part part, UniquePart uniquePart, HashMap<String, Integer> parameter);
	
}
