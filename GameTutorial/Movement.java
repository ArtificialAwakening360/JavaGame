import javax.swing.JComponent;

public class Movement extends JComponent{

  private int x;
  private int y;

  private int speedX;
  private int speedY;
  private Animation animate;

  public Movement(){

  }
  public void left(){
    animate.x += -5;
    repaint();
  }
  public void right(){
    animate.x += 5;
    repaint();
  }
  public void up(){
    animate.y += -5;
    repaint();
  }
  public void down(){
    animate.y += 5;
    repaint();
  }
}
