package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import View.Animation;

/**
 * Class that controls and determines parameters of all sprites. Gets and sets speed and health 
 * of all sprites, determines sprite movements and determines all collisions between sprites
 * on a map.
 */
public abstract class Sprite {
	protected Image image;
	double Health;
	boolean isAlive = false;
	int PositionX;
	int PositionY;
	int Height;
	int Width;
	double radius;
	double dx,dy;
	public TileMap map;
	public int mouseX;
	public int mouseY;
	public double angle;
	public enum Direction{ UP, RIGHT, LEFT, DOWN, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT }
	private ArrayList<Sprite> CollidedSprites;
	protected boolean random = true;
	protected int number;
	public Image Legs;
	public Image Blood;
	
	public ArrayList <Animation> animations;
	public ArrayList <Animation> bloodAnimations;
	public int currentAnimation = 0;
	public int currentWalkAnimation = 0;
	public int currentStateAnimation = 0;
	public ArrayList <Weapon> WeaponList;
	int currentWeapon = 0;
	
	public int Points;
	
	
	public Sprite(double health, int width, int height, int x, int y, double dx, double dy, TileMap Map){
		radius = 13;
		Health = health;
		isAlive = true;
		Height = height;
		Width = width;
		this.dx = dx;
		this.dy = dy;
		PositionX = x;
		PositionY = y;
		map = Map;
		animations = new ArrayList <Animation>();
		bloodAnimations = new ArrayList <Animation>();;
		currentAnimation = 0;
		currentWalkAnimation = 0;
		currentStateAnimation = 0;
	}
	
	public boolean getRandom(){
		return random;
	}
	
	public int getNumber(){
		return number;
	}
	
	/**
	 * returns player current weapon
	 * @return Int weapon
	 */
	public int getCurrentWeapon(){
		return currentWeapon;
	}
	
	/**
	 * get sprite Height
	 * @return Height
	 */
	public int getHeight(){
		return Height;
	}
	/**
	 * get sprite Width
	 * @return width
	 */
	public int getWidth(){
		return Width;
	}
	
	/**
	 * get sprite x position
	 * @return x
	 */
	public int getX(){
		return PositionX;
	}
	/**
	 * get sprite y Position
	 * @return y
	 */
	public int getY(){
		return PositionY;
	}
	/**
	 * set sprite X speed
	 */
	
	public void setXSpeed(double s){
		dx = s;
	}
	
	/**
	 * set sprite Y speed
	 */
	public void setYSpeed(double s){
		dy = s;
	}
	/**
	 * get sprite X speed
	 * @return dx
	 */
	public double getXSpeed(){
		return dx;
	}
	/**
	 * get sprite Y speed
	 * @return dy
	 */
	public double getYSpeed(){
		return dy;
	}
	
	/**
	 * update sprite health
	 * @param change
	 */
	public void updateHealth(double change){
		Health = Health + change;
		if (Health <= 0){
			isAlive = false;
		}
	}
	
	/**
	 * update sprite position based on time 
	 * and tests for collision
	 */
	
