package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * ±àÅÅÒôÀÖ½Ó¿Ú
 */
public interface Arranger {
	
	/**
	 * ±àÅÅÒôÀÖ
	 * @param musicDescription ÒôÀÖÃèÊö¶ÔÏó
	 * @param parameter ÒôÀÖ²ÎÊı
	 */
	public void arrange(MusicDescription musicDescription, HashMap<String, Integer> parameter);
	
}
