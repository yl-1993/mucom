package Composition.Hongyu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.GeneratorSelection;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.InnerStructureGenerator;
import Composition.Hongyu.Essential.MarkovChain;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.Note;
import Composition.Hongyu.Essential.NoteEvent;
import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.Part;
import Composition.Hongyu.Essential.Phrase;
import Composition.Hongyu.Essential.RenderEvent;
import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Sentence;
import Composition.Hongyu.Essential.StructureGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.Track;
import Composition.Hongyu.Essential.UniquePart;
import Composition.Hongyu.Essential.UniquePhrase;
import Composition.Hongyu.GeneratorSelections.GeneratorSelectionGetter;
import File.TextWriter;
import Generate.Composition;
import MIDI.Music;
/**
 * 基于音乐规则的实现
 */
public class CompositionHongyu implements Composition {


	
	/**
	 * 原始数据存储路径
	 */
	public static final String RAW_DATA_PREFIX = "./TrainingData/PicthTrainingRawData";
	
	/**
	 * 马尔科夫对象路径
	 */
	public static final String TRAINING_RESULT = "./MarkovTrainingResult.obj";
	/**
	 * 一个八度包含的半音数
	 */
	private static final int OCTAVE = 12;
	
	/**
	 * 钢琴上的白键
	 */
	private static final Integer[] ALLOW_PITCH = {
		-12, -10, -8, -7, -5, -3, -1,
		0, 2, 4, 5, 7, 9, 11,
		12, 14, 16, 17, 19, 21, 23,
		};
	
