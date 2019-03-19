import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JComponent;

public class Player {

	public BufferedImage image;
	public URL resource = getClass().getResource("adventurer/idle-00.png");

	public int xPos = 50;
	public int yPos = 370;
	public int height;
	public int width;
	public int state = 0;

	public int healthBar = 200;
	public int power = 7;
	public int defense = 2;
	public int manaPoints = 10;

	public boolean isIdle = true;
	public boolean isFacingRight = true;
	public boolean isJumping = false;
	public boolean isUsingMagic = false;
	public boolean isDead = false;
	public boolean useSword = false;

	public int gravity = 370;

	public ArrayList<MagicMissile> missileList = new ArrayList<>(15);
	public MagicMissile magicMissile[] = new MagicMissile[15];
	public int magicAmount = 1;

	public JComponent comp;

	public Player(JComponent comp){
		try{
			image = ImageIO.read(resource);
		}catch(IOException e){
			e.printStackTrace();
		}

		this.comp = comp;
		idleAnimation(comp);
	}
	
	public Player(int x, int y, JComponent comp){
		x = xPos;
		y = yPos;

		try{
			image = ImageIO.read(resource);
		}catch(IOException e){
			e.printStackTrace();
		}
		this.comp = comp;

		width = image.getWidth();
		height = image.getHeight();

		idleAnimation(comp);
	}

	public Rectangle playerBounds(){
		return(new Rectangle (xPos, yPos, width, height));
	}

	public void idleAnimation(JComponent comp){
		isIdle = true;
		Thread idleThread = new Thread (new Runnable(){
			public void run(){
				while(isIdle){
					for(int ctr = 0; ctr < 4; ctr++){
						if(isFacingRight == true){
							resource = getClass().getResource("adventurer/idle-2-0"+ctr+".png");
						}else{
							resource = getClass().getResource("adventurer/idleback"+ctr+".png");
						}
						checkDeath();

						try{
							image = ImageIO.read(resource);
							Thread.sleep(1000/30);
							comp.repaint();

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
			if(state < 6){
				if(isFacingRight == true){
					resource = getClass().getResource("adventurer/run"+state+".png");
				}else{
					resource = getClass().getResource("adventurer/runback"+state+".png");
				}
				state++;
			}else{
				state = 0;
			}

			try{
				comp.repaint();
				image = ImageIO.read(resource);
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
						comp.repaint();
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
						comp.repaint();
						image = ImageIO.read(resource);
						Thread.sleep(1000/30);
					}catch(IOException e){
						e.printStackTrace();
					}catch(InterruptedException e){
						e.printStackTrace();
					}						
				}while(yPos < gravity);
				idleAnimation(comp);
			}
		});
		fallThread.start();
	}

	public void crouchAnimation(){
		if(state < 4){
			if(isFacingRight){
				resource = getClass().getResource("adventurer/crouch-0"+state+".png");
			}else{				
				resource = getClass().getResource("adventurer/crouchback"+state+".png");
			}
			state++;
		}else{
			state = 0;
		}	
		try{
			comp.repaint();
			image = ImageIO.read(resource);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void playerMana(JComponent comp){
		
		Thread manaThread = new Thread(new Runnable(){
			public void run(){
				for(int m = 1; m < 9; m++){
					if(isFacingRight == true){
						resource = getClass().getResource("adventurer/cast-0"+m+".png");
					}else{
						resource = getClass().getResource("adventurer/cast-0"+m+".png");
					}

					try{
						comp.repaint();
						image = ImageIO.read(resource);
						Thread.sleep(1000/30);
					}catch(IOException e){
						e.printStackTrace();

					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				fireMana(comp);
				idleAnimation(comp);
			}
		});
		manaThread.start();
	}

	public void fireMana(JComponent comp){
		if(missileList.size() != 10){
			
			magicMissile[magicAmount] = new MagicMissile(xPos + 20, yPos, comp);
			
			missileList.add(magicMissile[magicAmount]);
	
			magicAmount++;
			
			isUsingMagic = true;
		}else{

		}	
		System.out.println(missileList.size() +"MISSILE TEST");
	}

	public void swordAttack(){
		Thread swordThread = new Thread(new Runnable(){
			public void run(){
				for(int s = 0; s < 15; s++){
					if(useSword == true){
						if(isFacingRight == true){
							resource = getClass().getResource("adventurer/attack"+s+".png");
						}else{
							resource = getClass().getResource("adventurer/swordback"+s+".png");
						}
					}

					try{
						comp.repaint();
						image = ImageIO.read(resource);
						Thread.sleep(1000/30);

					}catch(IOException e){
						e.printStackTrace();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				useSword = false;
				idleAnimation(comp);
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
				resource = getClass().getResource("adventurer/die-0"+d+".png");
			
			}else{
				resource = getClass().getResource("adventurer/dieback"+d+".png");
			}

			try{
				comp.repaint();
				image = ImageIO.read(resource);

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
		moveAnimation();
	}

	public void moveRight(){
		xPos = xPos + 5;
		isIdle = false;
		isFacingRight = true;
		checkDeath();
		moveAnimation();
	}

	public void jump(){
		isIdle = false;
		checkDeath();
		jumpAnimation();
	}

	public void crouch(){
		isIdle = false;
		checkDeath();
		crouchAnimation();
	}
	public void useMagic(JComponent comp){
		isIdle = false;
		checkDeath();
		playerMana(comp);
	}

	public void useSword(JComponent comp){
		isIdle = false;
		isUsingMagic = false;
		useSword = true;
		// comp.collisionDetection();
		checkDeath();
		swordAttack();
	}
}