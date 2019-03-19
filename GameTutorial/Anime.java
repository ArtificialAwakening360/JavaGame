

public class Anime{
	
	private int count;
	private int index;
	private int speed;
	private int numFrames;
	private Texture currentFrame;
	private Texture frames[];

	public Anime(int speed, Texture... frames){
		this.speed = speed;
		this.frames = frames;
		this.numFrames = numFrames;
	}
	private void nextFrame(){
		currentFrame = frames[index++];
		if(index> numFrames){
			index = 0;
		}
	}
	public void running(){
		count++;
		if(count>speed){
			index = 0;
			nextFrame();
		}
	}
	public void render(Graphics2D g, double x, double y){
		if(currentFrame != null){
			currentFrame.render(g, x, y);
		}
	}
}