package Composition.Hongyu.StructureGenerators;

import java.util.ArrayList;
import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.GeneratorSelection;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.StructureGenerator;
import Composition.Hongyu.GeneratorSelections.GeneratorSelectionGetter;
import Composition.Hongyu.InnerStructureGenerators.InnerStructureGeneratorGetter;
import Composition.Hongyu.Ornamenters.Appoggiatura;
import Composition.Hongyu.Ornamenters.DefaultOrnamenter;

/**
 * 考虑到时间等因素的结构生成器
 */

public class StructureGeneratorConsiderTime implements StructureGenerator {

	private static final String[] structures = {
		"1",
		"12",
		"121",
		"1211",
		"21211",
		"121311",
		"1213211",
		"21213211",
		"121231211",
	};
	
	@Override
	public void generateStructure(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int meter = 4;
		int time = parameter.get(Constant.TIME_STRING);
		time += (4 - 2);
		int halfPartCount = time / 8;
		int partsCount = halfPartCount / 2;
		int transpose = getTranspose(parameter.get(Constant.PITCH_STRING));
		GeneratorSelection generatorSelection = GeneratorSelectionGetter.getGeneratorSelection(parameter);
		//是否有半个段落
		boolean halfPartExisted = (halfPartCount % 2 == 1);
		//是否有引子
		boolean noVoicePartExisted = true;
		//结构描述
		String structureString = getStructureString(partsCount);
		//是否在bridge之后
		boolean afterbridge = false;
		
		if (!halfPartExisted && partsCount < 8) {
			if (Common.getRandomInteger(0, 2) == 0 || partsCount < 4) {
				noVoicePartExisted = false;
			}
		}
		
		// initialize unique parts
		int uniquePartsCount = getUniquePartsCount(structureString);
		if (halfPartExisted) {
			musicDescription.setUniquePartsCount(uniquePartsCount + 1);
			musicDescription.getUniquePart(0).innerStructureGenerator = InnerStructureGeneratorGetter.getInnerStructureGenerator(4);
		} else {
			musicDescription.setUniquePartsCount(uniquePartsCount);
			musicDescription.getUniquePart(0).innerStructureGenerator = InnerStructureGeneratorGetter.getInnerStructureGenerator(8);
		}
		for (int i = 1; i < musicDescription.getUniquePartsCount(); i++) {
			musicDescription.getUniquePart(i).innerStructureGenerator = InnerStructureGeneratorGetter.getInnerStructureGenerator(8);
		}
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			musicDescription.getUniquePart(i).setMeter(meter);
			musicDescription.getUniquePart(i).rhythmGenerator = generatorSelection.getRhythmGenerator();
			musicDescription.getUniquePart(i).harmonyGenerator = generatorSelection.getHarmonyGenerator();
			musicDescription.getUniquePart(i).melodyGenerator = generatorSelection.getMelodyGenerator();
		}
		
