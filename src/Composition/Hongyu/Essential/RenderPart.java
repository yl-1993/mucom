package Composition.Hongyu.Essential;

import java.util.ArrayList;

/**
 * 包装Part，用于渲染
 */
public class RenderPart {
	
	/**
	 * 音轨索引
	 */
	private int trackIndex;

	/**
	 * 音轨名称
	 */
	private String trackName;
	
	/**
	 * 音符事件
	 */
	private ArrayList<NoteEvent> noteEvents;
	
	/**
	 * 和弦
	 */
	private ArrayList<Harmonic> harmonics;
	
	/**
	 * 包含的Part
	 */
	private Part part;
	
	/**
	 * 对应的UniquePart
	 */
	private UniquePart uniquePart;
	
	/**
	 * 调式偏移数组
	 */
	private int[] scaleOffsets;
	
	/**
	 * 渲染用事件
	 */
	private RenderEvent renderEvent;

	/**
	 * 音乐描述对象
	 */
	private MusicDescription musicDescription;

	/**
	 * 音符描述数组
	 */
	private ArrayList<Note> notes = new ArrayList<>();
	
	/**
	 * 构造函数
	 * @param trackIndex 所属音轨
	 */
	public RenderPart(int trackIndex) {
		this.trackIndex = trackIndex;
	}
	
	/**
	 * 设置相关数据
	 * @param part 音乐的一段
	 * @param uniquePart 音乐的一段（独立的）
	 * @param renderEvent 渲染事件
	 * @param musicDescription 音乐描述对象
	 */
	public void setData(Part part, UniquePart uniquePart, RenderEvent renderEvent,
			MusicDescription musicDescription) {
		this.part = part;
		this.uniquePart = uniquePart;
		this.renderEvent = renderEvent;
		this.musicDescription = musicDescription;

		ArrayList<NoteEvent> events = part.getNoteEvents();
		this.noteEvents = new ArrayList<NoteEvent>();
		for (NoteEvent event : events) {
			this.noteEvents.add(event.clone());
		}

		ArrayList<Harmonic> harmonics = uniquePart.getHarmonics();
		this.harmonics = new ArrayList<Harmonic>();
		for (Harmonic harmonic : harmonics) {
			this.harmonics.add(harmonic.clone());
		}
		
		this.scaleOffsets = part.getCurrentScale().clone();
	}
	
	/**
	 * 获取事件个数
	 * @return 事件的个数
	 */
	public int getEventsCount() {
		return noteEvents.size();
	}
	
	/**
	 * 获取和弦
	 * @param time 时间
	 * @return 获取到的和弦
	 */
	public int getHarmonic(Time time) {
		int meter = uniquePart.getMeter();
		double position = time.getPosition(meter);

		for (int i = 0; i < harmonics.size(); i++) {
			Harmonic harmonic = harmonics.get(i);
			Interval interval = harmonic.toInterval(meter);
			if (interval.contains(position) && interval.contains(position + 0.01)) {
				return i;
			}
		}

		if (position < 0) {
			return 0;
		} else {
			return harmonics.size() - 1;
		}
		
	}
	
	/**
	 * 获得指定索引下标的事件的起始时间
	 * @param index 索引下标
	 * @return 起始时间
	 */
	public Time getEventStart(int index) {
		return noteEvents.get(index).getStart();
	}
	
	/**
	 * 获得指定索引下标的事件的结束时间
	 * @param index 索引下标
	 * @return 结束时间
	 */
	public Time getEventEnd(int index) {
		return noteEvents.get(index).getEnd();
	}
	
	/**
	 * 获得事件的音高
	 * @param index 事件的索引下标
	 * @return 音高
	 */
	public int getEventPitch(int index) {
		return 12 * renderEvent.getOctave()
				+ noteEvents.get(index).getPitch();
	}
	
	/**
	 * 获取事件的音量
	 * @param index 事件的索引下标
	 * @return 相对音量
	 */
	public double getEventVolume(int index) {
		return noteEvents.get(index).getVolume();
	}
	
	/**
	 * 获取和弦音高
	 * @param index 和弦事件索引
	 * @param chordNoteIndex 和弦音符索引
	 * @return 和弦的音高
	 */
	public int getHarmonicEventPitch(int index, int chordNoteIndex) {
		Harmonic harmonic = harmonics.get(index);
		int[] offsets = harmonic.getOffsets();
		int octaveOffset = 0;
		while (chordNoteIndex < 0) {
			chordNoteIndex += offsets.length;
			octaveOffset--;
		}
		while (chordNoteIndex >= offsets.length) {
			chordNoteIndex -= offsets.length;
			octaveOffset++;
		}
		int scaleIndex = harmonic.getBaseNote()
				+ harmonic.getOffsets()[chordNoteIndex];
		return 12 * renderEvent.getOctave() + octaveOffset * 12
				+ part.getAbsolutePitch(scaleIndex);
	}
	
