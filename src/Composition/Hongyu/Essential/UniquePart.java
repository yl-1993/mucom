package Composition.Hongyu.Essential;

import java.util.ArrayList;
import java.util.HashMap;

import Composition.Hongyu.HarmonyGenerators.DefaultHarmonyGenerator;
import Composition.Hongyu.InnerStructureGenerators.DefaultInnerStructureGenerator;
import Composition.Hongyu.MelodyGenerators.DefaultMelodyGenerator;
import Composition.Hongyu.RhythmGenerators.DefaultRhythmGenerator;

/**
 * 音乐的一段，是独立的
 */
public class UniquePart {
	
	/**
	 * 内部结构生成器
	 */
	public InnerStructureGenerator innerStructureGenerator = new DefaultInnerStructureGenerator();
	
	/**
	 * 节奏生成器
	 */
	public RhythmGenerator rhythmGenerator = new DefaultRhythmGenerator();
	
	/**
	 * 和弦生成器
	 */
	public HarmonyGenerator harmonyGenerator = new DefaultHarmonyGenerator();
	
	/**
	 * 旋律生成器
	 */
	public MelodyGenerator melodyGenerator = new DefaultMelodyGenerator();
		
	/**
	 * 每节的拍数
	 */
	private int meter = 4;
	
	/**
	 * 独立的小节数组
	 */
	private ArrayList<UniquePhrase> uniquePhrases = new ArrayList<>();
	
	/**
	 * 句子数组
	 */
	private ArrayList<Sentence> sentences = new ArrayList<>();
	
	/**
	 * 和弦数组
	 */
	private ArrayList<Harmonic> harmonics = new ArrayList<>();
	
	/**
	 * 音符事件数组
	 */
	private ArrayList<NoteEvent> noteEvents = new ArrayList<>();
	
	/**
	 * 事件与和弦的关联表
	 */
	private HashMap<NoteEvent, Harmonic> eventHarmonicMap = new HashMap<>();
	
	/**
	 * 将事件和和弦对应
	 */
	public void assignEventsToHarmony() {
		int barsCount = getBarsCount();
		int meter = getMeter();
		Time endOfTime = new Time(barsCount - 1, meter);
		
		for (int i = 0; i < harmonics.size(); i++) {
			Harmonic harmonic1 = harmonics.get(i);
			Time time1 = harmonic1.getStartTime();
			Time time2 = endOfTime;
			if (i + 1 < harmonics.size()) {
				Harmonic harmonic2 = harmonics.get(i + 1);
				time2 = harmonic2.getStartTime();
			}
			harmonic1.setEndTime(time2.clone());
			for (NoteEvent event : noteEvents) {
				if (event.intersects(time1, time2, meter)) {
					boolean hasEvent = eventHarmonicMap.containsKey(event);
					if (!hasEvent) {
						eventHarmonicMap.put(event, harmonic1);
					} else {
						Harmonic harmonic = eventHarmonicMap.get(event);
						double oldOverlap = harmonic.toInterval(meter)
								.intersectClone(event.toInterval(meter))
								.getLength();
						double newOverlap = harmonic1.toInterval(meter)
								.intersectClone(event.toInterval(meter))
								.getLength();
						if (newOverlap > oldOverlap) {
							eventHarmonicMap.put(event, harmonic1);
						}
					}
				}
			}
		}
		
		for (NoteEvent event : noteEvents) {
			if (!eventHarmonicMap.containsKey(event)) {
				Time start = event.getStart();
				Time end = event.getEnd();
				if (start.getPosition(meter) >= endOfTime.getPosition(meter)) {
					eventHarmonicMap.put(event, harmonics.get(harmonics.size() - 1));
				} else if (end.getPosition(meter) <= 0) {
					eventHarmonicMap.put(event, harmonics.get(0));
				} else {
					
				}
			}
		}
	}
	
	/**
	 * 将音符与和弦对应
	 * @param eventIndex 事件索引
	 * @param scaleNote 相对音名
	 * @return 基准音
	 */
	public int alignPitchToHarmonic(int eventIndex, int scaleNote) {
		NoteEvent event = noteEvents.get(eventIndex);
		Harmonic harmonic = eventHarmonicMap.get(event);
		Integer[] scaleDegrees = harmonic.getScaleDegrees();
		Integer scaleDegree = Common.positiveMod(scaleNote - 1, 7);
		if (Common.arrayContains(scaleDegrees, scaleDegree)) {
			return scaleNote;
		}
		int closestNote = harmonic.getBaseNote();
		int closestDistance = Integer.MAX_VALUE;
		for (int i = scaleNote - 7; i < scaleNote + 7; i++) {
			scaleDegree = Common.positiveMod(i - 1, 7);
			if (Common.arrayContains(scaleDegrees, scaleDegree)) {
				int distance = Math.abs(i - scaleNote);
				if (distance < closestDistance) {
					closestDistance = distance;
					closestNote = i;
				}
			}
		}
		return closestNote;
	}
	
