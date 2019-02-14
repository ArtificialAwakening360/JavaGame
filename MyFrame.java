import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener{

	Draw drawing;

	private Protagonist protagonist;

	public MyFrame(){
		this.drawing = new Draw();
	}

	public void keyPressed(KeyEvent e){

		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			drawing.moveRight();
			System.out.println("pos: " + drawing.x + ", " + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			drawing.moveLeft();
			System.out.println("pos: " + drawing.x + ", " + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_F){
			drawing.attackSword();
			System.out.println("attack");
		}
		else if(e.getKeyCode() == KeyEvent.VK_R){
			drawing.attackCast();
			System.out.println("cast");
		}
		else if(e.getKeyCode() == KeyEvent.VK_G){
			drawing.attackBow();
			System.out.println("fire");
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			drawing.spawnEnemy();
		}
	}

	public void keyReleased(KeyEvent e){
		data =e.getKeyCode();

		if(data == e.VK_LEFT){
			if(protagonist.isDead != true){
				protagonist.idleAnimation(drawing);
			}
		}else if(data == e.VK_RIGHT){
			if(protagonist.isDead != true){
				protagonist.idleAnimation(drawing);
			}
		}
	}

	public void keyTyped(KeyEvent e){
		
	}

	public static void main(String args[]){
		MyFrame gameFrame = new MyFrame();
		gameFrame.setSize(600,600);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		gameFrame.getContentPane().add(gameFrame.drawing);
		gameFrame.addKeyListener(gameFrame);
		System.out.println("practical programming");
	}
}