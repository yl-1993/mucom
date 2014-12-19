package MIDI;

/**
 * 该音符的发声时机
 */
public enum NoteTiming {
	/**
	 * 与上一个音符同时发声
	 */
	meanwhile,
	/**
	 * 在上一个音符发声之后发声
	 */
	succession
}
