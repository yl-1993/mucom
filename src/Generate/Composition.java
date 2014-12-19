package Generate;

import java.util.HashMap;

import MIDI.Music;

public interface Composition {
	
	/**
	 * 训练作曲器用，每当播放一首训练集中的乐曲时，将调用该函数，传入播放的乐曲和当前窗体中的参数
	 * @param music 播放的乐曲
	 * @param parameter 当前窗体中的参数
	 */
	public void train(Music music, HashMap<String, Integer> parameter);
	
	/**
	 * 训练作曲器用，当训练文件夹处理完毕时，将调用该函数，可以进行一些数据处理操作
	 */
	public void tidy();
	
	/**
	 * 产生乐曲用，当点击生成音乐时，将调用该函数，将图像-音乐参数转换类转换的结果传入其中
	 * @param parameter 图像-音乐参数转换类转换的结果
	 * @return 生成的音乐
	 */
	public Music generate(HashMap<String, Integer> parameter);
}
