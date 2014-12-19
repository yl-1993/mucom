package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Config.Config;
import File.MIDIWriter;
import File.Picture;
import Generate.Composition;
import Generate.ParameterConversion;
import Generate.ParameterGenerate;
import MIDI.Music;
import MIDI.Player;

/**
 * 正式使用时的Form
 */
public class Form3 {
	
	/**
	 * 窗口宽
	 */
	public static final int FRAME_WIDTH = 1068;
	
	/**
	 * 窗口高
	 */
	public static final int FRAME_HEIGHT = 530;
	
	/**
	 * 左基准
	 */
	public static final int LEFT_BASIS = 24;
	
	/**
	 * 右基准
	 */
	public static final int RIGHT_BASIS = 284;
	
	/**
	 * 上基准
	 */
	public static final int TOP_BASIS = 28;
	
	/**
	 * 底基准
	 */
	public static final int BOTTOM_BASIS = 28;
	
	/**
	 * 水平间距
	 */
	public static final int HORIZONTAL_INTERVAL = 116;
	
	/**
	 * 垂直间距
	 */	
	public static final int VERTICAL_INTERVAL = 50;
	
	/**
	 * 窗体的引用
	 */
	private JFrame frame;
	
	/**
	 * 图像Panel的引用
	 */
	private PicturePanel panel;
	
	/**
	 * 选择图像按钮
	 */
	private JButton selectPictrueButton;
	
	/**
	 * 生成音乐按钮
	 */
	private JButton generateMusicButton;
	
	/**
	 * 开始播放按钮
	 */
	private JButton startPlayButton;
	
	/**
	 * 停止播放按钮
	 */
	private JButton stopPlayButton;
	
	/**
	 * 颜色直方图的panel
	 */
	private HistogramPanel histogramPanel;
	
	/**
	 * 导出按钮
	 */
	private JButton exportButton;
	
	/**
	 * 使用帮助
	 */
	private JButton helpButton;
	
	/**
	 * 生成的音乐的引用
	 */
	private Music music;
	
	/**
	 * 当前图片的引用
	 */
	private Picture picture;
	
