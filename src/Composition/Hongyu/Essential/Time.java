package Composition.Hongyu.Essential;

/**
 * 时间类
 */
public class Time {
	
	/**
	 * 起始小节
	 */
	public int startBar;
	/**
	 * 相对位置
	 */
	public double position;
	
	/**
	 * 默认构造函数
	 */
	public Time() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 含参构造函数
	 * @param startBar 起始小节
	 * @param position 相对位置
	 */
	public Time(int startBar, double position) {
		this.startBar = startBar;
		this.position = position;
	}
	
	/**
	 * 获取当前节拍位置
	 * @param meter 每节的拍数
	 * @return 当前节拍位置
	 */
	public double getPosition(int meter) {
		return startBar + position / meter;
	}
	
	/**
	 * 克隆时间对象
	 * @return 克隆后的时间对象
	 */
	public Time clone() {
		return new Time(startBar, position);
	}
	
	/**
	 * 平移时间
	 * @param bar 平移的节拍数
	 */
	public void translate(int bars) {
		startBar += bars;
	}
	
	/**
	 * 平移时间
	 * @param time 平移的时间对象
	 */
	public void translate(Time time) {
		startBar += time.startBar;
		position += time.position;
	}
	
	/**
	 * 平移并克隆对象
	 * @param bars 平移的节拍数
	 * @return 平移并克隆后的时间对象
	 */
	public Time translateClone(int bars) {
		Time cloneTime = clone();
		cloneTime.translate(bars);
		return cloneTime;
	}

	/**
	 * 平移并克隆对象
	 * @param time 平移的时间对象
	 * @return 平移并克隆后的时间对象
	 */
	public Time translateClone(Time time) {
		Time cloneTime = clone();
		cloneTime.translate(time);
		return cloneTime;
	}
	
}
