import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Rectangle;

public class Monster{
	
	public BufferedImage monsterImage;
	public URL monsterFile = getClass().getResource("slime/idle0.png");

	public int xPos;
	public int yPos;
	public int width;
	public int height;

	public int hp = 25;
	public int atk = 3

	public boolean isIdle = false;
	public boolean isMoving = true;
	public boolean isFacingRight = false;
	public boolean isFacingLeft = false;
	public boolean isAttacking = false;
	public boolean isDead = false;

	public Paint paint;

	public Monster(Paint color){

		try{
			monsterFile = ImageIO.read(monsterImage);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public Monster(int x, int y, Draw color){

		xPos = x;
		yPos = y;

		try{
			monsterFile = ImageIO.read(monsterImage);
		}catch(IOException e){
			e.printStackTrace();
		}

		this.paint = color;
		this.width = creatureFile.getWidth();
		this.height = creatureFile.getHeight();

		idleMonster();
	}

	public Rectangle monsterBounds(){
		return (new Rectangle(xPos, yPos, width, height));
	}

	public boolean monsterDirection(){
		if(xPos < paint.protagonist.xPos){
			return isFacingRight = false;
		}else{
			return isFacingRight = true;
		}
	}

	public void idleMonster(){
		Thread idleThread = new Thread(new Runnable(){
			public void run(){
				while(isIdle){
					for(int ctr = 0; ctr < 4; ctr ++){
						monsterFile = getClass().getResource("slime/idle"+ctr+".png");

						try{
							paint.repaint();
							monsterImage = ImageIO.read(monsterFile);
							Thread.sleep(150);

						}catch(IOException e){
							e.printStackTrace();
						}catch(InterruptedException e){
							e.printStackTrace();
						}
					}
				}
			}
		});
		idleThread.start();
	}

	public void movementMonster(){
		isIdle = false;
		isMoving = true;
		monsterDirection();
		Thread moveThread = new Thread(new Runnable(){
			public void run(){
				while(isMoving){
					for(int ctr = 0; ctr < 4; ctr++){ 
						if(isFacingRight == true){
							if(xPos > draw.player.xPos){
								xPos--;
								creatureFile = getClass().getResource("slime/move"+i+".png");
							}
							
						//else use facing left images and move to the left
						}else{
							creatureFile = getClass().getResource("slimeIdle/moveback"+i+".png");
							xPos++;
						}
						try{
							draw.repaint();
							creatureImage = ImageIO.read(creatureFile);
							Thread.sleep(333);

						}catch(IOException e){
							e.printStackTrace();
						}catch(InterruptedException e){
							e.printStackTrace();
						}
					}
				}		
			}
		});
		moveThread.start();
	}
}