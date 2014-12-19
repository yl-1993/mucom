package Composition.Hongyu.Essential;

import java.util.ArrayList;

import Composition.Hongyu.Ornamenters.DefaultOrnamenter;

/**
 * 音乐的一段，与某个UniquePart关联
 */
public class Part {
	
	/**
	 * 装饰器
	 */
	public Ornamenter ornamenter = new DefaultOrnamenter();
	
	/**
	 * 起始小节
	 */
	private int startBar;
	
	/**
	 * 终止小节
	 */
	private int endBar;
	
	/**
	 * 独立部分的索引
	 */
	private int uniquePartIndex;
	
	/**
	 * 节拍
	 */
	private double tempo = 1.0;
	
	/**
	 * 移调
	 */
	private int transpose;
	
	/**
	 * 当前的调式名
	 */
	private String scale = "MAJOR";
	
	/**
	 * 当前的调式
	 */
	private int[] currentScale = Constant.MAJOR_ABSOLUTE_STEPS;
	
	/**
	 * 音符事件
	 */
	private ArrayList<NoteEvent> noteEvents = new ArrayList<NoteEvent>();

	/**
	 * 在安排每个部分时，需要用的标记
	 */
	private int arrangeHint = 3;
	
	/**
	 * 增加一个事件
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param pitch 音高
	 * @param volume 相对音量
	 */
	public void addEvent(Time start, Time end, int pitch, double volume) {
		noteEvents.add(new NoteEvent(start.clone(), end.clone(), pitch, volume));
	}
	
	/**
	 * 获得绝对的音高，用于加入事件
	 * @param scaleNote 相对音名
	 * @return 绝对的音高
	 */
	public int getAbsolutePitch(int scaleNote) {
		int scaleIndex = scaleNote - 1;
		int octaveOffset = 0;
		while (scaleIndex < 0) {
			scaleIndex += 7;
			octaveOffset--;
		}
		while (scaleIndex > 6) {
			scaleIndex -= 7;
			octaveOffset++;
		}
		return Constant.CHROMATIC_BASE + transpose + 
				currentScale[scaleIndex] + 12 * octaveOffset;
	}
	
	public int getStartBar() {
		return startBar;
	}
	
	public void setStartBar(int startBar) {
		this.startBar = startBar;
	}
	
	public int getEndBar() {
		return endBar;
	}
	
	public void setEndBar(int endBar) {
		this.endBar = endBar;
	}
	
	public int getUniquePartIndex() {
		return uniquePartIndex;
	}
	
	public void setUniquePartIndex(int uniquePartIndex) {
		this.uniquePartIndex = uniquePartIndex;
	}
	
	public double getTempo() {
		return tempo;
	}
	
	public void setTempo(double tempo) {
		this.tempo = tempo;
	}
	
	public int getTranspose() {
		return transpose;
	}
	
	public void setTranspose(int transpose) {
		this.transpose = transpose;
	}
	
	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
		currentScale = Constant.SCALE_OFFSETS_MAP.get(scale);
	}
	
	public int[] getCurrentScale() {
		return currentScale;
	}
	
	public void setCurrentScale(int[] currentScale) {
		this.currentScale = currentScale;
	}
	
	public ArrayList<NoteEvent> getNoteEvents() {
		return noteEvents;
	}
	
	public void setNoteEvents(ArrayList<NoteEvent> noteEvents) {
		this.noteEvents = noteEvents;
	}
	
	public int getArrangeHint() {
		return arrangeHint;
	}

	public void setArrangeHint(int arrangeHint) {
		this.arrangeHint = arrangeHint;
	}
}
