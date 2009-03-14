package Model;

public class ModelManager{
	
	TileMap map;
	public ModelManager(TileMap Map){
		map = Map;
	}
	
	public void updateSprites(long timeSpent){
		for (int i = 0; i < map.PlayerList.size(); i++){
			map.PlayerList.get(i).Movement(timeSpent);
			map.PlayerList.get(i).setSpriteOrientation();
		}
		for (int i = 0; i < map.SpriteList.size(); i++){
			map.SpriteList.get(i).Movement(timeSpent);
			map.SpriteList.get(i).setSpriteOrientation();
			if (map.SpriteList.get(i).isAlive == false){
				map.removeSprite(map.SpriteList.get(i));}
			if ( map.SpriteList.size() > 1){
				for (int j = 0; j!=i && j<map.SpriteList.size(); j++){
					if (isCollision(map.SpriteList.get(i),map.SpriteList.get(j)) == false){
						
					}
				}	
			}
		}
	}

	
	public synchronized boolean isCollision(Sprite sprite1, Sprite sprite2){
		double x = (sprite1.getX() - sprite2.getX())*(sprite1.getX() - sprite2.getX());
		double y = (sprite1.getY() - sprite2.getY())*(sprite1.getY() - sprite2.getY());
		
		double spriteDistance = Math.sqrt(x+y);
		
		if (spriteDistance <= (sprite1.radius + sprite2.radius)){
			//System.out.println(x + "  " + y +  " " + spriteDistance + "  " + "Collision:  " + sprite1.getX() + "  " + sprite2.getX() );
			return true;
		}
				
		else
			return false;
	}
}
