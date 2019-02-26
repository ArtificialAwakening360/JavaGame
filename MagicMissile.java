import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Rectangle;

public class MagicMissle{

	//Magic Image
	public BufferedImage magicImage;
	public URL magicFile = getClass().getResource("playerMagic/magic1.png");

	//Magic position
	public int magicX;
	public int magicY;
	public int magicWidth;
	public int magicHeight;

	public Draw draw;

	//Magic States
	public boolean faceRight = true;
	public boolean magicActive = false;
	public boolean missleImpact = false;
	public boolean missleDmg = false;
	
	// Magic Stats
	public int magicDmg = 7;

	public MagicMissle(Draw canvas){

		try{
			magicImage = ImageIO.read(magicFile);
		}catch(IOException e){
			e.printStackTrace();
		}
		usingMagic();
	}
	public MagicMissle(int x, int y, Draw canvas){
		//Turns on magic
		magicActive = true;
		//Assigns magic postion
		magicX = x;
		magicY = y;

		try{
			magicImage = ImageIO.read(magicFile);
		}catch(IOException e){
			e.printStackTrace();
		}
		//Assigns magic variables as image size
		this.magicWidth = magicImage.getWidth();
		this.magicHeight = magicImage.getHeight();

		//Goes to using magic method
		usingMagic();
	}

	//For magic Collision sets the rectangle as image of magic missle
	public Rectangle magicBounds(){
		return (new Rectangle(magicX, magicY, magicWidth, magicHeight));
	}

	public void usingMagic(){
		magicActive = true;
		Thread magicThread = new Thread(new Runnable(){
			public void run(){
				//While magic is being used
				while(magicActive){
					
					//First four frames do this code
					for(int i = 0; i < 7; i++){
						if(i < 4){
							magicFile = getClass().getResource("playerMagic/magic"+i+".png");
							magicX+=10;

							//else do this code until impact or magic goes off screen
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
		//Turns off magic active
		magicActive = false;
		for(int i = 0; i <8; i++){
				magicFile = getClass().getResource("playerMagic/hit" + i+".png");
		
			try{
				magicImage = ImageIO.read(magicFile);

			}catch(IOException e){
				e.printStackTrace();

			}
		}
		//After running hit animation turn impact to true	
		missleImpact = true;
		System.out.println("Magic Strike Hit!");		
	}
}