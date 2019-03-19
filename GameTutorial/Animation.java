import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.IOException;

public class Animation extends JComponent{

  //First Character Stats
  public int x = 20;
  public int y = 300;

  public int x2 = 400;
  public int y2 = 300;

  private int height;
  private int width;

  private BufferedImage image;
  private BufferedImage image2;
  private URL character;
  private URL character2;
  private URL characterMovement[];
  private URL characterRunningBack[];
  private URL characterJump[];
  private URL staticMovement[];

  private int frame = 0;

  public boolean moving;
  public boolean jumpingFrame;


  public Animation(){
    initiateImage();

    try{
    image = ImageIO.read(character);
    image2 = ImageIO.read(character2);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    g.drawImage(image, x, y, this);
    g.drawImage(image2, x2, y2, this);
  }
  public void initiateImage(){
    character = getClass().getResource("idle/idle0.png");
    character2 = getClass().getResource("idle/idle0.png");
    staticMovement = new URL[8];
    moving = true;
    frame = 0;
    Thread t1 = new Thread(){
      public void run(){
        do{
          for(int i = 0; i < 8; i++){
            if(i == frame){
              staticMovement[i] = getClass().getResource("idle/idle"+i+".png");
              character = staticMovement[i];
            }else if(frame > 7){
              frame = 0;
              i = 0;
              character = staticMovement[i];
            }
          }
            repaint();
            frame++;
            try{
              Thread.sleep(1000/30);
              image = ImageIO.read(character);
            }catch(IOException e){
              e.printStackTrace();
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          }while(moving == true);
        }
      };
      t1.start();
    }
  public void characterRunning(){
    characterMovement = new URL [6];
    moving = false;
    for(int i = 0; i < 6; i ++){
      if(i == frame){
      characterMovement[i] = getClass().getResource("running/run"+i+".png");
      character = characterMovement[i];
    }else if(frame > 5){
      i = 0;
      frame = 0;
      characterMovement[i] = getClass().getResource("running/run"+i+".png");
      character = characterMovement[i];
    }
    }
    frame++;
    try{
      image = ImageIO.read(character);
    }catch(IOException e){
      e.printStackTrace();
  }
  }
  public void characterRunningBack(){
    characterRunningBack = new URL [6];
    moving = false;
    for(int i = 0; i < 6; i ++){
      if(i == frame){
      characterRunningBack[i] = getClass().getResource("running/back"+i+".png");
      character = characterRunningBack[i];
    }else if(frame > 5){
      i = 0;
      frame = 0;
      characterRunningBack[i] = getClass().getResource("running/back"+i+".png");
      character = characterRunningBack[i];
    }
    }
    frame++;
    try{
      image = ImageIO.read(character);
    }catch(IOException e){
      e.printStackTrace();
  }
  }
  public void characterJump(){

    }

  public void left(){
    characterRunningBack();
    x += -5;
    repaint();
  }
  public void right(){
    characterRunning();
    x += 5;
    repaint();
  }
  public void up(){
    characterJump();
    y += -5;
    repaint();
  }
  public void down(){
    y += 5;
    repaint();
  }

}
