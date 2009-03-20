package Model;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import Main.GameFrame;

public class ModelManager{
	
	public TileMap map;
	Image zombieImage = Toolkit.getDefaultToolkit().createImage("zombie.png");
	Random generator = new Random();
	public int killed = 0;
	Player traveller;
	public ArrayList <TileMap> MapList;
	int currentMap;
	
	public ModelManager(ArrayList <TileMap> maplist){
		MapList = maplist;
		map = maplist.get(0);
		currentMap = 0;
	}
	
	public void updateAnimations(){
		map.PlayerList.get(0).image = map.PlayerList.get(0).animations.get(map.PlayerList.get(0).currentAnimation).getAnimationImage();
		map.PlayerList.get(0).WeaponList.get(map.PlayerList.get(0).getCurrentWeapon()).image = map.PlayerList.get(0).WeaponList.get(map.PlayerList.get(0).getCurrentWeapon()).animations.get(map.PlayerList.get(0).WeaponList.get(map.PlayerList.get(0).getCurrentWeapon()).currentAnimation).getAnimationImage();
	}
	
	public void manageSprites(){
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
					
					Zombie zombay = new Zombie(zombieImage, 50, 10, 10, x, y, 0.1, 0.1, this);
					map.addSprite(zombay);
				}
			}
		}
	}
	
	public void switchMap(){
		for( int i = 0; i < map.PlayerList.size(); i++){
			if (map.getCharTile(map.PlayerList.get(i).getX()/25, map.PlayerList.get(i).getY()/25) == "1"){
				Sprite traveller = map.PlayerList.get(i);
				map.PlayerList.remove(i);
				if ( currentMap == 0){
					map = MapList.get(1);
					currentMap = 1;
				}
				if ( currentMap == 1){
					map = MapList.get(0);
					currentMap = 0;
				}
				System.out.println("switching map");
				traveller.PositionX = traveller.getX();
				map.addPlayer(traveller);
			}
			/*
			else{
				if (map.getCharTile(map.PlayerList.get(i).getX()/25, map.PlayerList.get(i).getY()/25) == "2"){
					Sprite traveller = map.PlayerList.get(i);
					map.PlayerList.remove(i);
					MapList.get(0);
					System.out.println("switching map");
					map.addPlayer(traveller);
					
				}
			}
			*/
		}
		
	}
	
	public void updateSprites(long timeSpent){
		//System.out.println("updatesprites called");
		//Update position and orientation of players.
		for (int i = 0; i < map.PlayerList.size(); i++){
			map.PlayerList.get(i).attack();
			map.PlayerList.get(i).Movement(timeSpent);
			if (map.PlayerList.get(i).isAlive == false){
				System.exit(0);
				//map.removePlayer(map.PlayerList.get(i));
			}
		}
		
		//Update position and orientation of all sprites.
		for (int i = 0; i < map.SpriteList.size(); i++){
			map.SpriteList.get(i).Movement(timeSpent);
			map.SpriteList.get(i).setSpriteOrientation();
			if (map.SpriteList.get(i).isAlive == false){
				killed = killed +1;
				map.removeSprite(map.SpriteList.get(i));
			}
			//map.addToGrid(map.SpriteList.get(i).getX()/50,map.SpriteList.get(i).getY()/50, map.SpriteList.get(i));
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
