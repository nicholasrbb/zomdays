package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemotePlayer extends Remote{
	
	//throws RemoteException
	
	public void Movement(long time);
	public AffineTransform getSpriteOrientation();
	public AffineTransform getPlayerSpriteOrientation();
	public int getX() ;
	public int getY() ;
	public TileMap getMap() ;
	public ArrayList <Weapon> getWeapons() ;
	public int getMouseX() ;
	public int getMouseY() ;
	public void setMouseX(int number) ;
	public void setMouseY(int number) ;
	public int getHealth() ;
	public void setSpriteOrientation();
	public void setPlayerOrientation(int xPos, int yPos);
	public void setPlayerSpriteOrientation(int xPos, int yPos);
	public void setPlayerStartPosition(int x, int y);
	public int getCurrentWeapon();
	public Weapon playersWeapon();
	public void changeWeapon();
	public void addWeapon(Weapon weapon);
	public void removeWeapon(Weapon weapon);
	public void reloadWeapon();
	public void attack();
	public void setRight(boolean R);
	public Image getSpriteImage() ;
	public boolean getXBox();
	public void setXBox(boolean b);

}
