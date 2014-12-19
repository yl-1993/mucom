package ParameterGenerate.Test;

import java.util.HashMap;

import Image.ImageEdge;
import Image.ImageHSV;
//import Image.ImageTexture;

//import Image.ImageRGBDistribution;
import File.Picture;
//import Generate.Composition;
import Generate.ParameterGenerate;
import Config.Config;

//import java.awt.Color;  
//import java.awt.Graphics2D;  
//import java.awt.image.BufferedImage; 

public class GenerateTest implements ParameterGenerate {

	@Override
	public void train(Picture picture, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		System.out.println("parameter generate train");
		System.out.println(picture);
		System.out.println(parameter);
	}

	@Override
	public void tidy() {
		// TODO Auto-generated method stub
		System.out.println("parameter generate tidy");
	}
	
	
	@Override
	public HashMap<String, int[]> generate(Picture picture) {
		// TODO Auto-generated method stub
		//提取图像参数
		ImageHSV texture = new ImageHSV();
		HashMap<String, int[]> parameterHashMap = texture.HistogramHSV(picture.getImage());
		ImageEdge edge = new ImageEdge();
		int[] paraEdge = edge.HistogramEdge(picture.getImage());
		//ImageRGBDistribution rgbDistribution = new ImageRGBDistribution();
		//int[] paraRGBDistribution = rgbDistribution.getRGBDistribution(picture.getImage()); 
		parameterHashMap.put(Config.IMAGE_PARA_KEY[2], paraEdge);
		return parameterHashMap;
	}

}
