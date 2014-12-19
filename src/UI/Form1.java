package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Composition.Hongyu.Essential.Constant;
import Config.Config;
import File.MIDIReader;
import File.MIDIWriter;
import File.Picture;
import Generate.Composition;
import Generate.ParameterConversion;
import Generate.ParameterGenerate;
import MIDI.Conversion;
import MIDI.Music;
import MIDI.Player;

public class Form1 {
	
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
	 * 开始训练按钮
	 */
	private JButton pictureTrainingButton;
	
	/**
	 * 下一张图按钮
	 */
	private JButton musicTrainingButton;
	
	/**
	 * 生成的音乐的引用
	 */
	private Music music;
	
	/**
	 * 当前图片的引用
	 */
	private Picture picture;
	
	/**
	 * 训练集中的图像路径
	 */
	private LinkedList<String> picturePath;
	
	/**
	 * 训练集中的音乐路径
	 */
	private LinkedList<String> musicPath;
	
	/**
	 * 默认构造函数，初始化每一个界面元素
	 */
	public Form1() {
		// TODO Auto-generated constructor stub
		try {
			frame = new JFrame();
			//设定程序关闭方式为点击窗口关闭按钮关闭
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//设定窗体大小
			frame.setSize(768, 576);
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
			//选择图像按钮
			selectPictrueButton = new JButton("选择图像", null);
			selectPictrueButton.addActionListener(new SelectPictrueActionListener());
			frame.getContentPane().add(selectPictrueButton);
			selectPictrueButton.setBounds(24, 488, 88, 24);
			//生成音乐按钮
			generateMusicButton = new JButton("生成音乐", null);
			generateMusicButton.addActionListener(new GenerateMusicActionListener());
			frame.getContentPane().add(generateMusicButton);
			generateMusicButton.setBounds(140, 488, 88, 24);
			generateMusicButton.setEnabled(false);
			//开始播放按钮
			startPlayButton = new JButton("开始播放", null);
			startPlayButton.addActionListener(new StartPlayActionListener());
			frame.getContentPane().add(startPlayButton);
			startPlayButton.setBounds(256, 488, 88, 24);
			startPlayButton.setEnabled(false);
			//停止播放按钮
			stopPlayButton = new JButton("停止播放", null);
			stopPlayButton.addActionListener(new StopPlayActionListener());
			frame.getContentPane().add(stopPlayButton);
			stopPlayButton.setBounds(372, 488, 88, 24);
			stopPlayButton.setEnabled(false);
			//参数滑动条
			SliderPanel[] sliderPanels = new SliderPanel[Config.PARAMETER_NUMBER];
			String[] parameterName = new String[] {Constant.PITCH_STRING, Constant.SPEED_STRING, Constant.VARIATION_STRING, "扩展参数", "扩展参数"};
			for (int i = 0; i < sliderPanels.length; i++) {
				sliderPanels[i] = new SliderPanel(parameterName[i], 0, 100);
				frame.getContentPane().add(sliderPanels[i]);
				sliderPanels[i].setBounds(484, 28 + i * (212 - 28) / (sliderPanels.length - 1), 280, 20);
			}
			//主音色
			JLabel instrumentLabel1 = new JLabel("主音色");
			frame.getContentPane().add(instrumentLabel1);
			instrumentLabel1.setBounds(484, 256, 100, 20);
			JComboBox<String> instrumentComboBox1 = new JComboBox<String>();
			frame.getContentPane().add(instrumentComboBox1);
			instrumentComboBox1.setBounds(484, 282, 100, 20);
			//副音色
			JLabel instrumentLabel2 = new JLabel("副音色");
			frame.getContentPane().add(instrumentLabel2);
			instrumentLabel2.setBounds(600, 256, 100, 20);
			JComboBox<String> instrumentComboBox2 = new JComboBox<String>();
			frame.getContentPane().add(instrumentComboBox2);
			instrumentComboBox2.setBounds(600, 282, 100, 20);
			//加入音色
			String[] instrumentList = new String[] {
				"piano","music box","accordian","harmonica","guitar",
				"bass","violin","viola","cello","contrabass",
				"harp","timpani","string","trumpet","trombone",
				"tuba","French horn","sax","oboe","bassoon",
				"clarinet","piccolo","flute","recorder"
			};
			for (String string : instrumentList) {
				instrumentComboBox1.addItem(string);
				instrumentComboBox2.addItem(string);
			}
			//图像训练按钮
			pictureTrainingButton = new JButton("图像训练", null);
			pictureTrainingButton.addActionListener(new PictureTrainingActionListener());
			frame.getContentPane().add(pictureTrainingButton);
			pictureTrainingButton.setBounds(488, 488, 88, 24);
			//音乐训练按钮
			musicTrainingButton = new JButton("音乐训练", null);
			musicTrainingButton.addActionListener(new MusicTrainingActionListener());
			frame.getContentPane().add(musicTrainingButton);
			musicTrainingButton.setBounds(604, 488, 88, 24);
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
	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getArgs() {
		HashMap<String, Integer> args = new HashMap<String, Integer>();
		String instrumentType = "主音色";
		for (Component component : frame.getContentPane().getComponents()) {
			//加入滑动条的参数
			if (component.toString().contains("SliderPanel")) {
				args.put(((SliderPanel)component).getName(), ((SliderPanel)component).getValue());
			}
			//加入主音色和负音色
			if (component.toString().contains("JComboBox")) {
				if (instrumentType == "主音色") {
					args.put("主音色", Conversion.convertNoteInstrument(((JComboBox<String>)component).getSelectedItem().toString()));
					instrumentType = "副音色";
				} else {
					args.put("副音色", Conversion.convertNoteInstrument(((JComboBox<String>)component).getSelectedItem().toString()));
				}
			}
		}
		return args;
	}
	
	/**
	 * 设定参数到界面上，不设定音色
	 * @param args 设定的参数
	 */
	public void setArgs(HashMap<String, Integer> args) {
		for (Component component : frame.getContentPane().getComponents()) {
			if (component.toString().contains("SliderPanel")) {
				SliderPanel panel = (SliderPanel)component;
				panel.slider.setValue(args.get(panel.getName()));
				panel.valueLabel.setText(panel.getValueString(args.get(panel.getName())));
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
				Player.stop();
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
			//打开保存对话框
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
				//生成音乐
				ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				Composition composition = Config.COMPOSITION_MAP.get(Config.CURRENT_COMPOSITION_STRING);
				HashMap<String, Integer> musicParameterHashMap = parameterConversion.convert(parameterGenerate.generate(picture));
				HashMap<String, Integer> guiParameterHashMap = getArgs();
				musicParameterHashMap.put("主音色", guiParameterHashMap.get("主音色"));
				musicParameterHashMap.put("副音色", guiParameterHashMap.get("副音色"));
				music = composition.generate(musicParameterHashMap);
				//保存
				MIDIWriter.write(music, path);
				//处理按钮
				startPlayButton.setEnabled(true);
				stopPlayButton.setEnabled(false);
				Player.stop();
			}
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
	 * 图像训练按钮的事件处理函数
	 */
	public class PictureTrainingActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (pictureTrainingButton.getText() == "图像训练") {
				selectDirectory();
			} else {
				nextPicture();
			}
		}
		
		/**
		 * 设定路径
		 */
		public void selectDirectory() {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				picturePath = new LinkedList<String>();
				for (File file : chooser.getSelectedFile().listFiles()) {
					if (file.getName().contains(".jpg") || file.getName().contains(".bmp") || file.getName().contains(".png")) {
						picturePath.add(file.getAbsolutePath());
					}
				}
				pictureTrainingButton.setText("下一张图");
			}
		}
		
		/**
		 * 下一张图
		 */
		public void nextPicture() {
			if (picturePath.size() > 0) {
				if (picture != null) {
					ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
					ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
					parameterGenerate.train(picture, getArgs());
					parameterConversion.train(picture, getArgs());
				}
				picture = new Picture(picturePath.pop());
				picture.resize(Math.min((double)(panel.getWidth()) / picture.getImage().getWidth(), (double)(panel.getHeight()) / picture.getImage().getHeight()));
				panel.setPicture(picture);
			} else {
				ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				parameterGenerate.train(picture, getArgs());
				parameterConversion.train(picture, getArgs());
				parameterGenerate.tidy();
				parameterConversion.tidyPicture();
				picture = null;
				panel.setPicture(null);
				picturePath = null;
				pictureTrainingButton.setText("图像训练");
			}
		}
	}
	
	/**
	 * 音乐训练按钮的事件处理函数
	 */
	public class MusicTrainingActionListener implements ActionListener {
		
		/**
		 * 刚刚播放的，等待和界面参数一起传入训练函数训练的音乐
		 */
		private Music lastMusic = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (musicTrainingButton.getText() == "音乐训练") {
				selectDirectory();
			} else {
				nextMusic();
			}
		}
		
		/**
		 * 设定路径
		 */
		public void selectDirectory() {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				musicPath = new LinkedList<String>();
				for (File file : chooser.getSelectedFile().listFiles()) {
					if (file.getName().contains(".mid")) {
						musicPath.add(file.getAbsolutePath());
					}
				}
				musicTrainingButton.setText("下一乐曲");
			}
		}
		
		/**
		 * 下一乐曲
		 */
		public void nextMusic() {
			if (musicPath.size() > 0) {
				if (lastMusic != null) {
					ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
					Composition composition = Config.COMPOSITION_MAP.get(Config.CURRENT_COMPOSITION_STRING);
					parameterConversion.train(lastMusic, getArgs());
					composition.train(lastMusic, getArgs());
				}
				Player.stop();
				lastMusic = MIDIReader.read(musicPath.pop());
				Player.play(lastMusic);
			} else {
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				Composition composition = Config.COMPOSITION_MAP.get(Config.CURRENT_COMPOSITION_STRING);
				parameterConversion.train(lastMusic, getArgs());
				composition.train(lastMusic, getArgs());
				parameterConversion.tidyMusic();
				composition.tidy();
				Player.stop();
				lastMusic = null;
				musicPath = null;
				musicTrainingButton.setText("音乐训练");
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
}
