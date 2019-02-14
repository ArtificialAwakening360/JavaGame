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

		idleCMonster();
	}

	public Rectangle monsterBounds(){
		return (new Rectangle(xPos, yPos, width, height));
	}

	public boolean monsterDirection(){
		if(xPos < draw.player.xPos){
			return isFacingRight = false;
		}else{
			return isFacingRight = true;
		}
	}

	public BufferedImage image;
	public URL resource = getClass().getResource("slime/idle0.png");

	public Monster(Draw comp){
		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		animate(comp);
	}

	public Monster(int xPass, int yPass, Draw comp){
		xPos = xPass;
		yPos = yPass;

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		height = image.getHeight();
		width = image.getWidth();

		animate(comp);
	}

	public void animate(Draw compPass){
		Thread monThread = new Thread(new Runnable(){
			public void run(){
				while(idle){
					for(int ctr = 0; ctr < 5; ctr++){
						try {
							if(ctr==4){
								resource = getClass().getResource("slime/idle0.png");
							}
							else{
								resource = getClass().getResource("slime/idle"+ctr+".png");
							}
							
							try{
								image = ImageIO.read(resource);
							}
							catch(IOException e){
								e.printStackTrace();
							}

					        compPass.repaint();
					        Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					if(life<=0){
						die(compPass);
					}
				}
			}
		});
		monThread.start();
	}

	public void moveTo(int toX, int toY){
		if(xPos<toX){
			xPos++;
		}
		else if(xPos>toX){
			xPos--;
		}

		if(yPos<toY){
			yPos++;
		}
		else if(yPos>toY){
			yPos--;
		}
	}

	public void die(Draw compPass){
		idle = false;
		if(alive){
			Thread monThread = new Thread(new Runnable(){
				public void run(){
					for(int ctr = 0; ctr < 4; ctr++){
						try {					
							resource = getClass().getResource("slime/die"+ctr+".png");
							
							try{
								image = ImageIO.read(resource);
							}
							catch(IOException e){
								e.printStackTrace();
							}
					        compPass.repaint();
					        Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			monThread.start();
		}
		alive = false;
	}
}