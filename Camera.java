public class Camera{
	private int x;
	private int y;

	public Camera(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void tick(GameObject player){

		if(player.getX() >= 406){
			x = -player.getX() + Game.WIDTH/2;
		}

			y = -player.getY() + Game.HEIGHT/2;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

}