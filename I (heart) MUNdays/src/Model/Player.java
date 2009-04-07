package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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
@SuppressWarnings("serial")
public class Player extends Sprite implements RemotePlayer, Serializable{
	
	private AffineTransform playerOrientation;
	private AffineTransform Orientation;
	public boolean up = false;
	public boolean right = false;
	public boolean left = false;
	public boolean down = false;
	public String name;
	private File gun;
	private File shotgun;
	private File uzi;
	private File gunReloadFile;
	public boolean attack = false;
	Clip gunShotOrig;
	Clip gunReloadOrig;
	Clip shotgunShot;
	Clip uziShot;
	Clip voice1Orig;
	
	public int points = 0;
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
	public Player(Image playerImage, int health, int width, int height, int x, int y, double dx, double dy, TileMap Map,boolean Xboxcontroller) {
		super(health, width, height, x, y, dx, dy, Map);
		this.image = playerImage;
		this.xboxController = Xboxcontroller;
		playerOrientation = new AffineTransform();
		Orientation = new AffineTransform();
		WeaponList = new ArrayList <Weapon>();
		//pAnim = new PlayerAnimationManager(this);
		
		bulletList = new ArrayList <Bullet>();
		
		gun = new File("gunshot.wav");
		gunReloadFile = new File("reload.wav");
		
		shotgun = new File("shotgun.wav");
		uzi = new File("uzi.wav");
		
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
			AudioInputStream ShotgunShot = AudioSystem.getAudioInputStream(shotgun);
			shotgunShot = AudioSystem.getClip();
			shotgunShot.open(ShotgunShot);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
				e.printStackTrace();}
		
		try {
			AudioInputStream UziShot = AudioSystem.getAudioInputStream(uzi);
			uziShot = AudioSystem.getClip();
			uziShot.open(UziShot);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
				e.printStackTrace();}
		
