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

			for(int i = 0; i < monsterList.size(); i++){
				if(monsterList.size() != 0){
					g.drawImage(monsterList.get(i).monsterImage, monsterList.get(i).xPos, creatureList.get(i).yPos, this);
					g.setColor(Color.RED);
					g.fillRect(monsterList.get(i).xPos, monsterList.get(i).yPos, 35, 2);
					g.setColor(Color.GREEN);
					g.fillRect(monsterList.get(i).xPos, monsterList.get(i).yPos, creatureList.get(i).health, 2);
				}		
			}
	}

	public void startGame(){
		Thread gameThread = new Thread(new Runnable(){
			public void run(){
				while(true){
					try{
						for(int c = 0; c < monsters.length; c++){
							if(monsters[c]!=null){
								monsters[c].moveTo(x,y);
								repaint();
							}
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
			}
		});
		gameThread.start();
	}

	public void spawnEnemy(){
		if(enemyCount < 10){
			monsters[enemyCount] = new Monster(randomizer.nextInt(500), randomizer.nextInt(500), this);
			enemyCount++;
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