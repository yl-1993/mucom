package Composition.Hongyu.MelodyGenerators;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import Composition.Hongyu.CompositionHongyu;
import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MarkovChain;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

public class MarkovMelodyGenerator implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// 恢复马尔科夫链
		MarkovChain markovChain = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new FileInputStream(CompositionHongyu.TRAINING_RESULT));
			markovChain = (MarkovChain) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			System.err.println("load markov chain failed");
			DefaultMelodyGenerator defaultMelodyGenerator = new DefaultMelodyGenerator();
			defaultMelodyGenerator.generateMelody(uniquePart, parameter);
			return;
		}
		// 生成旋律
		int basis = uniquePart.getEventBasis(0);
		int note = basis;
		// 根据参数调整基准
		int pitchParameter = parameter.get(Constant.PITCH_STRING);
		if (pitchParameter >= 100) pitchParameter = 99;
		if (pitchParameter <= 0) pitchParameter = 0;
		int[] offsets = {-4, -4, -2, -2, 0, 0, 2, 2, 4, 4};
		basis = basis + offsets[pitchParameter / 10];
		// 记录数组和条件数组
		ArrayList<Integer> condition = new ArrayList<>();
		ArrayList<Integer> notes = new ArrayList<>();
		// 循环产生音符
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			if (i == uniquePart.getEventsCount() - 2)
				note = (note + 1) / 2;
			// 备份note
			int backup = note;
			// 对齐
			note = uniquePart.alignPitchToHarmonic(i, note);
			// 避免A-B-A-B-A类型
			if (notes.size() >= 4) {
				int n1 = notes.get(notes.size() - 4);
				int n2 = notes.get(notes.size() - 3);
				int n3 = notes.get(notes.size() - 2);
				int n4 = notes.get(notes.size() - 1);
				if (n1 == n3 && n2 == n4 && n3 == note) {
					note = 2 * n2 - n1;
					note = uniquePart.alignPitchToHarmonic(i, note);
				}
			}
			// 避免A-A-A类型和A-B-B类型
			if (notes.size() >= 2) {
				int n1 = notes.get(notes.size() - 2);
				int n2 = notes.get(notes.size() - 1);
				if (n1 != n2 && n2 == note) {
					note = n1;
					note = uniquePart.alignPitchToHarmonic(i, note);
				}
				if (n1 == n2 && n2 == note) {
					n2 = note - 3;
					n2 = uniquePart.alignPitchToHarmonic(i - 1, n2);
					uniquePart.setEventPitch(i - 1, n2);
				}
			}
			uniquePart.setEventPitch(i, note);
			// 如果出现调整，则很可能清空条件
			if (note != backup && test(condition.size() * 0.4))
				condition.clear();
			// 如果满足一定概率，则清空条件数组
			if (condition.size() > 3 && test(condition.size() * 0.1))
				condition.clear();
			// 将刚添加的音符加入条件
			condition.add(note);
			notes.add(note);
			// 获取音符
			if (i < uniquePart.getEventsCount() - 2) {
				backup = note;
				note = markovChain.getNote(condition);
				// 如果本次音符与上次相差较远，则随机选取
				double distance = Math.abs(note - backup);
				if (distance > 2 && test(0.2 * distance + 0.2))
					note = backup + getDelta(notes, basis, parameter);
				// 当本次音符与上次相同时，重新选取
				double coefficient = (double)(parameter.get(Constant.VARIATION_STRING)) / 50.0 + Double.MIN_NORMAL;
				while (note == backup && test(coefficient)) {
					if (test(0.8))
						note = markovChain.getNote(condition);
					else
						note = backup + getDelta(notes, basis, parameter);
				}
				// 如果满足一定概率，则随机选取
				if (test(0.2))
					note = backup + getDelta(notes, basis, parameter);
				// 音符过高或过低的调整
				if (note < basis - 8 * coefficient) {
					if (test(0.6))
						note += Common.getRandomInteger(0, 3);
					if (test(0.2))
						note = basis - Common.getRandomInteger(0, (int)(4 * coefficient) + 1);
				}
				if (note > basis + 8 * coefficient) {
					if (test(0.6))
						note -= Common.getRandomInteger(0, 3);
					if (test(0.2))
						note = basis + Common.getRandomInteger(0, (int)(4 * coefficient) + 1);
				}
			}
		}
		// 设置最后一个音
		int last_note = uniquePart.getEventBasis(0);
		if (note > 5)
			last_note += 7;
		if (note <= -3)
			last_note -= 7;
		uniquePart.setEventPitch(uniquePart.getEventsCount() - 1, last_note);
	}

	/**
	 * 给定一个实验成功的概率，按照该概率返回实验结果
	 * @param probability 给定的概率
	 * @return 实验结果
	 */
	private boolean test(double probability) {
		return probability > Common.getRandomDouble(0, 1);
	}
	
	/**
	 * 获取下个音跟这个音的音量差
	 * @param notes 所有音阶
	 * @param basis 基准音
	 * @return
	 */
	private int getDelta(ArrayList<Integer> notes, int basis,
			HashMap<String, Integer> parameter) {
		// 统计最近8个音的音高偏移
		int start = notes.size() >= 8 ? notes.size() - 8 : 0;
		double sum = 0;
		for (int i = start; i < notes.size(); i++) {
			sum += notes.get(i);
		}
		double average = sum / (notes.size() - start);
		double offset = average - basis;
		double coefficient = Common.getCoefficient(parameter.get(Constant.VARIATION_STRING), 0, 100);
		// 根据偏移决定走向
		if (offset <= -4 * coefficient)
			return 2;
		else if (offset <= -2.5 * coefficient)
			return Common.getRandomInteger(1, 3);//1或2
		else if (offset <= -1 * coefficient)
			return Common.getRandomInteger(0, 3);//0或1或2
		else if (offset <= 1 * coefficient)
			return Common.getRandomInteger(-1, 2) * 2;//-2或0或2
		else if (offset <= 2.5 * coefficient)
			return Common.getRandomInteger(-2, 1);//-2或-1或0
		else if (offset <= 4 * coefficient)
			return Common.getRandomInteger(-2, 0);//-2或-1
		else
			return -2;
	}
}
