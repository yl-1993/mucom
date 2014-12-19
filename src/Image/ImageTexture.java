package Image;

import java.util.HashMap;

import  File.Picture;
import Config.Config;

/**
 * 提取的纹理特征如下：
 * 1.ASM(能量):
 *		灰度共生矩阵元素值的平方和，反映了图像灰度分布均匀程度和纹理粗细度。
 *		如果共生矩阵的所有值均相等，则ASM值小；相反，如果其中一些值大而其它值小，则ASM值大。
 *		当共生矩阵中元素集中分布时，此时ASM值大。ASM值大表明一种较均一和规则变化的纹理模式。
 * 2.Contrast:(对比度)
 * 		反映了图像的清晰度和纹理沟纹深浅的程度。
 * 		纹理沟纹越深，其对比度越大，视觉效果越清晰；反之，对比度小，则沟纹浅，效果模糊。
 * 		灰度差即对比度大的象素对越多，这个值越大。灰度公生矩阵中远离对角线的元素值越大，CON越大。
 * 3.IDM:(逆差距)
 * 		反映了纹理的清晰程度和规则程度。
 * 		纹理清晰、规律性较强、易于描述的，值较大；杂乱无章的，难于描述的，值较小。
 * 4.Entropy:(熵)
 * 		表明图像的复杂程序，当复杂程序高时，熵值较大，反之则较小
 * 5.Correlation(相关性):
 * 		相关值大小反映了图像中局部灰度相关性。
 * 		当矩阵元素值均匀相等时，相关值就大；
 * 		相反，如果矩阵像元值相差很大则相关值小。
 * 
 * 可以在不同方向上(0, 90, 180, 270)提取该特征：
 * Angle           Offset[x,y]
 * 	0              [ D  0]
 *	45             [ D -D]
 *	90             [ 0 -D]
 *	135            [-D -D]
 * 
 * @author LY
 *
 */

public class ImageTexture {
	
	private static int size = 256;
	private static boolean doIcalculateASM = true;
	private static boolean doIcalculateContrast = true;
	private static boolean doIcalculateCorrelation = true;
	private static boolean doIcalculateIDM = true;
	private static boolean doIcalculateEntropy = true;	
	
	private double [][] glcm;
	
	public void calGLCM(Picture picture, int stepX, int stepY){
		// This part get al the pixel values into the pixel [ ] array 

		int [][] pixels =picture.getGrayscales();
		int width = picture.getImageWidth();
		int height = picture.getImageHeight();
		
		// The variable a holds the value of the pixel where the Image Processor is sitting its attention
		// The varialbe b holds the value of the pixel which is the neighbor to the  pixel where the Image Processor is sitting its attention

		int a;
		int b;
		double pixelCounter=0;	

		//====================================================================================================
		// This part computes the Gray Level Correlation Matrix based in the step selected by the user
		
		glcm= new double [size][size];
		for (int y=0; y<height; y++) 	
		{
			for (int x=0; x<width; x++)	 
			{				
				a = 0xff & pixels[x][y];
				b = 0xff &	(picture.getGrayscale(x+stepX, y+stepY));					
				glcm [a][b] +=1;
				glcm [b][a] +=1;
				pixelCounter +=2;
			}
		}	
		
		//=====================================================================================================

		// This part divides each member of the glcm matrix by the number of pixels. The number of pixels was stored in the pixelCounter variable
		// The number of pixels is used as a normalizing constant

		for (a=0;  a<size; a++)  
		{
			for (b=0; b<size;b++) 
			{
				glcm[a][b]=(glcm[a][b])/(pixelCounter);
			}
		}
	}
	
	/**
	 * 计算二阶矩
	 * @return
	 */
	public double calASM(){
		double asm = 0.0;
		for (int a=0;  a<size; a++)  {
			for (int b=0; b<size;b++) {
				asm=asm+ (glcm[a][b]*glcm[a][b]);
			}
		}
		return asm;
	}
	
