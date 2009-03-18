package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import PlayerAnimations.PlayerAnimationManager;


public class Player extends Sprite{
	public PlayerAnimationManager pAnim;
	
	private AffineTransform playerOrientation;
	public boolean up = false;
	public boolean right = false;
	public boolean left = false;
	public boolean down = false;
	public ArrayList <Weapon> WeaponList;
	int currentWeapon = 0;
	
	
	public Player(Image playerImage, int health, int width, int height, int x, int y, double dx, double dy, ModelManager Manager) {
		super(health, width, height, x, y, dx, dy, Manager);
		this.image = playerImage;
		playerOrientation = new AffineTransform();
		WeaponList = new ArrayList <Weapon>();
		pAnim = new PlayerAnimationManager(this);}

	
	
	public void Movement(long time){
		
		//PlayerAnimationManager Controls for Animating Walking and staying still
		if (up || down || right || left)
			pAnim.getCurrentAnimation().update(time);
		

		
		
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
		playerOrientation.rotate(Math.toRadians(angle),image.getWidth(null)/2, image.getHeight(null)/2);
			
			
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
			WeaponList.get(currentWeapon).reload();
		}
	}

	@Override
	public void attack() {
		
		Weapon attackingWeapon = WeaponList.get(currentWeapon);
		int range = attackingWeapon.getRange();
		int damage = attackingWeapon.getDamage();
		
		if ( attackingWeapon.magAmmo() != 0){
			attackingWeapon.updateAmmo(-1);
			
			for ( int r = 0; r <= range; r++){
				String tileShoot = manager.map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(angle)))/50),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/50));
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
		
	}
	

	
	
	
}
