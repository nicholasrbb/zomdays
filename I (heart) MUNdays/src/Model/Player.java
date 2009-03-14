package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;


public class Player extends Sprite{
	private AffineTransform playerOrientation;
	public boolean up = false;
	public boolean right = false;
	public boolean left = false;
	public boolean down = false;
	
	
	
	public Player(Image playerImage, int health, int width, int height, int x, int y, double dx, double dy, TileMap Map) {
		super(health, width, height, x, y, dx, dy, Map);
		this.image = playerImage;
		playerOrientation = new AffineTransform();
	}

	
	
	public void Movement(long time){
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
		//System.out.println("Player position is: " + getX() + "    " + getY());
	}

	
	
	
	public AffineTransform getSpriteOrientation(){
		return playerOrientation;
	}

	@Override
	public void setSpriteOrientation() {
		
	}



	@Override
	public void setPlayerSpriteOrientation(int xPos, int yPos) {
		
		
		
		solveAngle(mouseX, mouseY);
		
		playerOrientation.setToTranslation(getX()-xPos-25, getY()-yPos-25);	
		playerOrientation.rotate(Math.toRadians(angle),image.getWidth(null)/2, image.getHeight(null)/2);
			
			
	}
	
	
	
}
