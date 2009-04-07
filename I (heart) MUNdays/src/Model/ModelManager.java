package Model;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Main.GameFrame;
import View.Animation;
import View.AnimationFrame;
import View.finiteAnimation;


/**
 * Manager that controls the updating of all sprites and animations for the game,
 * respawns Zombies and controls the transition between different game Maps.
 */
@SuppressWarnings("serial")
public class ModelManager implements Serializable{
	
	public TileMap map;
	public GameFrame game;
	Image zombieImage; 
	Random generator = new Random();
	public int killed = 0;
	Player traveller;
	public ArrayList <TileMap> MapList;
	int currentMap;
	private Image ZombieLegs;
	
	Animation ZombieWalk;
	finiteAnimation ZombieBloodBig;
	finiteAnimation ZombieBloodSmall;
	
	ArrayList <AnimationFrame> bloodbigframes;
	ArrayList <AnimationFrame> bloodsmallframes;
	

	
	/**
	 * contains an ArrayList of tileMaps, and sets a default map
	 * 
	 * @param maplist
	 */
	public ModelManager(ArrayList <TileMap> maplist, GameFrame game){
		this.game = game;
		MapList = maplist;
		map = maplist.get(0);
		currentMap = 0;
		
		ZombieLegs = Toolkit.getDefaultToolkit().createImage("Player/player_shortlegs_right.png");
		
		Image walk0 = Toolkit.getDefaultToolkit().createImage("Player/player_shortlegs_right.png");
		Image walk1 = Toolkit.getDefaultToolkit().createImage("Player/player_longlegs_right.png");
		Image walk2 = Toolkit.getDefaultToolkit().createImage("Player/player_shortlegs_left.png");
		Image walk3 = Toolkit.getDefaultToolkit().createImage("Player/player_longlegs_left.png");
		
		AnimationFrame zomFrame1 = new AnimationFrame(walk0, 100000000L);
		AnimationFrame zomFrame2 = new AnimationFrame(walk1, 100000000L);
		AnimationFrame zomFrame3 = new AnimationFrame(walk0, 100000000L);
		AnimationFrame zomFrame4 = new AnimationFrame(walk2, 100000000L);
		AnimationFrame zomFrame5 = new AnimationFrame(walk3, 100000000L);
		AnimationFrame zomFrame6 = new AnimationFrame(walk2, 100000000L);
		
		ArrayList <AnimationFrame> frames1 = new ArrayList <AnimationFrame>();
		
		frames1.add(zomFrame1);
		frames1.add(zomFrame2);
		frames1.add(zomFrame3);
		frames1.add(zomFrame4);
		frames1.add(zomFrame5);
		frames1.add(zomFrame6);
		
	    ZombieWalk = new Animation(frames1);
	    
	    //Initialize Blood Animation
		Image bloodsmall = Toolkit.getDefaultToolkit().createImage("Zombie/zombie_blood_small.png");
		Image bloodlarge = Toolkit.getDefaultToolkit().createImage("Zombie/zombie_blood_big.png");
		Image blank = Toolkit.getDefaultToolkit().createImage("Zombie/blank.png");
		
		AnimationFrame bloodFrame1 = new AnimationFrame(bloodsmall, 100000000L);
		AnimationFrame bloodFrame2 = new AnimationFrame(bloodlarge, 100000000L);
		AnimationFrame bloodFrame3 = new AnimationFrame(blank, 200000000L);
		
		
		bloodbigframes = new ArrayList <AnimationFrame>();
		
		bloodbigframes.add(bloodFrame1);
		bloodbigframes.add(bloodFrame2);
		bloodbigframes.add(bloodFrame1);

		bloodbigframes.add(bloodFrame3);

		
	    
	    
	    
	}
	
	/**
	 * updates the players animation
	 * 
	 * @see Animation, AnimationFrame
	 * 
	 */
	
	public void updateAnimations(){
		for (int Map = 0; Map < MapList.size(); Map++){
			map = MapList.get(Map);
			for (int i = 0; i < map.PlayerList.size(); i++){
				
				

				//System.out.println(Map + "   "   +i + " "+map.PlayerList.size()  + " " + map.PlayerList.get(i).WeaponList.size());
				map.PlayerList.get(i).image = map.PlayerList.get(i).animations.get(map.PlayerList.get(i).currentAnimation).getAnimationImage();
				map.PlayerList.get(i).WeaponList.get(map.PlayerList.get(i).getCurrentWeapon()).image = map.PlayerList.get(i).WeaponList.get(map.PlayerList.get(i).getCurrentWeapon()).animations.get(map.PlayerList.get(i).WeaponList.get(map.PlayerList.get(i).getCurrentWeapon()).currentAnimation).getAnimationImage();
			}
			for (int i = 0; i < map.SpriteList.size(); i++){
				map.SpriteList.get(i).Legs = map.SpriteList.get(i).animations.get(map.SpriteList.get(i).currentWalkAnimation).getAnimationImage();
				map.SpriteList.get(i).Blood = map.SpriteList.get(i).bloodAnimations.get(map.SpriteList.get(i).currentStateAnimation).getAnimationImage();		
				}
		}
	}
	
	/**
	 * randomly places enemy sprites on the outside of the main view Area
	 * and keep the number of enemies on map at a constant number.
	 */
	