	/**
	 * 获取和弦事件的起始时间
	 * @param index 索引下标
	 * @return 起始时间
	 */
	public Time getHarmonicEventStart(int index) {
		Harmonic harmonic = harmonics.get(index);
		return harmonic.getStartTime();
	}
	
	/**
	 * 获取和弦事件的终止时间
	 * @param index 索引下标
	 * @return 终止时间
	 */
	public Time getHarmonicEventEnd(int index) {
		Harmonic harmonic = harmonics.get(index);
		return harmonic.getEndTime();
	}
	
	/**
	 * 获取和弦事件的相对音量
	 * @param index 索引下标
	 * @return 相对音量
	 */
	public double getHarmonicEventVolume(int index) {
		Harmonic harmonic = harmonics.get(index);
		return harmonic.getVolume();
	}
	
	/**
	 * 音阶对齐
	 * @param chromaticChordNote 音阶
	 * @param scaleOffset 音名下标
	 * @return 处理后的音阶
	 */
	public int alignPitch(int chromaticChordNote, int scaleOffset) {

		int baseChromaticScaleNote = part.getAbsolutePitch(1);
		int[] scaleOffsets = part.getCurrentScale();
		Integer[] pitchClasses = new Integer[scaleOffsets.length];
		for (int i = 0; i < scaleOffsets.length; i++) {
			pitchClasses[i] = (baseChromaticScaleNote + scaleOffsets[i]) % 12;
		}
		int inPitchClass = chromaticChordNote % 12;
		int theOriginalScaleIndex = 0;
		if (!Common.arrayContains(pitchClasses, inPitchClass)) {
			for (int i = chromaticChordNote - 7; i < chromaticChordNote + 8; i++) {
			}
		}
		for (int i = 0; i < pitchClasses.length; i++) {
			if (inPitchClass == pitchClasses[i]) {
				theOriginalScaleIndex = i;
				break;
			}
		}
		int newPitchClass = pitchClasses[Common.positiveMod(
				theOriginalScaleIndex + scaleOffset, pitchClasses.length)];
		int increment = scaleOffset > 0 ? 1 : -1;
		int currentNote = chromaticChordNote;
		while (true) {
			if ((currentNote % 12) == newPitchClass) {
				return currentNote;
			}
			currentNote += increment;
			while (currentNote < 0)
				currentNote += 12;
		}
	}
	
	/**
	 * 添加音符
	 * @param start 起始时间
	 * @param end 终止时间
	 * @param pitch 音高
	 * @param absoluteVolume 绝对音量
	 */
	public void addNote(Time start, Time end, int pitch, int absoluteVolume) {
		int initialStep = renderEvent.getInitialStep() - part.getStartBar();
		int finalStep = renderEvent.getFinalStep() - part.getStartBar();
		if (start.startBar >= initialStep && start.startBar <= finalStep) {
			notes.add(new Note(start.clone(), end.clone(), pitch,
					(int) (absoluteVolume * renderEvent.getVolume())));
		}
	}
	
	/**
	 * 平移音符
	 * @param bars 小节数
	 */
	public void translateNotes(int bars) {
		for (Note n : notes) {
			n.translate(bars);
		}
	}

	/**
	 * 平移音符
	 * @param time 平移时间
	 */
	public void translateNotes(Time time) {
		for (Note n : notes) {
			n.translate(time);
		}
	}
	
	/**
	 * 获取和弦类型数组的大小
	 * @param index 和弦的索引下标
	 * @return 数组大小
	 */
	public int getHarmonicOffsetCount(int index) {
		Harmonic harmonic = harmonics.get(index);
		return harmonic.getOffsets().length;
	}
	
	public int getTrackIndex() {
		return trackIndex;
	}

	public void setTrackIndex(int trackIndex) {
		this.trackIndex = trackIndex;
	}
	
	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public ArrayList<NoteEvent> getNoteEvents() {
		return noteEvents;
	}

	public void setNoteEvents(ArrayList<NoteEvent> noteEvents) {
		this.noteEvents = noteEvents;
	}

	public ArrayList<Harmonic> getHarmonics() {
		return harmonics;
	}

	public void setHarmonics(ArrayList<Harmonic> harmonics) {
		this.harmonics = harmonics;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public UniquePart getUniquePart() {
		return uniquePart;
	}

	public void setUniquePart(UniquePart uniquePart) {
		this.uniquePart = uniquePart;
	}

	public int[] getScaleOffsets() {
		return scaleOffsets;
	}

	public void setScaleOffsets(int[] scaleOffsets) {
		this.scaleOffsets = scaleOffsets;
	}

	public RenderEvent getRenderEvent() {
		return renderEvent;
	}

	public void setRenderEvent(RenderEvent renderEvent) {
		this.renderEvent = renderEvent;
	}
	
	public MusicDescription getMusicDescription() {
		return musicDescription;
	}

	public void setMusicDescription(MusicDescription musicDescription) {
		this.musicDescription = musicDescription;
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}
}
