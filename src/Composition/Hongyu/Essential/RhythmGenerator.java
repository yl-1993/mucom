package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * 产生节奏的接口
 */
public interface RhythmGenerator {
	
	/**
	 * 产生节奏
	 * @param uniquePhrase 独立的一节音乐
	 * @param parameter 音乐参数
	 */
	public void generateRhythm(UniquePhrase uniquePhrase, HashMap<String, Integer> parameter);
}
