package Model;

public class Bullet {
	public double Duration;
	public boolean visible;
	private double BulletTime;
	
	public int xStart;
	public int yStart;
	public int xEnd;
	public int yEnd;
	
	
	public Bullet(int xStart, int yStart, int xEnd, int yEnd){
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.xStart = xStart;
		this.yStart = yStart;
		visible = true;
		
		Duration = (double) System.currentTimeMillis();
		BulletTime = 35;
		
	}
	
	public void updateBullet(){
		if (System.currentTimeMillis() - Duration > BulletTime){
			visible = false;
		}
	}

}
