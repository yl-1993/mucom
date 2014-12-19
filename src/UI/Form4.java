package UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.awt.AWTUtilities;

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
public class Form4 {
	
	static Point origin = new Point();  //全局的位置变量，用于表示鼠标在窗口上的位置
	static Point helpOrigin = new Point();
	
	/**
	 * 窗口宽
	 */
	public static final int FRAME_WIDTH = 768;
	
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
	public static final int RIGHT_BASIS = 270;
	
	/**
	 * 上基准
	 */
	public static final int TOP_BASIS = 95;
	
	/**
	 * 底基准
	 */
	public static final int BOTTOM_BASIS = 28;
	
	/**
	 * 水平间距
	 */
	public static final int HORIZONTAL_INTERVAL = 80;
	
	/**
	 * 垂直间距
	 */	
	public static final int VERTICAL_INTERVAL = 60;
	
	public static final int PICTURE_WH = 370;
	
	public static final int PICTURE_X = 50;
	
	public static final int PICTURE_Y = 60;
	
	ImageIcon buttonImage = new ImageIcon("button\\normal\\mini.png");
	
	private final int ICON_WIDTH = buttonImage.getIconWidth();
	
	private final int ICON_HEIGHT = buttonImage.getIconHeight();
	
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
	private IButton selectPictureButton;
	
	/**
	 * 生成音乐按钮
	 */
	private IButton generateMusicButton;
	
	/**
	 * 开始播放按钮
	 */
	private ClickButton startPlayButton;
	
	/**
	 * 停止播放按钮
	 */
	private IButton stopPlayButton;
	
	/**
	 * 导出按钮
	 */
	private IButton exportButton;
	
	/**
	 * 使用帮助
	 */
	private IButton helpButton;
	
	/**
	 * 退出
	 */
	private IButton exitButton;
	
	/**
	 * 最小化
	 */
	private IButton miniButton;
	
	protected static Timer timerClose;
	
	protected static Timer timerStart;
	
	private PlayerSlider playerSlider;
	
	static float value = 1.0f;
	
	/**
	 * 生成的音乐的引用
	 */
	private Music music;
	
	/**
	 * 当前图片的引用
	 */
	private Picture picture;

	/**
	 * 帮助窗体
	 */
	private JFrame helpFrame;
	
