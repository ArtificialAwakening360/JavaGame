import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Rectangle;
import java.util.ArrayList;

public class MagicAmount{

	public BufferedImage magicImage;
	public URL magicFile = getClass().getResource("manaImages/mana0.png");

	public Draw canvas;

	public int xPos; 
	public int yPos;

	public ManaAmount(int x, int y, Draw canvas){

		this.canvas = canvas;

		this.xPos = x;
		this.yPos = y;

		try{
			magicImage = ImageIO.read(magicFile);
		}catch(IOException e){
			e.printStackTrace();
		}
		magicCycle();
	}	

	public void magicCycle(){
		Thread magicThread = new Thread(new Runnable(){
			public void run(){
				while(true){
					for(int m = 0; m < 8; m++){
						magicFile = getClass().getResource("magicImages/mana"+m+".png");

						try{
							magicImage = ImageIO.read(magicFile);
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
		magicThread.start();
	}
}