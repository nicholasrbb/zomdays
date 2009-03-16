package Model;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import Main.GameFrame;

public class ModelManager{
	
	public TileMap map;
	Image zombieImage = Toolkit.getDefaultToolkit().createImage("zombie.jpg");
	Random generator = new Random();
	public int killed = 0;
	Player traveller;
	
	public ModelManager(TileMap Map){
		map = Map;
	}
	
	public void manageSprites(){
		while(map.SpriteList.size() < 10){
			int x = Math.abs(generator.nextInt(3300)) + 50;
			int y = Math.abs(generator.nextInt(1500)) + 50;
			for (int i = 0; i < map.PlayerList.size(); i++){
				if (map.getCharTile(x/50, y/50) == " " && Math.abs(map.PlayerList.get(i).getX() - x) > 450 && Math.abs(map.PlayerList.get(i).getY() - y) > 550){
					Zombie zombay = new Zombie(zombieImage, 50, 10, 10, x, y, 0.1, 0.1, this);
					map.addSprite(zombay);
				}
			}
		}
	}
	
	public void switchMap(){
		for( int i = 0; i < map.PlayerList.size(); i++){
			if (map.getCharTile(map.PlayerList.get(i).getX()/50, map.PlayerList.get(i).getY()/50) == "1"){
				Sprite traveller = map.PlayerList.get(i);
				map.PlayerList.remove(i);
				map = GameFrame.getMap(1);
				System.out.println("switching map");
				map.addPlayer(traveller);
			}else{
				if (map.getCharTile(map.PlayerList.get(i).getX()/50, map.PlayerList.get(i).getY()/50) == "2"){
					
					Sprite traveller = map.PlayerList.get(i);
					map.PlayerList.remove(i);
					map = GameFrame.getMap(0);
					System.out.println("switching map");
					map.addPlayer(traveller);
					
				}
			}
		}
		
	}
	
	public void updateSprites(long timeSpent){
		
		//Update position and orientation of players.
		for (int i = 0; i < map.PlayerList.size(); i++){
			map.PlayerList.get(i).Movement(timeSpent);
			map.PlayerList.get(i).setSpriteOrientation();
			if (map.PlayerList.get(i).isAlive == false){
				map.removePlayer(map.PlayerList.get(i));}
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
