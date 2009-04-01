package Model;

import java.awt.geom.AffineTransform;

public interface RemotePlayer {
	
	public void Movement(long time);
	public AffineTransform getSpriteOrientation();
	public AffineTransform getPlayerSpriteOrientation();
	
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

}
