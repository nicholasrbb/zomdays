package Model;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;




/**
 * Class that holds orientation and health for each zombie sprite and controls there movement.
 *
 */
public class Zombie extends Sprite{

	Random generator = new Random();
	private AffineTransform npcOrientation;
	public boolean up = false;
	public boolean right = false;
	public boolean left = false;
	public boolean down = false;
	
	private Sprite targetPlayer;
	private double damage = 0.05;
	private File voice1;
	private Clip voice1Orig;
	
	int count;
	
	public Zombie(Image npcImage, int health, int width, int height, int x, int y, double dx, double dy, TileMap Map) {
		super(health, width, height, x, y, dx, dy, Map);
		this.image = npcImage;
		npcOrientation = new AffineTransform();
		
		voice1 = new File("creep.wav");

		
		try {
			AudioInputStream voice01 = AudioSystem.getAudioInputStream(voice1);
			voice1Orig = AudioSystem.getClip();
			voice1Orig.open(voice01);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
				e.printStackTrace();}
	}
	
	/**
	 * Returns the player closest to the zombie.
	 * @return sprite
	 */
	public Sprite whichPlayer(){
		if ( map.PlayerList.size() >= 1){
			int closestX = 0;
			int closestY = 0;
			Sprite player = map.PlayerList.get(0);
		
			for (int i = 0; i < map.PlayerList.size(); i++){
				if (map.PlayerList.get(i).getX() != PositionX || map.SpriteList.get(i).getY() != PositionY){
					if ( (Math.abs(PositionX - map.PlayerList.get(i).getX()) < Math.abs(PositionX - closestX)) && ((Math.abs(PositionY - map.PlayerList.get(i).getY()) < Math.abs(PositionY - closestY)))){
						closestX = map.PlayerList.get(i).getX();	
						closestY = map.PlayerList.get(i).getY();
						player = map.PlayerList.get(i);
					}
				}
			}
			return player;
		}
		return null;
	}
	
	/**
	 * Calls Sprite's Movement method based player's position
	 * 
	 * @param time
	 * 
	 * @see Sprite.Movement
	 */
	public void Movement(long time){
		Sprite target = whichPlayer();
		targetPlayer = target;
		
		if (count > 50){
			number = generator.nextInt(8);
			count = 0;
		}else{
			count++;
		}
		
		double x = Math.abs(this.getX() - target.getX())   *    Math.abs(this.getX() - target.getX());
		double y = Math.abs(this.getY() - target.getY())   *    Math.abs(this.getY() - target.getY());
		double spriteDistance = Math.sqrt(x+y);
		
		
		String tileWalk = null;
		if ( spriteDistance < 600){
			for ( int r = 0; r <= 600; r++){
				tileWalk = map.getCharTile((int)((PositionX + r*Math.sin(Math.toRadians(angle)))/25),(int) ((PositionY - (int) r*Math.cos(Math.toRadians(angle)))/25));
				if (tileWalk != " "){
					random = true;
					r = 600;
				}else if (tileWalk == " " && r > spriteDistance){
					random = false;
					r = 600;
				}
			}	
		}
		
		
		if (random == true){
			if (number == 2){
				up = false;
				right = true;
				left = false;
				down = false;			
			}
			if (number == 6){
				up = false;
				right = false;
				left = true;
				down = false;			
			}
			if (number == 8){
				up = true;
				right = false;
				left = false;
				down = false;			
			}
			if (number == 4){
				up = false;
				right = false;
				left = false;
				down = true;			
			}
			if (number == 5){
				up = false;
				right = false;
				left = true;
				down = true;			
			}
			if (number == 1){
				up = true;
				right = true;
				left = false;
				down = false;			
			}
			if (number == 7){
				up = true;
				right = false;
				left = true;
				down = false;			
			}
			if (number == 3){
				up = false;
				right = true;
				left = false;
				down = true;			
			}
		}
		
		
		
		if (random == false){
			if ( targetPlayer != null){
				x = (this.getX() - target.getX())*(this.getX() - target.getX());
				y = (this.getY() - target.getY())*(this.getY() - target.getY());
				if (Math.sqrt(x+y) <= 600){
					if ( (target.getX() - 18) > getX()){
						right = true;
					}
					if ( (target.getX() + 18) < getX()){
						left = true;
					}
					if ( (target.getY() + 18) < getY()){
						up = true;
					}
					if ( (target.getY() - 18) > getY()){
						down = true;
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
		up = false;
		right = false;
		left = false;
		down = false;
	}
	
	
	/**
	 * set the Zombie's AffineTransform, Postion and angle
	 * 
	 */
	public synchronized void setSpriteOrientation(){
		int x = targetPlayer.getX();
		int y = targetPlayer.getY();
		solveAngle(x,y);
		
		
		
		
		npcOrientation.setToTranslation(getX()-25, getY()-25);
		npcOrientation.rotate(Math.toRadians(angle), image.getWidth(null)/2, image.getHeight(null)/2);	
		
	}
	
	
	/**
	 * get the Zombie's AffineTransform
	 * 
	 * @return playerOrientation
	 */
	public AffineTransform getSpriteOrientation(){
		return npcOrientation;
	}

	/**
	 * Does nothing for Zombie
	 */
	@Override
	public void setPlayerSpriteOrientation(int x, int y) {
		
	}
	
	/**
	 * Zombie attack current target
	 * 
	 */
	public void attack(){
		//zAnim.setState(ZombieAnimationStates.Attacking);

		targetPlayer.updateHealth(-damage);
	}
	
	/**
	 * update sprite health
	 * @param change
	 */
	@Override
	public void updateHealth(double change){
		//zAnim.setState(ZombieAnimationStates.Damage);

		Health = Health + change;
		if (Health <= 0){
			//if (!voice1Orig.isActive()){
			//voice1Orig.setFramePosition(0);
			//voice1Orig.start();}
			isAlive = false;
		}
	}

	
	

}
