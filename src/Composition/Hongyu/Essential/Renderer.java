package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * äÖÈ¾½Ó¿Ú
 */
public interface Renderer {
	
	/**
	 * äÖÈ¾ÒôÀÖ¶ÎÂä
	 * @param renderPart ÒôÀÖ¶ÎÂä
	 * @param parameter ²ÎÊı¶ÔÏó
	 */
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter);
	
}
