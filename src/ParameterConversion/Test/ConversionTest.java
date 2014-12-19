package ParameterConversion.Test;

import java.util.HashMap;

import Config.Config;
import File.Picture;
import Generate.ParameterConversion;
import MIDI.Music;

public class ConversionTest implements ParameterConversion {

	@Override
	public void train(Music music, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		System.out.println("parameter convertion train");
		System.out.println(music);
		System.out.println(parameter);
	}

	@Override
	public void train(Picture picture, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		System.out.println("parameter convertion train");
		System.out.println(picture);
		System.out.println(parameter);
	}
	
	@Override
	public void tidyPicture() {
		// TODO Auto-generated method stub
		System.out.println("parameter convertion tidy picture");
	}

	@Override
	public void tidyMusic() {
		// TODO Auto-generated method stub
		System.out.println("parameter convertion tidy music");
	}
	
	@Override
	public HashMap<String, Integer> convert(HashMap<String, int[]> parameter) {
		//获取图像参数
		int[][] imageParameter = new int[Config.IMAGE_PARAMETER_NUM][Config.IMAGE_PARA_LENGTH];
		int imageLength = Config.IMAGE_PARA_KEY.length;
		for(int i = 0; i < imageLength; ++i)
		{
			 imageParameter[i] = parameter.get(Config.IMAGE_PARA_KEY[i]);
		}
		
		//转换成音乐的参数
		HashMap<String, Integer> returnParameter=new HashMap<>();
		int[] coolScale=imageParameter[0];
		int[] VScale=imageParameter[1];
		int[] softScale=imageParameter[2];
		int tone,rhythm;
		double change;
		
		//音调高低
		int t=0,tp=0;
		for (int i=0;i<=180;i++){
			t+=(i-90)*coolScale[i];
			tp+=coolScale[i];
		}
		tone=(int)(100-((double)t/tp+90)/180*100);
		if (tone<0)
			tone=0;
		if (tone>100)
			tone=100;
		if (tone<=50)
			tone=(int)(tone-1.2*Math.sqrt(25-Math.abs((double)tone-25)));
		else
			tone=(int)(tone+1.2*Math.sqrt(25-Math.abs((double)tone-75)));
		if (tone<0)
			tone=0;
		if (tone>100)
			tone=100;
		
		//节奏快慢
		t=0;
		for (int i=0;i<=100;i++)
			t+=(i-50)*VScale[i];
		rhythm=(int)((double)t/tp+50);
		if (rhythm<=50)
			rhythm=(int)(rhythm-1.2*Math.sqrt(25-Math.abs((double)rhythm-25)));
		else
			rhythm=(int)(rhythm+1.2*Math.sqrt(25-Math.abs((double)rhythm-75)));
		if (rhythm<0)
			rhythm=0;
		if (rhythm>100)
			rhythm=100;
		
		
		//变化多少
		t=0;
		tp=0;
		int begin = 0, end = 400;
		while (begin <= 400 && softScale[begin] == 0)
			begin++;
		while (end >= 0 && softScale[end]==0)
			end--;
		double total = 0;
		for (int j = 0; j <= 400; j++) {
			total += Math.sqrt(softScale[j]) * j;
		}
		change = total / 600 / (end - begin + 1) * (begin + end);
		if (change <= 50)
			change = change-1.2*Math.sqrt(25-Math.abs((double)change-25));
		else
			change = change+1.2*Math.sqrt(25-Math.abs((double)change-75));
		if (change < 0)
			change = 0;
		if (change > 100)
			change = 100;
		// 尽量降低过大或过小的数值
		if (change <= 36)
			change = Math.sqrt(change) * 6;
		if (change >= 64)
			change = 100 - Math.sqrt(100 - change) * 6;
		if (change <= 25)
			change = Math.sqrt(change) * 5;
		if (change >= 75)
			change = 100 - Math.sqrt(100 - change) * 5;
		//相互关联
		int newTone = (tone + (tone + rhythm) / 2) / 2;
		int newRhythm = (rhythm + (rhythm + tone) / 2) / 2;
		if (newRhythm < 50 && newTone > 50) {
			double pos = (double)(newTone - 50) / (double)(50 - newRhythm);
			pos = pos * pos;
			int mid = (int) (newRhythm + (newTone - newRhythm) * (pos) / (1 + pos));
			newRhythm = (newRhythm + mid) / 2;
			newTone = (newTone + mid) / 2;
		}
		returnParameter.put(Config.MUSIC_PARA_KEY[0], newTone);
		returnParameter.put(Config.MUSIC_PARA_KEY[1], newRhythm);
		returnParameter.put(Config.MUSIC_PARA_KEY[2], (int)change);
		
		// TODO Auto-generated method stub
		return returnParameter;
	}

}