	/**
	 * 计算对比度	
	 * @return
	 */
	public double calContrast(){
		double contrast = 0;
		for (int a=0;  a<size; a++)  {
			for (int b=0; b<size;b++) {
				contrast=contrast+ (a-b)*(a-b)*(glcm[a][b]);
			}
		}
		return contrast;
	}
	
	/**
	 * 计算逆差距
	 * @return
	 */
	public double calIDM(){
		double IDM=0.0;
		for (int a=0;  a<size; a++)  
		{
			for (int b=0; b<size;b++) 
			{
				IDM=IDM+ (glcm[a][b]/(1+(a-b)*(a-b)))  ;
			}
		}
		return IDM;
	}
	
	/**
	 * 计算熵
	 * @return
	 */
	public double calEntropy(){
		double entropy=0.0;
		for (int a=0;  a<size; a++)  
		{
			for (int b=0; b<size;b++) {
				if (glcm[a][b]>0) {
					entropy=entropy-(glcm[a][b]*(Math.log(glcm[a][b])));
				}
			}
		}
		return entropy;
	}
	
	/**
	 * 计算相关度
	 * @return
	 */
	public double calCorrelation(){
		// px []  and py [] are arrays to calculate the correlation
		// meanx and meany are variables  to calculate the correlation
		//  stdevx and stdevy are variables to calculate the correlation

		//First step in the calculations will be to calculate px [] and py []
		int a = 0, b = 0;
		double correlation=0.0;
		double px=0, py=0;
		//double meanx=0.0;
		//double meany=0.0;
		double stdevx=0.0;
		double stdevy=0.0;

		for (a=0; a<size;a++)
		{
		   for (b=0; b <size; b++)
		   {
              px=px+a*glcm [a][b];  
              py=py+b*glcm [a][b];
		   } 
		}

		// Now calculate the standard deviations
		for (a=0; a<size; a++)
		{
			for (b=0; b <size; b++)
			{
				stdevx=stdevx+(a-px)*(a-px)*glcm [a][b];
				stdevy=stdevy+(b-py)*(b-py)*glcm [a][b];
			}
		}

		// Now finally calculate the correlation parameter
		for (a=0;  a<size; a++)  
		{
			for (b=0; b<size;b++) 
			{
				correlation=correlation+((a-px)*(b-py)*glcm [a][b]/(stdevx*stdevy)) ;
			}
		}
		
		return correlation;
	}
	
	/**
	 * 返回图像纹理参数
	 * @return 图像纹理参数
	 */
	public HashMap<String, Integer> getParameter(Picture picture){
		HashMap<String, Integer> parameter = new HashMap<>();

		//=====================================================================================================
		// This part calculates the angular second moment; the value is stored in glsm
		calGLCM(picture, 1, 0);

		//=====================================================================================================
		// This part calculates the angular second moment; the value is stored in asm

		if (doIcalculateASM==true){
			double asm = calASM();
			parameter.put(Config.IMAGE_PARA_KEY[0],(int)(asm));
		}

		//=====================================================================================================
		// This part calculates the contrast; the value is stored in contrast

		if (doIcalculateContrast==true){
			double contrast=calContrast();
			parameter.put(Config.IMAGE_PARA_KEY[1],(int)(contrast));
		}

		//=====================================================================================================
		//This part calculates the correlation; the value is stored in correlation
		
		if(doIcalculateCorrelation){
			double correlation = calCorrelation();
			parameter.put(Config.IMAGE_PARA_KEY[2],(int)(correlation));
		}
		
		//===============================================================================================
		// This part calculates the inverse difference moment

		if(doIcalculateIDM){
			double IDM = calIDM();
			parameter.put(Config.IMAGE_PARA_KEY[3],(int)(IDM));
		}

		//===============================================================================================
		// This part calculates the entropy

		if (doIcalculateEntropy==true){
			double entropy=calEntropy();
			parameter.put(Config.IMAGE_PARA_KEY[4],(int)(entropy));
		}		
		
		return parameter;
	}
}
