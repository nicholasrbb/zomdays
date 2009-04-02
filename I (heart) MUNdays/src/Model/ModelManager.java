package Model;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * Manager that controls the updating of all sprites and animations for the game,
 * respawns Zombies and controls the transition between different game Maps.
 */
public class ModelManager implements Serializable{
	
	public TileMap map;
	Image zombieImage = Toolkit.getDefaultToolkit().createImage("zombie.png");
	Random generator = new Random();
	public int killed = 0;
	Player traveller;
	public ArrayList <TileMap> MapList;
	int currentMap;

	
	/**
	 * contains an ArrayList of tileMaps, and sets a default map
	 * 
	 * @param maplist
	 */
	public ModelManager(ArrayList <TileMap> maplist){
		MapList = maplist;
		map = maplist.get(0);
		currentMap = 0;
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
				map.PlayerList.get(i).image = map.PlayerList.get(i).animations.get(map.PlayerList.get(i).currentAnimation).getAnimationImage();
				map.PlayerList.get(i).WeaponList.get(map.PlayerList.get(i).getCurrentWeapon()).image = map.PlayerList.get(i).WeaponList.get(map.PlayerList.get(i).getCurrentWeapon()).animations.get(map.PlayerList.get(i).WeaponList.get(map.PlayerList.get(i).getCurrentWeapon()).currentAnimation).getAnimationImage();
				//System.out.println(map.PlayerList.get(i).WeaponList.get(map.PlayerList.get(0).getCurrentWeapon()).image);
			
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
			while(map.SpriteList.size() < 100){
				int x = Math.abs(generator.nextInt(15000)) + 50;
				int y = Math.abs(generator.nextInt(15000)) + 50;
				for (int i = 0; i < map.PlayerList.size(); i++){
					if (map.getCharTile(x/25, y/25) == " " && 
						map.getCharTile((x+25)/25, y/25) == " " && 
						map.getCharTile((x-25)/25, y/25) == " " &&
						map.getCharTile(x/25, (y+25)/25) == " " &&
						map.getCharTile(x/25, (y-25)/25) == " " &&
						Math.abs(map.PlayerList.get(i).getX() - x) > 450 && Math.abs(map.PlayerList.get(i).getY() - y) > 550){
						Zombie zombay = new Zombie(zombieImage, 50, 10, 10, x, y, 0.1, 0.1, map);
						map.addSprite(zombay);
					}
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
					
				}
			}
			
			//Update position and orientation of all sprites.
			for (int i = 0; i < map.SpriteList.size(); i++){
				if ( map.PlayerList.size() != 0){
					map.SpriteList.get(i).Movement(timeSpent);
					map.SpriteList.get(i).setSpriteOrientation();
				}
				if (map.SpriteList.get(i).isAlive == false){
					killed = killed +1;
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
