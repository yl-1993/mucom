package Generate;

import java.util.HashMap;

import File.Picture;
import MIDI.Music;

public interface ParameterConversion {

	/**
	 * 训练参数转换类用，每当播放一首训练集中的乐曲时，将调用该函数，传入播放的乐曲和当前窗体中的参数
	 * @param music 播放的乐曲
	 * @param parameter 当前窗体中的参数
	 */
	public void train(Music music, HashMap<String, Integer> parameter);
	
	/**
	 * 训练参数转换类用，每当显示一张训练集中的图片时，将调用该函数，传入显示的图片和当前窗体中的参数
	 * @param music 显示的图片
	 * @param parameter 当前窗体中的参数
	 */
	public void train(Picture picture, HashMap<String, Integer> parameter);
	
	/**
	 * 训练参数转换类用，当训练文件夹中的图像处理完毕时，将调用该函数，可以进行一些数据处理操作
	 */
	public void tidyPicture();
	
	/**
	 * 训练参数转换类用，当训练文件夹中的音乐处理完毕时，将调用该函数，可以进行一些数据处理操作
	 */
	public void tidyMusic();
	
	/**
	 * 转换参数用，当图像的参数获取完毕时，调动该函数，转换为音乐的参数
	 * @param parameter 图像参数
	 * @return 生成的音乐参数
	 */
	public HashMap<String, Integer> convert(HashMap<String, int[]> parameter);
}
