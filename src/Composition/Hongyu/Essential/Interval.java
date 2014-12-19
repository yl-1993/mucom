package Composition.Hongyu.Essential;

public class Interval {
	
	/**
	 * 下界
	 */
	private double lower = 0.0;
	
	/**
	 * 上界
	 */
	private double upper = 1.0;
	
	/**
	 * 默认构造函数
	 */
	public Interval() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化区间上下界的构造函数
	 * @param lower 下界
	 * @param upper 上界
	 */
	public Interval(double lower, double upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	/**
	 * 克隆区间
	 * @return 克隆后的对象
	 */
	public Interval clone() {
		return new Interval(lower, upper);
	}
	
	/**
	 * 求两个区间的交集
	 * @param interval 另一个区间
	 * @return 交集后的结果
	 */
	public Interval intersect(Interval interval) {
		lower = Math.max(lower, interval.lower);
		upper = Math.min(upper, interval.upper);
		return this;
	}
	
	/**
	 * 求两个区间的交集并克隆
	 * @param interval 另一个区间
	 * @return 交集并克隆后的结果
	 */
	public Interval intersectClone(Interval interval) {
		return clone().intersect(interval);
	}
	
	/**
	 * 获取区间长度
	 * @return 区间的长度
	 */
	public double getLength() {
		return Math.max(upper - lower, 0);
	}

	/**
	 * 判断区间段是否包含指定时间
	 * @param position
	 * @return
	 */
	public boolean contains(double position) {
		return (position >= this.lower && position <= this.upper);
	}
}
