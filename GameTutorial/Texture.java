import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.awt.image.BufferedImage;

public class Texture{
	
	private final static Map<String,TextureManager> textMap = new HashMap<String, TextureManager>();
	private TextureManager manager;
	private String filename;


	public Texture(String filename){
		this.filename = filename;
		TextureManager oldText = textMap.get(filename);
		if(oldText != null){
			manager = oldText;
				manager.addReference();

		}else{
			try{
				manager = new TextureManager(ImageIO.read(new File("Adventurer/" + filename+".png")));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	protected void finalize() throws Throwable{
		if(manager.removeReference() && !filename.isEmpty()) textMap.remove(filename);
		super.finalize();
	}
	public void render(Graphics g, double x, double y){
		g.drawImage(manager.getImage(), (int) x, (int) y, null);
	}
	public BufferedImage getImage(){
		return manager.getImage();
	}
}