package Game.Setting;

import java.awt.Dimension;
import java.awt.Toolkit;


public class Data {
	public final static int NUM_BLOCCHI_Y=19;
	public final static int NUM_BLOCCHI_X=87;
	public static int height;
	public static int width;
	public static int size;	

	
	public Data() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int) screenSize.getHeight();
		size=height/NUM_BLOCCHI_Y;
	}
	
	public int getBlockSize() {
		return size;
	}
}
