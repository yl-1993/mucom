package Composition.Hongyu.Essential;

import java.util.ArrayList;

/**
 * 音乐的一句
 */
public class Sentence {

	/**
	 * 所有小节
	 */
	private ArrayList<Phrase> phrases = new ArrayList<Phrase>();
	
	/**
	 * 获取小节的数量
	 * @return 小节的数量
	 */
	public int getPhrasesCount() {
		return phrases.size();
	}
	
	/**
	 * 获取指定索引下标的小节
	 * @param index 索引下标
	 * @return 获取到的小节
	 */
	public Phrase getPhrase(int index) {
		return Common.getElementSafe(index, phrases, Phrase.class);
	}
	
	/**
	 * 设定小节的数量
	 * @param count 设定的数量
	 */
	public void setPhrasesCount(int count) {
		Common.setSize(count, phrases, Phrase.class);
	}
	
	/**
	 * 获取音乐中实际小节的数量
	 * @return 获取到的数量
	 */
	public int getBarsCount() {
		int sum = 0;
		for (Phrase phrase : phrases) {
			sum += phrase.getBars();
		}
		return sum;
	}

}
