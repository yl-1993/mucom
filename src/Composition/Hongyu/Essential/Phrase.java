package Composition.Hongyu.Essential;

/**
 * 音乐的一节，与某个UniquePhrase关联
 */
public class Phrase {
	
	/**
	 * 一节所包含的的真正小节数
	 */
	private int bars = 2;
	
	/**
	 * 对应独立小节的id
	 */
	private int uniquePhraseId;
	
	/**
	 * 对应独立小节
	 */
	private UniquePhrase uniquePhrase;
	
	public int getBars() {
		return bars;
	}

	public void setBars(int bars) {
		this.bars = bars;
	}

	public int getUniquePhraseId() {
		return uniquePhraseId;
	}

	public void setUniquePhraseId(int uniquePhraseId) {
		this.uniquePhraseId = uniquePhraseId;
	}

	public UniquePhrase getUniquePhrase() {
		return uniquePhrase;
	}

	public void setUniquePhrase(UniquePhrase uniquePhrase) {
		this.uniquePhrase = uniquePhrase;
	}

}
