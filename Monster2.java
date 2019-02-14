import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Rectangle;

public class Monster2{
	
	public BufferedImage monster2Image;
	public URL monster2File = getClass().getResource("knight/idle0.png");

	public int xPos;
	public int yPos;
	public int width;
	public int height;

	public int hp = 40;
	public int atk = 10

	public boolean isIdle = false;
	public boolean isMoving = true;
	public boolean isFacingRight = false;
	public boolean isFacingLeft = false;
	public boolean isAttacking = false;
	public boolean isDead = false;

	public Paint paint;

	public Monster2(Paint color){

		try{
			monster2Image = ImageIO.read(monster2File);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public Monster2(int x, int y, Paint color){
		xPos = x;
		yPos = y;
		try{
			monster2Image = ImageIO.read(monster2File);
		}catch(IOException e){
			e.printStackTrace();
		}
		this.paint = color;
		this.width = monster2File.getWidth();
		this.height = monster2File.getHeight();
		idleMonster();
	}

	public Rectangle monster2Bounds(){
		return (new Rectangle(xPos, yPos, width, height));
	}

	public boolean monster2Direction(){
		if(xPos < paint.protagonist.xPos){
			return isFacingRight = false;
		}else{
			return isFacingRight = true;
		}
	}

	public void idleMonster2(){
		Thread idleThread = new Thread(new Runnable(){
			public void run(){
				while(isIdle){
					for(int ctr = 0; ctr < 4; ctr ++){
						monster2File = getClass().getResource("slime/idle"+ctr+".png");
						try{
							paint.repaint();
							monster2Image = ImageIO.read(monster2File);
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

	public void movementMonster2(){
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
								monster2File = getClass().getResource("slime/move"+ctr+".png");
							}
						
						}else{
							monster2File = getClass().getResource("slime/move"+ctr+".png");
							xPos++;
						}
						try{
							paint.repaint();
							monster2Image = ImageIO.read(monster2File);
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

	public void attackMonster2(){
		isMoving = false;
		monsterDirection();
		for(int ctr = 0; ctr < 5; ctr++){
			if(isFacingRight == true && isAttacking == true){
				monster2File = getClass().getResource("slime/attack"+ctr+".png");
				xPos-=5;
			}else{
				monster2File = getClass().getResource("slime/attack"+ctr+".png");
				xPos+=5;
			}

			try{
				paint.repaint();
				monster2Image = ImageIO.read(monster2File);
				Thread.sleep(200);

			}catch(IOException e){
				e.printStackTrace();
			
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public boolean chkHealth(){
		if(health <= 0){
			isAttacking = false;
			isMoving = false;
			return true;
		}
		return false;
	}

	public void monster2Death(){
		for(int ctr = 0; ctr < 4; ctr++){
			monster2File = getClass().getResource("slime/die"+ctr+".png");
			try{
				paint.repaint();
				monster2Image = ImageIO.read(monster2File);
				Thread.sleep(300);
			}catch(IOException e){
				e.printStackTrace();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		System.out.println("Monster2 Death");
		isDead = true;
	}
}