	@Override
	public void train(Music music, HashMap<String, Integer> parameter) {
		try {
			//从音乐中获取音符
			ArrayList<ArrayList<Integer>> pitchs = getPitchs(music);
			//创建追加原始数据的writer
			BufferedWriter[] writers = new BufferedWriter[MarkovChain.MARKOV_CHAIN_LENGTH];
			for (int i = 0; i < writers.length; i++) {
				writers[i] = new BufferedWriter(new FileWriter(RAW_DATA_PREFIX + (i + 1) + ".txt", true));
			}
			//记录原始数据代码，原始数据中每一行第一个数代表了12平均律中的音高位置，后边几个数代表后边音节相对第一个音高的偏差
			for (int i = 0; i < writers.length; i++) {
				for (int j = 0; j < pitchs.size(); j++) {
					for (int k = 0; k < pitchs.get(j).size() - i - 1; k++) {
						ArrayList<Integer> record = new ArrayList<>();
						//记录首音符
						record.add(pitchs.get(j).get(k) % OCTAVE);
						//记录其它音符相对首音符的位置，如果差异超过一个音高，则取余数
						for (int l = 1; l < i + 1; l++) {
							record.add((pitchs.get(j).get(k + l) - pitchs.get(j).get(k)) % OCTAVE + record.get(0));
						}
						//记录最终的结果音符
						record.add((pitchs.get(j).get(k + i + 1) - pitchs.get(j).get(k)) % OCTAVE + record.get(0));
						//有效的部分
						if (isValid(record)) {
							for (int l = 0; l < record.size() - 1; l++) {
								writers[i].write(record.get(l) + " ");
							}
							writers[i].write(record.get(record.size() - 1) + "\n");
						}
					}
				}
				writers[i].flush();
				writers[i].close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private boolean isValid(ArrayList<Integer> record) {
		//检查是否都在白键上
		for (Integer integer : record) {
			if (!Common.arrayContains(ALLOW_PITCH, integer)) {
				return false;
			}
		}
		//留下来的概率为不同音的数量除以总的数量
		HashSet<Integer> unique = new HashSet<>(record);
		int randomNumber = Common.getRandomInteger(0, record.size());
		if (randomNumber >= unique.size())
			return false;
		//更换对应的note
		for (int i = 0; i < record.size(); i++) {
			for (int j = 0; j < ALLOW_PITCH.length; j++) {
				if (record.get(i) == ALLOW_PITCH[j]) {
					//C对应j=7，应转化为1
					record.set(i, j - 6);
				}
			}
		}
		return true;
	}

	/**
	 * 将音符整理到数组中
	 * @param music 待读入的音乐
	 * @return 整理后的音符，每一个ArrayList<Integer>代表一条音轨
	 */
	private static ArrayList<ArrayList<Integer>> getPitchs(Music music) {
		ArrayList<ArrayList<Integer>> pitchs = null;
		try {
			//将music转换成文本再读取，方便处理
			TextWriter.write(music, "music.txt");
			//读取music，获取每个音轨的音节
			pitchs = new ArrayList<ArrayList<Integer>>();
			BufferedReader reader = new BufferedReader(new FileReader("music.txt"));
			while(true) {
				//读取一行
				String line = reader.readLine();
				//判断是否到文件尾
				if (line == null || line == "") {
					break;
				}
				//分离数据
				String[] split = line.split(" ");
				//判断是否是新音轨
				if (Integer.parseInt(split[0]) == -1) {
					pitchs.add(new ArrayList<Integer>());
					continue;
				}
				//判断是否为速度描述
				if (split.length == 1) {
					continue;
				}
				//判断是否是音符
				if (Integer.parseInt(split[0]) == 8 || Integer.parseInt(split[0]) == 9) {
					if (Integer.parseInt(split[2]) != 0) {
						pitchs.get(pitchs.size() - 1).add(Integer.parseInt(split[1]));
					}
				}
			}
			reader.close();
			//删除临时文件 
			File file = new File("music.txt");
			file.delete();
			//清除空音轨
			for (int i = pitchs.size() - 1; i >= 0; i--) {
				if (pitchs.get(i).size() == 0) {
					pitchs.remove(i);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pitchs;
	}
	
	@Override
	public void tidy() {
		try {
			ArrayList<Integer[]> rawData = new ArrayList<>();
			//读取文件
			for (int i = 1; i <= MarkovChain.MARKOV_CHAIN_LENGTH; i++) {
				BufferedReader reader = new BufferedReader(new FileReader(RAW_DATA_PREFIX + i + ".txt"));
				while (true) {
					//读取马尔科夫链的条件部分
					String line = reader.readLine();
					if (line == null || line == "") {
						break;
					}
					//分开条件部分的数字，后边的音符赋值给array，前边的基准赋值给baseNote
					String[] lineSplit = line.split(" ");
					Integer[] integers = new Integer[lineSplit.length];
					for (int j = 0; j < lineSplit.length; j++) {
						integers[j] = Integer.parseInt(lineSplit[j]);
					}
					rawData.add(integers);
				}
				reader.close();
			}
			MarkovChain markovChain = new MarkovChain(rawData);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(TRAINING_RESULT));
			objectOutputStream.writeObject(markovChain);
			objectOutputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public Music generate(HashMap<String, Integer> parameter) {
		// 创建相关类
		MusicDescription musicDescription = new MusicDescription();
		//StructureGenerator和Arranger在这里设置，Ornamenters是Part的变量，其余是UniquePart的变量，都在StructureGenerator中设置
		GeneratorSelection generatorSelection = GeneratorSelectionGetter.getGeneratorSelection(parameter);
		StructureGenerator structureGenerator = generatorSelection.getStructureGenerator();
		Arranger arranger = generatorSelection.getArranger();
		// 一些信息
		System.out.println("**********");
		System.out.println();
		System.out.println(structureGenerator);
		System.out.println(arranger);
		// 产生结构
		structureGenerator.generateStructure(musicDescription, parameter);
		// 产生内部结构
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			InnerStructureGenerator innerStructureGenerator = uniquePart.innerStructureGenerator;
			innerStructureGenerator.generateInnerStructure(uniquePart,
					parameter);
		}
		// 关联Part和UniquePart
		int currentBar = 0;
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			Part part = musicDescription.getPart(i);
			int uniquePartIndex = part.getUniquePartIndex();
			UniquePart uniquePart = musicDescription
					.getUniquePart(uniquePartIndex);
			// 保证每个part有正确的起始小节号和终止小节号
			part.setStartBar(currentBar);
			part.setEndBar(currentBar + uniquePart.getBarsCount() - 1);
			int sentenceCount = uniquePart.getSentencesCount();
			for (int j = 0; j < sentenceCount; j++) {
				Sentence sentence = uniquePart.getSentence(j);
				int phraseCount = sentence.getPhrasesCount();
				for (int k = 0; k < phraseCount; k++) {
					Phrase phrase = sentence.getPhrase(k);
					int uniquePhraseId = phrase.getUniquePhraseId();
					UniquePhrase uniquePhrase = uniquePart
							.getUniquePhrase(uniquePhraseId);
					phrase.setUniquePhrase(uniquePhrase);
					phrase.setBars(uniquePhrase.getBars());
					uniquePhrase.setStartsSentence(k == 0);
					uniquePhrase.setEndsSentence(k == phraseCount - 1);
					uniquePhrase.setStartsPart(j == 0 && k == 0);
					uniquePhrase.setEndsPart(j == sentenceCount - 1
							&& k == phraseCount - 1);
				}
			}
			int bars = uniquePart.getBarsCount();
			currentBar += bars;
		}
		// 产生节奏
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			RhythmGenerator rhythmGenerator = uniquePart.rhythmGenerator;
			for (int j = 0; j < uniquePart.getUniquePhrasesCount(); j++) {
				UniquePhrase uniquePhrase = uniquePart.getUniquePhrase(j);
				rhythmGenerator.generateRhythm(uniquePhrase, parameter);
			}
		}
		// 拷贝UniquePhrase的音符到UniquePart中
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			int bars = 0;
			for (int j = 0; j < uniquePart.getSentencesCount(); j++) {
				Sentence sentence = uniquePart.getSentence(j);
				int phrases = sentence.getPhrasesCount();
				for (int k = 0; k < phrases; k++) {
					Phrase phrase = sentence.getPhrase(k);
					UniquePhrase uniquePhrase = phrase.getUniquePhrase();
					for (int l = 0; l < uniquePhrase.getEventsCount(); l++) {
						NoteEvent event = uniquePhrase.getEvent(l);
						uniquePart.addEvent(event.clone().translate(bars));
					}
					bars += phrase.getBars();
				}
			}
		}
		// 产生和弦
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			HarmonyGenerator harmonyGenerator = uniquePart.harmonyGenerator;
			harmonyGenerator.generateHarmony(uniquePart, parameter);
		}
		// 关联和弦与音乐事件
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			uniquePart.assignEventsToHarmony();
		}
		// 产生旋律
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			MelodyGenerator melodyGenerator = uniquePart.melodyGenerator;
			melodyGenerator.generateMelody(uniquePart, parameter);
		}
		// 修饰音乐
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			Part part = musicDescription.getPart(i);
			int uniquePartIndex = part.getUniquePartIndex();
			UniquePart uniquePart = musicDescription.getUniquePart(uniquePartIndex);
			Ornamenter ornamenter = part.ornamenter;
			ornamenter.ornament(part, uniquePart, parameter);
		}
		
