package Composition.Hongyu.Essential;

import java.util.ArrayList;

public class Track {
	
	/**
	 * 渲染事件
	 */
	private ArrayList<RenderEvent> renderEvents = new ArrayList<>();
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 构造函数
	 * @param name 名称
	 */
	public Track(String name) {
		this.name = name;
	}

	public ArrayList<RenderEvent> getRenderEvents() {
		return renderEvents;
	}

	public void setRenderEvents(ArrayList<RenderEvent> renderEvents) {
		this.renderEvents = renderEvents;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	public int getPatch() {
		return patch;
	}

	public void setPatch(int patch) {
		this.patch = patch;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getPan() {
		return pan;
	}

	public void setPan(int pan) {
		this.pan = pan;
	}
	*/
}
