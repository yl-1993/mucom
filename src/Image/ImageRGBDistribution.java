package Image;

import java.awt.image.BufferedImage;

import File.Picture;

public class ImageRGBDistribution {

	
	public static int[] getRGBDistribution(BufferedImage image)
	{
		int height = image.getHeight();
		int width = image.getWidth();
		int[] RGB = new int[height * width];
		Picture.getRGB(image, 0, 0, width, height, RGB);
		int[] result=new int[64];
		for (int i = 0; i < 64; i++) result[i] = 0;
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
			{
				int rgb = RGB[i*width +j];
				int red = (rgb >> 16) &0xff;
				int green = (rgb >> 8) &0xff;
				int blue = rgb & 0xff;
				result[ (red / 64) * 16 + (green / 64) * 4 + (blue / 64)]++;
			}
		return result;
	}
}
