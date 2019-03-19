import java.awt.Canvas;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class GameFrame extends Canvas implements Runnable{

	public static final String title = "Arcanist";
	public static final int width = 640;
	public static final int height = width /4*3;

	private boolean running;

	private Texture texture;
	private SpriteSheet sheet;
	private Sprite sprite;
	private Sprite sprite2;
	private double sX = 350, sY = 300;

	private Menu menu;

	public GameFrame(){
		sheet = new SpriteSheet(new Texture("characterSheet"), 45);
		sprite = new Sprite(sheet, 1,3);
		sprite2 = new Sprite(sheet, 3,3);
		addKeyListener(new KeyHandler());
		MouseHandler mouse = new MouseHandler();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		menu = new Menu();
	}
	private void tick(){
		
	}
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,width,height);
		sprite2.render(g, 100,100);
		sprite.render(g, sX, sY);
		menu.render(g);
		g.dispose();
		bs.show();

	}

	private void start(){

		if(running) return;
		running = true;
		new Thread(this, "FirstThread").start();

	}
	private void stop(){

		if(!running) return;
		running = false;

	}

	public void run(){
		requestFocus();
		double target = 60.0;
		double nanoSecondsPerTick = 1000000000.0/target;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double unprocessed = 0.0;
		int fps = 0;
		int tps = 0;
		boolean canRender = false;

		while(running){
			long now = System.nanoTime();
			unprocessed += (now - lastTime)/ nanoSecondsPerTick;
			lastTime = now;

			if(unprocessed >= 1){
				tick();
				KeyHandler.update();
				MouseHandler.update();
				unprocessed--;
				tps++;
				canRender = true;

			}else canRender = false;

			try{
			Thread.sleep(1);
			}catch(InterruptedException e){
				e.printStackTrace();
			}

			if(canRender){
				render();
				fps++;

			}
			if(System.currentTimeMillis() - 1000 > timer){
				timer += 1000;
				System.out.printf("FPS: %d | TPS: %d ", fps, tps);
				fps = 0;
				tps = 0;
			}
		}

	}
	public static void main(String args[]){

		GameFrame game = new GameFrame();
		JFrame frame = new JFrame(title);
		frame.add(game);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		game.start();


	}
}