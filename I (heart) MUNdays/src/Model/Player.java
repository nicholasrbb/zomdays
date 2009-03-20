package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import PlayerAnimations.PlayerAnimationManager;


public class Player extends Sprite{
	public PlayerAnimationManager pAnim;
	
	private AffineTransform playerOrientation;
	public boolean up = false;
	public boolean right = false;
	public boolean left = false;
	public boolean down = false;
	//public ArrayList <Weapon> WeaponList;
	//int currentWeapon = 0;
	private File gun;
	private File voice1;
	private Clip gunFired;
	private File gunReload;
	public boolean attack = false;
	Clip gunShotOrig;
	Clip gunReloadOrig;
	Clip voice1Orig;
	
	public int coinCount;
	
	
	public Player(Image playerImage, int health, int width, int height, int x, int y, double dx, double dy, ModelManager Manager) {
		super(health, width, height, x, y, dx, dy, Manager);
		this.image = playerImage;
		playerOrientation = new AffineTransform();
		WeaponList = new ArrayList <Weapon>();
		//pAnim = new PlayerAnimationManager(this);
		
		gun = new File("gunshot.wav");
		gunReload = new File("reload.wav");
		
		
		

		
		try {
			AudioInputStream handgunShot = AudioSystem.getAudioInputStream(gun);
			gunShotOrig = AudioSystem.getClip();
			gunShotOrig.open(handgunShot);
								
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
				e.printStackTrace();}	
		
		try {
			AudioInputStream handgunReload = AudioSystem.getAudioInputStream(gunReload);
			gunReloadOrig = AudioSystem.getClip();
			gunReloadOrig.open(handgunReload);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
				e.printStackTrace();}
		
		
		

		
	
	}

	
	
	public void Movement(long time){
		/*
		//PlayerAnimationManager Controls for Animating Walking and staying still
		if (up || down || right || left)
			pAnim.getCurrentAnimation().update(time);
		*/
		if (up || down || right || left){
			currentAnimation = 1;
		}else{
			currentAnimation = 0;
		}
		
		//Paint all the Items
		for ( int i = 0; i < manager.map.ItemList.size(); i++){
			if ( Math.abs(PositionX - manager.map.ItemList.get(i).getX()) < 25 && Math.abs(PositionY - manager.map.ItemList.get(i).getY()) < 25){
				if ( manager.map.ItemList.get(i).getType()  == "ammo"){
					WeaponList.get(currentWeapon).ammo += manager.map.ItemList.get(i).getAmount();
				}
				if ( manager.map.ItemList.get(i).getType()  == "health"){
					Health += manager.map.ItemList.get(i).getAmount();
				}
				
				manager.map.ItemList.remove(i);
			}
			
		
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
		playerOrientation.rotate(Math.toRadians(angle),image.getWidth(null)/2, (image.getHeight(null)/2));
			
			
	}

	public void setPlayerStartPosition(int x, int y){
		PositionX = x;
		PositionY = y;
	}
	
	public int getCurrentWeapon(){
		return currentWeapon;}
	
	public Weapon playersWeapon(){
		return WeaponList.get(currentWeapon);	}
	
	public void changeWeapon(){
		
		if( currentWeapon < (WeaponList.size()-1)){
			currentWeapon = currentWeapon + 1;
		}else{
			currentWeapon = 0;
		}
	}
	
	public void addWeapon(Weapon weapon){
		WeaponList.add(weapon);
	}
	
	public void removeWeapon(Weapon weapon){
		
		WeaponList.remove(weapon);
	}
	
	public void reloadWeapon(){
		
		if (WeaponList.get(currentWeapon).getAmmo() >=0){
			gunReloadOrig.setFramePosition(0);
			gunReloadOrig.start();
			WeaponList.get(currentWeapon).reload();
		}
	}

	@Override
	public void attack() {
		if (attack == true){
			//currentAnimation = 1;
			
			attack = false;
			
			
			
			
			Weapon attackingWeapon = WeaponList.get(currentWeapon);
			int range = attackingWeapon.getRange();
			int damage = attackingWeapon.getDamage();
			
			if ( attackingWeapon.magAmmo() != 0){
				WeaponList.get(currentWeapon).currentAnimation = 1;
				gunShotOrig.setFramePosition(0);
				
				gunShotOrig.start();

				
				attackingWeapon.updateAmmo(-1);
				
				for ( int r = 20; r <= range; r++){
					String tileShoot = manager.map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
					//System.out.println("Angle: " + (angle) + "gun hitting x: " +(PositionX + r*Math.sin(Math.toRadians(angle)))  + " y: " + (PositionY - r*Math.cos(Math.toRadians(angle))));
					Sprite target = getCollider(PositionX + r*Math.sin(Math.toRadians(angle)), PositionY - r*Math.cos(Math.toRadians(angle)));
					
					if (tileShoot != " ")
						return;
					if ( target != null){
						target.updateHealth(-damage);
						return;
					}
				}
			}
			
		}else{
			System.out.println("gun not firing)");
			WeaponList.get(currentWeapon).currentAnimation = 0;

		}
		
		
	}
	

	
	
	
}
