import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener{

	private final int FRAME_WIDTH = 600;
	private final int FRAME_HEIGHT = 500;
	private final String TITLE = "Adventures";

	public int code;

	private Draw draw;
	private Player player;
	private Mouse mouse;

	public MyFrame(){
		draw = new Draw();
		player = draw.player;

		mouse = draw.mouse;

		addKeyListener(this);
		initialize();
	}

	public void initialize(){
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(3);
		setResizable(false);
		setTitle(TITLE);
		setVisible(true);
		getContentPane().add(draw);
	}

	public void keyPressed(KeyEvent e){
		code = e.getKeyCode();

		if(code == e.VK_LEFT){
			if(player.isDead != true){
				player.moveLeft();
			}

		}else if(code == e.VK_RIGHT){
			if(player.isDead != true){
				player.moveRight();
			}

		}else if(code == e.VK_UP){
			if(player.isDead != true){
				player.jump();
			}
			
		}else if(code == e.VK_DOWN){
			if(player.isDead != true){
				player.crouch();
			}

		}else if(code == e.VK_A){
			if(player.isDead != true){
				player.useMagic(draw);
			}

		}else if(code == e.VK_S){
			if(player.isDead != true){
				player.useSword(draw);
			}
		}
	}

	public void keyReleased(KeyEvent e){
		code = e.getKeyCode();

		if(code == e.VK_LEFT){
			if(player.isDead != true){
				player.idleAnimation(draw);
			}
		}else if(code == e.VK_RIGHT){
			if(player.isDead != true){
				player.idleAnimation(draw);
			}
			
		}else if(code == e.VK_UP){


		}else if(code == e.VK_DOWN){
			if(player.isDead != true){
				player.idleAnimation(draw);
			}
		}else if(code == e.VK_SPACE){
		}
	}

	public void keyTyped(KeyEvent e){

	}

	public int getFrameWidth(){
		return FRAME_WIDTH;
	}

	public int getFrameHeight(){
		return FRAME_HEIGHT;
	}

	public static void main(String args[]){
		MyFrame frame = new MyFrame();
	}
}