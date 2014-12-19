package File;

import java.io.File;

import javax.sound.midi.MidiSystem;

import MIDI.Music;

/**
 * 将Music对象写入成MIDI音乐格式
 */
public class MIDIWriter {
	
	/**
	 * 创建MIDI文件
	 * @param music 音乐文件
	 * @param path 路径
	 */
	public static void write(Music music, String path) {
		try {
			File file = new File(path);
			MidiSystem.write(music.getSequence(), 1, file);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
