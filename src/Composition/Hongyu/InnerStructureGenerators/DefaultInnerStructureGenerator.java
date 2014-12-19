package Composition.Hongyu.InnerStructureGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.InnerStructureGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * FixedClassical
 */
public class DefaultInnerStructureGenerator implements InnerStructureGenerator {

	@Override
	public void generateInnerStructure(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		// Set sentences
		uniquePart.setSentencesCount(2);

		uniquePart.getSentence(0).setPhrasesCount(2);
		uniquePart.getSentence(1).setPhrasesCount(2);

		// Set unique phrases
		uniquePart.setUniquePhrasesCount(Common.getRandomInteger(1, 5));

		for (int i = 0; i < uniquePart.getUniquePhrasesCount(); i++)
			uniquePart.getUniquePhrase(i).setBars(2);

		if (uniquePart.getUniquePhrasesCount() == 1) // AAAA
		{
			uniquePart.getSentence(0).getPhrase(0).setUniquePhraseId(0);
			uniquePart.getSentence(0).getPhrase(1).setUniquePhraseId(0);
			uniquePart.getSentence(1).getPhrase(0).setUniquePhraseId(0);
			uniquePart.getSentence(1).getPhrase(1).setUniquePhraseId(0);
		} else if (uniquePart.getUniquePhrasesCount() == 2) // ABAB
		{
			uniquePart.getSentence(0).getPhrase(0).setUniquePhraseId(0);
			uniquePart.getSentence(0).getPhrase(1).setUniquePhraseId(1);
			uniquePart.getSentence(1).getPhrase(0).setUniquePhraseId(0);
			uniquePart.getSentence(1).getPhrase(1).setUniquePhraseId(1);
		} else if (uniquePart.getUniquePhrasesCount() == 3) // ABAC && ABCB
		{
			if (Common.getRandomInteger(0, 2) == 0) {
				uniquePart.getSentence(0).getPhrase(0).setUniquePhraseId(0);
				uniquePart.getSentence(0).getPhrase(1).setUniquePhraseId(1);
				uniquePart.getSentence(1).getPhrase(0).setUniquePhraseId(0);
				uniquePart.getSentence(1).getPhrase(1).setUniquePhraseId(2);
			} else {
				uniquePart.getSentence(0).getPhrase(0).setUniquePhraseId(0);
				uniquePart.getSentence(0).getPhrase(1).setUniquePhraseId(1);
				uniquePart.getSentence(1).getPhrase(0).setUniquePhraseId(2);
				uniquePart.getSentence(1).getPhrase(1).setUniquePhraseId(1);
			}
		} else if (uniquePart.getUniquePhrasesCount() == 4) // ABCD
		{
			uniquePart.getSentence(0).getPhrase(0).setUniquePhraseId(0);
			uniquePart.getSentence(0).getPhrase(1).setUniquePhraseId(1);
			uniquePart.getSentence(1).getPhrase(0).setUniquePhraseId(2);
			uniquePart.getSentence(1).getPhrase(1).setUniquePhraseId(3);
		}
	}

}
