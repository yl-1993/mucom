package Composition.Hongyu.Essential;

/**
 * 和弦类
 */
public class Harmonic {

	/**
	 * 开始时间
	 */
	private Time startTime;
	
	/**
	 * 结束时间
	 */
	private Time endTime;
	
	/**
	 * 相对音量
	 */
	private double volume;
	
	/**
	 * 基准音
	 */
	private int baseNote;
	
	/**
	 * 和弦音符的偏移
	 */
	private int[] offsets;
	
	/**
	 * 和弦模式
	 */
	private String chordString;
	
	/**
	 * 默认构造函数
	 */
	public Harmonic() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 含参构造函数
	 * @param time 起始时间
	 * @param baseNote 基准音
	 * @param chordString 和弦类型
	 * @param volume 相对音量
	 */
	public Harmonic(Time time, int baseNote, String chordString, double volume) {
		this.startTime = time;
		this.baseNote = baseNote;
		this.chordString = chordString;
		this.volume = volume;
		this.offsets = new int[chordString.length()];
		for (int i = 0; i < chordString.length(); i++) {
			char numberChar = chordString.charAt(i);
			if (numberChar >= '1' && numberChar <= '7') {
				String numberString = "" + numberChar;
				this.offsets[i] = Integer.parseInt(numberString) - 1;
			}
		}
	}
	
	/**
	 * 转换为区间
	 * @param meter 每节的拍数
	 * @return 转换后的区间
	 */
	public Interval toInterval(int meter) {
		return new Interval(startTime.getPosition(meter), endTime.getPosition(meter));
	}
	
	/**
	 * 获取和弦音符的度
	 * @return 和弦音符的度数组
	 */
	public Integer[] getScaleDegrees() {
		Integer[] result = new Integer[offsets.length];
		for (int i = 0; i < offsets.length; i++) {
			result[i] = (baseNote - 1 + offsets[i]) % 7;
		}
		return result;
	}
	
	/**
	 * 克隆这个对象
	 * @return 克隆后结果
	 */
	public Harmonic clone() {
		Harmonic result = new Harmonic();
		result.offsets = offsets.clone();
		result.startTime = startTime.clone();
		result.endTime = endTime.clone();
		result.volume = volume;
		result.baseNote = baseNote;
		result.chordString = chordString;
		return result;
	}
	
	/**
	 * 平移起始时间和终止时间
	 * @param bars 平移的小节数
	 */
	public void translate(int bars) {
		startTime = startTime.translateClone(bars);
		endTime = endTime.translateClone(bars);
	}
	
	public Time getStartTime() {
		return startTime.clone();
	}
	
	public void setStartTime(Time time) {
		startTime = time.clone();
	}
	
	public Time getEndTime() {
		return endTime.clone();
	}
	
	public void setEndTime(Time time) {
		endTime = time.clone();
	}
	
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	
	public int getBaseNote() {
		return baseNote;
	}

	public void setBaseNote(int baseNote) {
		this.baseNote = baseNote;
	}
	
	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public String getChordString() {
		return chordString;
	}

	public void setChordString(String chordString) {
		this.chordString = chordString;
	}

}
