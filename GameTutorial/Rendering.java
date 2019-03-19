import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class Texture{
	
	private final static Map<String,Texture> textMap = new HasMap<String, Texture>();
	private BufferedImage image;


	public Texture(String filename){
		try{
			image = ImageIO.read(new File("resources/textures" + filename+".png"));
			textureMap.put(filename, this);
		}catch(IOException e){
			e.printStackTrace();
		}

	}
}