package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemotePlayer extends Remote{
	
	//throws RemoteException
	
	public void Movement(long time) throws RemoteException;
	public AffineTransform getSpriteOrientation() throws RemoteException;
	public AffineTransform getPlayerSpriteOrientation() throws RemoteException;
	public int getX() throws RemoteException ;
	public int getY() throws RemoteException ;
	public TileMap getMap() throws RemoteException ;
	public ArrayList <Weapon> getWeapons()  throws RemoteException;
	public int getMouseX()  throws RemoteException;
	public int getMouseY()  throws RemoteException;
	public void setMouseX(int number)  throws RemoteException;
	public void setMouseY(int number)  throws RemoteException;
	public int getHealth()  throws RemoteException;
	public void setSpriteOrientation() throws RemoteException;
	public void setPlayerOrientation(int xPos, int yPos) throws RemoteException;
	public void setPlayerSpriteOrientation(int xPos, int yPos) throws RemoteException;
	public void setPlayerStartPosition(int x, int y) throws RemoteException;
	public int getCurrentWeapon() throws RemoteException;
	public Weapon playersWeapon() throws RemoteException;
	public void changeWeapon() throws RemoteException;
	public void addWeapon(Weapon weapon) throws RemoteException;
	public void removeWeapon(Weapon weapon) throws RemoteException;
	public void reloadWeapon() throws RemoteException;
	public void attack() throws RemoteException;
	public Image getSpriteImage()  throws RemoteException;
	public boolean getXBox() throws RemoteException;
	public void setXBox(boolean b) throws RemoteException;
	public int getPoints() throws RemoteException;
	public void setAttack(boolean b) throws RemoteException;
	public void setUP(boolean b) throws RemoteException;
	public void setLEFT(boolean b) throws RemoteException;
	public void setDOWN(boolean b) throws RemoteException;
	public void setRIGHT(boolean b) throws RemoteException;

}
