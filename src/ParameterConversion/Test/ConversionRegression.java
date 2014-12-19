package ParameterConversion.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import Composition.Hongyu.Essential.Constant;
import Config.Config;
import File.Picture;
import Generate.ParameterConversion;
import Image.ImageHSV;
import MIDI.Music;
import ParameterConversion.Test.MultiRegression;

public class ConversionRegression implements ParameterConversion {

	public static final int PARA_UP_SIZE = 50;
	
	public static final int PARA_TONE_NUM = 10;
	
	public static final int PARA_SPEED_NUM = 12;

	public static final int TRAINING_SIZE = 200;

	public static final String TRAINING_RESULT = "./TrainConversionRes";
	
	public double[][] gb_tone_trainingArr = new double[PARA_UP_SIZE][TRAINING_SIZE];
	
	public double[][] gb_speed_trainingArr = new double[PARA_UP_SIZE][TRAINING_SIZE];
	
	public double[] gb_tone_yArr = new double[TRAINING_SIZE];
	
	public double[] gb_speed_yArr = new double[TRAINING_SIZE];
	
	
	public int gb_trainingSize = 0;
	
	@Override
	public void train(Music music, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		System.out.println("train");
	}

	@Override
	public void train(Picture picture, HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		System.out.println("train picture with regression");
		//提取图像参数
		System.out.println(picture);
		ImageHSV texture = new ImageHSV();
		HashMap<String, int[]> parameterHashMap = texture.HistogramHSV(picture.getImage());
		System.out.println("para:"+parameter);
		//多元线性回归训练，使结果趋近于parameter
		//训练第一个参数
		//输入参数个数： m = 20
		
		int[] coolScale = parameterHashMap.get(Config.IMAGE_PARA_KEY[0]);
		int[] brightScale = parameterHashMap.get(Config.IMAGE_PARA_KEY[1]);
		int[] trainingArr = new int[PARA_UP_SIZE+1];
		int	  tmpSize = 0;
		for(int i = 0 ; i < PARA_TONE_NUM; i++){
			trainingArr[i] = 0;
		}
		//音调 0 - 100, 由亮度决定
		tmpSize = 100 / PARA_TONE_NUM;
		for (int i = 0; i < 100; i++) {
			trainingArr[i/tmpSize] += brightScale[i];
		}
		for (int i = 0; i < PARA_TONE_NUM; i++){
			if(trainingArr[i] != 0)
				gb_tone_trainingArr[i][gb_trainingSize] = trainingArr[i];
			else {
				gb_tone_trainingArr[i][gb_trainingSize] = MultiRegression.genNormal(1, 0.2);
			}
		}
		gb_tone_yArr[gb_trainingSize] = parameter.get(Constant.PITCH_STRING);

		//节奏 0 - 180， 由冷暖决定
		tmpSize = 0;
		for(int i = 0 ; i < PARA_SPEED_NUM; i++){
			trainingArr[i] = 0;
		}
		tmpSize = 180 / PARA_SPEED_NUM;
		for (int i = 0; i < 180; i++) {
			trainingArr[i/tmpSize] += coolScale[i];
		}
		for (int i = 0; i < PARA_SPEED_NUM; i++){
			if(trainingArr[i] != 0)
				gb_speed_trainingArr[i][gb_trainingSize] = trainingArr[i];
			else {
				gb_speed_trainingArr[i][gb_trainingSize] = MultiRegression.genNormal(1, 0.2);
			}
		}
		gb_speed_yArr[gb_trainingSize] = parameter.get(Constant.SPEED_STRING);
		gb_trainingSize++;
	}

