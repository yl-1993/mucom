package MIDI;

import java.util.HashMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * 音乐类
 */
public class Music {
	
	/**
	 * 一条音轨以及相关信息
	 */
	private class TrackInfo {
		
		/**
		 * 音轨
		 */
		public Track track;
		
		/**
		 * 乐器名
		 */
		public String instrument;
		
		/**
		 * 上一个被添加音符的起始发声时机
		 */
		public long lastTiming;
		
		/**
		 * 上一个被添加音符的时长
		 */
		public long lastDuration;
		
		/**
		 * 该音轨的所用的通道
		 */
		public int channel;
	}
	
	/**
	 * 音乐的时间序列，可以放置多个音轨
	 */
	private Sequence sequence;
	
	/**
	 * 音乐的所有音轨的映射表
	 */
	private HashMap<String, TrackInfo> tracks;
	
	/**
	 * 当前正在操作的音轨
	 */
	private TrackInfo currentTrack;
	
	/**
	 * 默认构造函数，取默认速度medium=120BPM
	 */
	public Music() {
		// TODO Auto-generated constructor stub
		this("medium");
	}
	
	/**
	 * 构造函数
	 * @param speed 音乐的整体速度，字符串描述
	 */
	public Music(String speed) {
		// TODO Auto-generated constructor stub
		this(Conversion.convertSequenceSpeed(speed));
	}
	