	/**
	 * 获取总的节拍数
	 * @return
	 */
	public int getBarsCount() {
		int bars = 0;
		for (Sentence sentence : sentences) {
			bars += sentence.getBarsCount();
		}
		return bars;
	}
	
	/**
	 * 获得事件和弦的基准音
	 * @param index 事件的索引下标
	 * @return 基准音
	 */
	public int getEventBasis(int index) {
		NoteEvent event = noteEvents.get(index);
		Harmonic harmonic = eventHarmonicMap.get(event);
		return harmonic.getBaseNote();
	}
	
	/**
	 * 增加新的和弦
	 * @param time 时间
	 * @param baseNote 基准音
	 * @param chordString 和弦模式
	 * @param volume 相对音量
	 */
	public void addHarmonic(Time time, int baseNote, String chordString, double volume) {
		harmonics.add(new Harmonic(time, baseNote, chordString, volume));
	}
	
	/**
	 * 增加一个音符事件
	 * @param event 待增加的事件
	 */
	public void addEvent(NoteEvent event) {
		noteEvents.add(event);
	}
	
	/**
	 * 获取指定事件对应的和弦
	 * @param event 指定的事件
	 * @return 对应的和弦
	 */
	public Harmonic getEventHarmonic(NoteEvent event) {
		return eventHarmonicMap.get(event);
	}
	
	/**
	 * 获取一个事件的开始时间
	 * @param index 索引下标
	 * @return 开始时间
	 */
	public Time getEventStart(int index) {
		return noteEvents.get(index).getStart();
	}
	
	/**
	 * 获取一个事件的结束时间
	 * @param index 索引下标
	 * @return 结束时间
	 */
	public Time getEventEnd(int index) {
		return noteEvents.get(index).getEnd();
	}
	
	/**
	 * 获取一个事件的音高
	 * @param index 索引下标
	 * @return 音高
	 */
	public int getEventPitch(int index) {
		return noteEvents.get(index).getPitch();
	}
	
	/**
	 * 获取一个事件的相对音量
	 * @param index 索引下标
	 * @return 相对音量
	 */
	public double getEventVolume(int index) {
		return noteEvents.get(index).getVolume();
	}
	
	/**
	 * 获取事件的个数
	 * @return 事件的个数
	 */
	public int getEventsCount() {
		return noteEvents.size();
	}
	
	/**
	 * 设定一个事件的音高
	 * @param index 索引下标
	 * @param pitch 设置的音高
	 */
	public void setEventPitch(int index, int pitch) {
		noteEvents.get(index).setPitch(pitch);
	}
	
	/**
	 * 设定句子数量
	 * @param count 设定的数量
	 */
	public void setSentencesCount(int count) {
		Common.setSize(count, sentences, Sentence.class);
	}
	
	/**
	 * 获取句子数量
	 * @return 句子的数量
	 */
	public int getSentencesCount() {
		return sentences.size();
	}
	
	/**
	 * 获取指定索引下标的句子
	 * @param index 索引下标
	 * @return 获取到的句子
	 */
	public Sentence getSentence(int index) {
		return Common.getElementSafe(index, sentences, Sentence.class);
	}
	
	/**
	 * 设定独立小节的数量
	 * @param count 设定的数量
	 */
	public void setUniquePhrasesCount(int count) {
		Common.setSize(count, uniquePhrases, UniquePhrase.class);
	}
	
	/**
	 * 获取独立小节的数量
	 * @return 独立小节的数量
	 */
	public int getUniquePhrasesCount() {
		return uniquePhrases.size();
	}
	
	/**
	 * 获取指定索引下标的独立小节
	 * @param index 索引下标
	 * @return 获取到的独立小节
	 */
	public UniquePhrase getUniquePhrase(int index) {
		return Common.getElementSafe(index, uniquePhrases, UniquePhrase.class);
	}
	
	public int getMeter() {
		return meter;
	}

	public void setMeter(int meter) {
		this.meter = meter;
	}

	public ArrayList<UniquePhrase> getUniquePhrases() {
		return uniquePhrases;
	}

	public void setUniquePhrases(ArrayList<UniquePhrase> uniquePhrases) {
		this.uniquePhrases = uniquePhrases;
	}

	public ArrayList<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentences = sentences;
	}

	public ArrayList<Harmonic> getHarmonics() {
		return harmonics;
	}

	public void setHarmonics(ArrayList<Harmonic> harmonics) {
		this.harmonics = harmonics;
	}

	public ArrayList<NoteEvent> getNoteEvents() {
		return noteEvents;
	}

	public void setNoteEvents(ArrayList<NoteEvent> noteEvents) {
		this.noteEvents = noteEvents;
	}

	public HashMap<NoteEvent, Harmonic> getEventHarmonicMap() {
		return eventHarmonicMap;
	}

	public void setEventHarmonicMap(HashMap<NoteEvent, Harmonic> eventHarmonicMap) {
		this.eventHarmonicMap = eventHarmonicMap;
	}

}
