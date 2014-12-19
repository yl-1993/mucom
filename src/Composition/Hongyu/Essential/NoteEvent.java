package Composition.Hongyu.Essential;

/**
 * 音符事件类
 */
public class NoteEvent {
	
	/**
	 * 音高
	 */
	private int pitch;
	
	/**
	 * 起始时间
	 */
	private Time start;
	
	/**
	 * 终止时间
	 */
	private Time end;

	/**
	 * 音量系数
	 */
	private double volume;
	
	/**
	 * 默认构造函数
	 */
	public NoteEvent() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 设定起始时间和终止时间的构造函数
	 * @param start 起始时间
	 * @param end 终止时间
	 * @param volume 相对音量
	 */
	public NoteEvent(Time start, Time end, double volume) {
		this.start = start;
		this.end = end;
		this.volume = volume;
	}
	
	/**
	 * 设定起始时间、终止时间和音高的构造函数
	 * @param start 起始时间
	 * @param end 终止时间
	 * @param volume 相对音量
	 * @param pitch 音高
	 */
	public NoteEvent(Time start, Time end, int pitch, double volume) {
		this.start = start;
		this.end = end;
		this.pitch = pitch;
		this.volume = volume;
	}
	
	/**
	 * 克隆这个事件对象
	 * @return 克隆后的对象
	 */
	public NoteEvent clone() {
		return new NoteEvent(start.clone(), end.clone(), pitch, volume);
	}
	
	/**
	 * 判断某个时间是否在这个事件的持续范围内
	 * @param time 待判断时间
	 * @param meter 每节的拍数
	 * @return 判断结果
	 */
	public boolean contains(Time time, int meter) {
		return contains(start, end, time, meter);
	}
	
	/**
	 * 判断某个时间是否在给定时间段的持续范围内
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param time 待判断时间
	 * @param meter 每节的拍数
	 * @return 判断结果
	 */
	public boolean contains(Time start, Time end, Time time, int meter) {
		double startPosition = start.getPosition(meter);
		double endPosition = end.getPosition(meter);
		double timePosition = time.getPosition(meter);
		return (timePosition >= startPosition && timePosition <= endPosition);
	}
	
	/**
	 * 判断某段时间是否与这个事件的持续范围相交
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param meter 每节的拍数
	 * @return 判断结果
	 */
	public boolean intersects(Time start, Time end, int meter) {
		return intersects(this.start, this.end, start, end, meter);
	}
	
	/**
	 * 判断两段时间是否相交
	 * @param oneStart 第一段的起始
	 * @param oneEnd 第一段的结束
	 * @param anotherStart 第二段的起始
	 * @param anotherEnd 第二段的结束
	 * @param meter 每节的拍数
	 * @return 判断结果
	 */
	public boolean intersects(Time oneStart, Time oneEnd, Time anotherStart, Time anotherEnd, int meter) {
		double oneStartPosition = oneStart.getPosition(meter);
		double oneEndPosition = oneEnd.getPosition(meter);
		double anotherStartPosition = anotherStart.getPosition(meter);
		double anotherEndPosition = anotherEnd.getPosition(meter);
		return (anotherEndPosition >= oneStartPosition && anotherStartPosition <= oneEndPosition);
	}
	
	/**
	 * 平移给定的小节数
	 * @param bars 给定的小节数
	 * @return 平移后的对象本身
	 */
	public NoteEvent translate(int bars) {
		start.translate(bars);
		end.translate(bars);
		return this;
	}
	
	/**
	 * 转换为区间
	 * @param meter 每节的拍数
	 * @return 转换后的区间
	 */
	public Interval toInterval(int meter) {
		return new Interval(start.getPosition(meter), end.getPosition(meter));
	}
	
	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}
	
	public Time getStart() {
		return start.clone();
	}

	public void setStart(Time start) {
		this.start = start.clone();
	}

	public Time getEnd() {
		return end.clone();
	}

	public void setEnd(Time end) {
		this.end = end.clone();
	}
	
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}
	
}
