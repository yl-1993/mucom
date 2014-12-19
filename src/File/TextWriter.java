package File;

import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import MIDI.Music;

/**
 * 将音乐的信息写入到文本
 */
public class TextWriter {
	
	/**
	 * 写入音乐的文本形式
	 * @param music 音乐对象
	 * @param path 存储路径
	 */
	public static void write(Music music, String path) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			Sequence sequence = music.getSequence();
			//写入速度
			long speed = 500000 * sequence.getTickLength() / sequence.getMicrosecondLength();
			writer.write(speed + "\n");
			//写入每一条音轨
			for (Track track : sequence.getTracks()) {
				writer.write(-1 + "\n");
				for (int i = 0; i < track.size(); i++) {
					MidiEvent event = track.get(i);
					MidiMessage message = event.getMessage();
					//只写入抬起音符，按下音符，更换乐器三种信息
					if (message.getStatus() / 16 == 8 || message.getStatus() / 16 == 9 || message.getStatus() / 16 == 12) {
						writer.write(message.getStatus() / 16 + " ");
						for (int j = 1; j < message.getMessage().length; j++) {
							writer.write(message.getMessage()[j] + " ");
						}
						writer.write(event.getTick() + "\n");
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
