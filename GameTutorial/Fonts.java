import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.FontMetrics;

public class Fonts{




	public static void drawString(Graphics g, Font f, Color c, String text, int x, int y){
		
		g.setColor(c);
		g.setFont(f);
		g.drawString(text,x, y);
	}
	
	public static void drawString(Graphics g, Font f, Color c, String text){
		
		FontMetrics fm = g.getFontMetrics(f);
		int x = (GameFrame.width - fm.stringWidth(text)) /2; //horizontal center
		int y = ((GameFrame.height - fm.getHeight()) / 2) + fm.getAscent(); //Vertical Center

		drawString(g, f, c, text, x, y);
	}
	public static void drawString(Graphics g, Font f, Color c, String text, double x){
		
		FontMetrics fm = g.getFontMetrics(f);
		int y = ((GameFrame.height - fm.getHeight()) / 2) + fm.getAscent(); //Vertical Center

		drawString(g, f, c, text, (int)x, y);
	}
	public static void drawString(Graphics g, Font f, Color c, String text, int y){
		
		FontMetrics fm = g.getFontMetrics(f);
		int x = (GameFrame.width - fm.stringWidth(text)) /2; //horizontal center
		drawString(g, f, c, text, x, y);
	}
}