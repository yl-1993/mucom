package Entry;

import Config.Config;
import UI.Form4;

/**
 * 程序入口类
 */
public class Entry {
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		Config.configInit();
		Form4 form = new Form4();
		form.show();
	}
}