		// initialize parts
		if (halfPartExisted) {
			//存在半个段落，作为引子
			musicDescription.setPartsCount(partsCount + 1);
			musicDescription.getPart(0).setUniquePartIndex(0);
			musicDescription.getPart(0).setArrangeHint(0);
			musicDescription.getPart(0).setTranspose(transpose - 1);
			musicDescription.getPart(0).setScale(getScale('0', parameter));
			for (int i = 1; i < musicDescription.getPartsCount(); i++){
				char ch = structureString.charAt(i - 1);
				musicDescription.getPart(i).setUniquePartIndex(ch - '0');
				musicDescription.getPart(i).setArrangeHint(getArrangeHint(ch, afterbridge));
				musicDescription.getPart(i).setTranspose(getTranspose(ch, transpose));
				musicDescription.getPart(i).setScale(getScale(ch, parameter));
				if (ch == '3') { afterbridge = true; }
			}
		}
		if (noVoicePartExisted && !halfPartExisted) {
			//有引子但是不存在半个段落
			musicDescription.setPartsCount(partsCount);
			musicDescription.getPart(0).setUniquePartIndex(0);
			musicDescription.getPart(0).setArrangeHint(0);
			musicDescription.getPart(0).setTranspose(transpose - 1);
			musicDescription.getPart(0).setScale(getScale('0', parameter));
			for (int i = 1; i < musicDescription.getPartsCount(); i++){
				char ch = structureString.charAt(i);
				musicDescription.getPart(i).setUniquePartIndex(ch - '1');
				musicDescription.getPart(i).setArrangeHint(getArrangeHint(ch, afterbridge));
				musicDescription.getPart(i).setTranspose(getTranspose(ch, transpose));
				musicDescription.getPart(i).setScale(getScale(ch, parameter));
				if (ch == '3') { afterbridge = true; }
			}
		}
		if (!noVoicePartExisted && !halfPartExisted) {
			//没有引子
			musicDescription.setPartsCount(partsCount);
			for (int i = 0; i < musicDescription.getPartsCount(); i++) {
				char ch = structureString.charAt(i);
				musicDescription.getPart(i).setUniquePartIndex(ch - '1');
				musicDescription.getPart(i).setArrangeHint(getArrangeHint(ch, afterbridge));
				musicDescription.getPart(i).setTranspose(getTranspose(ch, transpose));
				musicDescription.getPart(i).setScale(getScale(ch, parameter));
				if (ch == '3') { afterbridge = true; }
			}
		}
		//其它的初始化部分
		for (int i = 0; i < musicDescription.getPartsCount(); i++)
		{
			musicDescription.getPart(i).setTempo(1.0);
			if (i < musicDescription.getPartsCount() - 1) {
				musicDescription.getPart(i).ornamenter = generatorSelection.getOrnamenter();
			} else {
				if (Common.getRandomInteger(0, 2) == 0) {
					musicDescription.getPart(i).ornamenter = new DefaultOrnamenter();
				} else {
					musicDescription.getPart(i).ornamenter = new Appoggiatura();
				}
			}
			
		}
		//处理开始的段落
		if (musicDescription.getPart(0).getArrangeHint() != 0)
			musicDescription.getPart(0).setArrangeHint(1);
		musicDescription.getPart(1).setArrangeHint(1);
		//最后重复的两段，后者比前者音调高一些
		if (structureString.endsWith("11")) {
			int lastTranspose = musicDescription.getPart(musicDescription.getPartsCount() - 2).getTranspose();
			musicDescription.getPart(musicDescription.getPartsCount() - 1).setTranspose(lastTranspose + Common.getRandomInteger(1, 3));
		}
		//结尾处理，PS，设为伴奏感觉不太好听
		if ((musicDescription.getPartsCount() >= 4 && noVoicePartExisted) || 
				(musicDescription.getPartsCount() >= 6))
			musicDescription.getPart(musicDescription.getPartsCount() - 1).setArrangeHint(1);
		//选取几个生成器
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			System.out.println();
			System.out.println("UniquePart " + i + ":");
			System.out.println(musicDescription.getUniquePart(i).rhythmGenerator);
			System.out.println(musicDescription.getUniquePart(i).harmonyGenerator);
			System.out.println(musicDescription.getUniquePart(i).melodyGenerator);
			System.out.println(musicDescription.getUniquePart(i).innerStructureGenerator);
		}
		System.out.println();
		//输出修饰器等信息
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			System.out.println("part" + i + " -> unique part" + musicDescription.getPart(i).getUniquePartIndex()
					+ ", arrange hint = " + musicDescription.getPart(i).getArrangeHint()
					+ ", transpose = " + musicDescription.getPart(i).getTranspose());
			System.out.println(musicDescription.getPart(i).ornamenter);
			System.out.println();
		}
	}
	
	/**
	 * 获得调式
	 * @param ch 独立片段索引
	 * @param parameter 参数
	 * @return 调式
	 */
	private String getScale(char ch, HashMap<String, Integer> parameter) {
		int pitch = parameter.get(Constant.PITCH_STRING);
		int speed = parameter.get(Constant.SPEED_STRING);
		if ((pitch * pitch + speed * speed) <= 2500) {
			if (ch == '3') {
				return "MAJOR";
			} else {
				return "MINOR";
			}
		} else {
			if (ch == '3') {
				return "MINOR";
			} else {
				return "MAJOR";
			}
		}
	}

	/**
	 * 根据参数设定基准音高
	 * @param pitch 参数
	 * @return 基准音高
	 */
	private int getTranspose(int pitch) {
		if (pitch <= 10)
			return -3;
		if (pitch <= 20)
			return 0;
		if (pitch <= 30)
			return 2;
		if (pitch <= 50)
			return 5;
		if (pitch <= 70)
			return 7;
		if (pitch <= 80)
			return 9;
		if (pitch <= 90)
			return 12;
		return 14;
	}

	/**
	 * 根据part索引控制音高
	 * @param ch 独立片段索引
	 * @param transpose 当前基准音
	 * @return
	 */
	private int getTranspose(char ch, int transpose) {
		if (ch == '3')
			if (transpose <= 5)
				return transpose + 5;
			else
				return transpose - 5;
		return transpose + adjust();
	}

	/**
	 * 根据part索引获取排布器提示
	 * @param ch 独立片段索引
	 * @param afterbridge 是否过了桥接段
	 * @return 排布器提示
	 */
	private int getArrangeHint(char ch, boolean afterbridge) {
		if (afterbridge)
			return 3;
		if (ch == '1' && Common.getRandomInteger(0, 1) == 0)
			return 3;
		if (ch == '3')
			return 2;
		return 1;
	}

	/**
	 * 根据part的个数获取乐曲结构
	 * @param partCount
	 * @return
	 */
	private String getStructureString(int partCount) {
		ArrayList<String> candidates = new ArrayList<>();
		for (String string : structures) {
			if (string.length() == partCount) {
				candidates.add(string);
			}
		}
		return candidates.get(Common.getRandomInteger(0, candidates.size()));
	}
	
	/**
	 * 根据乐曲结构获取所需的UniquePart的个数
	 * @param structureString 乐曲结构
	 * @return UniquePart的个数
	 */
	private int getUniquePartsCount(String structureString) {
		int max = 1;
		for (int i = 0; i < structureString.length(); i++) {
			if (max < structureString.charAt(i) - '0') {
				max = structureString.charAt(i) - '0';
			}
		}
		return max;
	}
	
	/**
	 * 返回一个随机数，对基准音进行微调
	 * @return 随机数
	 */
	private int adjust() {
		return Common.getRandomInteger(-1, 2);
	}
}
