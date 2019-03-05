import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Rectangle;

public class MagicMissile{

	public BufferedImage magicImage;
	public URL magicFile = getClass().getResource("projectileMagic/magic1.png");

	public int magicX;
	public int magicY;
	public int magicWidth;
	public int magicHeight;

	public JComponent comp;

	public boolean faceRight = true;
	public boolean magicActive = false;
	public boolean missileImpact = false;
	public boolean missileDmg = false;
	
	public int magicDmg = 7;

	public MagicMissile(Draw canvas){

		try{
			magicImage = ImageIO.read(magicFile);
		}catch(IOException e){
			e.printStackTrace();
		}
		usingMagic();
	}
	public MagicMissile(int x, int y, JComponent comp){
		magicActive = true;
		magicX = x;
		magicY = y;

		try{
			magicImage = ImageIO.read(magicFile);
		}catch(IOException e){
			e.printStackTrace();
		}
	
		this.magicWidth = magicImage.getWidth();
		this.magicHeight = magicImage.getHeight();

		usingMagic();
	}

	public Rectangle magicBounds(){
		return (new Rectangle(magicX, magicY, magicWidth, magicHeight));
	}

	public void usingMagic(){
		magicActive = true;
		Thread magicThread = new Thread(new Runnable(){
			public void run(){
				
				while(magicActive){
					
					for(int i = 0; i < 7; i++){
						if(i < 4){
							magicFile = getClass().getResource("playerMagic/magic"+i+".png");
							magicX+=10;

						}else if(i >= 3 && i < 7){
							if(magicX < 600){
								magicFile = getClass().getResource("playerMagic/magic"+i+".png");
								magicX+=10;
							}else{
								magicActive = false;
							}
						}

						try{
							magicImage = ImageIO.read(magicFile);
							Thread.sleep(1000/30);

						}catch(IOException e){
							e.printStackTrace();
						}catch(InterruptedException e){
							e.printStackTrace();
						}				
					}
					System.out.println("magic moving");
				}
			}
		});
		magicThread.start();
	}

	public void magicHit(){
		magicActive = false;
		for(int i = 0; i <8; i++){
				magicFile = getClass().getResource("playerMagic/hit"+i+".png");
		
			try{
				magicImage = ImageIO.read(magicFile);

			}catch(IOException e){
				e.printStackTrace();

			}
		}	
		missileImpact = true;
		System.out.println("Magic Strike Hit!");		
	}
}