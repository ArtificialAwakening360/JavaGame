import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Menu{
	
	private final String option[] = {"PLAY", "OPTIONS", "EXIT"}; //selection 0, 1 or 2
	private int currentSelection;


	public void render(Graphics g){

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameFrame.width, GameFrame.height);
		Fonts.drawString(g, new Font("Arial", Font.BOLD, 72), Color.RED, GameFrame.title, 80);

		for(int i = 0; i <option.length; i++){
			if(i == currentSelection){
				Fonts.drawString(g, new Font("Arial", Font.ITALIC, 48), Color.BLUE, option[i], 200 + i *80);
			}else 
				Fonts.drawString(g, new Font("Arial", Font.PLAIN, 32), Color.GREEN, option[i], 200 + i *80);
		}
	}
}