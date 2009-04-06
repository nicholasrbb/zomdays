package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PlayerRMI extends UnicastRemoteObject implements RMIInterface {

	//throws RemoteException
	Player player;
	
	public PlayerRMI(Player player) throws RemoteException {
		this.player = player;
	}

	public Player getPlayer(){
		return player;
	}
	
	public void Movement(long time){
		player.Movement(time);
	}
	public AffineTransform getSpriteOrientation(){
		return player.getSpriteOrientation();
	}
	public AffineTransform getPlayerSpriteOrientation(){
		return player.getPlayerSpriteOrientation();
	}
	public int getX(){
		return player.getX();
	}
	public int getY(){
		return player.getY();
	}
	public TileMap getMap(){
		return player.getMap();
	}
	public ArrayList <Weapon> getWeapons(){
		return player.getWeapons();
	}
	public int getMouseX(){
		return player.getMouseX();
	}
	public int getMouseY(){
		return player.getMouseY();
	}
	public void setMouseX(int number){
		player.setMouseX( number);
	}
	public void setMouseY(int number){
		player.setMouseY( number);
	}
	public int getHealth(){
		return player.getHealth();
	}
	public void setSpriteOrientation(){
		player.setSpriteOrientation();
	}
	public void setPlayerOrientation(int xPos, int yPos){
		player.setPlayerOrientation( xPos,  yPos);
	}
	public void setPlayerSpriteOrientation(int xPos, int yPos){
		player.setPlayerSpriteOrientation( xPos,  yPos);
	}
	public void setPlayerStartPosition(int x, int y){
		player.setPlayerStartPosition( x,  y);
	}
	public int getCurrentWeapon(){
		return player.getCurrentWeapon();
	}
	public Weapon playersWeapon(){
		return player.playersWeapon();
	}
	public void changeWeapon(){
		player.changeWeapon();
	}
	public void addWeapon(Weapon weapon){
		player.addWeapon(weapon);
	}
	public void removeWeapon(Weapon weapon){
		player.removeWeapon(weapon);
	}
	public void reloadWeapon(){
		player.reloadWeapon();
	}
	public void attack(){
		player.attack();
	}
	public Image getSpriteImage(){
		return player.getSpriteImage();
	}
	public boolean getXBox(){
		return player.getXBox();
	}
	public void setXBox(boolean b){
		player.setXBox( b);
	}
	public int getPoints(){
		return player.getPoints();
	}
	public void setAttack(boolean b){
		player.setAttack( b);
	}
	public void setUP(boolean b){
		player.setUP( b);
	}
	public void setLEFT(boolean b){
		player.setLEFT( b);
	}
	public void setDOWN(boolean b){
		player.setDOWN( b);
	}
	public void setRIGHT(boolean b){
		player.setRIGHT( b);
	}
	
	
	
}
