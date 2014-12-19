package Composition.Hongyu.InnerStructureGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.InnerStructureGenerator;
import Composition.Hongyu.Essential.UniquePart;

public class ShortInnerStructureGenerator implements InnerStructureGenerator {

	@Override
	public void generateInnerStructure(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		uniquePart.setSentencesCount(2);
		uniquePart.getSentence(0).setPhrasesCount(1);
		uniquePart.getSentence(1).setPhrasesCount(1);
		
		// Set unique phrases
		int mode = Common.getRandomInteger(0, 2);
		if (mode == 0) {
			//AA
			uniquePart.setUniquePhrasesCount(1);
			uniquePart.getSentence(0).getPhrase(0).setUniquePhraseId(0);
			uniquePart.getSentence(1).getPhrase(0).setUniquePhraseId(0);
		} else {
			//AB
			uniquePart.setUniquePhrasesCount(2);
			uniquePart.getSentence(0).getPhrase(0).setUniquePhraseId(0);
			uniquePart.getSentence(1).getPhrase(0).setUniquePhraseId(1);
		}
		for (int i = 0; i < uniquePart.getUniquePhrasesCount(); i++)
			uniquePart.getUniquePhrase(i).setBars(2);
	}
	
}
