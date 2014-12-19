package Image;

import java.awt.image.BufferedImage;

public class ImageEdge {
	/*
	 * 获取图像边缘数据
	 * 1。现将图片转换成灰度图
	 * 2。再讲灰度图通过一维高斯滤波
	 * 3。用canny算子计算图像梯度及方向
	 * 4.边缘检测
	 * 5.返回边缘的梯度幅值统计直方图
	 * 参考资料链接：http://blog.csdn.net/likezhaobin/article/details/6892629
	 * @author 曾华
	 */
	/*图片宽度
	 * 
	 */
	private int Width;
	/*
	 * 图片高度
	 */
	private int Height;
	/*
	 * 原始图片数据
	 */
	private int[] OriginalImageData;
	/*\
	 * 灰度图片数据
	 */
	private int[] GreyImageData;
	/*
	 * //非极大值抑制结果
	 */
	private static int[] N;                 
	/*
	 * 梯度幅值
	 */
	private static int[] M;
	
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
	
	public int[] HistogramEdge( BufferedImage image)
	{
		Width = image.getWidth();
		Height = image.getHeight();
		OriginalImageData = new int[Height * Width];
		getRGB(image, 0, 0, Width, Height, OriginalImageData);
		

		int i, j;
		
		GreyImageData = new int[Height * Width];
		for (i = 0; i < Height; i++)
			for (j = 0; j < Width; j++)
			{
				int data = OriginalImageData[i * Width + j];
				int red = (data >> 16) &0xff;
				int green = (data >> 8) &0xff;
				int blue = data & 0xff;
				GreyImageData[i * Width + j] = (int)(0.114*blue + 0.587*green + 0.299*red);//(int)(0.072169*blue + 0.715160*green + 0.212671*red);
			}
			
		/*
		 * 平滑后图像数据
		 */
		int[] pCanny = new int[Width * Height];
		/*
		 * 高斯滤波
		 */
		double nSigma = 0.4;                            //定义高斯函数的标准差  
		int nWidowSize = 1+2*(int)(3*nSigma);            //定义滤波窗口的大小  
		int nCenter = (nWidowSize)/2;                   //定义滤波窗口中心的索引  
		double[] pdKernal = new double[nWidowSize * nWidowSize];
		double dSum_2 = 0.0;
		///////////////////////二维高斯函数公式////////////////////////////////////      
		////    x*x+y*y                        ///////////////  
		////-1*--------------                ///////////////  
		////1             2*Sigma*Sigma                ///////////////  
		////---------------- e                                   ///////////////  
		////2*pi*Sigma*Sigma                                     ///////////////  
		///////////////////////////////////////////////////////////////////////////
		for(i=0; i<nWidowSize; i++)  
		{  
		    for(j=0; j<nWidowSize; j++)  
		    {  
		        int nDis_x = i-nCenter;  
		        int nDis_y = j-nCenter;  
		        pdKernal[i+j*nWidowSize]=Math.exp(-(1/2)*(nDis_x*nDis_x+nDis_y*nDis_y)  
		            /(nSigma*nSigma))/(2*3.1415926*nSigma*nSigma);  
		        dSum_2 += pdKernal[i+j*nWidowSize];  
		    }  
		}  
		for(i=0; i<nWidowSize; i++)  
		{  
		    for(j=0; j<nWidowSize; j++)                 //进行归一化  
		        {  
		        pdKernal[i+j*nWidowSize] /= dSum_2;  
		    }  
		}  
		
		int x;  
		int y;  
		for(i=0; i<Height; i++)  
		{  
		    for(j=0; j<Width; j++)  
		    {  
		        double dFilter=0.0;  
		        double dSum = 0.0;  
		        for(x=(-nCenter); x<=nCenter; x++)                     //行  
		        {  
		                        for(y=(-nCenter); y<=nCenter; y++)             //列  
		            {  
		                if( (j+x)>=0 && (j+x)<Width && (i+y)>=0 && (i+y)<Height) //判断边缘  
		                {  
		                    dFilter += (double)GreyImageData [(i+y)*Width + (j+x)] * pdKernal[(y+nCenter)*nWidowSize+(x+nCenter)];  
		                    dSum += pdKernal[(y+nCenter)*nWidowSize+(x+nCenter)];  
		                }  
		            }  
		        }  
		        pCanny[i*Width+j] = (int)(dFilter/dSum);  
		    }  
		}  
		/*
		 * 图像增强
		 * Canny算子
		 * P[i,j]=(S[i,j+1]-S[i,j]+S[i+1,j+1]-S[i+1,j])/2
		 * Q[i,j]=(S[i,j]-S[i+1,j]+S[i,j+1]-S[i+1,j+1])/2
		 */
		double[] P = new double [Height * Width];
		double[] Q = new double [Height * Width];
		M = new int [Height * Width];
		double[] Theta = new double[Height * Width];
		//计算x,y方向的偏导数  
		for(i=0; i<(Height-1); i++)  
		{  
		        for(j=0; j<(Width-1); j++)  
		        {  
		              P[i*Width+j] = (double)(pCanny[i*Width + Math.min(j+1, Width-1)] - pCanny[i*Width+j] + pCanny[Math.min(i+1, Height-1)*Width+Math.min(j+1, Width-1)] - pCanny[Math.min(i+1, Height-1)*Width+j])/2;  
		              Q[i*Width+j] = (double)(pCanny[i*Width+j] - pCanny[Math.min(i+1, Height-1)*Width+j] + pCanny[i*Width+Math.min(j+1, Width-1)] - pCanny[Math.min(i+1, Height-1)*Width+Math.min(j+1, Width-1)])/2;  
		    }  
		}  
		for(i=0; i<Height; i++)  
		{  
		        for(j=0; j<Width; j++)  
		        {  
		              M[i*Width+j] = (int)(Math.sqrt(P[i*Width+j]*P[i*Width+j] + Q[i*Width+j]*Q[i*Width+j])+0.5);  
		              Theta[i*Width+j] = Math.atan2(Q[i*Width+j], P[i*Width+j]) * 57.3;  
		              if(Theta[i*Width+j] < 0)  
		                    Theta[i*Width+j] += 360;              //将这个角度转换到0~360范围  
		    }  
		}
		N = new int[Width*Height];                       //非极大值抑制结果  
		double g1=0, g2=0, g3=0, g4=0;                            //用于进行插值，得到亚像素点坐标值  
		double dTmp1=0.0, dTmp2=0.0;                           //保存两个亚像素点插值得到的灰度数据  
		double dWeight=0.0;                                    //插值的权重 
		
		for(i=0; i<Width; i++)  
		{  
		        N[i] = 0;  
		        N[(Height-1)*Width+i] = 0;  
		}  
		for(j=0; j<Height; j++)  
		{  
		        N[j*Width] = 0;  
		        N[j*Width+(Width-1)] = 0;  
		} 
		
		for(i=1; i<(Width-1); i++)  
		{  
		    for(j=1; j<(Height-1); j++)  
		    {  
		        int nPointIdx = i+j*Width;       //当前点在图像数组中的索引值  
		        if(M[nPointIdx] == 0)  
		            N[nPointIdx] = 0;         //如果当前梯度幅值为0，则不是局部最大对该点赋为0  
		        else  
		        {  
		        ////////首先判断属于那种情况，然后根据情况插值///////  
		        ////////////////////第一种情况///////////////////////  
		        /////////       g1  g2                  /////////////  
		        /////////           C                   /////////////  
		        /////////           g3  g4              /////////////  
		        /////////////////////////////////////////////////////  
		        if( ((Theta[nPointIdx]>=90)&&(Theta[nPointIdx]<135)) ||   
		                ((Theta[nPointIdx]>=270)&&(Theta[nPointIdx]<315)))  
		            {  
		                //////根据斜率和四个中间值进行插值求解  
		                g1 = M[nPointIdx-Width-1];  
		                g2 = M[nPointIdx-Width];  
		                g3 = M[nPointIdx+Width];  
		                g4 = M[nPointIdx+Width+1];  
		                dWeight = Math.abs(P[nPointIdx])/Math.abs(Q[nPointIdx]);   //反正切  
		                dTmp1 = g1*dWeight+g2*(1-dWeight);  
		                dTmp2 = g4*dWeight+g3*(1-dWeight);  
		            }  
		        ////////////////////第二种情况///////////////////////  
		        /////////       g1                      /////////////  
		        /////////       g2  C   g3              /////////////  
		        /////////               g4              /////////////  
		        /////////////////////////////////////////////////////  
		            else if( ((Theta[nPointIdx]>=135)&&(Theta[nPointIdx]<180)) ||   
		                ((Theta[nPointIdx]>=315)&&(Theta[nPointIdx]<360)))  
		            {  
		                g1 = M[nPointIdx-Width-1];  
		                g2 = M[nPointIdx-1];  
		                g3 = M[nPointIdx+1];  
		                g4 = M[nPointIdx+Width+1];  
		                dWeight = Math.abs(Q[nPointIdx])/Math.abs(P[nPointIdx]);   //正切  
		                dTmp1 = g2*dWeight+g1*(1-dWeight);  
		                dTmp2 = g4*dWeight+g3*(1-dWeight);  
		            }  
		        ////////////////////第三种情况///////////////////////  
		        /////////           g1  g2              /////////////  
		        /////////           C                   /////////////  
		        /////////       g4  g3                  /////////////  
		        /////////////////////////////////////////////////////  
		            else if( ((Theta[nPointIdx]>=45)&&(Theta[nPointIdx]<90)) ||   
		                ((Theta[nPointIdx]>=225)&&(Theta[nPointIdx]<270)))  
		            {  
		                g1 = M[nPointIdx-Width];  
		                g2 = M[nPointIdx-Width+1];  
		                g3 = M[nPointIdx+Width];  
		                g4 = M[nPointIdx+Width-1];  
		                dWeight = Math.abs(P[nPointIdx])/Math.abs(Q[nPointIdx]);   //反正切  
		                dTmp1 = g2*dWeight+g1*(1-dWeight);  
		                dTmp2 = g3*dWeight+g4*(1-dWeight);  
		            }  
		            ////////////////////第四种情况///////////////////////  
		            /////////               g1              /////////////  
		            /////////       g4  C   g2              /////////////  
		            /////////       g3                      /////////////  
		            /////////////////////////////////////////////////////  
		            else if( ((Theta[nPointIdx]>=0)&&(Theta[nPointIdx]<45)) ||   
		                ((Theta[nPointIdx]>=180)&&(Theta[nPointIdx]<225)))  
		            {  
		                g1 = M[nPointIdx-Width+1];  
		                g2 = M[nPointIdx+1];  
		                g3 = M[nPointIdx+Width-1];  
		                g4 = M[nPointIdx-1];  
		                dWeight = Math.abs(Q[nPointIdx])/Math.abs(P[nPointIdx]);   //正切  
		                dTmp1 = g1*dWeight+g2*(1-dWeight);  
		                dTmp2 = g3*dWeight+g4*(1-dWeight);  
		            }  
		        }         
		        //////////进行局部最大值判断，并写入检测结果////////////////  
		        if((M[nPointIdx]>=dTmp1) && (M[nPointIdx]>=dTmp2))  
		            N[nPointIdx] = 128;  
		        else  
		            N[nPointIdx] = 0;  
		        }  
		}  
		
		int[] HistCounter = new int[1024];
		int pEdgeNum;             //可能边界数  
		int MaxMag = 0;           //最大梯度数  
		int HighCount;  
		for(i=0;i<1024;i++)  
			HistCounter[i] = 0;  
		for(i=0; i<Height; i++)  
		{  
			for(j=0; j<Width; j++)  
	        	{  
					if(N[i*Width+j]!=0)  
						HistCounter[M[i*Width+j]]++;  
	        	}  
		}  
		pEdgeNum = HistCounter[0];  
		MaxMag = 0;                    //获取最大的梯度值        
		for(i=1; i<1024; i++)           //统计经过“非最大值抑制”后有多少像素  
		{  
		    if(HistCounter[i] != 0)       //梯度为0的点是不可能为边界点的  
		    {  
		        MaxMag = i;  
		    }     
		    pEdgeNum += HistCounter[i]; 
		}  
		double  dRatHigh = 0.79;  
		double  dThrHigh;  
		//double  dThrLow;  
		//double  dRatLow = 0.5;  
		HighCount = (int)(dRatHigh * pEdgeNum + 0.5);  
		j=1;  
		int counter = HistCounter[1];  
		while((j<(MaxMag-1)) && (counter < HighCount))  
		{  
		       j++;  
		       counter += HistCounter[j];  
		}  
		dThrHigh = j;                                   //高阈值  
		//dThrLow = (int)((dThrHigh) * dRatLow + 0.5);    //低阈值
		
		for(i=0; i<Height; i++)  
		{  
		    for(j=0; j<Width; j++)  
		    {  
		        if((N[i*Width+j]==128) && (M[i*Width+j] >= dThrHigh))  
		        {  
		            N[i*Width+j] = 255;  
		            //TraceEdge(Height, Width, i, j, dThrLow);  
		        }  
		    }  
		}  
		
		int[] histogramEdge = new int[401];
		for (i=0; i<400;i++) histogramEdge[i] = 0;
		for(i=0; i<Height; i++)  
		{  
		    for(j=0; j<Width; j++)  
		    {  
		        if((N[i*Width+j]==255))  
		        {  
		            histogramEdge[M[i*Width+j]]++;
		        }
		    }  
		}  
		return histogramEdge;
	}//
}
