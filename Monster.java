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
	public int atk = 3;

	public boolean isIdle = false;
	public boolean isMoving = true;
	public boolean isFacingRight = false;
	public boolean isFacingLeft = false;
	public boolean isAttacking = false;
	public boolean isDead = false;

	public Paint paint;

	public Monster(Paint color){

		try{
			monsterImage = ImageIO.read(monsterFile);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public Monster(int x, int y, Paint color){
		xPos = x;
		yPos = y;
		try{
			monsterImage = ImageIO.read(monsterFile);
		}catch(IOException e){
			e.printStackTrace();
		}
		this.paint = color;
		this.width = monsterFile.getWidth();
		this.height = monsterFile.getHeight();
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
							if(xPos > paint.protagonist.xPos){
								xPos--;
								monsterFile = getClass().getResource("slime/move"+ctr+".png");
							}
						
						}else{
							monsterFile = getClass().getResource("slime/move"+ctr+".png");
							xPos++;
						}
						try{
							paint.repaint();
							monsterImage = ImageIO.read(monsterFile);
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

	public void attackMonster(){
		isMoving = false;
		monsterDirection();
		for(int ctr = 0; ctr < 5; ctr++){
			if(isFacingRight == true && isAttacking == true){
				monsterFile = getClass().getResource("slime/attack"+ctr+".png");
				xPos-=5;
			}else{
				monsterFile = getClass().getResource("slime/attack"+ctr+".png");
				xPos+=5;
			}

			try{
				paint.repaint();
				monsterImage = ImageIO.read(monsterFile);
				Thread.sleep(200);

			}catch(IOException e){
				e.printStackTrace();
			
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public boolean checkHealth(){
		if(health <= 0){
			isAttacking = false;
			isMoving = false;
			return true;
		}
		return false;
	}

	public void monsterDeath(){
		for(int ctr = 0; ctr < 4; ctr++){
			monsterFile = getClass().getResource("slime/die"+ctr+".png");
			try{
				paint.repaint();
				monsterImage = ImageIO.read(monsterFile);
				Thread.sleep(300);
			}catch(IOException e){
				e.printStackTrace();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		System.out.println("Monster Death");
		isDead = true;
	}
}