	/**
	 * 默认构造函数，初始化每一个界面元素
	 */
	public Form3() {
		// TODO Auto-generated constructor stub
		try {
			frame = new JFrame();
			//设定程序关闭方式为点击窗口关闭按钮关闭
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//设定窗体大小
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			//居中窗体
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
			//设定窗口标题
			frame.setTitle("Musical Creation");
			//设定布局方式为手动布局
			frame.setLayout(null);
			//Panel控件
			panel = new PicturePanel();			
			frame.getContentPane().add(panel);
			panel.setBounds(24, 28, 436, 436);
			//直方图控件
			histogramPanel = new HistogramPanel();
			frame.getContentPane().add(histogramPanel);
			histogramPanel.setBounds(480,235,230,230);
			//参数滑动条
			String[] argNames = new String[] {"音调高低", "节奏快慢", "变化多少", "音乐时长"};
			int[] mins = new int[] {0, 0, 0, 30};
			int[] maxs = new int[] {100, 100, 100, 150};
			SliderPanel[] sliderPanels = new SliderPanel[argNames.length];
			for (int i = 0; i < sliderPanels.length; i++) {
				sliderPanels[i] = new SliderPanel(argNames[i], mins[i], maxs[i]);
				frame.getContentPane().add(sliderPanels[i]);
				sliderPanels[i].setBounds(484, 28 + i * VERTICAL_INTERVAL, 280, 20);
			}
			//生成图像参数滑动条
			String[] imgArgNames = new String[] {"纹理参数1", "纹理参数2", "纹理参数3", "纹理参数4"};
			int[] imgMins = new int[] {0, 0, 0, 0};
			int[] imgMaxs = new int[] {1, 1, 1, 1};
			SliderPanel[] imgSliderPanels = new SliderPanel[argNames.length];
			for (int i = 0; i < imgSliderPanels.length; i++) {
				imgSliderPanels[i] = new SliderPanel(imgArgNames[i], imgMins[i], imgMaxs[i]);
				frame.getContentPane().add(sliderPanels[i]);
				imgSliderPanels[i].setBounds(784, 28 + i * VERTICAL_INTERVAL, 280, 20);
			}
			//选择图像按钮
			selectPictrueButton = new JButton("选择图像", null);
			selectPictrueButton.addActionListener(new SelectPictrueActionListener());
			frame.getContentPane().add(selectPictrueButton);
			selectPictrueButton.setBounds(frame.getWidth() - RIGHT_BASIS, 28 + (sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			//生成音乐按钮
			generateMusicButton = new JButton("生成音乐", null);
			generateMusicButton.addActionListener(new GenerateMusicActionListener());
			frame.getContentPane().add(generateMusicButton);
			generateMusicButton.setBounds(frame.getWidth() - RIGHT_BASIS + HORIZONTAL_INTERVAL, 28 + (sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			generateMusicButton.setEnabled(true);
			//开始播放按钮
			startPlayButton = new JButton("开始播放", null);
			startPlayButton.addActionListener(new StartPlayActionListener());
			frame.getContentPane().add(startPlayButton);
			startPlayButton.setBounds(frame.getWidth() - RIGHT_BASIS, 28 + (1 + sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			startPlayButton.setEnabled(false);
			//停止播放按钮
			stopPlayButton = new JButton("停止播放", null);
			stopPlayButton.addActionListener(new StopPlayActionListener());
			frame.getContentPane().add(stopPlayButton);
			stopPlayButton.setBounds(frame.getWidth() - RIGHT_BASIS + HORIZONTAL_INTERVAL, 28 + (1 + sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			stopPlayButton.setEnabled(false);
			//导出按钮
			exportButton = new JButton("导出音乐", null);
			exportButton.addActionListener(new ExportActionListener());
			frame.getContentPane().add(exportButton);
			exportButton.setBounds(frame.getWidth() - RIGHT_BASIS, 28 + (2 + sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			exportButton.setEnabled(false);
			//帮助按钮
			helpButton = new JButton("使用帮助", null);
			helpButton.addActionListener(new HelpActionListener());
			frame.getContentPane().add(helpButton);
			helpButton.setBounds(frame.getWidth() - RIGHT_BASIS + HORIZONTAL_INTERVAL, 28 + (2 + sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			helpButton.setEnabled(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void show() {
		//设置可见性
		frame.setVisible(true);
	}
	
	/**
	 * 从UI上获取显示出来的参数
	 * @return 获得的参数
	 */
	public HashMap<String, Integer> getArgs() {
		HashMap<String, Integer> args = new HashMap<String, Integer>();
		for (Component component : frame.getContentPane().getComponents()) {
			//加入滑动条的参数
			if (component.toString().contains("SliderPanel")) {
				args.put(((SliderPanel)component).getName(), ((SliderPanel)component).getValue());
			}
		}
		return args;
	}
	
	/**
	 * 设定参数到界面上
	 * @param args 设定的参数
	 */
	public void setArgs(HashMap<String, Integer> args) {
		for (Component component : frame.getContentPane().getComponents()) {
			if (component.toString().contains("SliderPanel")) {
				SliderPanel panel = (SliderPanel)component;
				if (args.containsKey(panel.getName())) {
					panel.slider.setValue(args.get(panel.getName()));
					panel.valueLabel.setText(panel.getValueString(args.get(panel.getName())));
				}
			}
		}
	}
	
	/**
	 * 选择图像按钮的事件处理函数
	 */
	public class SelectPictrueActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("图像文件(*.jpg, *.bmp, *.png)", "jpg", "bmp", "png"));
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			int result = chooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				//获得路径
				String path = chooser.getSelectedFile().getPath();
				//获取图片
				picture = new Picture(path);
				//缩放图片
				double percentage = Math.min((double)(panel.getWidth()) / picture.getImage().getWidth(), (double)(panel.getHeight()) / picture.getImage().getHeight());
				picture.resize(percentage);
				//显示图片
				panel.setPicture(picture);
				//处理按钮
				generateMusicButton.setEnabled(true);
				startPlayButton.setEnabled(false);
				stopPlayButton.setEnabled(false);
				exportButton.setEnabled(false);
				Player.stop();
				//生成图片参数
				ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				//生成图像颜色直方图
				histogramPanel.getParameter(picture);
				histogramPanel.paintComponent(histogramPanel.getGraphics());
				histogramPanel.add(new JLabel("Gray Histogram",JLabel.CENTER));
				//将图片参数显示在界面上
				HashMap<String, Integer> imageParameterHashMap = new HashMap<>();
				//for(int i=0; i < 4; i++)
				//	imageParameterHashMap.put(argNames[i], 0);
				setArgs(imageParameterHashMap);
				//转换成音乐参数
				HashMap<String, Integer> musicParameterHashMap = parameterConversion.convert(parameterGenerate.generate(picture));				
				setArgs(musicParameterHashMap);
			}
		}
	}
	
	/**
	 * 生成音乐按钮的事件处理函数
	 */
	public class GenerateMusicActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//生成音乐
			Composition composition = Config.COMPOSITION_MAP.get(Config.CURRENT_COMPOSITION_STRING);
			music = composition.generate(getArgs());
			//处理按钮
			startPlayButton.setEnabled(true);
			stopPlayButton.setEnabled(false);
			exportButton.setEnabled(true);
			Player.stop();
		}
	}
	
	/**
	 * 播放音乐按钮的事件处理函数
	 */
	public class StartPlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			startPlayButton.setEnabled(false);
			stopPlayButton.setEnabled(true);
			Player.play(music);
		}
	}
	
	/**
	 * 停止播放按钮的事件处理函数
	 */
	public class StopPlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			startPlayButton.setEnabled(true);
			stopPlayButton.setEnabled(false);
			Player.stop();
		}
	}
	
	/**
	 * 导出按钮响应事件
	 */
	public class ExportActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("MIDI音乐文件(*.mid)", "mid"));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			int result = chooser.showOpenDialog(frame);
			//判断是否保存
			if (result == JFileChooser.APPROVE_OPTION) {
				//获取路径
				String path = chooser.getSelectedFile().getPath();
				if (!path.contains(".")) {
					path += ".mid";
				}
				MIDIWriter.write(music, path);
			}
		}
	}
	
	/**
	 * 帮助按钮响应事件
	 */
	public class HelpActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	public class HistogramPanel extends JPanel{

		/**
		 * 序列化ID
		 */
		private static final long serialVersionUID = -8272802488574401188L;

		private int[] para;
		
		public void getParameter(Picture picture){
			para = new int[256];
			for(int i = 0 ; i < 256; i++)
				para[i] = 0;
			para = picture.getHistogram();
		}
		
		private int findMaxValue(int[] intensity) { 
			if (intensity == null)
				return 0;
	        int max = -1;  
	        for(int i=0; i<intensity.length; i++) {  
	            if(max < intensity[i]) {  
	                max = intensity[i];  
	            }  
	        }  
	        return max;  
	    }  
		@Override
		public void paintComponent(Graphics g) {
			//清空图像
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			//画出边框 
			g.setColor(Color.gray);
			g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
			//画出直方图
			g.setColor(Color.red);
			g.drawString("Gray Histogram", 100, -50);
			g.setColor(Color.GREEN);  
	        int max = findMaxValue(para);  
	        float rate = 200.0f/((float)max);  
	        int offset = 2;  
	        int frequency = 0;
	        if (para == null)
	        	return;
	        for(int i=0; i<para.length; i++) {  
	            frequency = (int)(para[i] * rate);  
	            g.drawLine(offset + i, 230, offset + i, 230-frequency);  
	        }  
		}
		
	}
	
	/**
	 * 用来显示图片的Panel
	 */
	public class PicturePanel extends JPanel {
		
		/**
		 * 序列化ID
		 */
		private static final long serialVersionUID = -7312575960417775755L;
		
		/**
		 * 图片
		 */
		private Picture picture;
		
		@Override
		public void paintComponent(Graphics g) {
			//清空图像
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			//画出边框
			g.setColor(Color.gray);
			g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
			//如果图片不为空，则画出图片
			
			if (picture != null) {
				BufferedImage image = picture.getImage();
				g.drawImage(image, (this.getWidth() - image.getWidth()) / 2, (this.getHeight() - image.getHeight()) / 2, this);
			}
		}
		
		/**
		 * 设定JPanel的图像
		 * @param picture
		 */
		public void setPicture(Picture picture) {
			this.picture = picture;
			paintComponent(getGraphics());
		}
	}
	
	/**
	 * 用来显示参数信息的Panel
	 */
	public class SliderPanel extends JPanel implements ChangeListener {

		/**
		 * 序列化ID
		 */
		private static final long serialVersionUID = 5141083245464373171L;

		/**
		 * 名称
		 */
		public JLabel nameLabel;
		
		/**
		 * 滑动条
		 */
		public JSlider slider;
		
		/**
		 * 值
		 */
		public JLabel valueLabel;
		
		/**
		 * 构造函数
		 * @param name 名称
		 * @param minValue 最小值
		 * @param maxValue 最大值
		 * @param initialValue 初始值
		 */
		public SliderPanel(String name, int minValue, int maxValue, int initialValue) {
			// TODO Auto-generated constructor stub
			nameLabel = new JLabel(name);
			slider = new JSlider(minValue, maxValue, initialValue);
			valueLabel = new JLabel(getValueString(initialValue));
			this.add(nameLabel);
			this.add(slider);
			this.add(valueLabel);
			this.setLayout(null);
			nameLabel.setBounds(0, 0, 60, 20);
			slider.setBounds(60, 0, 140, 20);
			valueLabel.setBounds(208, 0, 80, 20);
			slider.addChangeListener(this);
		}
		
		/**
		 * 构造函数
		 * @param name 名称
		 * @param minValue 最小值
		 * @param maxValue 最大值
		 */
		public SliderPanel(String name, int minValue, int maxValue) {
			// TODO Auto-generated constructor stub
			this(name, minValue, maxValue, (minValue + maxValue) / 2);
		}
		
		/**
		 * 根据值获取信息
		 * @param value 值
		 * @return 值的字符串形式
		 */
		public String getValueString(int value) {
			return Integer.toString(value);
		}
		
		/**
		 * 获取滑动条的值
		 * @return 滑动条的值
		 */
		public int getValue() {
			return slider.getValue();
		}
		
		/**
		 * 获取名字
		 * @return 名字
		 */
		public String getName() {
			return nameLabel.getText();
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			valueLabel.setText(getValueString(slider.getValue()));
		}
	}
	
	public class MusicLength extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5203856266711331525L;
		
		/**
		 * 名称
		 */
		public JLabel nameLabel;
		
		/**
		 * 单位
		 */
		public JLabel unit;
		
		/**
		 * 值
		 */
		public JTextField musicTime;
		
		/**
		 * 构造函数
		 * @param name 名称
		 * @param Unit 单位
		 */
		public MusicLength(String name, String Unit) {
			nameLabel = new JLabel(name);
			unit = new JLabel(Unit);
			musicTime = new JTextField();
			this.add(nameLabel);
			this.add(musicTime);
			this.add(unit);
			this.setLayout(null);
			nameLabel.setBounds(0, 0, 60, 20);
			musicTime.setBounds(68, 0, 125, 20);
			unit.setBounds(208, 0, 40, 20);
			musicTime.setText("5.0");
		}
		
		/**
		 * 获取时间
		 */
		public double getTimeLength() {
			return Double.valueOf(musicTime.getText());
		}
	}
}
