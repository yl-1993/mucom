package Composition.Hongyu.Essential;

/**
 * 音符描述类
 */
public class Note {
	
	/**
	 * 起始时间
	 */
	private Time start;
	
	/**
	 * 终止时间
	 */
	private Time end;
	
	/**
	 * 音高
	 */
	private int pitch;
	
	/**
	 * 音量
	 */
	private int volume;
	
	/**
	 * 含参构造函数
	 * @param start 起始时间
	 * @param end 终止时间
	 * @param pitch 音高
	 * @param volume 音量
	 */
	public Note(Time start, Time end, int pitch, int volume) {
		this.start = start;
		this.end = end;
		this.pitch = pitch;
		this.volume = volume;
	}
	
	/**
	 * 平移
	 * @param bars 小节数
	 */
	public void translate(int bars) {
		start = start.translateClone(bars);
		end = end.translateClone(bars);
	}
	
	/**
	 * 平移
	 * @param time 时间
	 */
	public void translate(Time time) {
		start = start.translateClone(time);
		end = end.translateClone(time);
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
