import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

public class Player {

	public BufferedImage image;
	public URL resource = getClass().getResource("adventurer/idle-00.png");

	//Protagonist's location:
	public int xPos = 50;
	public int yPos = 370;
	public int height;
	public int width;
	public int state = 0;

	//Protagonist's stats:
	public int healthBar = 200;
	public int power = 7;
	public int defense = 2;
	public int manaPoints = 10;

	//Protagonist's states:
	public boolean isIdle = true;
	public boolean isFacingRight = true;
	public boolean isJumping = false;
	public boolean isCasting = false;
	public boolean isDead = false;
	public boolean drawsSword = false;

	//Gravity:
	public int gravity = 370;

	//Protagonist's Draw activation:
	public Paint paint;

	public Player(Paint paint){
		try{
			image = ImageIO.read(resource);
		}catch(IOException e){
			e.printStackTrace();
		}

		this.paint = paint;
		idleAnimation(paint);
	}
	
	public Player(int x, int y, Paint paint){
		x = xPos;
		y = yPos;

		try{
			image = ImageIO.read(resource);
		}catch(IOException e){
			e.printStackTrace();
		}
		this.paint = paint;

		width = image.getWidth();
		height = image.getHeight();

		idleAnimation(paint);
	}

	public void idleAnimation(Paint paint){
		isIdle = true;
		Thread idleThread = new Thread (new Runnable(){
			public void run(){
				while(isIdle){
					for(int ctr = 0; ctr < 4; ctr++){
						if(isFacingRight == true){
							resource = getClass().getResource("adventurer/idle-0"+ctr+".png");
						}else{
							resource = getClass().getResource("adventurer/idleback-0"+ctr+".png");
						}
						verifyDeath();

						try{
							image = ImageIO.read(resource);
							Thread.sleep(1000/30);
							paint.repaint();

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

	public void moveAnimation(){
			if(r < 6){
				if(isFacingRight == true){
					resource = getClass().getResource("adventurer/run"+r+".png");
				}else{
					resource = getClass().getResource("adventurer/run"+r+".png");
				}
				r++;
			}else{
				r = 0;
			}

			try{
				paint.repaint();
				resource = ImageIO.read(image);
			}catch(IOException e){
				e.printStackTrace();
			}
		}

	public void jumpingAnimation(){
		Thread jumpingThread = new Thread(new Runnable(){
			public void run(){
				for(int i = 0; i < 7; i++){
					if(isFacingRight == true){
						imageFile = getClass().getResource("jumImages/jump"+i+".png");
						yPos-=10;			
						xPos+=15;
					}else{
						imageFile = getClass().getResource("jumImages/jumpback"+i+".png");
						yPos-=10;		
						xPos-=15;						
					}
					try{
						canvas.repaint();
						playerImage = ImageIO.read(imageFile);
						Thread.sleep(1000/30);
					}catch(IOException e){
						e.printStackTrace();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				//After Jumping go to fall method
				fallingAnimation();				
			}
		});		
		jumpingThread.start();
	}

	//Player Falling method
	public void fallingAnimation(){
		Thread fallingThread = new Thread(new Runnable(){
			public void run(){
				do{
					for(int i = 0; i < 4; i ++){
						if(isFacingRight == true){
							imageFile = getClass().getResource("fallImages/fall"+i+".png");
							
						}else{
							imageFile = getClass().getResource("fallImages/fallback"+i+".png");
						}
						yPos+=2;
					}							
					try{
						canvas.repaint();
						playerImage = ImageIO.read(imageFile);
						Thread.sleep(1000/30);

					}catch(IOException e){
						e.printStackTrace();
					}catch(InterruptedException e){
						e.printStackTrace();
					}	

				//Do this code while the player is Higher than the gravity variable					
				}while(yPos < gravity);
				//return to idle position after falling
				standingAnimation(canvas);
			}
		});
		fallingThread.start();
	}

	public void moveLeft(){
		x = x - 5;
		reloadImage();
		repaint();
		checkCollision();
	}

	public void moveRight(){
		x = x + 5;
		reloadImage();
		repaint();
		checkCollision();
	}
}