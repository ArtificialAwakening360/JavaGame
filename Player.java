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
			if(m < 6){
				if(isFacingRight == true){
					resource = getClass().getResource("adventurer/run"+m+".png");
				}else{
					resource = getClass().getResource("adventurer/run"+m+".png");
				}
				m++;
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

	public void jumpAnimation(){
		Thread jumpThread = new Thread(new Runnable(){
			public void run(){
				for(int j = 0; j < 7; j++){
					if(isFacingRight == true){
						resource = getClass().getResource("adventurer/jump-0"+j+".png");
						yPos-=10;			
						xPos+=15;
					}else{
						resource = getClass().getResource("adventurer/jumpback"+j+".png");
						yPos-=10;		
						xPos-=15;						
					}
					try{
						paint.repaint();
						image = ImageIO.read(resource);
						Thread.sleep(1000/30);
					}catch(IOException e){
						e.printStackTrace();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				fallAnimation();				
			}
		});		
		jumpThread.start();
	}

	public void fallAnimation(){
		Thread fallThread = new Thread(new Runnable(){
			public void run(){
				do{
					for(int f = 0; f < 4; f ++){
						if(isFacingRight == true){
							resource = getClass().getResource("adventurer/fall-0"+f+".png");
						}else{
							resource = getClass().getResource("adventurer/fallback"+f+".png");
						}
						yPos+=2;
					}							
					try{
						paint.repaint();
						image = ImageIO.read(resource);
						Thread.sleep(1000/30);
					}catch(IOException e){
						e.printStackTrace();
					}catch(InterruptedException e){
						e.printStackTrace();
					}						
				}while(yPos < gravity);
				idleAnimation(paint);
			}
		});
		fallThread.start();
	}

	public void crouchAnimation(){
		if(c < 4){
			if(isFacingRight){
				imageFile = getClass().getResource("adventurer/crouch-0"+state+".png");
			}else{				
				imageFile = getClass().getResource("adventurer/crouch-0"+state+".png");
			}
			c++;
		}else{
			c = 0;
		}	
		try{
			paint.repaint();
			image = ImageIO.read(resource);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void playerMana(Draw canvas){
		
		Thread manaThread = new Thread(new Runnable(){
			public void run(){
				for(int m = 0; m < 7; m++){
					if(isFacingRight == true){
						imageFile = getClass().getResource("adventurer/cast-0"+m+".png");
					}else{
						imageFile = getClass().getResource("adventurer/cast-0"+m+".png");
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
				fireMana(canvas);
				idleAnimation(canvas);
			}
		});
		manaThread.start();
	}

	public void fireMana(Draw canvas){
		if(missileList.size() != 11){
			
			magicMissile[magicAmmo] = new MagicMissile(xPos + 20, yPos, canvas);
			
			missileList.add(magicMissile[magicAmmo]);
	
			magicAmmo++;
			
			isUsingMagic = true;
		}else{

		}	
		System.out.println(misslieTest.size() +"MISSiLE TEST");
	}

	public void swordAttack(){
		Thread swordThread = new Thread(new Runnable(){
			public void run(){
				for(int s = 0; s < 15; s++){
					if(useSword == true){
						if(isFacingRight == true){
							imageFile = getClass().getResource("adventurer/attack" +i+".png");
						}else{
							imageFile = getClass().getResource("adventurer/swordback" +i+".png");
						}
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
				drawSword = false;
				standingAnimation(canvas);
			}
		});
		swordThread.start();
	}

	public void checkDeath(){
		if(healthBar <= 0){
			isDead = true;
			isIdle = false;
			playerDeath();
		}
	}

	public void playerDeath(){
		
		for(int d = 0; d < 6; d++){	
			if(isFacingRight == true){
				imageFile = getClass().getResource("adventurer/die-0"+d+".png");
			
			}else{
				imageFile = getClass().getResource("adventurer/dieback"+d+".png");
			}

			try{
				canvas.repaint();
				playerImage = ImageIO.read(imageFile);

			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public void moveLeft(){
		xPos-= 5;
		isIdle = false;
		isFacingRight = false;
		checkDeath();
		movementAnimation();
	}

	public void moveRight(){
		xPos = xPos + 5;
		isIdle = false;
		isFacingRight = true;
		checkDeath();
		movementAnimation();
	}

	public void jump(){
		isIdle = false;
		checkDeath();
		jumpingAnimation();
	}

	public void crouch(){
		isIdle = false;
		checkDeath();
		crouchAnimation();
	}
	public void useMagic(Draw canvas){
		isIdle = false;
		verifyDeath();
		playerMagic(canvas);
	}

	public void useSword(){
		isIdle = false;
		isUsingMagic = false;
		drawSword = true;
		canvas.collisionDetection();
		checkDeath();
		swordAttack();
	}
}