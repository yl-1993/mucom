package Composition.Hongyu.StructureGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.GeneratorSelection;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.StructureGenerator;
import Composition.Hongyu.GeneratorSelections.GeneratorSelectionGetter;

/**
 * ModernSongStructure
 */
public class DefaultStructureGenerator implements StructureGenerator {

	@Override
	public void generateStructure(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		
		int mode = Common.getRandomInteger(0, 3);
		// 0 - based on none
		// 1 - based on verse ( no voice )
		// 2 - based on chorus ( no voice )
		
		int startWith = Common.getRandomInteger(-2, 2);
		// 0 - verse
		// 1 - chorus
		
		if (startWith < 0) startWith = 0;
		if (mode == 1) startWith = 1;
		if (mode == 2) startWith = 0;
		
		int afterBridge = Common.getRandomInteger(0, 2);
		// 0 - verse
		// 1 - chorus
		
		int uniquePartsCount = 3;
		int bridge = 2;
		int verse = 0;
		int chorus = 1;
		
		musicDescription.setUniquePartsCount(uniquePartsCount);
		
		int meter = 4;
		
		// initialize unique parts
		for (int i = 0; i < uniquePartsCount; i++)
			musicDescription.getUniquePart(i).setMeter(meter);
		
		// count normal parts
		int partsCount = 7; // 2 x Verse, 4 x Chorus and 1 x Bridge
		if (mode > 0) partsCount++;
		if (startWith > 0) partsCount++;
		if (afterBridge == 0) partsCount++;
		
		int mangleEnding = Common.getRandomInteger(0, 2);
		if (mangleEnding > 0) partsCount++;
		
		String scale = Constant.SCALES[Common.getRandomInteger(0, Constant.SCALES.length)];
		int transpose = Common.getRandomInteger(0, 12);
		
		musicDescription.setPartsCount(partsCount);
		
		for (int i = 0; i < partsCount; i++)
		{
			musicDescription.getPart(i).setTempo(1.0);
			musicDescription.getPart(i).setScale(scale);
			musicDescription.getPart(i).setTranspose(transpose);
		}
		
		// link the structure
		int part = 0;
		
		if (mode == 1) // from verse
		{
			musicDescription.getPart(0).setUniquePartIndex(0);
			musicDescription.getPart(0).setArrangeHint(0);
			part++;
		}
		if (mode == 2) // from chorus
		{
			musicDescription.getPart(0).setUniquePartIndex(1);
			musicDescription.getPart(0).setArrangeHint(0);
			part++;
		}
		
		// link the structure (verses)
		int firstVerse = part;
		if (startWith > 0) firstVerse++;
		for (int i = 0; i < 2; i++)
		{
			musicDescription.getPart(firstVerse+(i*2)).setUniquePartIndex(verse);
			musicDescription.getPart(firstVerse+(i*2)).setArrangeHint(1);
		}
		
		if (afterBridge == 0) 
		{
			musicDescription.getPart(firstVerse+5).setUniquePartIndex(verse);
			musicDescription.getPart(firstVerse+5).setArrangeHint(1);
		}
		
		// link the structure (bridge)
		musicDescription.getPart(firstVerse+4).setUniquePartIndex(bridge);
		musicDescription.getPart(firstVerse+4).setArrangeHint(3);
		musicDescription.getPart(firstVerse+4).setTranspose((transpose+5)%12);
		musicDescription.getPart(firstVerse+4).setScale(
				Constant.SCALES[Common.getRandomInteger(0, Constant.SCALES.length)]);
		
		// link the structure (chorus)
		if (startWith > 0)
		{
			musicDescription.getPart(part).setUniquePartIndex(chorus);
			musicDescription.getPart(part).setArrangeHint(3);
		}
		
		for (int i = 0; i < 2; i++)
		{
			musicDescription.getPart(firstVerse+(i*2)+1).setUniquePartIndex(chorus);
			musicDescription.getPart(firstVerse+(i*2)+1).setArrangeHint(3);
		}
		
		if (afterBridge == 0) firstVerse++;	
		
		musicDescription.getPart(firstVerse+5).setUniquePartIndex(chorus);
		musicDescription.getPart(firstVerse+5).setArrangeHint(3);
		musicDescription.getPart(firstVerse+6).setUniquePartIndex(chorus);
		musicDescription.getPart(firstVerse+6).setArrangeHint(3);
		
		if (mangleEnding > 0)
		{
			transpose = (transpose + Common.getRandomInteger(1, 4))%12;
			musicDescription.getPart(firstVerse+7).setUniquePartIndex(chorus);
			musicDescription.getPart(firstVerse+7).setArrangeHint(3);
			musicDescription.getPart(firstVerse+7).setTranspose(transpose);
			musicDescription.getPart(firstVerse+6).setTranspose(transpose);
		}
		//选取几个生成器
		GeneratorSelection generatorSelection = GeneratorSelectionGetter.getGeneratorSelection(parameter);
		for (int i = 0; i < uniquePartsCount; i++) {
			musicDescription.getUniquePart(i).rhythmGenerator = generatorSelection.getRhythmGenerator();
			musicDescription.getUniquePart(i).harmonyGenerator = generatorSelection.getHarmonyGenerator();
			musicDescription.getUniquePart(i).melodyGenerator = generatorSelection.getMelodyGenerator();
			System.out.println();
			System.out.println("UniquePart " + i + ":");
			System.out.println(musicDescription.getUniquePart(i).rhythmGenerator);
			System.out.println(musicDescription.getUniquePart(i).harmonyGenerator);
			System.out.println(musicDescription.getUniquePart(i).melodyGenerator);
			System.out.println(musicDescription.getUniquePart(i).innerStructureGenerator);
		}
		System.out.println();
		//选取并修饰器等信息
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			musicDescription.getPart(i).ornamenter = generatorSelection.getOrnamenter();
			System.out.println("part" + i + " -> unique part" + musicDescription.getPart(i).getUniquePartIndex()
					+ ", arrange hint = " + musicDescription.getPart(i).getArrangeHint());
			System.out.println(musicDescription.getPart(i).ornamenter);
			System.out.println();
		}
	}
}
