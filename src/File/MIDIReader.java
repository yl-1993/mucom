package File;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import MIDI.Music;

/**
 * 从MIDI文件中读取音乐
 */
public class MIDIReader {
	
	/**
	 * 读取MIDI音乐
	 * @param path MIDI路径
	 * @return Music文件
	 */
	public static Music read(String path) {
		try {
			File file = new File(path);
			Sequence sequence = MidiSystem.getSequence(file);
			Music music = new Music(sequence);
			return music;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
