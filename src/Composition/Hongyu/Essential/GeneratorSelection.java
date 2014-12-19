package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * 生成器选择抽象类，用于选择生成器
 */
public abstract class GeneratorSelection {
	
	protected StructureGenerator structureGenerator = null;
	
	protected RhythmGenerator rhythmGenerator = null;
	
	protected HarmonyGenerator harmonyGenerator = null;
	
	protected MelodyGenerator melodyGenerator = null;
	
	protected Ornamenter ornamenter = null;
	
	protected Arranger arranger = null;
	
	protected HashMap<String, Integer> parameter = null;
	
	protected GeneratorSelection(HashMap<String, Integer> parameter) {
		this.parameter = parameter;
	}
	
	public abstract StructureGenerator getStructureGenerator();

	public abstract RhythmGenerator getRhythmGenerator();

	public abstract HarmonyGenerator getHarmonyGenerator();
	
	public abstract MelodyGenerator getMelodyGenerator();

	public abstract Ornamenter getOrnamenter();

	public abstract Arranger getArranger();
	
}
