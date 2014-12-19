package Generate;

import java.util.HashMap;

import File.Picture;

public interface ParameterGenerate {

	/**
	 * 图像参数产生类用，每当显示一张训练集中的图片时，将调用该函数，传入显示的图片和当前窗体中的参数
	 * @param music 显示的图片
	 * @param parameter 当前窗体中的参数
	 */
	public void train(Picture picture, HashMap<String, Integer> parameter);
	
	/**
	 * 训练图像产生类用，当训练文件夹处理完毕时，将调用该函数，可以进行一些数据处理操作
	 */
	public void tidy();
	
	/**
	 * 将图像的特征参数产生出来
	 * @param picture 传入的图像
	 * @return 产生的图像特征
	 */
	public HashMap<String, int[]> generate(Picture picture);
}
