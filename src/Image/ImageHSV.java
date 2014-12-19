package Image;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import Config.Config;

public class ImageHSV {
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
	public static double[] RGBtoHSV(double r, double g, double b){

		r/=255;
		g/=255;
		b/=255;
	    double h, s, v;

	    double min, max, delta;

	    min = Math.min(Math.min(r, g), b);
	    max = Math.max(Math.max(r, g), b);

	    // V
	    v = max;

	     delta = max - min;

	    // S
	     if( max != 0 )
	        s = delta / max;
	     else {
	        s = 0;
	        h = -1;
	        return new double[]{h,s,v};
	     }

	     if (delta==0){
	    	 return new double[]{160,s,v};
	     }
	     
	    // H
	     if( r == max )
	        h = ( g - b ) / delta; // between yellow & magenta
	     else if( g == max )
	        h = 2 + ( b - r ) / delta; // between cyan & yellow
	     else
	        h = 4 + ( r - g ) / delta; // between magenta & cyan

	     h *= 60;    // degrees

	    if( h < 0 )
	        h += 360;

	    return new double[]{h,s,v};
	}
	
	public HashMap<String, int[]> HistogramHSV(BufferedImage image)
	{
		int Width = image.getWidth();
		int Height = image.getHeight();
		int[] pixels = new int[Height * Width];
		getRGB(image, 0, 0, Width, Height, pixels);
		
		int[] HS = new int[181];
		int[] V = new int[101];
		int i,j;
		for (i=0;i<180;i++)HS[i]=0;
		for (i=0;i<100;i++)V[i]=0;
		for (i=0;i<Height;i++)
		{
			for(j=0;j<Width;j++)
			{
				int R,G,B;
				int data = pixels[i * Width + j];
				R = (data >> 16) &0xff;
				G = (data >> 8) &0xff;
				B = data & 0xff;
				double[] HSV = RGBtoHSV(R, G, B);
				double h = HSV[0];
				double s = HSV[1];
				double v = HSV[2];
				V[(int)(v*100)]++;
				int hs;
				if (h < 180) hs = (int)((h-90)*s + 90);
				else hs = (int)((270-h)*s + 90);
				HS[hs]++;
			}
		}
	//	System.out.println(HS[0]+" "+HS[90]);
		HashMap<String, int[]> res = new HashMap<>();
		res.put(Config.IMAGE_PARA_KEY[0],HS);
		res.put(Config.IMAGE_PARA_KEY[1], V);
		return res;
	}
}
