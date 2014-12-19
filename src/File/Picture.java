package File;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 图像类，包括读取图片信息和基本操作
 */
public class Picture {
	
	/**
	 * 存储读取的图像
	 */
	private BufferedImage image;
	
	/**
	 * 像素类
	 */
	public class Pixel {
		
		/**
		 * 红色像素值
		 */
		public int r;
		
		/**
		 * 绿色像素值
		 */
		public int g;
		
		/**
		 * 蓝色像素值
		 */
		public int b;
		
		/**
		 * x坐标
		 */
		public int x;
		
		/**
		 * y坐标
		 */
		public int y;
	}
	
	/**
	 * 构造函数，从指定路径中读取图像
	 * @param path 图像的文件路径
	 */
	public Picture(String path) {
		// TODO Auto-generated constructor stub
		try {
			image = ImageIO.read(new File(path));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造函数，从指定的路径读取图像，并缩放到指定大小
	 * @param path 图像的文件路径
	 * @param width 宽度
	 * @param height 高度
	 */
	public Picture(String path, int width, int height) {
		// TODO Auto-generated constructor stub
		try {
			image = ImageIO.read(new File(path));
			AffineTransform transform = new AffineTransform();
			transform.scale((double)(width) / image.getWidth(), (double)(height) / image.getHeight());
			AffineTransformOp transformOp = new AffineTransformOp(transform, null);
			BufferedImage result = new BufferedImage(width, height, image.getType());
			transformOp.filter(image, result);
			image = result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造函数，从指定的路径读取图像，并将长和宽都缩放到指定百分比
	 * @param path 图像的文件路径
	 * @param percentage 百分比
	 */
	public Picture(String path, double percentage) {
		// TODO Auto-generated constructor stub
		try {
			image = ImageIO.read(new File(path));
			AffineTransform transform = new AffineTransform();
			transform.scale(percentage, percentage);
			AffineTransformOp transformOp = new AffineTransformOp(transform, null);
			BufferedImage result = new BufferedImage((int)(image.getWidth() * percentage), (int)(image.getHeight() * percentage), image.getType());
			transformOp.filter(image, result);
			image = result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造函数，从指定的路径读取图像，并缩放到指定大小，保持长宽比例不变
	 * @param path 图像的文件路径
	 * @param size 面积
	 */
	public Picture(String path, int size) {
		try {
			image = ImageIO.read(new File(path));
			double percentage = Math.sqrt((double)size / image.getWidth() / image.getHeight());
			AffineTransform transform = new AffineTransform();
			transform.scale(percentage, percentage);
			AffineTransformOp transformOp = new AffineTransformOp(transform, null);
			BufferedImage result = new BufferedImage((int)(image.getWidth() * percentage), (int)(image.getHeight() * percentage), image.getType());
			transformOp.filter(image, result);
			image = result; 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 重新设定图片大小
	 * @param percentage 图片大小的百分比
	 */
	public void resize(double percentage) {
		AffineTransform transform = new AffineTransform();
		transform.scale(percentage, percentage);
		AffineTransformOp transformOp = new AffineTransformOp(transform, null);
		BufferedImage result = new BufferedImage((int)(image.getWidth() * percentage), (int)(image.getHeight() * percentage), image.getType());
		transformOp.filter(image, result);
		image = result;
	}
	
	public static BufferedImage resize(BufferedImage image, double percentage) {
		AffineTransform transform = new AffineTransform();
		transform.scale(percentage, percentage);
		AffineTransformOp transformOp = new AffineTransformOp(transform, null);
		BufferedImage result = new BufferedImage((int)(image.getWidth() * percentage), (int)(image.getHeight() * percentage), image.getType());
		transformOp.filter(image, result);
		image = result;
		return image;
	}
	
	/**
	 * 获得指定坐标的像素
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @return 像素
	 */
	public Pixel getPixel(int x, int y) {
		int result = image.getRGB(x, y);
		Pixel pixel = new Pixel();
		pixel.r = (result & 0xff0000) >> 16;
		pixel.g = (result & 0xff00) >> 8;
		pixel.b = (result & 0xff);
		pixel.x = x;
		pixel.y = y;
		return pixel;
	}
	
	/**
	 * 获得所有像素
	 * @return 所有像素的数组
	 */
	public Pixel[][] getPixels() {
		Pixel[][] result = new Pixel[image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				//getPixel函数的参数为：横坐标、纵坐标
				result[i][j] = getPixel(j, i);
			}
		}
		return result;
	}
	
	/**
	 * 获取指定坐标的灰度
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @return 像素的灰度
	 */
	public int getGrayscale(int x, int y) {
		Pixel pixel = getPixel(x, y);
		return (pixel.r * 30 + pixel.g * 59 + pixel.b * 11) / 100;
	}
	
	/**
	 * 获取所有像素的灰度
	 * @return 像素的灰度数组
	 */
	public int[][] getGrayscales() {
		int[][] result = new int[image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				//getGrayscale函数的参数为：横坐标、纵坐标
				result[i][j] = getGrayscale(j, i);
			}
		}
		return result;
	}
	
	/**
	 * 获取图像的宽
	 * @return 图像的宽
	 */
	
	public int getImageWidth(){
		return image.getWidth();
	}
	
	/**
	 * 获取图像的高
	 * @return 图像的高
	 */

	public int getImageHeight(){
		return image.getHeight();
	}
	
	/**
	 * 获取图像的RGB值
	 * @return 图像的RGB数组
	 */
	public int[] getRGB( 
			int x, int y,
			int width, int height, int[] pixels ) 
	{
		int t = image.getType();
		if ( t == BufferedImage.TYPE_INT_ARGB || t == BufferedImage.TYPE_INT_RGB )
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}
	
	public static int[] getRGB( 
			BufferedImage image,int x, int y,
			int width, int height, int[] pixels ) 
	{
		int t = image.getType();
		if ( t == BufferedImage.TYPE_INT_ARGB || t == BufferedImage.TYPE_INT_RGB )
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}

	/**
	 * 设置图像的RGB值
	 */
	public void setRGB( 
			int x, int y, 
			int width, int height, int[] pixels ) 
	{
		int t = image.getType();
		if ( t == BufferedImage.TYPE_INT_ARGB || t == BufferedImage.TYPE_INT_RGB )
			image.getRaster().setDataElements(x, y, width, height, pixels);
		image.setRGB(x, y, width, height, pixels, 0, width);
	}
	
	/**
	 * 获取图像的颜色灰度直方图
	 * @return 图像的256位颜色灰度直方图
	 */
	
	public int[] getHistogram() {
		int[] srcPixels = new int[image.getWidth() * image.getHeight()];
		int[] itensity = new int[256];
		//initialzie the itensity
		for ( int i = 0; i < itensity.length; i++ ) itensity[i] = 0;
		//fill the srcPixels
		getRGB(0, 0, image.getWidth(), image.getHeight(), srcPixels);
		
		int index = 0;
		int r, g, b;
		int gray;
		for ( int row = 0; row < image.getHeight(); row++ ) {
			for ( int col = 0; col < image.getWidth(); col++ ) {
				index = row * image.getWidth() + col;
				//a = (srcPixels[index] >> 24) & 0xFF;
				r = (srcPixels[index] >> 16) & 0xFF;
				g = (srcPixels[index] >> 8 ) & 0xFF;
				b = (srcPixels[index] >> 0 ) & 0xFF;
				gray = (int) (0.299 * (double) r)
						+ (int) (0.587 * (double) g)
						+ (int) (0.114 * (double) b);
				itensity[gray]++;
			}
		}
		return itensity;
	}
	
	/**
	 * 获取图像
	 * @return 当前的图像
	 */
	public BufferedImage getImage() {
		return image;
	}

}
