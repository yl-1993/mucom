package MIDI;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

/**
 * 播放器类
 */
public class Player {
	
	/**
	 * 相当于播放器
	 */
	private static Sequencer sequencer;
	
	private static int audioLength;
	
	private static int audioPosition;
	
	/**
	 * 时间有一点误差
	 */
	private static final double timeCorrection = 154.0 / 148.0;
	
	/**
	 * 初始化sequencer
	 */
	static {
		try {
			sequencer = MidiSystem.getSequencer();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 播放音乐
	 * @param music 音乐对象
	 */
	public static void play(Music music) {
		try {
			//打开播放器
			sequencer.open();
			//设定音乐
			sequencer.setSequence(music.getSequence());
			//播放音乐
			//sequencer.start();
			
			audioLength = (int) sequencer.getTickLength();
			audioPosition = 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止播放
	 */
	public static void stop() {
		if (sequencer.isOpen()) {
			//停止播放音乐
			sequencer.stop();
			//关闭播放器
			sequencer.close();
		}
	}
	
	public static void pause() {
		sequencer.stop();
	}
	
	public static void start() {
		sequencer.start();
	}
	
	public static void setAudioLength(int audioLength) {
		audioLength /= timeCorrection;
		Player.audioLength = audioLength;
	}
	
	public static int getAudioLength() {
		return (int) (audioLength * timeCorrection);
	}
	
	public static void setAudioPosition(int audioPosition) {
		audioPosition /= timeCorrection;
		Player.audioPosition = audioPosition;
	}
	
	public static int getAudioPosition() {
		return (int) (audioPosition * timeCorrection);
	}
	
	public static void skip(int position) {
		if (position < 0 || position > audioLength){   
            return;
        }
		position /= timeCorrection;
        audioPosition = position;
        if (sequencer.isRunning()) {
        	sequencer.stop();
            sequencer.setTickPosition(position);
            sequencer.start();
        } else if (sequencer.isOpen()) {
        	sequencer.setTickPosition(position);
		}
	}
	
	public static void tick() {
		 // 获取当前音频播放的位置，并将进度条指向该位置   
        if (sequencer.isRunning()) {   
            audioPosition = (int)sequencer.getTickPosition();
        }
        else {   
            reset();   
        }   
	}
        
    public static void reset() {
    	sequencer.setTickPosition(0);
    	audioPosition = 0;
    	Player.stop();
    }
    
    public static boolean isRunning() {
    	return sequencer.isRunning();
    }
}