	protected void makeMovement(Direction direction, long time){
		int tempX1 = PositionX;
		int tempY1 = PositionY;
		
		double tempDistanceX = 0;
		double tempDistanceY = 0;
		
		if (getCollider(PositionX, PositionY) != null){
			CollidedSprites = getCollider(PositionX, PositionY);
			
		}
		
		
		
		switch( direction){
		
			case UP :
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				for (int i = 0; i < CollidedSprites.size(); i++){
					tempDistanceY = this.getY() - CollidedSprites.get(i).getY();
					if (tempDistanceY > 0){
						PositionY = tempY1;
					}
				}
				break ;
				
			case DOWN :
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				for (int i = 0; i < CollidedSprites.size(); i++){
					tempDistanceY = this.getY() - CollidedSprites.get(i).getY();
					if (tempDistanceY < 0){
					PositionY = tempY1;}
				}
				break ;
				
			case RIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				for (int i = 0; i < CollidedSprites.size(); i++){
					tempDistanceX = this.getX() - CollidedSprites.get(i).getX();
				if (tempDistanceX < 0){
					PositionX = tempX1;}
				}
				break ;	
				
			case LEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				for (int i = 0; i < CollidedSprites.size(); i++){
					tempDistanceX = this.getX() - CollidedSprites.get(i).getX();
				
					if (tempDistanceX > 0){
						PositionX = tempX1;
					}
				}
				break ;	
				
			case UPRIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				for (int i = 0; i < CollidedSprites.size(); i++){
					tempDistanceX = this.getX() - CollidedSprites.get(i).getX();
					tempDistanceY = this.getY() - CollidedSprites.get(i).getY();
				if (tempDistanceX < 0){
					PositionX = tempX1;
				}
				if (tempDistanceY > 0){
					PositionY = tempY1;}
				}
				break ;
				
			case UPLEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				for (int i = 0; i < CollidedSprites.size(); i++){
					tempDistanceX = this.getX() - CollidedSprites.get(i).getX();
					tempDistanceY = this.getY() - CollidedSprites.get(i).getY();
				if (tempDistanceX > 0){
					PositionX = tempX1;
				}
				if (tempDistanceY > 0){
					PositionY = tempY1;}
				}
				break ;
				
			case DOWNRIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				for (int i = 0; i < CollidedSprites.size(); i++){
					tempDistanceX = this.getX() - CollidedSprites.get(i).getX();
					tempDistanceY = this.getY() - CollidedSprites.get(i).getY();
				if (tempDistanceX < 0){
					PositionX = tempX1;
				}
				if (tempDistanceY < 0){
					PositionY = tempY1;}
				}
				break ;
				
			case DOWNLEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				for (int i = 0; i < CollidedSprites.size(); i++){
					tempDistanceX = this.getX() - CollidedSprites.get(i).getX();
					tempDistanceY = this.getY() - CollidedSprites.get(i).getY();
				if (tempDistanceX > 0){
					PositionX = tempX1;
				}
				if (tempDistanceY < 0){
					PositionY = tempY1;}
				}
				break ;	
				
		}
		
		if (map.getCharTile(tempX1/25, (PositionY+13)/25) != " " 
			&& map.getCharTile(tempX1/25, (PositionY+13)/25) != "1" 
			&& map.getCharTile(tempX1/25, (PositionY+13)/25) != "2"){
			if(PositionY > tempY1){
			PositionY = tempY1;}			
			
		}
		

		if (map.getCharTile(tempX1/25, (PositionY-13)/25) != " " 
			&& map.getCharTile(tempX1/25, (PositionY-13)/25) != "1" 
			&& map.getCharTile(tempX1/25, (PositionY-13)/25) != "2"){
			if(PositionY < tempY1){
				PositionY = tempY1;}

		}
		
		if (map.getCharTile((PositionX-13)/25, tempY1/25) != " " 
			&& map.getCharTile((PositionX-13)/25, tempY1/25) != "1" 
			&& map.getCharTile((PositionX-13)/25,tempY1/25 ) != "2"){
			if(PositionX < tempX1){
			PositionX = tempX1;}
		}
		if (map.getCharTile((PositionX+13)/25, tempY1/25) != " " 
			&& map.getCharTile((PositionX+13)/25, tempY1/25) != "1" 
			&& map.getCharTile((PositionX+13)/25,tempY1/25 ) != "2"){
			if(PositionX > tempX1){
				PositionX = tempX1;}
		}
		

		
		
	}
	
	public abstract void Movement(long time);
	
	public abstract void setSpriteOrientation();
	
	public abstract void setPlayerSpriteOrientation(int x, int y);
	
	public abstract AffineTransform getSpriteOrientation();
	
	/**
	 * get sprite current Image
	 * @return Image
	 */
	public Image getSpriteImage(){
		return image;
	}
	
	/**
	 * set sprite Image
	 *
	 */
	public void setSpriteImage(Image image){
		this.image = image;
	}
	
	/**
	 * used to solve the angle between two points
	 *
	 */
	public void solveAngle(int x, int y){
		
		if ( x >= getX() && y <= getY()){
			angle =   90 - (180*Math.atan2(getY()-y ,x-getX()))/Math.PI;
		}
		if ( x >= getX() && y > getY()){
			angle = 90 + (180*Math.atan2(y-getY(),x-getX()))/Math.PI;
		}
		if ( x < getX() && y > getY()){
			angle = -90 - (180*Math.atan2(y-getY(),getX()-x))/Math.PI;
		}
		if ( x < getX() && y <= getY()){
			angle = -90 + (180*Math.atan2(getY()-y,getX()-x))/Math.PI;
		}
	}
	
	/**
	 * Return boolean if Sprite is in collision with another sprite
	 * @return isCollsion
	 */
	public synchronized boolean isCollision(Sprite sprite2){
		if ( sprite2 != this){
			double x = (this.getX() - sprite2.getX())*(this.getX() - sprite2.getX());
			double y = (this.getY() - sprite2.getY())*(this.getY() - sprite2.getY());
			
			double spriteDistance = Math.sqrt(x+y);
			
			if (spriteDistance <= (this.radius + sprite2.radius)){
				return true;
			}else{
				return false;
			}
		}
		return false;	
	}
	
	/**
	 * get sprite Health
	 * @return health
	 */
	public int getHealth(){
		return (int) Health;
	}
	
	
	public abstract void attack();
	/**
	 * get the Sprite that its in collision with
	 * @return Sprite
	 */
	public synchronized ArrayList<Sprite> getCollider(double checkx, double checky){
		//Search for other sprite.
		ArrayList<Sprite> tmp = new ArrayList<Sprite>();
		double checkRadius = 0;
		if ( checkx == PositionX && PositionY == getY()){
			checkRadius = 13;
		}
			for  (int i =0; i < map.SpriteList.size(); i++){
				if ( Math.abs(map.SpriteList.get(i).getX() - checkx) < (checkRadius + map.SpriteList.get(i).radius)){
					if ( Math.abs(map.SpriteList.get(i).getY() - checky) < (checkRadius + map.SpriteList.get(i).radius)){
						tmp.add(map.SpriteList.get(i));
					}
				}
			}
		return tmp;
	}
}
