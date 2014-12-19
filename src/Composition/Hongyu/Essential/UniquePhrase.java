package Composition.Hongyu.Essential;

import java.util.ArrayList;

/**
 * 音乐的一节，是独立的
 */
public class UniquePhrase {
	
	/**
	 * 每节的拍数
	 */
	private int meter = 4;

	/**
	 * 包含的音乐小节数
	 */
	private int bars = 2;
	
	/**
	 * 所有事件
	 */
	private ArrayList<NoteEvent> events = new ArrayList<NoteEvent>();
	
	/**
	 * 是否为本段的开始
	 */
	private boolean startsPart;
	
	/**
	 * 是否为本段的结束
	 */
	private boolean endsPart;
	
	/**
	 * 是否为本句的开始
	 */
	private boolean startsSentence;
	
	/**
	 * 是否为本句的结束
	 */
	private boolean endsSentence;
	
	/**
	 * 序列号
	 */
	private int id;
	
	/**
	 * 添加一个事件
	 * @param start 起始时间
	 * @param end 终止时间
	 * @param volume 相对音量
	 */
	public void addEvent(Time start, Time end, double volume) {
		NoteEvent event = new NoteEvent(start, end, volume);
		events.add(event);
	}
	
	/**
	 * 获取事件的个数
	 * @return 事件的个数
	 */
	public int getEventsCount() {
		return events.size();
	}
	
	/**
	 * 获取指定索引下标的事件
	 * @param index 索引下标
	 * @return 获取到的事件
	 */
	public NoteEvent getEvent(int index) {
		return events.get(index);
	}
	
	public int getMeter() {
		return meter;
	}

	public void setMeter(int meter) {
		this.meter = meter;
	}

	public int getBars() {
		return bars;
	}

	public void setBars(int bars) {
		this.bars = bars;
	}

	public ArrayList<NoteEvent> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<NoteEvent> events) {
		this.events = events;
	}

	public boolean isStartsPart() {
		return startsPart;
	}

	public void setStartsPart(boolean startsPart) {
		this.startsPart = startsPart;
	}

	public boolean isEndsPart() {
		return endsPart;
	}

	public void setEndsPart(boolean endsPart) {
		this.endsPart = endsPart;
	}

	public boolean isStartsSentence() {
		return startsSentence;
	}

	public void setStartsSentence(boolean startsSentence) {
		this.startsSentence = startsSentence;
	}

	public boolean isEndsSentence() {
		return endsSentence;
	}

	public void setEndsSentence(boolean endsSentence) {
		this.endsSentence = endsSentence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
