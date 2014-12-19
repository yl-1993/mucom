package Composition.Hongyu.Essential;

import java.util.HashMap;

import Config.Config;

public class Constant {
	
	/**
	 * 大调
	 */
	public static final int[] MAJOR_ABSOLUTE_STEPS = new int[] { 0, 2, 4, 5, 7, 9, 11 };
	
	/**
	 * 小调
	 */
	public static final int[] MINOR_ABSOLUTE_STEPS = new int[] { 0, 2, 3, 5, 7, 8, 10 };
	
	/**
	 * 所有调式
	 */
	public static final String[] SCALES = 
			new String[] { "MAJOR", "MINOR"};
	
	/**
	 * 调式名和调式的关联
	 */
	public static final HashMap<String, int[]> SCALE_OFFSETS_MAP = new HashMap<String, int[]>();
	static {
		SCALE_OFFSETS_MAP.put("MAJOR", MAJOR_ABSOLUTE_STEPS);
		SCALE_OFFSETS_MAP.put("MINOR", MINOR_ABSOLUTE_STEPS);
	}
	
	/**
	 * 基准音高
	 */
	public static final int CHROMATIC_BASE = 45;
	
	/**
	 * 音调高低
	 */
	public static final String PITCH_STRING = Config.MUSIC_PARA_KEY[0];
	
	/**
	 * 节奏快慢
	 */
	public static final String SPEED_STRING = Config.MUSIC_PARA_KEY[1];
	
	/**
	 * 变化多少
	 */
	public static final String VARIATION_STRING = Config.MUSIC_PARA_KEY[2];
	
	/**
	 * 音乐时长
	 */
	public static final String TIME_STRING = "音乐时长";
}
