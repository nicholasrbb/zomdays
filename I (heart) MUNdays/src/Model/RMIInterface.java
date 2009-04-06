package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.rmi.Remote;
import java.util.ArrayList;

public interface RMIInterface extends Remote{
	//throws RemoteException
	public Player getPlayer() ;
	public void Movement(long time);
	public AffineTransform getSpriteOrientation();
	public AffineTransform getPlayerSpriteOrientation();
	public int getX();
	public int getY();
	public TileMap getMap();
	public ArrayList <Weapon> getWeapons();
	public int getMouseX();
	public int getMouseY();
	public void setMouseX(int number);
	public void setMouseY(int number);
	public int getHealth();
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
	public Image getSpriteImage();
	public boolean getXBox();
	public void setXBox(boolean b);
	public int getPoints();
	public void setAttack(boolean b);
	public void setUP(boolean b);
	public void setLEFT(boolean b);
	public void setDOWN(boolean b);
	public void setRIGHT(boolean b);
}
