import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyHandler extends KeyAdapter{
		private static final int  Num_keys= 256;
		private static final boolean[] keys = new boolean[Num_keys];
		private static final boolean[] lastKeys = new boolean[Num_keys];

	public void keyPressed(KeyEvent e){
		super.keyPressed(e);

		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()] = false;
	}
	public static void update(){
		for(int i = 0; i< Num_keys;i++){
			lastKeys[i] = keys[i];
		}
	}
	public static boolean wasKeyPressed(int keyCode){
		return isKeyDown(keyCode) && !lastKeys[keyCode];
	}
	public static boolean isKeyDown(int keyCode){
		return keys[keyCode];
	}


}