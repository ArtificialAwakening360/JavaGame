import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.Rectangle;

public class Mouse extends MouseAdapter{

	public int x;
	public int y;

	public int buttonAmount = 5;
	public boolean buttons[] = new boolean[buttonAmount];

	public void mouseMoved(MouseEvent e){

		x = e.getX();
		y = e.getY();
	}

	public void mousePressed(MouseEvent e){
		buttons[e.getButton()] = true;

	}

	public boolean wasPressed(int button){
		return isDown(button) && buttons[button];
	}

	public boolean isDown(int button){
		return buttons[button];
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}	
}
