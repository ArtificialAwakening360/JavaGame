import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Rectangle;
import javax.swing.JComponent;

public class Monster2{
	
	public BufferedImage monsterImage2;
	public URL monsterFile2 = getClass().getResource("knight/idle0.png");

	public int xPos;
	public int yPos;
	public int width;
	public int height;

	public int hp = 30;
	public int atk = 5;

	public boolean isIdle = false;
	public boolean isMoving = true;
	public boolean isFacingRight = false;
	public boolean isFacingLeft = false;
	public boolean isAttacking = false;
	public boolean isDead = false;

	public JComponent comp;

	public Monster2(JComponent comp){

		try{
			monsterImage2 = ImageIO.read(monsterFile2);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public Monster2(int x, int y, JComponent comp){
		xPos = x;
		yPos = y;
		try{
			monsterImage2 = ImageIO.read(monsterFile2);
		}catch(IOException e){
			e.printStackTrace();
		}
		this.comp = comp;
		this.width = monsterFile2.getWidth();
		this.height = monsterFile2.getHeight();
		idleMonster2();
	}

	public Rectangle monsterBounds2(){
		return (new Rectangle(xPos, yPos, width, height));
	}

	public boolean monsterDirection2(){
		if(xPos < comp.player.xPos){
			return isFacingRight = false;
		}else{
			return isFacingRight = true;
		}
	}

	public void idleMonster2(){
		Thread idleThread2 = new Thread(new Runnable(){
			public void run(){
				while(isIdle){
					for(int i = 0; i < 4; i ++){
						monsterFile2 = getClass().getResource("knight/idle"+i+".png");
						try{
							comp.repaint();
							monsterImage2 = ImageIO.read(monsterFile2);
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
		idleThread2.start();
	}

	public void movementMonster2(){
		isIdle = false;
		isMoving = true;
		monsterDirection2();
		Thread moveThread2 = new Thread(new Runnable(){
			public void run(){
				while(isMoving){
					for(int m = 0; m < 4; m++){ 
						if(isFacingRight == true){
							if(xPos > comp.player.xPos){
								xPos--;
								monsterFile2 = getClass().getResource("knight/idle"+m+".png");
							}
						
						}else{
							monsterFile2 = getClass().getResource("knight/idle"+m+".png");
							xPos++;
						}
						try{
							comp.repaint();
							monsterImage2 = ImageIO.read(monsterFile2);
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
		moveThread2.start();
	}

	public void attackMonster2(){
		isMoving = false;
		monsterDirection2();
		for(int a = 0; a < 10; a++){
			if(isFacingRight == true && isAttacking == true){
				monsterFile2 = getClass().getResource("knight/attack"+a+".png");
				xPos-=5;
			}else{
				monsterFile2 = getClass().getResource("knight/attack"+a+".png");
				xPos+=5;
			}

			try{
				comp.repaint();
				monsterImage2 = ImageIO.read(monsterFile2);
				Thread.sleep(200);

			}catch(IOException e){
				e.printStackTrace();
			
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public boolean chkHealth2(){
		if(hp <= 0){
			isAttacking = false;
			isMoving = false;
			return true;
		}
		return false;
	}

	public void monsterDeath2(){
		for(int d = 0; d < 4; d++){
			monsterFile2 = getClass().getResource("knight/idle"+d+".png");
			try{
				comp.repaint();
				monsterImage2 = ImageIO.read(monsterFile2);
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