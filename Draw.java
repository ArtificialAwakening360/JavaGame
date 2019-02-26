import javax.swing.JComponent;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Draw extends JComponent implements ActionListener{

	public boolean beginGame = false;
	public Rectangle buttonOne;
	public int button1_x = 170;
	public int button1_y = 150;
	public int button1_width = 200;
	public int button1_height = 50;

	public MouseMotion mouse;
	public Rectangle mouseBounds;

	public Rectangle buttonTwo;
	public int button2_x = 170;
	public int button2_y = 250;
	public int button2_width = 200;
	public int button2_height = 50;

	private final int gravity = 370;
	private final int x = 50;
	private BufferedImage backgroundImage;
	private URL backgroundFile = getClass().getResource("day.png");

	public Player player;
	public int playerX = 20;
	public int playerY = 60;

	public ManaAmount mana[];
	public ArrayList<ManaAmount>manaList = new ArrayList<>();
	public int manaCapacity = 1;
	public MagicMissle magicMissle;
	public int manaX = 55;
	public int manaY = 48;
	public int bManaX = 65;
	public int bManaY = 57;

	public Monster monster;
	public ArrayList<Monster> monsterList = new ArrayList<>();
	public int counter = 1;
	public Timer timer = new Timer(20000, this);

	public boolean magicHits = false;

	public Draw(){
		timer.start();
		spawnPlayer();
		spawnCreature();
		
		mouse = new MouseMotion();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		try{
			backgroundImage = ImageIO.read(backgroundFile);
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		startGame();
	}

	public void spawnPlayer(){
		player = new Player(x, gravity, this);
		mana = new ManaAmount[10];

		for(int i = 0; i < 10; i++){
			mana[i] = new ManaAmount(manaX, manaY, this);
			manaList.add(mana[i]);
			manaX+=20;
		}
	}

	public void spawnMonster(){
		int spawnPostion = 510;
		if(monsterList.size() != 10){
			creature = new Creature(spawnPostion, gravity + 10, this);
			creatureList.add(creature);
			counter++;
		}	
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 600, 500);
			g.drawImage(backgroundImage, 0, 0, this);

			g.drawImage(player.playerImage, player.xPos, player.yPos, this);

			loadPlayerStats(g);

			for(int m = 0; m <player.missileList.size(); m++ ){
				if(player.isCasting == true){
					if(player.missileList.size() != 0){	
						g.drawImage(player.missileList.get(m).magicImage,player.missileList.get(m).magicX, player.missileList.get(m).magicY, this);	
					}
				}
			}

			for(int r = 0; r < monsterList.size(); r++){
				if(monsterList.size() != 0){
					g.drawImage(monsterList.get(r).monsterImage, monsterList.get(r).xPos, monsterList.get(r).yPos, this);
					g.setColor(Color.RED);
					g.fillRect(monsterList.get(r).xPos, monsterList.get(r).yPos, 35, 2);
					g.setColor(Color.GREEN);
					g.fillRect(monsterList.get(r).xPos, monsterList.get(r).yPos, monsterList.get(r).health, 2);
				}		
			}
	}

	public void loadPlayerStats(Graphics g){

		String health = String.valueOf(player.healthBar);
		g.setColor(Color.BLACK);
		g.fillRect(6, 18, 264, 34);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(8, 20, 260, 30);

		g.setColor(Color.MAGENTA);
		g.fillRect(6, 53, 264, 24);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(8, 55, 260, 20);

		g.setColor(Color.RED);
		g.fillRect(50, 25, 200, 10);
		g.setColor(Color.GREEN);
		g.fillRect(50, 25, player.healthBar, 10);

		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString("Health:", 10, 34);
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString(health + "/200", 60, 47);

		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString("Mana:", 10, 66);

		for(int m = 0; m < manaList.size(); m++){
			g.drawImage(manaList.get(m).manaImage, manaList.get(m).xPos, manaList.get(m).yPos, this);
		}
	}

	public void startGame(){
		Thread startThread = new Thread(new Runnable(){
			public void run(){
				try{	
					while(true){
						
						for(int m = 0; m < monsterList.size(); m++){

							if(monsterList.get(m).checkHealth() == true){
								monsterList.get(m).monsterDeath();
								collisionDetection();
								eraseImages();
							} 
							if(monsterList.get(m).isAttacking != true){
								monsterList.get(m).movementMonster();
								collisionDetection();

							}else {
								monsterList.get(m).attackMonster();
								System.out.println("monster attack");
								collisionDetection();
								damageDetection();

							}
						}
						Thread.sleep(350);
					}
				}catch(ArrayIndexOutOfBoundsException e){
					e.printStackTrace();
					spawnCreature();
					
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		});
		gameThread.start();
	}

	public void actionPerformed(ActionEvent arg0) {
		spawnMonster();	
	}

	public void collisionDetection(){
		
		for(int c = 0; c < monsterList.size(); c++){
			Rectangle playerBounds = player.playerBounds();
			Rectangle monsterBounds = monsterList.get(c).monsterBounds();
			
			if(monsterList.size() != 0){
				if(playerBounds.intersects(monsterBounds)){
					monsterList.get(c).isAttacking = true;
					System.out.println("INTERSECT MONSTERS");
				
				}else{
					creatureList.get(c).isAttacking = false;
					creatureList.get(c).isMoving = true;
				}
				if(player.drawsSword == true){
					if(playerBounds.intersects(monsterBounds)){
						System.out.println("Player attacks");
						monsterList.get(c).health-=player.power;
					}
				}
				for(MagicMissle playerMagic: player.missileList){
					if(playerMagic.magicBounds().intersects(monsterBounds)){
						playerMagic.magicHit();
						damageDetection();
						eraseImages();
						break;
					}
				}
			}
		}
	}

	public void damageDetection(){

		for(int m = 0; m < monsterList.size(); m ++){
			for(int p = 0; p < player.missleList.size(); p++){
				System.out.println("Assessing");
				
				if(player.missleList.get(j).missleImpact == true){
					System.out.println("Assessing Magic Damage");
					creatureList.get(j).health-=player.missleList.get(j).magicDmg;
				}	
			}
		}

		for(int m = 0; m < monsterList.size(); m ++){
			if(monsterList.get(i).isAttacking == true){
				player.healthBar = player.healthBar - (monsterList.get(d).power / player.defense);
				System.out.println("Assessing Monster Damage");
			}
		}
	}

	public void swordAnimation(){
		Thread swordThread = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 17; ctr++){
					try {
						if(ctr==17){
							resource = getClass().getResource("adventurer/adventurer-swrd-drw-00.png");
						}
						else{
							resource = getClass().getResource("adventurer/attack"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		swordThread.start();
		for(int x=0; x<monsters.length; x++){
					if(monsters[x]!=null){
						if(monsters[x].contact){
							monsters[x].life = monsters[x].life - 10;
						}
					}
				}

	}

	public void castAnimation(){
		Thread castThread = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 1; ctr < 5; ctr++){
					try {
						if(ctr==5){
							resource = getClass().getResource("adventurer/idle-00.png");
						}
						else{
							resource = getClass().getResource("adventurer/cast-0"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		castThread.start();
		for(int x=0; x<monsters.length; x++){
							if(monsters[x]!=null){
								if(monsters[x].contact){
									monsters[x].life = monsters[x].life - 10;
								}
							}
						}
	}

	public void bowAnimation(){
		Thread bowThread = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 10; ctr++){
					try {
						if(ctr==9){
							resource = getClass().getResource("adventurer/idle-2-03.png");
						}
						else{
							resource = getClass().getResource("adventurer/bow-0"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		bowThread.start();
		for(int x=0; x<monsters.length; x++){
					if(monsters[x]!=null){
						if(monsters[x].contact){
							monsters[x].life = monsters[x].life - 10;
						}
					}
				}
	}



	public void attackSword(){
		swordAnimation();
	}

	public void attackBow(){
		bowAnimation();
	}

	public void attackCast(){
		castAnimation();
	}
	
	public void checkCollision(){
		int xChecker = x + width;
		int yChecker = y;

		for(int x=0; x<monsters.length; x++){
			boolean collideX = false;
			boolean collideY = false;

			if(monsters[x]!=null){
				monsters[x].contact = false;

				if(yChecker > monsters[x].yPos){
					if(yChecker-monsters[x].yPos < monsters[x].height){
						collideY = true;
					}
				}
				else{
					if(monsters[x].yPos - yChecker < monsters[x].height){
						collideY = true;
					}
				}

				if(xChecker > monsters[x].xPos){
					if(xChecker-monsters[x].xPos < monsters[x].width){
						collideX = true;
					}
				}
				else{
					if(monsters[x].xPos - xChecker < 5){
						collideX = true;
					}
				}
			}

			if(collideX && collideY){
				System.out.println("collision!");
				monsters[x].contact = true;
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		g.drawImage(backgroundImage, 0, 0, this);

		// character grid for hero
		// g.setColor(Color.YELLOW);
		// g.fillRect(x, y, width, height);
		g.drawImage(image, x, y, this);
		
		for(int c = 0; c < monsters.length; c++){
			if(monsters[c]!=null){
				// character grid for monsters
				// g.setColor(Color.BLUE);
				// g.fillRect(monsters[c].xPos, monsters[c].yPos+5, monsters[c].width, monsters[c].height);
				g.drawImage(monsters[c].image, monsters[c].xPos, monsters[c].yPos, this);
				g.setColor(Color.GREEN);
				g.fillRect(monsters[c].xPos+7, monsters[c].yPos, monsters[c].life, 2);
			}	
		}
	}
}