	/**
	 * 默认构造函数，初始化每一个界面元素
	 */
	public Form4() {
		// TODO Auto-generated constructor stub
		try {
			frame = new JFrame();
			frame.setUndecorated(true);
			
			//设定程序关闭方式为点击窗口关闭按钮关闭
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			ImageIcon img = new ImageIcon("BG.png");//这是背景图片
			img = new ImageIcon(img.getImage().getScaledInstance(FRAME_WIDTH, FRAME_HEIGHT, Image.SCALE_DEFAULT));
			JLabel imgLabel = new JLabel(img);//将背景图放在标签里。
			imgLabel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//注意这里是关键，将背景标签添加到jframe的LayeredPane面板里。  
			imgLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);//设置背景标签的位置  
			Container cp = frame.getContentPane(); 
			cp.setLayout(new BorderLayout());
			 
			((JPanel)cp).setOpaque(false); //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。   
			
			AWTUtilities.setWindowOpacity(frame, 0.02f);
			timerStart = new Timer(15, new timerStartActionListener());
			timerStart.start();
			//设定窗体大小
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			//居中窗体
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
			//设定窗口标题
			frame.setTitle("Musical Creation");
			JFrame.setDefaultLookAndFeelDecorated(true);
	        //Round Corner  
	        //AWTUtilities.setWindowShape(frame, new RoundRectangle2D.Double(  
	        //  0.0D, 0.0D, frame.getWidth(), frame.getHeight(), 100.0D,  
	        //100.0D));
	        AWTUtilities.setWindowOpaque(frame, false);
	        frame.setVisible(true);
			//设定布局方式为手动布局
			frame.setLayout(null);
			
			//Panel控件
			panel = new PicturePanel();
			frame.getContentPane().add(panel);
			//panel.setBounds(24, 28, PICTURE_WH, PICTURE_WH);
			panel.setOpaque(false);
			
			//参数滑动条
			String[] argNames = new String[] {Config.MUSIC_PARA_KEY[0], 
					Config.MUSIC_PARA_KEY[1], 
					Config.MUSIC_PARA_KEY[2], 
					"音乐时长"};
			int[] mins = new int[] {0, 0, 0, 30};
			int[] maxs = new int[] {100, 100, 100, 150};
			CustomSlider[] customSliders = new CustomSlider[argNames.length];
			for (int i = 0; i < customSliders.length; i++) {
				customSliders[i] = new CustomSlider(argNames[i], mins[i], maxs[i], 140);
				frame.getContentPane().add(customSliders[i]);
				customSliders[i].setOpaque(false);
				customSliders[i].setBounds(frame.getWidth() - RIGHT_BASIS, TOP_BASIS + i * VERTICAL_INTERVAL, 280, 20);
			}
			//选择图像按钮
			selectPictureButton = new IButton("button\\normal\\select.png", "button\\highlight\\select.png", "button\\click\\select.png", "button\\inable\\select.png");
			selectPictureButton.addActionListener(new SelectPictrueActionListener());
			frame.getContentPane().add(selectPictureButton);
			selectPictureButton.setBounds(frame.getWidth() - RIGHT_BASIS + 10, TOP_BASIS + (customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			//生成音乐按钮
			generateMusicButton = new IButton("button\\normal\\generate.png", "button\\highlight\\generate.png", "button\\click\\generate.png", "button\\inable\\generate.png");
			generateMusicButton.addActionListener(new GenerateMusicActionListener());
			frame.getContentPane().add(generateMusicButton);
			generateMusicButton.setBounds(selectPictureButton.getX() + HORIZONTAL_INTERVAL, TOP_BASIS + (customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			generateMusicButton.setEnabled(true);
			//开始播放按钮
			startPlayButton = new ClickButton("button\\normal\\start.png", "button\\highlight\\start.png", "button\\click\\start.png", "button\\inable\\start.png",
					"button\\normal\\pause.png", "button\\highlight\\pause.png", "button\\click\\pause.png", "button\\inable\\pause.png");
			startPlayButton.addActionListener(new StartPlayActionListener());
			frame.getContentPane().add(startPlayButton);
			startPlayButton.setBounds(frame.getWidth() - RIGHT_BASIS + 10, TOP_BASIS + (1 + customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			startPlayButton.setEnabled(false);
			//停止播放按钮
			stopPlayButton = new IButton("button\\normal\\stop.png", "button\\highlight\\stop.png", "button\\click\\stop.png", "button\\inable\\stop.png");
			stopPlayButton.addActionListener(new StopPlayActionListener());
			frame.getContentPane().add(stopPlayButton);
			stopPlayButton.setBounds(startPlayButton.getX() + HORIZONTAL_INTERVAL, TOP_BASIS + (1 + customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			stopPlayButton.setEnabled(false);
			//导出按钮
			exportButton = new IButton("button\\normal\\export.png", "button\\highlight\\export.png", "button\\click\\export.png", "button\\inable\\export.png");
			exportButton.addActionListener(new ExportActionListener());
			frame.getContentPane().add(exportButton);
			exportButton.setBounds(startPlayButton.getX() + 2 * HORIZONTAL_INTERVAL, TOP_BASIS + (1 + customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			exportButton.setEnabled(false);
			//帮助按钮
			helpButton = new IButton("button\\normal\\help.png", "button\\highlight\\help.png", "button\\click\\help.png", "button\\inable\\help.png");
			helpButton.addActionListener(new HelpActionListener());
			frame.getContentPane().add(helpButton);
			helpButton.setBounds(selectPictureButton.getX() + 2 * HORIZONTAL_INTERVAL, TOP_BASIS + (customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			helpButton.setEnabled(true);
			//exit
			timerClose = new Timer(15, new timerCloseActionListener());
			exitButton = new IButton("button\\normal\\exit.png", "button\\highlight\\exit.png", "button\\click\\exit.png", "button\\inable\\exit.png");
			exitButton.addActionListener(new ExitActionListener());
			frame.getContentPane().add(exitButton);
			exitButton.setBounds(frame.getWidth() - 50 - (int)(0.5 * ICON_WIDTH), 45 - (int)(0.5 * ICON_HEIGHT), ICON_WIDTH, ICON_HEIGHT);
			exitButton.setEnabled(true);
			//mini
			miniButton = new IButton("button\\normal\\mini.png", "button\\highlight\\mini.png", "button\\click\\mini.png", "button\\inable\\mini.png");
			miniButton.addActionListener(new MiniActionListener());
			frame.getContentPane().add(miniButton);
			miniButton.setBounds(exitButton.getX() - 50, exitButton.getY(), ICON_WIDTH, ICON_HEIGHT);
			miniButton.setEnabled(true);
			
			playerSlider = new PlayerSlider("播放", 0, 150, 400);
			playerSlider.setBounds(LEFT_BASIS + 100, frame.getHeight() - 60, 520, 20);
			frame.getContentPane().add(playerSlider);
			playerSlider.setOpaque(false);
			
			frame.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
                       	if (e.getY() < TOP_BASIS){
                       		origin.x = e.getX();  //当鼠标按下的时候获得窗口当前的位置
                       		origin.y = e.getY();
                       	}
                }
			});
			frame.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {  //拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
                    if (e.getY() < 28){
                    	Point p = frame.getLocation();  //当鼠标拖动时获取窗口当前位置
                        //设置窗口的位置
                        //窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
                        frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
                    }
                        
                }
        	});
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
	
	public HashMap<String, Integer> getArgsCustom() {
		HashMap<String, Integer> args = new HashMap<String, Integer>();
		for (Component component : frame.getContentPane().getComponents()) {
			//加入滑动条的参数
			if (component.toString().contains("CustomSlider")) {
				args.put(((CustomSlider)component).getName(), ((CustomSlider)component).getValue());
			}
		}
		return args;
	}
	
	/**
	 * 设定参数到界面上
	 * @param args 设定的参数
	 */
	// TODO
	
	public void setArgsCustom(HashMap<String, Integer> args) {
		for (Component component : frame.getContentPane().getComponents()) {
			if (component.toString().contains("CustomSlider")) {
				CustomSlider panel = (CustomSlider)component;
				if (args.containsKey(panel.getName())) {
					panel.setValue(args.get(panel.getName()));
					panel.valueLabel.setText(panel.getValueString(args.get(panel.getName())));
				}
			}
		}
	}
	
	/**
	 * 帮助信息
	 */
	public class HelpActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (helpFrame == null) {
				initialHelpFrame();
			}
			if (helpFrame.isVisible()) {
				helpFrame.setVisible(false);
			}
			else {
				helpFrame.setVisible(true);
			}
		}
		
	}
	
	public void initialHelpFrame() {
		// TODO Auto-generated method stub
		final int width = 320;
		final int height = 240;
		JFrame.setDefaultLookAndFeelDecorated(true);
		helpFrame = new JFrame();
		helpFrame.setUndecorated(true);
		helpFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		helpFrame.setSize(width, height);
		ImageIcon img = new ImageIcon("HELP_BG.png");//这是背景图片
		img = new ImageIcon(img.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		JLabel imgLabel = new JLabel(img);//将背景图放在标签里。
		imgLabel.setSize(width, height);
		helpFrame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//注意这里是关键，将背景标签添加到jframe的LayeredPane面板里。  
		imgLabel.setBounds(0, 0, width, height);//设置背景标签的位置  
		Container cp = helpFrame.getContentPane(); 
		cp.setLayout(new BorderLayout());
		((JPanel)cp).setOpaque(false); //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。   
		com.sun.awt.AWTUtilities.setWindowOpacity(helpFrame, 1f);
		//设定窗体大小
		helpFrame.setSize(width, height);
		//居中窗体
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		helpFrame.setLocation(dimension.width / 2 - helpFrame.getWidth() / 2, dimension.height / 2 - helpFrame.getHeight() / 2);
		//设定窗口标题
		helpFrame.setTitle("Help");
		//设定布局方式为手动布局
        helpFrame.setLayout(null);
        
        helpFrame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
                helpOrigin.x = e.getX();  //当鼠标按下的时候获得窗口当前的位置
                helpOrigin.y = e.getY();
            }
		});
        helpFrame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {  //拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
                Point p = helpFrame.getLocation();  //当鼠标拖动时获取窗口当前位置
                //设置窗口的位置
                //窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
                helpFrame.setLocation(p.x + e.getX() - helpOrigin.x, p.y + e.getY() - helpOrigin.y);         
            }
    	});
        
        JLabel label1 = new JLabel("欢迎您使用图生音软件。");
        helpFrame.getContentPane().add(label1);
        label1.setBounds(100, 40, width, 20);
        JLabel label2 = new JLabel("选择图片，请点击加号。");
        helpFrame.getContentPane().add(label2);
        label2.setBounds(100, 65, width, 20);
        JLabel label3 = new JLabel("创作音乐，请点击灯泡。");
        helpFrame.getContentPane().add(label3);
        label3.setBounds(100, 90, width, 20);
        JLabel label4 = new JLabel("请求帮助，请点击问号。");
        helpFrame.getContentPane().add(label4);
        label4.setBounds(100, 115, width, 20);
        JLabel label5 = new JLabel("关闭帮助，请再次点击。");
        helpFrame.getContentPane().add(label5);
        label5.setBounds(100, 140, width, 20);
        JLabel label6 = new JLabel("保存音乐，请点击星星。");
        helpFrame.getContentPane().add(label6);
        label6.setBounds(100, 165, width, 20);
        JLabel label7 = new JLabel("也可用参数来生成音乐。");
        helpFrame.getContentPane().add(label7);
        label7.setBounds(100, 190, width, 20);
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
				double percentage = Math.min((double)PICTURE_WH / picture.getImage().getWidth(), (double)PICTURE_WH / picture.getImage().getHeight());
				picture.resize(percentage);
				panel.setPicture(picture);
				
				//处理按钮
				generateMusicButton.setEnabled(true);
				startPlayButton.setEnabled(false);
				stopPlayButton.setEnabled(false);
				exportButton.setEnabled(false);
				Player.stop();
				//参数转换
				ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				HashMap<String, Integer> musicParameterHashMap = parameterConversion.convert(parameterGenerate.generate(picture));
				setArgsCustom(musicParameterHashMap);
				//setArgs(musicParameterHashMap);
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
			music = composition.generate(getArgsCustom());
			//处理按钮
			Player.play(music);
			startPlayButton.setEnabled(true);
			startPlayButton.setInitial(true);
			startPlayButton.setClick(false);
			startPlayButton.paintComponent(startPlayButton.getGraphics());
			stopPlayButton.setEnabled(false);
			exportButton.setEnabled(true);
			playerSlider.setMaxValue(Player.getAudioLength());
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
			if (startPlayButton.isInitial()) {
				stopPlayButton.setEnabled(true);
				Player.play(music);
				Player.start();
				Player.skip(playerSlider.getValue());
				playerSlider.timer.start();
				startPlayButton.setInitial(false);
				startPlayButton.setClick(true);
				return;
			}
			
			if (startPlayButton.isClick()) {
				startPlayButton.setClick(false);
				Player.pause();
				playerSlider.timer.stop();
			}
			else {
				startPlayButton.setClick(true);
				Player.start();
				playerSlider.timer.start();
			}
			
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
			Player.reset();
			playerSlider.setValue(0);
			playerSlider.paintComponents(playerSlider.getGraphics());
			playerSlider.timer.stop();
			startPlayButton.setClick(false);
			startPlayButton.setInitial(true);
			startPlayButton.paintComponent(startPlayButton.getGraphics());
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
	 * exitButtonActionListener
	 */
	public class ExitActionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.exit(0);
			timerClose.start();
		}
		
	}
	
	/**
	 * timerActionListener
	 */
	public class timerCloseActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.exit(0);
			value -= 0.02f;  
            if (value >= 0.02f) {  
                SwingUtilities.invokeLater(new Runnable() {  
                    public void run() {  
                        com.sun.awt.AWTUtilities.setWindowOpacity(frame, value);  
                    }  
                });  
            } else {  
                System.exit(0);  
            }  
		}
	}
	
	/**
	 * timerActionListener
	 */
	public class timerStartActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.exit(0);
			value -= 0.02f;  
            if (value >= 0.02f) {  
                SwingUtilities.invokeLater(new Runnable() {  
                    public void run() {  
                        com.sun.awt.AWTUtilities.setWindowOpacity(frame, 1 - value);  
                    }  
                });  
            } else {  
                value = 1.0f;
                timerStart.stop();
                com.sun.awt.AWTUtilities.setWindowOpacity(frame, value);
            }  
		}
	}
	
	/**
	 * MiniActionListener
	 */
	
	public class MiniActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.exit(0);
			frame.setState(JFrame.ICONIFIED); 
		}
	}
	
	/**
	 * 用来显示图片的Panel
	 */
	public class PicturePanel extends JPanel {
		// TODO Auto-generated method stub
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
			setVisible(false);
			g.clearRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
			
			//如果图片不为空，则画出图片
			if (picture != null) {
				setBounds((int) (PICTURE_X + 0.5 * (PICTURE_WH - picture.getImage().getWidth())), (int) (PICTURE_Y + 0.5 * (PICTURE_WH - picture.getImage().getHeight())), picture.getImage().getWidth(), picture.getImage().getHeight());
				BufferedImage image = picture.getImage();
				//g.fillRoundRect(0, 0, getWidth(), getHeight(), 100, 100);
				g.drawImage(image, (getWidth() - image.getWidth()) / 2, (getHeight() - image.getHeight()) / 2, this);
				setVisible(true);
			}
			else {
				setVisible(false);
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
	
	public class CustomSlider extends JPanel implements ChangeListener {

		/**
		 * 序列化ID
		 */
		private static final long serialVersionUID = 5141083245464373171L;
		
		private static final int NAMELABEL_LENGTH = 60;
		
		private static final int HEIGHT = 20;
		
		protected int minValue;
		
		protected int maxValue;
		
		ImageIcon tmp = new ImageIcon("slider\\normal.png");
		
		private final int SLIDER_WIDTH = tmp.getIconWidth();
		
		private final int SLIDER_HEIGHT = tmp.getIconHeight();
		
		ImageIcon tmp2 = new ImageIcon("slider\\bar.png");
		
		private final int BAR_HEIGHT = tmp2.getIconHeight();
		
		//private final int BAR_WIDTH = 140;
		
		/**
		 * 名称
		 */
		public JLabel nameLabel;
		
		public IButton slider;
		
		public JLabel bar;
		
		private Point origin = new Point();
		
		protected int value;
		
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
		public CustomSlider(String name, int minValue, int maxValue, int initialValue, int sliderLength) {
			// TODO Auto-generated constructor stub
			
			nameLabel = new JLabel(name);
			nameLabel.setFont(new Font("经典繁行书", Font.PLAIN, 15));
			value = initialValue;
			this.minValue = minValue;
			this.maxValue = maxValue;
			valueLabel = new JLabel(getValueString(initialValue));
			valueLabel.setFont(new Font("宋体", Font.PLAIN, 15));
			slider = new IButton("slider\\normal.png", "slider\\highlight.png", "slider\\highlight.png", "slider\\highlight.png");
			slider.setEnabled(true);
			bar = new JLabel(new ImageIcon("slider\\bar.png"));
			bar.setSize(sliderLength, BAR_HEIGHT);
			this.add(nameLabel);
			this.add(valueLabel);
			this.add(slider);
			this.add(bar);
			this.setLayout(null);
			nameLabel.setBounds(0, 0, NAMELABEL_LENGTH, HEIGHT);
			valueLabel.setBounds(NAMELABEL_LENGTH + sliderLength + SLIDER_WIDTH, 0, 80, HEIGHT);
			bar.setBounds((int) (NAMELABEL_LENGTH + 0.5 * SLIDER_WIDTH), (int) (0.5 * (HEIGHT - BAR_HEIGHT)), sliderLength, BAR_HEIGHT);
			slider.setBounds(0, (int)(0.5 * (HEIGHT - SLIDER_HEIGHT)), SLIDER_WIDTH, SLIDER_HEIGHT);
			origin = slider.getLocation();
			setValue(initialValue);
			slider.addChangeListener(this);
			
			slider.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
	            	origin.x = e.getX();
	            	origin.y = slider.getY();//当鼠标按下的时候获得slider当前的位置

	            }
			});
			
			slider.addMouseMotionListener(new MouseMotionAdapter() {
	            public void mouseDragged(MouseEvent e) {  //拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
	                	Point p = slider.getLocation();  //当鼠标拖动时获取窗口当前位置
	                    //设置窗口的位置
	                    //窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
	                    slider.setLocation(p.x + e.getX() - origin.x, origin.y);
	                    if (slider.getX() + 0.5 * SLIDER_WIDTH < bar.getX()) {
	                    	slider.setLocation((int) (bar.getX() - 0.5 * SLIDER_WIDTH), origin.y);
	                    }
	                    if (slider.getX() + 0.5 * SLIDER_WIDTH > bar.getWidth() + bar.getX()) {
	                    	slider.setLocation((int) (bar.getWidth() + bar.getX() - 0.5 * SLIDER_WIDTH), origin.y);
	                    }
	            }
	    	});
		}
		
		/**
		 * 构造函数
		 * @param name 名称
		 * @param minValue 最小值
		 * @param maxValue 最大值
		 */
		public CustomSlider(String name, int minValue, int maxValue, int sliderLength) {
			// TODO Auto-generated constructor stub
			this(name, minValue, maxValue, (minValue + maxValue) / 2, sliderLength);
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
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
			slider.setLocation((int) ((value - minValue) * bar.getWidth() / (maxValue - minValue) - 0.5 * SLIDER_WIDTH + bar.getX()), origin.y);
			valueLabel.setText(getValueString(value));
		}
		
		/**
		 * 获取名字
		 * @return 名字
		 */
		public String getName() {
			return nameLabel.getText();
		}

		public void stateChanged(ChangeEvent arg0) {
			value = (int) (slider.getX() + 0.5 * SLIDER_WIDTH - bar.getX()) * (maxValue - minValue) / bar.getWidth() + minValue;
			valueLabel.setText(getValueString(value));
		}
		
	}
	
	public class PlayerSlider extends CustomSlider implements ChangeListener{

		/**
		 * SeiralVersionUID
		 */
		private static final long serialVersionUID = -6368720754983954860L;

		public Timer timer;
		
		private boolean play = false;

		public PlayerSlider(String name, int minValue, int maxValue,
				int sliderLength) {
			super(name, minValue, maxValue, sliderLength);
			this.nameLabel.setBounds(25, 0, 35, 20);
			this.remove(slider);
			slider = new IButton("slider\\player.png", "slider\\player-highlight.png", "slider\\player-highlight.png", "slider\\player.png");
			slider.setEnabled(true);
			this.add(slider);
			this.remove(bar);
			this.add(bar);
			slider.setBounds(0, (int)(0.5 * (CustomSlider.HEIGHT - super.SLIDER_HEIGHT)), slider.buttonWidth, slider.buttonHeight);
			origin = slider.getLocation();
			setValue(0);
			
			slider.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
	            	origin.x = e.getX();
	            	origin.y = slider.getY();//当鼠标按下的时候获得slider当前的位置

	            }
			});
			
			slider.addMouseMotionListener(new MouseMotionAdapter() {
	            public void mouseDragged(MouseEvent e) {  //拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
	                	Point p = slider.getLocation();  //当鼠标拖动时获取窗口当前位置
	                    //设置窗口的位置
	                    //窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
	                    slider.setLocation(p.x + e.getX() - origin.x, origin.y);
	                    if (slider.getX() + 0.5 * slider.getWidth() < bar.getX()) {
	                    	slider.setLocation((int) (bar.getX() - 0.5 * slider.getWidth()), origin.y);
	                    }
	                    if (slider.getX() + 0.5 * slider.getWidth() > bar.getWidth() + bar.getX()) {
	                    	slider.setLocation((int) (bar.getWidth() + bar.getX() - 0.5 * slider.getWidth()), origin.y);
	                    }
	            }
	    	});
			
			timer = new Timer(100, new ActionListener() {   
	            public void actionPerformed(ActionEvent e) {   
	                Player.tick();
	                setValue(Player.getAudioPosition());
	                if (!Player.isRunning()) {
	                	startPlayButton.setClick(false);
	                	startPlayButton.setInitial(true);
	                	stopPlayButton.setEnabled(false);
	                	startPlayButton.paintComponent(startPlayButton.getGraphics());
	                }
	            }   
	        });
			
			bar.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
	            	int tmp = e.getX() * (getMaxValue() - getMinValue()) / bar.getWidth() + getMinValue();
	            	setValue(tmp);
	            	Player.skip(value);
	            }
			});
			// TODO Auto-generated constructor stub
		}
		
		public boolean isPlay() {
			return play;
		}
		
		public void setValue(int value) {
			this.value = value;
			slider.setLocation((int) ((value - minValue) * bar.getWidth() / (maxValue - minValue) - 0.5 * slider.getWidth() + bar.getX()), origin.y);
			valueLabel.setText(getValueString(value / 100));
		}
		
		public void stateChanged(ChangeEvent arg0) {
			value = (int) (slider.getX() + 0.5 * slider.getWidth() - bar.getX()) * (maxValue - minValue) / bar.getWidth() + minValue;
			valueLabel.setText(getValueString(value));
			if (value != Player.getAudioPosition()) {   
                Player.skip(value);   
            }
		}
		
		public void setMaxValue(int maxValue) {
			this.maxValue = maxValue;
		}
		
		public int getMaxValue() {
			return maxValue;
		}
		
		public int getMinValue() {
			return minValue;
		}
	}
	
	public class IButton extends JButton{
		// TODO Auto-generated method stub
        
		private static final long serialVersionUID = 5357507583085033763L;
		protected BufferedImage image_over;     //鼠标在按钮上的图片
		protected BufferedImage image_off;      //鼠标不在按钮上的图片
        protected BufferedImage image_pressed;  //鼠标按下按钮时的图片
        protected BufferedImage image_inable;   //
        protected int buttonWidth;              //宽
        protected int buttonHeight;             //高
        protected int[] pixels;                 //储存图片数据的数组，用于计算contains
        protected boolean mouseOn;
        protected boolean mousePressed;
         
        public IButton() {};
        
        public IButton(String pic1, String pic2, String pic3, String pic4){
                mouseOn = false;
                mousePressed = false;
                //加载图片
                image_over = loadImage(pic2);
                image_off = loadImage(pic1);
                image_pressed = loadImage(pic3);
                image_inable = loadImage(pic4);
                
                buttonWidth = image_off.getWidth();
                buttonHeight = image_off.getHeight();
                
                //读取图片数据
                pixels = new int[buttonWidth * buttonHeight];
                //抓取像素数据
                PixelGrabber pg = new PixelGrabber(image_off, 0, 0, buttonWidth, buttonHeight, pixels, 0, buttonWidth);
                try{
                        pg.grabPixels();
                }
                catch(Exception e){
                        e.printStackTrace();
                }
                
                //必须设置！否则会有残影！
                this.setOpaque(false);
                
                this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                this.addMouseListener(new MouseHandler());
        }
        
        //读取图片文件
        public BufferedImage loadImage(String filename){
                File file = new File(filename);
                
                if(!file.exists())
                        return null;
                
                try{
                        return ImageIO.read(file);
                }
                catch(IOException e){
                        e.printStackTrace();
                        return null;
                }
        }
        
        //覆盖此方法绘制自定义的图片
        public void paintComponent(Graphics g){
                g.drawImage(image_off, 0, 0, this);
                if (this.isEnabled()){
                	if(mouseOn)
                		g.drawImage(image_over, 0, 0, this);
                	else if(mousePressed)
                        g.drawImage(image_pressed, 0, 0, this);
                }
                else {
                	g.drawImage(image_inable, 0, 0, this);
                }
        }
        
        //覆盖此方法绘制自定义的边框
        public void paintBorder(Graphics g){
                //不要边框
        }
        
        public boolean contains(int x, int y){
                //不判定的话会越界，在组件之外也会激发这个方法
                if(!super.contains(x, y))
                        return false;
                
                int alpha = (pixels[(buttonWidth * y + x)] >> 24) & 0xff;

                repaint();
                if(alpha == 0){
                        return false;
                }
                else{
                        return true;
                }
        }
             
        //处理进入、离开图片范围的消息
        class MouseHandler extends MouseAdapter  {
                public void mouseExited(MouseEvent e){
                        mouseOn = false;
                        repaint();
                }
                public void mouseEntered(MouseEvent e){
                        mouseOn = true;
                        repaint();
                }
                public void mousePressed(MouseEvent e){
                        mouseOn = false;
                        mousePressed = true;
                        repaint();
                }
                public void mouseReleased(MouseEvent e){
                        //防止在按钮之外的地方松开鼠标
                        if(contains(e.getX(), e.getY()))
                                mouseOn = true;
                        else
                                mouseOn = false;
                        
                        mousePressed = false;
                        repaint();
                }
        }
        
	}

	public class ClickButton extends IButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4990454727481081866L;
		// TODO
		private BufferedImage image_click_off;
		private BufferedImage image_click_over;
		private BufferedImage image_click_pressed;
		private BufferedImage image_click_inable;
		private boolean click;
		private boolean initial;
		
		public ClickButton(String pic1, String pic2, String pic3, String pic4, String pic5, String pic6, String pic7, String pic8){
            mouseOn = false;
            mousePressed = false;
            click = false;
            initial = true;
            
            //加载图片
            image_over = loadImage(pic2);
            image_off = loadImage(pic1);
            image_pressed = loadImage(pic3);
            image_inable = loadImage(pic4);
            
            image_click_over = loadImage(pic6);
            image_click_off = loadImage(pic5);
            image_click_pressed = loadImage(pic7);
            image_click_inable = loadImage(pic8);
            //double percentage = Math.min((double)(image_over.getWidth()) / this.getWidth(), (double)(image_over.getHeight()) / this.getHeight());
            
            buttonWidth = image_off.getWidth();
            buttonHeight = image_off.getHeight();
            
            //读取图片数据
            pixels = new int[buttonWidth * buttonHeight];
            //抓取像素数据
            PixelGrabber pg = new PixelGrabber(image_off, 0, 0, buttonWidth, buttonHeight, pixels, 0, buttonWidth);
            try{
                    pg.grabPixels();
            }
            catch(Exception e){
                    e.printStackTrace();
            }
            
            //必须设置！否则会有残影！
            this.setOpaque(false);
            
            this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            this.addMouseListener(new MouseHandler());
		}
		
		public void setClick(boolean state) {
			click = state;
		}
		
		public boolean isClick() {
			return click;
		}
		
		public boolean isInitial() {
			return initial;
		}
		
		public void setInitial(boolean state) {
			initial = state;
		}
		
		public void paintComponent(Graphics g){
			if (!click) {
				g.drawImage(image_off, 0, 0, this);
		        if (this.isEnabled()){
		        	if(mouseOn)
		            	g.drawImage(image_over, 0, 0, this);
		            else if(mousePressed)
		                g.drawImage(image_pressed, 0, 0, this);
		        }
		        else {
		        	g.drawImage(image_inable, 0, 0, this);
		        }
			}
			else {
				g.drawImage(image_click_off, 0, 0, this);
	            if (this.isEnabled()){
	            	if(mouseOn)
	            		g.drawImage(image_click_over, 0, 0, this);
	            	else if(mousePressed)
	                    g.drawImage(image_click_pressed, 0, 0, this);
	            }
	            else {
	            	g.drawImage(image_click_inable, 0, 0, this);
	            }
			}
		}
	}
	
}

