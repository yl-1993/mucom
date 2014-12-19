package Composition.Hongyu.Essential;

/**
 * 渲染用事件
 */
public class RenderEvent {

	/**
	 * 所用的渲染方式
	 */
	private final Renderer renderer;
	
	/**
	 * 起始
	 */
	private final int initialStep;
	
	/**
	 * 终止
	 */
	private final int finalStep;

	/**
	 * 八度
	 */
	private final int octave;
	
	/**
	 * 时间偏移
	 */
	private final Time timeOffset;
	
	/**
	 * 音量系数
	 */
	private final double volume;
	
	/**
	 * 构造函数
	 * @param track 音轨号
	 * @param initialStep 起始位置
	 * @param finalStep 终止位置
	 * @param octave 八度
	 * @param timeOffset 时间偏移
	 * @param volume 音量系数
	 */
	public RenderEvent(Renderer renderer, int initialStep, int finalStep, int octave, Time timeOffset, double volume) {
		this.renderer = renderer;
		this.initialStep = initialStep;
		this.finalStep = finalStep;
		this.octave = octave;
		this.timeOffset = timeOffset;
		this.volume = volume;
	}
	
	public Renderer getRenderer() {
		return renderer;
	}
	
	public int getInitialStep() {
		return initialStep;
	}

	public int getFinalStep() {
		return finalStep;
	}

	public int getOctave() {
		return octave;
	}

	public Time getTimeOffset() {
		return timeOffset;
	}

	public double getVolume() {
		return volume;
	}
	
}
