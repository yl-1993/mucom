package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;
import Composition.Hongyu.Essential.UniquePhrase;
/**
 * RandomPhrasedMelody
 */
public class RandomPhrasedMelody implements MelodyGenerator {

	double GetEventLength(UniquePart up, int e) {
		Time t1 = up.getEventStart(e);
		Time t2 = up.getEventEnd(e);
		return (t2.startBar - t1.startBar) * up.getMeter() + (t2.position - t1.position);
	}
	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int UniquePhraseCount = uniquePart.getUniquePhrasesCount();
		
		int [][] melody = new int [UniquePhraseCount][];
		int [][] align  = new int [UniquePhraseCount][];
		
		for (int i = 0; i < UniquePhraseCount; i++) {
			UniquePhrase p = uniquePart.getUniquePhrase(i);
			melody[i] = new int[p.getEventsCount()];
			align[i] = new int[p.getEventsCount()];
			for (int m = 0; m < p.getEventsCount(); m++) {
				melody[i][m] = Common.getRandomInteger(-2, 3);
				align[i][m] = Common.getRandomInteger(0, 2);
			}

			melody[i][0] = Common.getRandomInteger(2, 9);
		}

		int event = 0;
		int theNote = 0;

		for (int s = 0; s < uniquePart.getSentencesCount(); s++) {
			for (int p = 0; p < uniquePart.getSentence(s).getPhrasesCount(); p++) {
				UniquePhrase upp = uniquePart.getSentence(s).getPhrase(p)
						.getUniquePhrase();

				int note = melody[uniquePart.getSentence(s).getPhrase(p).getUniquePhraseId()][0];

				for (int e = 0; e < upp.getEventsCount(); e++) {
					if (event < uniquePart.getEventsCount() - 1) {
						if (align[uniquePart.getSentence(s).getPhrase(p)
								.getUniquePhraseId()][e] == 1
								|| GetEventLength(uniquePart, event) > 1.2) {
							uniquePart.setEventPitch(event, uniquePart.alignPitchToHarmonic(event,
									note));
						} else {
							uniquePart.setEventPitch(event, note);
						}

						event++;
						if (e < upp.getEventsCount() - 1)
							note += melody[uniquePart.getSentence(s).getPhrase(p)
									.getUniquePhraseId()][e + 1];
					}
				}
			}
		}

		int last_note = 1;

		if (Common.getRandomInteger(0, 3) != 0)
			last_note = 1 + (Common.getRandomInteger(0, 3) * 2);
		
		while (theNote > 5) {
			theNote -= 7;
			last_note += 7;
		}

		while (theNote < -3) {
			theNote += 7;
			last_note -= 7;
		}

		if (theNote == 5 && Common.getRandomInteger(0, 2) == 0)
			last_note += 7;

		uniquePart.setEventPitch(uniquePart.getEventsCount() - 1, last_note);
	}
}