	@Override
	public void tidyPicture() {
		// TODO Auto-generated method stub
		System.out.println("tidy");
		double[] a = new double[PARA_UP_SIZE+1];
		double[] dt = new double[4];
		double[] v = new double[PARA_UP_SIZE+1];
		int paraCount = 0;
		int mSize = 0;
		/*
		double[][] x = { { 1.1, 1.0, 1.2, 1.1, 0.9 },
				{ 2.0, 2.0, 1.8, 1.9, 2.1 }, { 3.2, 3.2, 3.0, 2.9, 2.9 },
				{ 4.2, 4.2, 4.0, 4.9, 4.9 }, { 5.2, 5.2, 5.0, 5.9, 5.9 },
				{ 6.2, 6.2, 6.0, 6.9, 6.9 }, { 7.2, 7.2, 7.0, 7.9, 7.9 },
				{ 8.2, 8.2, 8.0, 8.9, 8.9 }, { 9.2, 9.2, 9.0, 9.9, 9.9 }};
		double[] y = { 10.1, 10.2, 10.0, 10.1, 10.0 };
		MultiRegression.sqtRegression(x, y, 6, 4, a, dt, v);
		*/
		//System.out.println(a);
		//将训练参数存储于obj文件中
		try {
			ObjectOutputStream objectOutputStream;


			//音调
			paraCount++;
			mSize = PARA_TONE_NUM;
			MultiRegression.sqtRegression(gb_tone_trainingArr, gb_tone_yArr, mSize, gb_trainingSize, a, dt, v);
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(TRAINING_RESULT+paraCount+".obj"));
			objectOutputStream.writeObject(a);
			objectOutputStream.close();
			//节奏
			paraCount++;
			mSize = PARA_SPEED_NUM;
			MultiRegression.sqtRegression(gb_speed_trainingArr, gb_speed_yArr, mSize, gb_trainingSize, a, dt, v);
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(TRAINING_RESULT+paraCount+".obj"));
			objectOutputStream.writeObject(a);
			objectOutputStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tidyMusic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, Integer> convert(HashMap<String, int[]> parameter) {
		double[] a_tone = new double[PARA_TONE_NUM+1];
		double[] a_speed = new double[PARA_SPEED_NUM+1];
		ObjectInputStream objectInputStream;
		try {

			//获取tone的训练参数
			objectInputStream = new ObjectInputStream(new FileInputStream(TRAINING_RESULT+"1.obj"));
			a_tone = (double[]) objectInputStream.readObject();
			objectInputStream.close();
			//获取speed的训练参数
			objectInputStream = new ObjectInputStream(new FileInputStream(TRAINING_RESULT+"2.obj"));
			a_speed = (double[]) objectInputStream.readObject();
			objectInputStream.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取图像参数
		int[][] imageParameter = new int[Config.IMAGE_PARAMETER_NUM][Config.IMAGE_PARA_LENGTH];
		int imageLength = Config.IMAGE_PARA_KEY.length;
		for(int i = 0; i < imageLength; ++i)
		{
			 imageParameter[i] = parameter.get(Config.IMAGE_PARA_KEY[i]);
		}
		
		//转换成音乐的参数
		//HashMap<String, Integer> returnParameter = new HashMap<>();
		int[] coolScale=imageParameter[0];
		int[] brightScale=imageParameter[1];
		int[] softScale=imageParameter[2];
		int[] para_tone = new int[PARA_TONE_NUM+1];
		int[] para_speed = new int[PARA_SPEED_NUM+1];
		int tone = 0, rhythm = 0;
		double change = 0;
		//整合参数
		int tmpSize= 100/PARA_TONE_NUM;
		for(int i = 0 ; i < 100; i++)
		{
			para_tone[i/tmpSize] += brightScale[i];
		}
		tmpSize= 180/PARA_SPEED_NUM;
		for(int i = 0 ; i < 180; i++)
		{
			para_speed[i/tmpSize] += coolScale[i];
		}
		//估计tone参数,与tone, bright有关
		for(int i = 0 ; i < PARA_TONE_NUM; i++){
			tone += para_tone[i]*a_tone[i];
		}
		tone += a_tone[PARA_TONE_NUM];
		if(tone < 0 )
			tone = -tone;
		if(tone > 100)
			tone = 50;
		if(tone < 50 && tone > 30)
			tone = (int)(tone-1.2*Math.sqrt(25-Math.abs((double)tone-25)));
		if(tone < 70 && tone > 50)
			tone = (int)(tone+1.2*Math.sqrt(25-Math.abs((double)tone-75)));
		if(tone < 20)
			tone = 20 + tone/2;
		//估计bright参数,与tone, bright有关
		for(int i = 0 ; i < PARA_SPEED_NUM; i++){
			rhythm += para_speed[i]*a_speed[i];
		}
		rhythm += a_speed[PARA_SPEED_NUM];
		if(rhythm < 0 )
			rhythm = -rhythm;
		if(rhythm > 100)
			rhythm = 50;
		if(rhythm < 50 && rhythm > 30)
			rhythm = (int)(rhythm-1.2*Math.sqrt(25-Math.abs((double)rhythm-25)));
		if(rhythm < 70 && rhythm > 50)
			rhythm = (int)(rhythm+1.2*Math.sqrt(25-Math.abs((double)rhythm-75)));
		if(rhythm < 20)
			rhythm = 20 + rhythm/2;
		//估计soft参数,与tone, bright, soft都有关
		
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
		if (change < 0)
			change = 0;
		if (change > 100)
			change = 50 + Math.random()%10;
		if (change <= 50)
			change = change-1.2*Math.sqrt(25-Math.abs((double)change-25));
		else
			change = change+1.2*Math.sqrt(25-Math.abs((double)change-75));
		
		// 尽量降低过大或过小的数值
		if (change <= 36)
			change = Math.sqrt(change) * 8 + 3;
		if (change >= 64)
			change = 100 - Math.sqrt(100 - change) * 6 - 3;
		if (change <= 25)
			change = Math.sqrt(change) * 6 + 5;
		if (change >= 75)
			change = 100 - Math.sqrt(100 - change) * 6 - 5;
		if (change < 20)
			change = 10 + change/2;
		//显示在界面上
		HashMap<String, Integer> retParameter=new HashMap<>();
		int newTone = (tone + tone + rhythm) / 3;
		int newRhythm = (rhythm * 2 + (rhythm + tone) / 2) / 3;
		if (newRhythm < 50 && newTone > 50) {
			double pos = (double)(newTone - 50) / (double)(50 - newRhythm);
			pos = pos * pos / 4;
			int mid = (int) (newRhythm + (newTone - newRhythm) * (pos) / (1 + pos));
			newRhythm = (newRhythm + mid) / 2;
			newTone = (newTone + mid) / 2;
		}
		retParameter.put(Config.MUSIC_PARA_KEY[0], newTone);
		retParameter.put(Config.MUSIC_PARA_KEY[1], newRhythm);
		retParameter.put(Config.MUSIC_PARA_KEY[2], (int)change);
		
		// TODO Auto-generated method stub
		return retParameter;
	}

}
