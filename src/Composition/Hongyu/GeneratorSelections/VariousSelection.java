package Composition.Hongyu.GeneratorSelections;

import java.util.HashMap;

import Composition.Hongyu.Arrangers.ArrangerGetter;
import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.GeneratorSelection;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.StructureGenerator;
import Composition.Hongyu.HarmonyGenerators.HarmonyGeneratorGetter;
import Composition.Hongyu.MelodyGenerators.MelodyGeneratorGetter;
import Composition.Hongyu.MelodyGenerators.MelodyGeneratorGetter.GeneratorsEnum;
import Composition.Hongyu.Ornamenters.OrnamenterGetter;
import Composition.Hongyu.RhythmGenerators.RhythmGeneratorGetter;
import Composition.Hongyu.StructureGenerators.StructureGeneratorGetter;

public class VariousSelection extends GeneratorSelection {

	public VariousSelection(HashMap<String, Integer> parameter) {
		// TODO Auto-generated constructor stub
		super(parameter);
	}
	
	@Override
	public StructureGenerator getStructureGenerator() {
		// TODO Auto-generated method stub
		return StructureGeneratorGetter.getStructureGenerator(parameter);
	}

	@Override
	public RhythmGenerator getRhythmGenerator() {
		// TODO Auto-generated method stub
		return RhythmGeneratorGetter.getRhythmGenerator(parameter);
	}

	@Override
	public HarmonyGenerator getHarmonyGenerator() {
		// TODO Auto-generated method stub
		return HarmonyGeneratorGetter.getHarmonyGenerator(parameter);
	}

	@Override
	public MelodyGenerator getMelodyGenerator() {
		// TODO Auto-generated method stub
		int pitch = parameter.get(Constant.PITCH_STRING);
		if (80 <= pitch)
			return MelodyGeneratorGetter.MELODY_GENERATORS.get(GeneratorsEnum.ExtremeVariousMelodyGenerator);
		if (60 <= pitch && pitch < 80)
			return MelodyGeneratorGetter.MELODY_GENERATORS.get(GeneratorsEnum.ExtremeVariousMelodyGenerator);
		return MelodyGeneratorGetter.getMelodyGenerator(parameter);
	}

	@Override
	public Ornamenter getOrnamenter() {
		// TODO Auto-generated method stub
		return OrnamenterGetter.getOrnamenter(parameter);
	}

	@Override
	public Arranger getArranger() {
		// TODO Auto-generated method stub
		return ArrangerGetter.getArranger(parameter);
	}

}
