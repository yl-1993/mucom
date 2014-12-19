package MIDI;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 音符表示与数字表示的转换类
 */
public class Conversion {
	
	/**
	 * 音高-数值转换表
	 */
	private static HashMap<String, Integer> pitchConversion;
	
	/**
	 * 音轨整体速度转换
	 * @param speed 字符串形式的速度
	 * @return 整数形式的速度
	 */
	public static int convertSequenceSpeed(String speed) {
		switch (speed) {
		//60BPM
		case "slow":
			return 2;
		//90BPM
		case "slower":
			return 3;
		//120BPM
		case "medium":
			return 4;
		//150BPM
		case "faster":
			return 5;
		//180BPM
		case "fast":
			return 6;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 音轨整体速度转换
	 * @param speed 整数形式的速度
	 * @return 字符串形式的速度
	 */
	public static String convertSequenceSpeed(int speed) {
		switch (speed) {
		//60BPM
		case 2:
			return "slow";
		//90BPM
		case 3:
			return "slower";
		//120BPM
		case 4:
			return "medium";
		//150BPM
		case 5:
			return "faster";
		//180BPM
		case 6:
			return "fast";
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 音符时值-数字时值转换
	 * @param duration 音符时值
	 * @return 数字时值
	 */
	public static int convertNoteDuration(String duration) {
		switch (duration) {
		//零时长音符
		case "zero":
			return 0;
		//十六分音符
		case "sixteenth":
			return 1;
		//八分音符
		case "eighth":
			return 2;
		//附点八分音符
		case "eighth_dot":
			return 3;
		//四分音符
		case "quarter":
			return 4;
		//附点四分音符
		case "quarter_dot":
			return 6;
		//二分音符
		case "half":
			return 8;
		//附点二分音符
		case "half_dot":
			return 12;
		//全音符
		case "whole":
			return 16;
		//附点全音符
		case "whole_dot":
			return 24;
		//其它情况
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 数字时值-音符时值转换
	 * @param duration 数字时值
	 * @return 音符时值
	 */
	public static String convertNoteDuration(int duration) {
		switch (duration) {
		//零时长音符
		case 0:
			return "zero";
		//十六分音符
		case 1:
			return "sixteenth";
		//八分音符
		case 2:
			return "eighth";
		//附点八分音符
		case 3:
			return "eighth_dot";
		//四分音符
		case 4:
			return "quarter";
		//附点四分音符
		case 6:
			return "quarter_dot";
		//二分音符
		case 8:
			return "half";
		//附点二分音符
		case 12:
			return "half_dot";
		//全音符
		case 16:
			return "whole";
		//附点全音符
		case 24:
			return "whole_dot";
		//其它情况
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 音符强弱-数字音量转换
	 * @param strength 音符强弱
	 * @return 数字音量
	 */
	public static int convertNoteStrength(String strength) {
		switch (strength) {
		case "ppp":
			return 36;
		case "pp":
			return 48;
		case "p":
			return 60;
		case "mp":
			return 72;
		case "mf":
			return 84;
		case "f":
			return 96;
		case "ff":
			return 108;
		case "fff":
			return 120;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 数字音量-音符强弱转换
	 * @param strength 数字音量
	 * @return 音符强度
	 */
	public static String convertNoteStrength(int strength) {
		switch (strength) {
		case 36:
			return "ppp";
		case 48:
			return "pp";
		case 60:
			return "p";
		case 72:
			return "mp";
		case 84:
			return "mf";
		case 96:
			return "f";
		case 108:
			return "ff";
		case 120:
			return "fff";
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 音色转换
	 * @param instrument 字符串形式的音色
	 * @return 数字形式的音色
	 */
	public static int convertNoteInstrument(String instrument) {
		switch (instrument) {
		//钢琴
		case "piano":
			return 0;
		//八音盒
		case "music box":
			return 10;
		//手风琴
		case "accordian":
			return 21;
		//口琴
		case "harmonica":
			return 22;
		//吉他
		case "guitar":
			return 24;
		//低音号
		case "bass":
			return 32;
		//小提琴
		case "violin":
			return 40;
		//中提琴
		case "viola":
			return 41;
		//大提琴
		case "cello":
			return 42;
		//低音提琴
		case "contrabass":
			return 43;
		//竖琴
		case "harp":
			return 46;
		//定音鼓
		case "timpani":
			return 47;
		//弦乐合奏
		case "string":
			return 48;
		//小号
		case "trumpet":
			return 56;
		//长号
		case "trombone":
			return 57;
		//大号
		case "tuba":
			return 58;
		//圆号
		case "French horn":
			return 60;
		//萨克斯
		case "sax":
			return 64;
		//双簧管
		case "oboe":
			return 68;
		//大管
		case "bassoon":
			return 70;
		//单簧管
		case "clarinet":
			return 71;
		//短笛
		case "piccolo":
			return 72;
		//长笛
		case "flute":
			return 73;
		//竖笛
		case "recorder":
			return 74;
		//其它情况
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 音色转换
	 * @param instrument 数字形式的音色
	 * @return 字符串形式的音色
	 */
	public static String convertNoteInstrument(int instrument) {
		switch (instrument) {
		//钢琴
		case 0:
			return "piano";
		//八音盒
		case 10:
			return "music box";
		//手风琴
		case 21:
			return "accordian";
		//口琴
		case 22:
			return "harmonica";
		//吉他
		case 24:
			return "guitar";
		//贝司
		case 32:
			return "bass";
		//小提琴
		case 40:
			return "violin";
		//中提琴
		case 41:
			return "viola";
		//大提琴
		case 42:
			return "cello";
		//低音提琴
		case 43:
			return "contrabass";
		//竖琴
		case 46:
			return "harp";
		//定音鼓
		case 47:
			return "timpani";
		//弦乐合奏
		case 48:
			return "string";
		//小号
		case 56:
			return "trumpet";
		//长号
		case 57:
			return "trombone";
		//大号
		case 58:
			return "tuba";
		//圆号
		case 60:
			return "French horn";
		//萨克斯
		case 64:
			return "sax";
		//双簧管
		case 68:
			return "oboe";
		//大管
		case 70:
			return "bassoon";
		//单簧管
		case 71:
			return "clarinet";
		//短笛
		case 72:
			return "piccolo";
		//长笛
		case 73:
			return "flute";
		//竖笛
		case 74:
			return "recorder";
		//其它情况
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 音高转换
	 * @param pitch 字符串形式音高
	 * @return 数值形式音高
	 */
	public static int convertNotePitch(String pitch) {
		//判断转换表是否被初始化
		if (pitchConversion == null) {
			initialPitchConversion();
		}
		//根据转换表转换
		if (pitchConversion.containsKey(pitch)) {
			return pitchConversion.get(pitch);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 音高转换
	 * @param pitch 数值形式音高
	 * @return 字符串形式音高，由于不唯一，因此用ArrayList<String>表示
	 */
	public static ArrayList<String> convertNotePitch(int pitch) {
		//判断转换表是否被初始化
		if (pitchConversion == null) {
			initialPitchConversion();
		}
		//寻找所有音高等于数值的音高
		ArrayList<String> result = new ArrayList<String>();
		for (String key : pitchConversion.keySet()) {
			if (pitch == pitchConversion.get(key)) {
				result.add(key);
			}
		}
		//判断参数的合法性
		if (!result.isEmpty()) {
			return result;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 初始化音高-数值转换表
	 */
	private static void initialPitchConversion() {
		pitchConversion = new HashMap<String, Integer>();
		//适用于小写字母开头的音名
		String[] lowerCase = new String[] {"c", "d", "e", "f", "g", "a", "b"};
		//适用于大写字母开头的音名
		String[] upperCase = new String[] {"C", "D", "E", "F", "G", "A", "B"};
		//音符相对于C音的距离
		int[] increment = new int[] {0, 2, 4, 5, 7, 9, 11}; 
		//钢琴上最低的几个音
		pitchConversion.put("A2", 21);
		pitchConversion.put("#_A2", 22);
		pitchConversion.put("b_B2", 22);
		pitchConversion.put("B2", 23);
		pitchConversion.put("#_B2", 24);
		//C1~B1
		for (int i = 0; i < upperCase.length; i++) {
			pitchConversion.put("b_" + upperCase[i] + "1", 24 + increment[i] - 1);
			pitchConversion.put(upperCase[i] + "1", 24 + increment[i]);
			pitchConversion.put("#_" + upperCase[i] + "1", 24 + increment[i] + 1);
		}
		//C~B
		for (int i = 0; i < upperCase.length; i++) {
			pitchConversion.put("b_" + upperCase[i], 36 + increment[i] - 1);
			pitchConversion.put(upperCase[i], 36 + increment[i]);
			pitchConversion.put("#_" + upperCase[i], 36 + increment[i] + 1);
		}
		//c~b
		for (int i = 0; i < lowerCase.length; i++) {
			pitchConversion.put("b_" + lowerCase[i], 48 + increment[i] - 1);
			pitchConversion.put(lowerCase[i], 48 + increment[i]);
			pitchConversion.put("#_" + lowerCase[i], 48 + increment[i] + 1);
		}
		//c1~b1, c2~b2, c3~b3, c4~b4
		for (int j = 1; j <= 4; j++) {
			for (int i = 0; i < lowerCase.length; i++) {
				pitchConversion.put("b_" + lowerCase[i] + j, 48 + j * 12 + increment[i] - 1);
				pitchConversion.put(lowerCase[i] + j, 48 + j * 12 + increment[i]);
				pitchConversion.put("#_" + lowerCase[i] + j, 48 + j * 12 + increment[i] + 1);
			}
		}
		//钢琴上最高的几个音
		pitchConversion.put("b_c5", 107);
		pitchConversion.put("c5", 108);
	}
}
