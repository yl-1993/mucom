package Composition.Hongyu.Essential;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 马尔科夫链以及相关的操作
 */
public class MarkovChain implements Serializable {
	
	/**
	 * 马尔科夫链的长度
	 */
	public static final int MARKOV_CHAIN_LENGTH = 3;

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4265830282812668463L;
	
	/**
	 * key为前几个音符的组合，value为每个结果音符对应的概率
	 */
	HashMap<String, HashMap<Integer, Double>> probability = new HashMap<>();
	
	/**
	 * 构造函数
	 * @param 所有的原始数据
	 */
	public MarkovChain(ArrayList<Integer[]> rawData) {
		//固定条件后，每个可能结果出现的频数
		HashMap<String, HashMap<Integer, Integer>> frequency = new HashMap<>();
		for (Integer[] integers : rawData) {
			//获取条件字符串
			String condition = "";
			for (int i = 0; i < integers.length - 1; i++) {
				condition += (integers[i] + ",");
			}
			int resultPicth = integers[integers.length - 1];
			//检查是否为新出现条件
			if (!frequency.containsKey(condition)) {
				HashMap<Integer, Integer> hashMap = new HashMap<>();
				hashMap.put(resultPicth, 1);
				frequency.put(condition, hashMap);
			} else {
				//检查该结果音符是否出现过
				HashMap<Integer, Integer> hashMap = frequency.get(condition);
				if (hashMap.containsKey(resultPicth)) {
					hashMap.put(resultPicth, hashMap.get(resultPicth) + 1);
				} else {
					hashMap.put(resultPicth, 1);
				}
			}
		}
		//转化为概率形式
		for (String condition : frequency.keySet()) {
			HashMap<Integer, Integer> frequencyMap = frequency.get(condition);
			HashMap<Integer, Double> probabilityMap = new HashMap<>();
			int rawCount = 0;
			for (Integer pitch : frequencyMap.keySet()) {
				rawCount += frequencyMap.get(pitch);
			}
			int count = 0;
			for (Integer pitch : frequencyMap.keySet()) {
				if ((double)(frequencyMap.get(pitch)) / (double)(rawCount) > 0.1) {
					count += frequencyMap.get(pitch);
				}
			}
			for (Integer pitch : frequencyMap.keySet()) {
				if ((double)(frequencyMap.get(pitch)) / (double)(rawCount) > 0.1) {
					probabilityMap.put(pitch, (double)(frequencyMap.get(pitch)) / (double)(count));
				}
			}
			probability.put(condition, probabilityMap);
		}
	}
	
	/**
	 * 根据马尔科夫链规则获得音符
	 * @param condition 条件
	 * @return 音符
	 */
	public int getNote(ArrayList<Integer> condition) {
		int length = Math.min(MARKOV_CHAIN_LENGTH, condition.size());
		int note = Integer.MIN_VALUE;
		for (int i = length; i > 0; i--) {
			//八度平移
			int octiveCount = 0;
			int startPosition = condition.size() - i;
			int startPitch = condition.get(startPosition);
			while (startPitch <= 0) {
				startPitch += 7;
				octiveCount--;
			}
			while (startPitch >= 8) {
				startPitch -= 7;
				octiveCount++;
			}
			//获取条件
			String conditionString = "";
			for (int j = startPosition; j < condition.size(); j++) {
				int pitch = condition.get(j) - octiveCount * 7;
				conditionString += (pitch + ",");
			}
			if (probability.containsKey(conditionString)) {
				HashMap<Integer, Double> hashMap = probability.get(conditionString);
				Integer[] pitchs = new Integer[hashMap.keySet().size()];
				Double[] sumProbability = new Double[hashMap.keySet().size()];
				int index = 0;
				for (Integer integer : hashMap.keySet()) {
					pitchs[index] = integer;
					sumProbability[index] = hashMap.get(integer);
					index++;
				}
				//概率累加
				double sum = 0;
				for (int j = 0; j < sumProbability.length; j++) {
					sumProbability[j] += sum;
					sum = sumProbability[j];
				}
				//防止double精度问题
				sumProbability[sumProbability.length - 1] = 1.0;
				//选取note
				double random = Common.getRandomDouble(0, 1);
				index = sumProbability.length - 1;
				for (; index >= 0; index--) {
					if (sumProbability[index] < random) {
						break;
					}
				}
				return pitchs[index + 1] + octiveCount * 7;
			}
		}
		//没找到音符
		if (note == Integer.MIN_VALUE) {
			note = condition.get(condition.size() - 1);
		}
		return note;
	}
	
}
