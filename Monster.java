import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Monster{
	
	public int xPos = 150;
	public int yPos = 300;

	public BufferedImage image;
	public URL resource = getClass().getResource("slime/idle0.png");

	public Monster(){
		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public Monster(int xPass, int yPass){
		xPos = xPass;
		yPos = yPass;

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}