	public void manageSprites(){
		for (int Map = 0; Map < MapList.size(); Map++){
			map = MapList.get(Map);
			while(map.SpriteList.size() < 120){
				
				int x = Math.abs(generator.nextInt(15000)) + 50;
				int y = Math.abs(generator.nextInt(15000)) + 50;
				for (int i = 0; i < map.PlayerList.size(); i++){
					if (map.getCharTile(x/25, y/25) == " " && 
						map.getCharTile((x+25)/25, y/25) == " " && 
						map.getCharTile((x-25)/25, y/25) == " " &&
						map.getCharTile(x/25, (y+25)/25) == " " &&
						map.getCharTile(x/25, (y-25)/25) == " " &&
						Math.abs(map.PlayerList.get(i).getX() - x) > 450 && Math.abs(map.PlayerList.get(i).getY() - y) > 550){
						
						int k = Math.abs(generator.nextInt(3));
						
						Image BlankBlood = Toolkit.getDefaultToolkit().createImage("Zombie/blank.png");
						if ( k == 1){
							zombieImage = Toolkit.getDefaultToolkit().createImage("Zombie/zombie_yellow.png");
							
							Zombie zombay = new Zombie(zombieImage,ZombieLegs,BlankBlood, 20, 10, 10, x, y, 0.1, 0.1, map,0.2);
							zombay.animations.add(ZombieWalk);
							ZombieBloodBig = new finiteAnimation(bloodbigframes);
							zombay.bloodAnimations.add(ZombieBloodBig);
							map.addSprite(zombay);
						}
						else if ( k == 2){
							zombieImage = Toolkit.getDefaultToolkit().createImage("Zombie/zombie_green.png");							
							Zombie zombay = new Zombie(zombieImage,ZombieLegs,BlankBlood, 50, 10, 10, x, y, 0.1, 0.1, map,0.05);
							zombay.animations.add(ZombieWalk);
							ZombieBloodBig = new finiteAnimation(bloodbigframes);
							zombay.bloodAnimations.add(ZombieBloodBig);
							map.addSprite(zombay);
						}
						else{
							zombieImage = Toolkit.getDefaultToolkit().createImage("Zombie/zombie_blue.png");
							Zombie zombay = new Zombie(zombieImage,ZombieLegs,BlankBlood, 30, 10, 10, x, y, 0.25, 0.25, map,0.05);
							zombay.animations.add(ZombieWalk);
							ZombieBloodBig = new finiteAnimation(bloodbigframes);
							zombay.bloodAnimations.add(ZombieBloodBig);
							map.addSprite(zombay);
						}
						
					}
				}
			}
		}
	}
	
	public void manageBullets(){
		for (int i = 0; i < map.PlayerList.size() && map.PlayerList.size() > 0; i++){
			for (int j = 0; j < map.PlayerList.get(i).bulletList.size() && map.PlayerList.get(i).bulletList.size() > 0; j++){
				map.PlayerList.get(i).bulletList.get(j).updateBullet();
				
				if (map.PlayerList.get(i).bulletList.get(j).visible == false){
					System.out.println("Removed");
					map.PlayerList.get(i).bulletList.remove(j);
				}
			}
		}
	}
	
	/**
	 * 
	 * Checks to see if player is located on stairs.
	 * If player is on stairs, load the appropriate map 
	 */
	public void switchMap(){
		for (int Map = 0; Map < MapList.size(); Map++){
			map = MapList.get(Map);
			for( int i = 0; i < map.PlayerList.size(); i++){
				if (map.getCharTile(map.PlayerList.get(i).getX()/25, map.PlayerList.get(i).getY()/25) == "1"){
					Sprite traveller = map.PlayerList.get(i);
					map.PlayerList.remove(i);
					if ( currentMap == 0){
						map = MapList.get(1);
						currentMap = 1;
						traveller.PositionX = 5375;
						traveller.PositionY = 4100;
					}else if ( currentMap == 1){
						map = MapList.get(0);
						currentMap = 0;
						traveller.PositionX = 4200;
						traveller.PositionY = 3900;
					}
					
					traveller.PositionX = traveller.getX();
					traveller.map = map;
					map.addPlayer(traveller);
				}
			
			}
		}
		
	}
	
	/** 
	 * update sprite position based on game loop time (timeSpent).
	 * <p> Checks for collision
	 * @param timeSpent
	 */
	public void updateSprites(long timeSpent){
		for (int Map = 0; Map < MapList.size(); Map++){
			map = MapList.get(Map);
			for (int i = 0; i < map.PlayerList.size(); i++){
				map.PlayerList.get(i).attack();
				map.PlayerList.get(i).Movement(timeSpent);
				map.PlayerList.get(i).setSpriteOrientation();
				if (map.PlayerList.get(i).isAlive == false){
					game.showLoseGameMenu();			
				}
			}
			
			//Update position and orientation of all sprites.
			for (int i = 0; i < map.SpriteList.size(); i++){
				if ( map.PlayerList.size() != 0){
					map.SpriteList.get(i).Movement(timeSpent);
					map.SpriteList.get(i).setSpriteOrientation();
				}
				if (map.SpriteList.get(i).isAlive == false){
					map.PlayerList.get(0).Points = map.PlayerList.get(0).Points + 1;
					map.removeSprite(map.SpriteList.get(i));
				}
			}
			
			//Check if colliding with player, and attack if you are.
			if ( map.SpriteList.size() > 1){
				for (int i = 0; i < map.SpriteList.size(); i++){
					for (int j = 0; j < map.PlayerList.size(); j++){
						if (map.SpriteList.get(i).isCollision(map.PlayerList.get(j)) == true){
							map.SpriteList.get(i).attack();
						}
					}	
				}
			}
		}
	}

	
}
