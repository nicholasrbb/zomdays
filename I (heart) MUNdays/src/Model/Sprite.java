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
		
		switch( direction){
		
			case UP :
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				break ;
				
			case DOWN :
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				break ;
				
			case RIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				break ;	
				
			case LEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				break ;	
				
			case UPRIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				break ;
				
			case UPLEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				PositionY = (int) (PositionY - this.getYSpeed()*time);
				break ;
				
			case DOWNRIGHT :
				PositionX = (int) (PositionX + this.getXSpeed()*time);
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				break ;
				
			case DOWNLEFT :
				PositionX = (int) (PositionX - this.getXSpeed()*time);
				PositionY = (int) (PositionY + this.getYSpeed()*time);
				break ;	
				
		}
		
		if (map.getCharTile(PositionX/50, PositionY/50) != " "){
			PositionX = tempX1;
			PositionY = tempY1;
		}
		/*
		int gridX = PositionX/50;
		int gridY = PositionY/50;
		for ( int i = gridY - 1; i < gridY + 1; i++){
			for ( int j = gridX - 1; j < gridX + 1; j++){
				System.out.println("gridX= " + gridX + " gridY= " + gridY);
				
				for ( int k = 0; k <= map.Grid[i][j].size(); k++){
					System.out.println(map.Grid[i][j].size());
					
					if (isCollision(map.Grid[i][j].get(k)) == true){
						PositionX = tempX1;
						PositionY = tempY1;
					}
					
				}
				
			}
		}
		*/
		/*if (PositionX < 25){
			PositionX = tempX1;
		}
		if (PositionY < 25){
			PositionY = tempY1;
		}
		if (PositionX > 1975){
			PositionX = tempX2;
		}
		if (PositionY > 1975){
			PositionY = tempY2;
		}*/
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
	
	public int getHealth(){
		return (int) Health;
	}
	
	public abstract void attack();
}
