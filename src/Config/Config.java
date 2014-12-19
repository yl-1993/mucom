package Config;

import java.util.HashMap;

import Composition.Hongyu.CompositionHongyu;
import Composition.Test.CompositionTest;
import Generate.Composition;
import Generate.ParameterConversion;
import Generate.ParameterGenerate;
import ParameterConversion.Test.ConversionTest;
import ParameterConversion.Test.ConversionRegression;
import ParameterGenerate.Test.GenerateTest;

/**
 * 这是一个配置类
 * 在里边配置每个环节所使用的具体类
 * 用hashMap来管理
 */
public class Config {
	
	/**
	 * 在这里配置你要使用的具体作曲类（产生乐曲）
	 */
	public static final String CURRENT_COMPOSITION_STRING = "hongyu";
	/**
	 * 在这里配置你要使用的具体参数转换类（图像参数-音乐参数）
	 */
	public static final String CURRENT_CONVERSION_STRING = "regression";
	/**
	 * 在这里配置你要使用的具体参数产生类（产生图像参数）
	 */
	public static final String CURRENT_GENERATE_STRING = "test";
	
	/**
	 * 存储字符串-作曲类的关联表
	 */
	public static final HashMap<String, Composition> COMPOSITION_MAP = new HashMap<>();
	/**
	 * 存储字符串-参数转换类的关联表
	 */
	public static final HashMap<String, ParameterConversion> PARAMETER_CONVERSION_MAP = new HashMap<>();
	/**
	 * 存储字符串-参数生成类的关联表
	 */
	public static final HashMap<String, ParameterGenerate> PARAMETER_GENERATE_MAP = new HashMap<>();
	
	/**
	 * 图像参数个数
	 */
	public static int IMAGE_PARAMETER_NUM = 10;
	
	/**
	 * 转换参数键值
	 */
	public static String[] IMAGE_PARA_KEY = {"1","2","3","4","5"};
	
	/**
	 * 音乐参数键值
	 */
	public static String[] MUSIC_PARA_KEY={"音调高低","节奏快慢","起伏变化"};
	
	public static int IMAGE_PARA_LENGTH=256;
	
	/**
	 * 初始化这些关联表
	 */
	public static void configInit() {
		//初始化作曲类关联表
		COMPOSITION_MAP.put("test", new CompositionTest());
		COMPOSITION_MAP.put("hongyu", new CompositionHongyu());
		//初始化参数转换类关联表
		PARAMETER_CONVERSION_MAP.put("test", new ConversionTest());
		PARAMETER_CONVERSION_MAP.put("regression", new ConversionRegression());
		//初始化参数产生类关联表
		PARAMETER_GENERATE_MAP.put("test", new GenerateTest());
	}
	
	/**
	 * 界面上参数条目的个数
	 */
	public static final int PARAMETER_NUMBER = 5;
	
	
}
