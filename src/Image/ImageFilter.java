package Image;

import java.awt.image.BufferedImage;

/**
 * common image filter . <br />
 * 
 * @author yanglei
 */
public class ImageFilter {
	
	public static int[] getRGB( 
			BufferedImage image,
			int x, int y,
			int width, int height, int[] pixels ) 
	{
		int t = image.getType();
		if ( t == BufferedImage.TYPE_INT_ARGB || t == BufferedImage.TYPE_INT_RGB )
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}
	
	public static float getRateCount( int max, int equals, int value ) {
		float idx = ((float) value) * ((float)equals) / ((float)max);
		if ( idx >= equals ) idx = equals - 1;
		return idx;
	}
	
	/**
	 * single color Histogram filter, 
	 * 		get the Eigenvalues of the specified image .
	 * 
	 * @param	image
	 * @param 	req
	 * @param	geq
	 * @param	beq
	 * @return	float[]
	 */
	public static float[] HistogramFilter( 
			BufferedImage image,
			int req, int geq, int beq ) 
	{
		int width = image.getWidth();
		int height = image.getHeight();
		
		int[] pixels = new int[width * height];
		float[] histogram = new float[req * geq * beq];
		for ( int i = 0; i < histogram.length; i++ ) histogram[i] = 0;
		
		getRGB(image, 0, 0, width, height, pixels);
		
		int r, g, b;
		int rp, gp, bp;
		int idx = 0, sidx = 0;
		for ( int row = 0; row < height; row++ ) {
			for ( int col = 0; col < width; col++ ) {
				idx = row * width + col;
				r = ( pixels[idx] >> 16) & 0xFF;
				g = ( pixels[idx] >> 8 ) & 0xFF;
				b = ( pixels[idx] >> 0 ) & 0xFF;
				
				//count the rgb part
				rp = (int) getRateCount(255, req, r);
				gp = (int) getRateCount(255, geq, g);
				bp = (int) getRateCount(255, beq, b);
				sidx = rp + gp * req + bp * req * geq;
				histogram[sidx] += 1;
			}
		}
		
		float total = width * height;
		for ( int i = 0; i < histogram.length; i++ )
			histogram[i] = histogram[i] / total;
		
		return histogram;
	}
	
	/**
	 * get the similarity of the specified
	 * 		two image .
	 * 
	 * @param	srcImage
	 * @param	dstImage
	 */
	public static double getSimilarity(
			BufferedImage srcImage,
			BufferedImage dstImage ) 
	{
		double similarity = 0.0D;
		float[] histogram1 = HistogramFilter(srcImage, 256, 256, 256);
		float[] histogram2 = HistogramFilter(dstImage, 256, 256, 256);
		
		for ( int i = 0; i < histogram1.length; i++ ) {
			similarity += Math.sqrt(histogram1[i] * histogram2[i]);
		}
		return similarity;
	}
}