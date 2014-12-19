package File;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import MIDI.Music;

/**
 * 从文本中读取音乐信息
 */
public class TextReader {
	
	/**
	 * 读取音乐
	 * @param path 音乐的路径
	 * @return 音乐对象
	 */
	public static Music read(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			Sequence sequence = new Sequence(Sequence.PPQ, Integer.parseInt(reader.readLine()));
			Track currentTrack = null;
			int trackNum = 0;
			while (true) {
				String line = reader.readLine();
				//判断读取是否结束
				if (line == null || line == "") {
					break;
				}
				//分离MIDI消息
				String[] numbers = line.split(" ");
				//新音轨
				if (Integer.parseInt(numbers[0]) == -1) {
					currentTrack = sequence.createTrack();
					trackNum++;
				}
				//处理换乐器消息
				if (Integer.parseInt(numbers[0]) == 12) {
					ShortMessage message = new ShortMessage();
					message.setMessage(ShortMessage.PROGRAM_CHANGE, trackNum - 1, Integer.parseInt(numbers[1]), 0);
					MidiEvent event = new MidiEvent(message, Integer.parseInt(numbers[2]));
					currentTrack.add(event);
				}
				//处理按下按键或抬起按键消息
				if (Integer.parseInt(numbers[0]) == 8 || Integer.parseInt(numbers[0]) == 9) {
					ShortMessage message = new ShortMessage();
					message.setMessage(Integer.parseInt(numbers[0]) * 16, trackNum - 1, Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));
					MidiEvent event = new MidiEvent(message, Integer.parseInt(numbers[3]));
					currentTrack.add(event);
				}
			}
			reader.close();
			return new Music(sequence);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