		// 编排音乐
		arranger.arrange(musicDescription, parameter);
		// 渲染音乐
		ArrayList<Track> tracks = musicDescription.getTracks();
		ArrayList<RenderPart> renderParts = new ArrayList<>();
		for (int i = 0; i < tracks.size(); i++) {
			Track track = tracks.get(i);
			ArrayList<RenderEvent> renderEvents = track.getRenderEvents();
			for (RenderEvent renderEvent : renderEvents) {
				int initialStep = renderEvent.getInitialStep();
				int finalStep = renderEvent.getFinalStep();

				ArrayList<Part> parts = new ArrayList<Part>();
				for (int j = 0; j < musicDescription.getPartsCount(); j++) {
					Part part = musicDescription.getPart(j);
					if ((part.getStartBar() >= initialStep && part.getEndBar() <= finalStep)
							|| (initialStep >= part.getStartBar() && finalStep <= part
									.getEndBar())) {
						parts.add(part);
					}
				}

				for (Part part : parts) {
					UniquePart uniquePart = musicDescription.getUniquePart(part
							.getUniquePartIndex());
					RenderPart renderPart = new RenderPart(i);
					renderParts.add(renderPart);
					renderPart.setData(part, uniquePart, renderEvent,
							musicDescription);
					Renderer renderer = renderPart.getRenderEvent().getRenderer();
					renderer.render(renderPart, parameter);
					renderPart.translateNotes(part.getStartBar());
					Time timeOffset = renderEvent.getTimeOffset();
					renderPart.translateNotes(timeOffset);
				}
			}
		}
		// 返回音乐
		return getMusic(musicDescription, renderParts, parameter);
	}

	Music getMusic(MusicDescription musicDescription, ArrayList<RenderPart> renderParts, HashMap<String, Integer> parameter) {
		//每秒所播放的ticks
		int ticksPerSecond = 96;
		//每小节的所占用的ticks
		int ticksPerBar = 192;
		//延迟一个全音符的长度，一般为2秒
		int offsetBars = 1;
		//输出所有时间
		double originalTime = (musicDescription.getBarsCount() + offsetBars) * ticksPerBar / (double)(ticksPerSecond);
		System.out.println("Original Time = " + originalTime + " BPM = " + ticksPerSecond * 1.25);
		/*
		//调整时间
		double exceptTime = parameter.get(Constant.TIME_STRING);
		double coefficient = originalTime / exceptTime;
		if (coefficient > 1.05) { coefficient = 1.05; }
		if (coefficient < 0.95) { coefficient = 0.95; }
		ticksPerSecond *= coefficient;
		double finalTime = (musicDescription.getBarsCount() + offsetBars) * ticksPerBar / (double)(ticksPerSecond);
		System.out.println("Final Time = " + finalTime + " BPM = " + ticksPerSecond * 1.25);
 		*/
		System.out.println();
		//参数为每半秒所播放的ticks
		Music music = new Music(ticksPerSecond / 2);
		//创建音轨
		ArrayList<Track> tracks = musicDescription.getTracks();
		for (int i = 0; i < tracks.size(); i++) {
				music.createTrack("" + i, "piano");
		}
		//渲染
		for (RenderPart renderPart : renderParts) {
			ArrayList<Note> notes = renderPart.getNotes();
			int trackIndex = renderPart.getTrackIndex();
			music.setTrack("" + trackIndex);
			UniquePart uniquePart = renderPart.getUniquePart();
			int meter = uniquePart.getMeter();
			for (Note note : notes) {
				Time start = note.getStart();
				Time end = note.getEnd();
				int pitch = note.getPitch();
				int volume = note.getVolume();
				while (pitch < 36) pitch += 12;
				while (pitch > 96) pitch -= 12;
				if (pitch < 48) volume *= (double)(pitch) / 48.0;
				long onTime = (long) (ticksPerBar * (offsetBars + start.startBar + start.position / meter));
				long offTime = (long) (ticksPerBar * (offsetBars + end.startBar + end.position / meter));
				// 防止爆音
				volume = (int)(volume * 0.8);
				music.pushNote(pitch, onTime, (int)(offTime - onTime), volume);
			}
		}
		return music;
	}
}
