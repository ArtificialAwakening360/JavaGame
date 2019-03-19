import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter{
	
	private static final int numButtons = 5;

	private static final boolean buttons[] = new boolean[numButtons];
	private static final boolean lastbuttons[] = new boolean[numButtons];
	private static boolean moving = true;
	public static int x = -1, y = -1;
	public static int  lastX = x, lastY = y;


	public void mousePressed(MouseEvent e){
		System.out.println("Button " + e.getButton() +" was pressed");
		buttons[e.getButton()] = true;

	}
	public void mouseReleased(MouseEvent e){
		buttons[e.getButton()] = false;

	}
	public void mouseMoved(MouseEvent e){
		x = e.getX();
		y = e.getY();
		moving = true;
	}
	public static boolean isMoving(){
		return moving;
	}
	public static void update(){
		
		for(int i = 0; i <numButtons; i++)
			lastbuttons[i] = buttons[i];

		if( x == lastX || y == lastY) moving = false;
			lastX = x;
			lastY = y;
	}
	public static boolean isDown(int button){
		return buttons[button];
	}
	public static boolean wasPressed(int button){
		return isDown(button) && !lastbuttons[button];
	}
	public static boolean wasReleased(int button){
		return isDown(button) && wasPressed(button);
	}
	public static int getX(){
		return x;
	}
	public static int getY(){
		return y;
	}



}