	/**
	 * 构造函数
	 * @param speed 音乐的整体速度，数字描述，越高表示越快
	 */
	public Music(int speed) {
		// TODO Auto-generated constructor stub
		try {
			sequence = new Sequence(Sequence.PPQ, speed);
			tracks = new HashMap<String, TrackInfo>();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过时间序列构造音乐对象，适用于MIDI格式转到Music对象
	 * @param sequence 序列
	 */
	public Music(Sequence sequence) {
		//设置sequence
		this.sequence = sequence;
		//设置tracks
		this.tracks = new HashMap<String, TrackInfo>();
		for (int i = 0; i < sequence.getTracks().length; i++) {
			//新建一个TrackInfo
			TrackInfo trackInfo = new TrackInfo();
			trackInfo.channel = -1;
			trackInfo.instrument = "unknown";
			trackInfo.lastDuration = 0;
			trackInfo.lastTiming = 1;
			trackInfo.track = sequence.getTracks()[i];
			//给track一个名字
			String trackName = "track" + i;
			//加入到tracks
			this.tracks.put(trackName, trackInfo);
		}
		//设置当前正在编辑的音轨名
		setTrack("track0");
	}
	
	/**
	 * 设置当前要处理的音轨
	 * @param trackName 要处理的音轨名
	 */
	public void setTrack(String trackName) {
		if (tracks.containsKey(trackName)) {
			currentTrack = tracks.get(trackName);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 新建一个音轨，并切换到该音轨
	 * @param trackName 音轨的名称
	 * @param instrument 音轨的乐器
	 */
	public void createTrack(String trackName, String instrument) {
		try {
			//检查音轨是否已经存在
			if (tracks.containsKey(trackName)) {
				throw new IllegalArgumentException();
			}
			//最多只能有16个channel
			if (tracks.size() >= 16) {
				throw new InvalidMidiDataException("channel out of range");
			}
			//创建新音轨，填写信息
			currentTrack = new TrackInfo();
			currentTrack.track = sequence.createTrack();
			currentTrack.instrument = instrument;
			currentTrack.lastTiming = 1;
			currentTrack.lastDuration = 0;
			currentTrack.channel = tracks.size();
			//加入已有音轨
			tracks.put(trackName, currentTrack);
			//通过ShortMessage设定音色
			ShortMessage message = new ShortMessage();
			message.setMessage(ShortMessage.PROGRAM_CHANGE, currentTrack.channel, Conversion.convertNoteInstrument(currentTrack.instrument), 0);
			MidiEvent event = new MidiEvent(message, 0);
			currentTrack.track.add(event);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除一条音轨，如果是当前编辑的，则必须切换到其它音轨
	 * @param trackName 要删除的音轨名
	 */
	public void deleteTrack(String trackName) {
		try {
			if (tracks.containsKey(trackName)) {
				//如果当前音轨就是要删除的音轨，则不能删除，必须设定到其它音轨上
				if (currentTrack == tracks.get(trackName)) {
					throw new Exception("you can't delete current track");
				}
				sequence.deleteTrack(tracks.get(trackName).track);
				tracks.remove(trackName);
			} else {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取音轨的时间序列，用于转成MIDI
	 * @return 返回时间序列
	 */
	public Sequence getSequence() {
		return sequence;
	}
	
	/**
	 * 设定音轨的时间序列
	 * @param sequence 时间序列
	 */
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * 添加音符
	 * @param pitch 音高的字符串描述
	 * @param noteTiming 发声时机的类型
	 * @param duration 音符时值的字符串描述
	 * @param strength 音符强弱的字符串描述
	 */
	public void pushNote(String pitch, NoteTiming noteTiming, String duration, String strength) {
		pushNote(pitch, noteTiming, "zero", duration, strength);
	}
	
	/**
	 * 添加音符
	 * @param pitch 音高的字符串描述
	 * @param noteTiming 发声时机的类型
	 * @param delay 延迟多少时值
	 * @param duration 音符时值的字符串描述
	 * @param strength 音符强弱的字符串描述
	 */
	public void pushNote(String pitch, NoteTiming noteTiming, String delay, String duration, String strength) {
		//如果当前音轨指向为空，则新建音轨
		if (currentTrack == null) {
			createTrack("piano", "piano");
		}
		//获取发声时机的具体值
		long timing = 1;
		if (noteTiming == NoteTiming.meanwhile) {
			timing = currentTrack.lastTiming;
		} else {
			timing = currentTrack.lastTiming + currentTrack.lastDuration;
		}
		timing += Conversion.convertNoteDuration(delay);
		pushNote(Conversion.convertNotePitch(pitch), timing, Conversion.convertNoteDuration(duration), Conversion.convertNoteStrength(strength));
	}
	
	/**
	 * 添加音符
	 * @param pitch 音高的数值描述
	 * @param noteTiming 发声时机的类型
	 * @param delay 延迟的时间，数字描述
	 * @param duration 持续时间，数字描述
	 * @param volume 发声音量，数字描述
	 */
	public void pushNote(int pitch, NoteTiming noteTiming, int delay, int duration, int volume) {
		//如果当前音轨指向为空，则新建音轨
		if (currentTrack == null) {
			createTrack("piano", "piano");
		}
		//获取发声时机的具体值
		long timing = 1;
		if (noteTiming == NoteTiming.meanwhile) {
			timing = currentTrack.lastTiming;
		} else {
			timing = currentTrack.lastTiming + currentTrack.lastDuration;
		}
		timing += delay;
		pushNote(pitch, timing, duration, volume);
	}
	
	/**
	 * 添加音符
	 * @param pitch 音高的数值描述
	 * @param timing 发声时机的数字描述
	 * @param duration 持续时间，数字描述
	 * @param volume 发生音量，数字描述
	 */
	public void pushNote(int pitch, long timing, int duration, int volume) {
		//如果当前音轨指向为空，则新建音轨
		if (currentTrack == null) {
			createTrack("piano", "piano");
		}
		try {
			//音乐是从文件中读取进来的，当前的通道数未知
			if (currentTrack.channel == -1) {
				throw new Exception("you can't push note in this track");
			}
			//如果当前音轨指向为空，则新建音轨
			if (currentTrack == null) {
				createTrack("piano", "piano");
			}
			//添加起始
			ShortMessage startMessage = new ShortMessage();
			startMessage.setMessage(ShortMessage.NOTE_ON, currentTrack.channel, pitch, volume);
			MidiEvent startEvent = new MidiEvent(startMessage, timing);
			currentTrack.track.add(startEvent);
			//添加终止
			ShortMessage endMessage = new ShortMessage();
			endMessage.setMessage(ShortMessage.NOTE_OFF, currentTrack.channel, pitch, 0);
			MidiEvent endEvent = new MidiEvent(endMessage, timing + duration);
			currentTrack.track.add(endEvent);
			//记录
			currentTrack.lastTiming = timing;
			currentTrack.lastDuration = duration;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加休止符
	 * @param duration 休止符的时值，字符串形式
	 */
	public void pushRest(String duration) {
		pushRest(Conversion.convertNoteDuration(duration));
	}
	
	/**
	 * 添加休止符
	 * @param duration 休止符的时值，数值形式
	 */
	public void pushRest(int duration) {
		currentTrack.lastTiming = currentTrack.lastTiming + currentTrack.lastDuration;
		currentTrack.lastDuration = duration;
	}
}
