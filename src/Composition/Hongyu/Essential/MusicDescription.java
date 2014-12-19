package Composition.Hongyu.Essential;

import java.util.ArrayList;

/**
 * 音乐的描述类，保存描述音乐需要的信息
 */
public class MusicDescription {
	
	/**
	 * 所有独立的乐段
	 */
	private ArrayList<UniquePart> uniqueParts = new ArrayList<UniquePart>();
	
	/**
	 * 所有乐段
	 */
	ArrayList<Part> parts = new ArrayList<Part>();
	
	/**
	 * 速度
	 */
	private int tempo = 120;
	
	/**
	 * 音轨描述
	 */
	ArrayList<Track> tracks = new ArrayList<Track>();

	/**
	 * 获得独立乐段的个数
	 * @return 获得的个数
	 */
	public int getUniquePartsCount() {
		return uniqueParts.size();
	}
	
	/**
	 * 获取指定索引下标的独立乐段
	 * @param index 索引下标
	 * @return 独立乐段
	 */
	public UniquePart getUniquePart(int index) {
		return Common.getElementSafe(index, uniqueParts, UniquePart.class);
	}
	
	/**
	 * 设定独立乐段的个数
	 * @param count 设定的个数
	 */
	public void setUniquePartsCount(int count) {
		Common.setSize(count, uniqueParts, UniquePart.class);
	}
	
	/**
	 * 获得乐段的个数
	 * @return 获得的个数
	 */
	public int getPartsCount() {
		return parts.size();
	}
	
	/**
	 * 获取指定索引下标的乐段
	 * @param index 索引下标
	 * @return 乐段
	 */
	public Part getPart(int index) {
		return Common.getElementSafe(index, parts, Part.class);
	}
	
	/**
	 * 设定乐段的个数
	 * @param count 设定的个数
	 */
	public void setPartsCount(int count) {
		Common.setSize(count, parts, Part.class);
	}
	
	/**
	 * 获取小节的总数
	 * @return 小节的总数
	 */
	public int getBarsCount() {
		int bars = 0;
		for (Part part : parts) {
			int uniquePartIndex = part.getUniquePartIndex();
			UniquePart uniquePart = uniqueParts.get(uniquePartIndex);
			bars += uniquePart.getBarsCount();
		}
		return bars;
	}
	
	/**
	 * 获取指定乐段的起始小节编号
	 * @param index 乐段的索引下标
	 * @return 起始小节编号
	 */
	public int getPartStartBar(int index) {
		Part part = parts.get(index);
		return part.getStartBar();
	}

	/**
	 * 获取指定乐段的结束小节编号
	 * @param index 乐段的索引下标
	 * @return 结束小节编号
	 */
	public int getPartEndBar(int index) {
		Part part = parts.get(index);
		return part.getEndBar();
	}
	
	/**
	 * 添加一个渲染事件
	 * @param renderer 渲染器
	 * @param trackIndex 音轨号
	 * @param initialStep 起始位置
	 * @param finalStep 终止位置
	 * @param octave 八度
	 * @param timeOffset 时间偏移
	 * @param volume 音量系数
	 */
	public void addRenderEvent(Renderer renderer, int trackIndex, int initialStep, int finalStep, int octave, Time timeOffset, double volume) {
		tracks.get(trackIndex).getRenderEvents().add(new RenderEvent(renderer, initialStep, finalStep, octave, timeOffset.clone(), volume));
	}
	
	/**
	 * 添加一个音轨
	 * @param name 音轨名
	 */
	public void addTrack(String name) {
		tracks.add(new Track(name));
	}
	
	public ArrayList<UniquePart> getUniqueParts() {
		return uniqueParts;
	}

	public void setUniqueParts(ArrayList<UniquePart> uniqueParts) {
		this.uniqueParts = uniqueParts;
	}

	public ArrayList<Part> getParts() {
		return parts;
	}

	public void setParts(ArrayList<Part> parts) {
		this.parts = parts;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}
	
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	
}
