package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



/**
 * Class that extends the Sprite class, and updates the movement, orientation, weapon and ammo
 * of a player as well as controls attacking enemies.
 * 
 * @see Sprite
 */
public class Player extends Sprite implements RemotePlayer{
	
	private AffineTransform playerOrientation;
	private AffineTransform Orientation;
	public boolean up = false;
	public boolean right = false;
	public boolean left = false;
	public boolean down = false;
	public String name;
	private File gun;
	private File voice1;
	private Clip gunFired;
	private File gunReload;
	public boolean attack = false;
	Clip gunShotOrig;
	Clip gunReloadOrig;
	Clip voice1Orig;
	
	public int points = 999;
	private double lastFireTime;
	
	
	public int TSangle = 0;
	private boolean xboxController = false;
	
	
	/** 
	 * Creates a Player Object with a given Image, Size, Position and Speed.
	 * <p> Player also knows the ModelManager
	 * @param playerImage
	 * @param health
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 * @param Manager
	 * 
	 * @see ModelManager
	 */
	public Player(Image playerImage, int health, int width, int height, int x, int y, double dx, double dy, TileMap Map) {
		super(health, width, height, x, y, dx, dy, Map);
		this.image = playerImage;
		playerOrientation = new AffineTransform();
		Orientation = new AffineTransform();
		WeaponList = new ArrayList <Weapon>();
		//pAnim = new PlayerAnimationManager(this);
		
		gun = new File("gunshot.wav");
		gunReload = new File("reload.wav");
		
		lastFireTime = -1;
		
		
		

		
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
	
	public TileMap getMap(){
		return map;
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
	
	public void setMouseX(int number){
		mouseX = number;
	}
	
	public void setMouseY(int number){
		mouseY = number;
	}
	
	public ArrayList <Weapon> getWeapons(){
		return WeaponList;
	}

	public void setRight(boolean R){
		right = R;
	}
	/**
	 * Calls Sprits Movement method based on keyListener
	 * 
	 * @param time
	 * 
	 * @see Sprite.Movement
	 */
	
	public void Movement(long time){
		
		
		if (up || down || right || left){
			currentAnimation = 1;
		}else{
			currentAnimation = 0;
		}
		
		
		//item effects
		for ( int i = 0; i < map.ItemList.size(); i++){
			if ( Math.abs(PositionX - map.ItemList.get(i).getX()) < 25 && Math.abs(PositionY - map.ItemList.get(i).getY()) < 25){
				if ( map.ItemList.get(i).getType()  == "ammo"){
					WeaponList.get(currentWeapon).ammo += map.ItemList.get(i).getAmount();
				}
				if ( map.ItemList.get(i).getType()  == "health"){
					Health += map.ItemList.get(i).getAmount();
				}
				
				map.ItemList.remove(i);
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

	
	/**
	 * get the players AffineTransform
	 * 
	 * @return playerOrientation
	 */
	public AffineTransform getSpriteOrientation(){
		return Orientation;
	}
	
	public AffineTransform getPlayerSpriteOrientation(){
		return playerOrientation;
	}
	/**
	 * set the players AffineTransform, Postion and angle
	 * 
	 */
	@Override
	public void setSpriteOrientation() {
		Orientation.setToTranslation(PositionX, PositionY);	
		Orientation.rotate(Math.toRadians(angle),image.getWidth(null)/2, (image.getHeight(null)/2));
		
	}

	public void setPlayerOrientation(int xPos, int yPos){
		if (xboxController == false){
			setPlayerSpriteOrientation(xPos, yPos);
		}else{
			setplayerSpriteOrientationXbox(xPos, yPos);
		}
	}

	/**
	 * set the players Angle based on mouseEvents
	 * 
	 */
	@Override
	public void setPlayerSpriteOrientation(int xPos, int yPos) {	
		
		solveAngle(mouseX, mouseY);
		int printX = getX()-xPos-55;
		int printY = getY()-yPos-55;
	
		
		playerOrientation.setToTranslation(printX, printY);	
		playerOrientation.rotate(Math.toRadians(angle),image.getWidth(null)/2, (image.getHeight(null)/2)+10);
	}
	
	public void setXBox(boolean xbox){
		xboxController = xbox;
	}
	
	public boolean getXBox(){
		return xboxController;
	}
	
	public void setplayerSpriteOrientationXbox(int xPos, int yPos){
		
		int printX = getX()-xPos-25;
		int printY = getY()-yPos-25;
	
		
		playerOrientation.setToTranslation(printX, printY);	
		playerOrientation.rotate(Math.toRadians(TSangle),image.getWidth(null)/2, (image.getHeight(null)/2));
	}
		
		
		
		
	/**
	 * set the players Position. 
	 * 
	 */
	public void setPlayerStartPosition(int x, int y){
		PositionX = x;
		PositionY = y;
	}
	
	/**
	 * get the players current Weapon (int in Array)
	 * 
	 * @return weapon
	 */
	public int getCurrentWeapon(){
		return currentWeapon;}
	/**
	 * get the players current Weapon
	 * 
	 * @return weapon
	 */
	public Weapon playersWeapon(){
		return WeaponList.get(currentWeapon);	}
	/**
	 * change the players current Weapon
	 * 
	 */
	public void changeWeapon(){
		
		if( currentWeapon < (WeaponList.size()-1)){
			currentWeapon = currentWeapon + 1;
		}else{
			currentWeapon = 0;
		}
	}
	
	/**
	 * add the Weapon To array
	 * 
	 */
	public void addWeapon(Weapon weapon){
		WeaponList.add(weapon);
	}
	/**
	 * remove the Weapon To array
	 * 
	 */
	public void removeWeapon(Weapon weapon){
		
		WeaponList.remove(weapon);
	}
	/**
	 * reload the current the Weapon
	 * 
	 */
	public void reloadWeapon(){
		
		if (WeaponList.get(currentWeapon).getAmmo() >=0){
			gunReloadOrig.setFramePosition(0);
			gunReloadOrig.start();
			WeaponList.get(currentWeapon).reload();
		}
	}

	/**
	 * player attack with current Weapon
	 * 
	 * rate of fire in rounds per second
	 * @param rate 
	 */
	@Override
	public void attack() {

		if (attack == true){
			attack = false;
				
			Weapon attackingWeapon = WeaponList.get(currentWeapon);
			int range = attackingWeapon.getRange();
			int damage = attackingWeapon.getDamage();
			
			/*
			 * Limit players attack to specific rate of fire of weapon
			 * and convert players rate from rounds per second to rounds per millisecond
			 */
			double tmp =System.currentTimeMillis() - lastFireTime ;
			
			if (System.currentTimeMillis() - lastFireTime >= attackingWeapon.getRate()*1000 || lastFireTime <=0 ){
				if ( attackingWeapon.magAmmo() != 0){
					if (currentWeapon == 0 || currentWeapon == 2){
						gunShotOrig.setFramePosition(0);
						lastFireTime = System.currentTimeMillis();
						WeaponList.get(currentWeapon).currentAnimation = 1;
						gunShotOrig.start();
					}
					if (WeaponList.get(currentWeapon).name.equals("Knife")){
						lastFireTime = System.currentTimeMillis();
						WeaponList.get(currentWeapon).currentAnimation = 1;
					}
					attackingWeapon.updateAmmo(-1);
					
					String tileShoot = null;
					Sprite target = null;
					for ( int r = 20; r <= range; r++){
						if (xboxController){
							tileShoot = map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(TSangle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(TSangle)))/25));
							//if((PositionX + r*Math.sin(Math.toRadians(TSangle)), PositionY - r*Math.cos(Math.toRadians(TSangle))).get(0) != null)
							//	target = getCollider(PositionX + r*Math.sin(Math.toRadians(TSangle)), PositionY - r*Math.cos(Math.toRadians(TSangle))).get(0);
							
						}else{
							tileShoot = map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
							if(getCollider(PositionX + r*Math.sin(Math.toRadians(angle)), PositionY - r*Math.cos(Math.toRadians(angle))).size() > 0){
								target = getCollider(PositionX + r*Math.sin(Math.toRadians(angle)), PositionY - r*Math.cos(Math.toRadians(angle))).get(0);
							}
						}
						if (tileShoot != " "){
							return;
						}
						if ( target != null){
							target.updateHealth(-damage);
							return;
						}
					}
				}
			}
			
			
		}
		if (System.currentTimeMillis() - lastFireTime >=150 || lastFireTime <=0){
				WeaponList.get(currentWeapon).currentAnimation = 0;
					}		
			
	}
	

	@Override
	public int getPoints() {
		return Points;
	}
		
		
}
	
	
	
	

	
	
	