		try {
			AudioInputStream gunReload = AudioSystem.getAudioInputStream(gunReloadFile);
			gunReloadOrig = AudioSystem.getClip();
			gunReloadOrig.open(gunReload);
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
	
	public ArrayList <Bullet> getBullets(){
		return bulletList;
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
		if(map.ItemList.size() > 0){
		for ( int i = 0; i < map.ItemList.size(); i++){
			if ( Math.abs(PositionX - map.ItemList.get(i).getX()-25) < 50 && Math.abs(PositionY - map.ItemList.get(i).getY()-25) < 50){
				if ( map.ItemList.get(i).getType()  == "ammo" && WeaponList.get(currentWeapon).ammo < 1000 &&WeaponList.get(currentWeapon).ammo >= 0){
					for(int j = 0 ; j < WeaponList.size();j++){
						String tmp = WeaponList.get(j).name;
						if (tmp.equals("Hand Gun"))
							WeaponList.get(j).ammo = WeaponList.get(j).ammo + 10; 
						
						else if (tmp.equals("2x Hand Gun"))
							WeaponList.get(j).ammo = WeaponList.get(j).ammo + 20; 
						
						else if (tmp.equals("Shotgun"))
							WeaponList.get(j).ammo = WeaponList.get(j).ammo + 4; 
						
						else if (tmp.equals("Uzi"))
							WeaponList.get(j).ammo = WeaponList.get(j).ammo + 20; 
						
					}
					
					
					if (WeaponList.get(currentWeapon).ammo > 1000)
						WeaponList.get(currentWeapon).ammo =1000;
					map.ItemList.remove(i);
					break;
				}
				else if ( map.ItemList.get(i) != null && map.ItemList.get(i).getType()  == "health" && Health < 50){
					Health += map.ItemList.get(i).getAmount();
					if (Health > 50)
						Health = 50;
					map.ItemList.remove(i);
					break;
				}
				else if ( map.ItemList.get(i) != null && map.ItemList.get(i).getType()  == "points10"){
					Points = Points + 10;
					map.ItemList.remove(i);
					break;
				}
				else if ( map.ItemList.get(i) != null && map.ItemList.get(i).getType()  == "points20"){
					Points = Points + 20;
					map.ItemList.remove(i);
					break;
				}
				else if ( map.ItemList.get(i) != null && map.ItemList.get(i).getType()  == "points50"){
					Points = Points + 50;
					map.ItemList.remove(i);
					break;
				}
				
				
			}
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
		int printY = getY()-yPos-69;
	
		
		playerOrientation.setToTranslation(printX, printY);	
		playerOrientation.rotate(Math.toRadians(angle),55, 69);
	}
	
	public void setXBox(boolean xbox){
		xboxController = xbox;
	}
	
	public boolean getXBox(){
		return xboxController;
	}
	
	public void setplayerSpriteOrientationXbox(int xPos, int yPos){
		
		int printX = getX()-xPos-55;
		int printY = getY()-yPos-69;
	
		
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
			if(currentWeapon == 0 || currentWeapon == 2){
				gunReloadOrig.setFramePosition(0);
				gunReloadOrig.start();
			}
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

		if (attack == true ){
			if(!WeaponList.get(currentWeapon).auto)
				attack = false;
				
			Weapon attackingWeapon = WeaponList.get(currentWeapon);
			int range = attackingWeapon.getRange();
			int damage = attackingWeapon.getDamage();
			
			/*
			 * Limit players attack to specific rate of fire of weapon
			 * and convert players rate from rounds per second to rounds per millisecond
			 */
			
			if (System.currentTimeMillis() - lastFireTime >= attackingWeapon.getRate()*1000 || lastFireTime <=0 ){
				if ( attackingWeapon.magAmmo() != 0){
					if (currentWeapon == 0 || currentWeapon == 2){
						gunShotOrig.setFramePosition(0);
						lastFireTime = System.currentTimeMillis();
						WeaponList.get(currentWeapon).currentAnimation = 1;
						gunShotOrig.start();
					}
					
					if (currentWeapon == 3){
						shotgunShot.setFramePosition(0);
						lastFireTime = System.currentTimeMillis();
						WeaponList.get(currentWeapon).currentAnimation = 1;
						shotgunShot.start();
						attackSpray(angle+2);
						attackSpray(angle-2);
						attackSpray(angle+8);
						attackSpray(angle-8);
						attackSpray(angle+10);
						attackSpray(angle-10);
						attackSpray(angle+16);
						attackSpray(angle-16);
						attackSpray(angle+20);
						attackSpray(angle-20);
					}
					
					if (currentWeapon == 4){
						Random random = new Random();
						int spray = Math.abs(random.nextInt(4));
						if (spray == 0){
							angle++;
							angle++;
						}
						else if (spray == 1){
							angle--;
							angle--;
						}
						else if (spray == 2){
							angle--;
							angle--;
							angle--;
							angle--;
						}
						else if (spray == 3){
							angle++;
							angle++;
							angle++;
							angle++;
							
						}
						uziShot.setFramePosition(0);
						lastFireTime = System.currentTimeMillis();
						WeaponList.get(currentWeapon).currentAnimation = 1;
						uziShot.start();
					}
					
					if (WeaponList.get(currentWeapon).name.equals("Knife")){
						lastFireTime = System.currentTimeMillis();
						WeaponList.get(currentWeapon).currentAnimation = 1;
					}
					attackingWeapon.updateAmmo();
					
					String tileShoot = null;
					Sprite target = null;
					for ( int r = 1; r <= range; r++){
						if (xboxController){
							tileShoot = map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(TSangle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(TSangle)))/25));
							if(getCollider(PositionX + r*Math.sin(Math.toRadians(TSangle)),PositionY- r*Math.cos(Math.toRadians(TSangle))).size() > 0)
							
								target = getCollider(PositionX + r*Math.sin(Math.toRadians(TSangle)), PositionY - r*Math.cos(Math.toRadians(TSangle))).get(0);
							
						}else{
							tileShoot = map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
							if(getCollider(PositionX + r*Math.sin(Math.toRadians(angle)), PositionY - r*Math.cos(Math.toRadians(angle))).size() > 0){
								target = getCollider(PositionX + r*Math.sin(Math.toRadians(angle)), PositionY - r*Math.cos(Math.toRadians(angle))).get(0);
							}
						}
						
						
						if (tileShoot != " "){
							Bullet shotTile = new Bullet(PositionX,PositionY,(int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
							bulletList.add(shotTile);
							return;
						}
						if ( target != null){
							Bullet shotTarget = new Bullet(PositionX,PositionY,(int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
							bulletList.add(shotTarget);
							target.updateHealth(-damage);
							return;
						}
						else if(r == range){
							Bullet shotAir = new Bullet(PositionX,PositionY,(int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
						bulletList.add(shotAir);
							
						}
							
					}
				}
			}
			
			
		}
		if (System.currentTimeMillis() - lastFireTime >=150 || lastFireTime <=0){
				WeaponList.get(currentWeapon).currentAnimation = 0;
					}		
			
	}
	

	private void attackSpray(double d) {
		
		Weapon attackingWeapon = WeaponList.get(currentWeapon);
		int range = attackingWeapon.getRange();
		int damage = attackingWeapon.getDamage();
	
		String tileShoot = null;
		Sprite target = null;
		for ( double r = 1; r <= range; r=r+0.5){
			if (xboxController){
				tileShoot = map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(TSangle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(TSangle)))/25));
				if(getCollider(PositionX + r*Math.sin(Math.toRadians(TSangle)), PositionY - r*Math.cos(Math.toRadians(TSangle))).get(0) != null)
					target = getCollider(PositionX + r*Math.sin(Math.toRadians(TSangle)), PositionY - r*Math.cos(Math.toRadians(TSangle))).get(0);
				
			}else{
				tileShoot = map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(d)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(d)))/25));
				if(getCollider(PositionX + r*Math.sin(Math.toRadians(d)), PositionY - r*Math.cos(Math.toRadians(d))).size() > 0){
					target = getCollider(PositionX + r*Math.sin(Math.toRadians(d)), PositionY - r*Math.cos(Math.toRadians(d))).get(0);
				}
			}
			
			
			if (tileShoot != " "){
				Bullet shotTarget = new Bullet(PositionX,PositionY,(int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
				bulletList.add(shotTarget);
				return;
			}
			if ( target != null){
				Bullet shotTarget = new Bullet(PositionX,PositionY,(int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
				bulletList.add(shotTarget);
				target.updateHealth(-damage);
				return;
			}		
		}
	}

	public int getPoints() {
		return Points;
	}

	public void setAttack(boolean b) {
		attack = b;
		
	}
	
	public void setUP(boolean b) {
		up = b;
		
	}
	public void setRIGHT(boolean b) {
		right = b;
		
	}
	public void setLEFT(boolean b) {
		left = b;
		
	}
	public void setDOWN(boolean b) {
		down = b;
		
	}
		
}
	
	
	
	

	
	
	

