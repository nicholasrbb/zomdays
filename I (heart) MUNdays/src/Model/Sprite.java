package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;

import View.Display;

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
	TileMap map;
	public int mouseX;
	public int mouseY;
	public double angle;
	public enum Direction{ UP, RIGHT, LEFT, DOWN, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT }
	private Sprite CollidedSprite;
	
	
	public Sprite(double health, int width, int height, int x, int y, double dx, double dy, TileMap Map){
		radius = 25;
		Health = health;
		isAlive = true;
		Height = height;
		Width = width;
		this.dx = dx;
		this.dy = dy;
		PositionX = x;
		PositionY = y;
		map = Map;
	}
	public int getHeight(){
		return Height;
	}
	public int getWidth(){
		return Width;
	}
	public int getX(){
		return PositionX;
	}
	public int getY(){
		return PositionY;
	}
	public void setXSpeed(int s){
		dx = s;
	}
	public void setYSpeed(int s){
		dy = s;
	}
	public double getXSpeed(){
		return dx;
	}
	public double getYSpeed(){
		return dy;
	}
	public void updateHealth(double change){
		Health = Health + change;
		if (Health <= 0){
			isAlive = false;
		}
	}
	protected void makeMovement(Direction direction, long time){
		int tempX1 = PositionX;
		int tempY1 = PositionY;
		
		double tempDistanceX = 0;
		double tempDistanceY = 0;
		if (getCollider(PositionX, PositionY) != null){
			CollidedSprite = getCollider(PositionX, PositionY);
			tempDistanceX = this.getX() - CollidedSprite.getX();
			tempDistanceY = this.getY() - CollidedSprite.getY();
		}
		
		switch( direction){
		
			case UP :
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				if (tempDistanceY > 0){
					PositionY = tempY1;
				}
				break ;
				
			case DOWN :
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				if (tempDistanceY < 0){
					PositionY = tempY1;
				}
				break ;
				
			case RIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				if (tempDistanceX < 0){
					PositionX = tempX1;
				}
				break ;	
				
			case LEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				if (tempDistanceX > 0){
					PositionX = tempX1;
				}
				break ;	
				
			case UPRIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				if (tempDistanceX < 0){
					PositionX = tempX1;
				}
				if (tempDistanceY > 0){
					PositionY = tempY1;
				}
				break ;
				
			case UPLEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				if (tempDistanceX > 0){
					PositionX = tempX1;
				}
				if (tempDistanceY > 0){
					PositionY = tempY1;
				}
				break ;
				
			case DOWNRIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				if (tempDistanceX < 0){
					PositionX = tempX1;
				}
				if (tempDistanceY < 0){
					PositionY = tempY1;
				}
				break ;
				
			case DOWNLEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				if (tempDistanceX > 0){
					PositionX = tempX1;
				}
				if (tempDistanceY < 0){
					PositionY = tempY1;
				}
				break ;	
				
		}
		
		if (map.getCharTile(PositionX/50, PositionY/50) != " "){
			PositionX = tempX1;
			PositionY = tempY1;
		}
		
	}
	public abstract void Movement(long time);
	
	public abstract void setSpriteOrientation();
	
	public abstract void setPlayerSpriteOrientation(int x, int y);
	
	public abstract AffineTransform getSpriteOrientation();
	
	public Image getSpriteImage(){
		return image;
	}
	
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
	
	public synchronized boolean isCollision(Sprite sprite2){
		if ( sprite2 != this){
			double x = (this.getX() - sprite2.getX())*(this.getX() - sprite2.getX());
			double y = (this.getY() - sprite2.getY())*(this.getY() - sprite2.getY());
			
			double spriteDistance = Math.sqrt(x+y);
			
			if (spriteDistance <= (this.radius + sprite2.radius)){
				//System.out.println(x + "  " + y +  " " + spriteDistance + "  " + "Collision:  " + sprite1.getX() + "  " + sprite2.getX() );
				return true;
			}else{
				return false;
			}
		}
		return false;	
	}
	
	public int getHealth(){
		return (int) Health;
	}
	
	public abstract void attack();
	
	public synchronized Sprite getCollider(double checkx, double checky){
		//Search for other sprite.
		double checkRadius = 0;
		if ( checkx == PositionX && PositionY == getY()){
			checkRadius = radius;
		}
			for  (int i =0; i < map.SpriteList.size(); i++){
				if ( Math.abs(map.SpriteList.get(i).getX() - checkx) < (checkRadius + map.SpriteList.get(i).radius)){
					if ( Math.abs(map.SpriteList.get(i).getY() - checky) < (checkRadius + map.SpriteList.get(i).radius)){
						return map.SpriteList.get(i);
					}
				}
			}
		return null;
	}
}
