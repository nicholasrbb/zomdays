package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;


public class Zombie extends Sprite{
	private AffineTransform npcOrientation;
	public boolean up = false;
	public boolean right = false;
	public boolean left = false;
	public boolean down = false;
	private Sprite targetPlayer;
	private double damage = 0.05;
		
	public Zombie(Image npcImage, int health, int width, int height, int x, int y, double dx, double dy, TileMap Map) {
		super(health, width, height, x, y, dx, dy, Map);
		this.image = npcImage;
		npcOrientation = new AffineTransform();
	}
	
	public Sprite whichPlayer(){
		int closestX = map.PlayerList.get(0).getX();
		int closestY = map.PlayerList.get(0).getY();
		Sprite player = map.PlayerList.get(0);
		
		for (int i = 1; i < map.PlayerList.size(); i++){
			if (map.PlayerList.get(i).getX() != PositionX || map.SpriteList.get(i).getY() != PositionY){
				if ( (Math.abs(PositionX - map.PlayerList.get(i).getX()) < Math.abs(PositionX - closestX)) && ((Math.abs(PositionY - map.PlayerList.get(i).getY()) < Math.abs(PositionY - closestY)))){
					closestX = map.PlayerList.get(i).getX();	
					closestY = map.PlayerList.get(i).getY();
					player = map.PlayerList.get(i);
				}
			}
		}
		return player;
	}
	
	public void Movement(long time){
		Sprite target = whichPlayer();
		targetPlayer = target;
		
		if ( (target.getX() - 35) > getX()){
			right = true;
		}
		if ( (target.getX() + 35) < getX()){
			left = true;
		}
		if ( (target.getY() + 35) < getY()){
			up = true;
		}
		if ( (target.getY() - 35) > getY()){
			down = true;
		}
		if (up && !right && !left && !down){
			makeMovement(Direction.UP, time);
		}
		if (!up && right && !left && !down){
			makeMovement(Direction.RIGHT, time);
		}
		if (!up && !right && left && !down){
			makeMovement(Direction.LEFT, time);
		}
		if (!up && !right && !left && down){
			makeMovement(Direction.DOWN, time);
		}
		if (up && right && !left && !down){
			makeMovement(Direction.UPRIGHT, time);
		}
		if (up && !right && left && !down){
			makeMovement(Direction.UPLEFT, time);
		}
		if (!up && right && !left && down){
			makeMovement(Direction.DOWNRIGHT, time);
		}
		if (!up && !right && left && down){
			makeMovement(Direction.DOWNLEFT, time);
		}
		up = false;
		right = false;
		left = false;
		down = false;
		//System.out.println("Zombie position is: " + getX() + "    " + getY());
	}
	
	public synchronized void setSpriteOrientation(){
		int x = targetPlayer.getX();
		int y = targetPlayer.getY();
		solveAngle(x,y);
		
		npcOrientation.setToTranslation(getX()-25, getY()-25);
		npcOrientation.rotate(Math.toRadians(angle), image.getWidth(null)/2, image.getHeight(null)/2);	
	}
	
	public AffineTransform getSpriteOrientation(){
		return npcOrientation;
	}

	@Override
	public void setPlayerSpriteOrientation(int x, int y) {
		
	}
	public void attack(){
		targetPlayer.updateHealth(-damage);
	}

	
	

}
