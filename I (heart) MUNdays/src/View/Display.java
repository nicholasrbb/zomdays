package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import Model.ModelManager;
import Model.Player;
import Model.TileMap;


@SuppressWarnings("serial")
public class Display extends JPanel{
	AffineTransform playerOrientation;
	ModelManager manager;
	int offsetX, offsetY;
	int screenWidth;
	int screenHeight;
	Player player;
	public int cornerX1;
	public int cornerY1;
	public int cornerX2;
	public int cornerY2;
	
	public int dcornerX1;
	public int dcornerY1;
	
	final Canvas canvas ;
	
	public Display(ModelManager Manager, Player playa, int SW, int SH){
		manager = Manager;
		player = playa;	
		setPreferredSize( new Dimension(500, 500) ) ;
		screenWidth = SW;
		screenHeight = SH;
		canvas  = new Canvas(500, 500, 10) ;
		cornerX1 = player.getX()- screenWidth/2;
		cornerY1 = player.getY()- screenHeight/2;
		cornerX2 = player.getX()+ screenWidth/2;
		cornerY2 = player.getY()+ screenHeight/2;
		
	}
	@Override public void paintComponent(Graphics g){
				
		Graphics2D g2d = (Graphics2D) g;
		
		//Correction the position of the display relative to player.
		dcornerX1 = cornerX1;
		dcornerY1 = cornerY1;
		
		if ( cornerX1 > (player.getX() - 100) ){
			cornerX1 = player.getX() - 100; 
			cornerX2 = (player.getX() - 100) + screenWidth;
			
		}
		if ( cornerY1 > player.getY() - 100 ){
			cornerY1 = player.getY() - 100; 
			cornerY2 = (player.getY() - 100) + screenHeight;
		}
		if ( cornerX2 < (player.getX() + 100) ){
			cornerX1 = (player.getX() + 100) - screenWidth; 
			cornerX2 = player.getX() + 100;
		}
		if ( cornerY2 < (player.getY() + 100) ){
			cornerY1 = (player.getY() + 100) - screenHeight; 
			cornerY2 = player.getY() + 100;
		}
		
		//Correcting the position of the display relative to the map size.
		if ( cornerX1 <= 0 ){
			cornerX1 = 0; 
			cornerX2 = screenWidth;
		}
		if ( cornerY1 <= 0 ){
			cornerY1 = 0; 
			cornerY2 = screenHeight;
		}
		
		if ( cornerX2 >= 3300 ){
			cornerX1 = 3300 - screenWidth; 
			cornerX2 = 3300;
		}
		if ( cornerY2 >= 3300 ){
			cornerX1 = 3300 - screenHeight; 
			cornerX2 = 3300;
		}
		
		dcornerX1 = dcornerX1-cornerX1;
		dcornerY1 = dcornerY1-cornerY1;
		player.mouseX = player.mouseX - dcornerX1;
		player.mouseY = player.mouseY - dcornerY1;
		
		//System.out.println("Corner One: " + cornerX1 + " " + cornerY1 + " Corner Two: " + cornerX2 + " " + cornerY2 +  " Player: " + player.getX() + "  " + player.getY() +  " Mouse: " + player.mouseX + "  " + player.mouseY +  " Zombie: " + map.SpriteList.get(0).getX() + "  " + map.SpriteList.get(0).getY());
		
		
		offsetX = cornerX1%50;
		offsetY = cornerY1%50;
		
		
		// setting tile view parameters
		int firstTileX = pixelsToTiles(cornerX1-offsetX);
		int lastTileX = pixelsToTiles(cornerX2 + offsetX);
		int firstTileY  = pixelsToTiles(cornerY1-offsetY);
		int lastTileY = pixelsToTiles(cornerY2 + offsetY);	
		
		
		// Print all the visible Tiles
		int dy = 0;
		for(int y = firstTileY; y < lastTileY; y++ ){
			int dx = 0;
			for(int x = firstTileX; x < lastTileX; x++ ){
				Image image = manager.map.getTile(x, y);
				g2d.drawImage(image,dx - offsetX,dy - offsetY, null);
				dx = dx+50;
			}
			dy = dy+50;
		}
		
		// Print Player 
		player.setPlayerSpriteOrientation(cornerX1,cornerY1);
		g2d.drawImage(player.getSpriteImage(),player.getSpriteOrientation(), null);
		
    	
			
		// Paint all other Players	
		for ( int i = 0; i < manager.map.PlayerList.size(); i++){	
			if(manager.map.PlayerList.get(i) != player)
				g2d.drawImage(manager.map.PlayerList.get(i).getSpriteImage(),playerOrientation, null);
    	}
		
		// Print all NPC's
    	for ( int i = 0; i < manager.map.SpriteList.size(); i++){
    		AffineTransform npcTransform = new AffineTransform();
    		npcTransform.setToTranslation(manager.map.SpriteList.get(i).getX() - cornerX1 - 25, manager.map.SpriteList.get(i).getY() - cornerY1 - 25);	
    		npcTransform.rotate(Math.toRadians(manager.map.SpriteList.get(i).angle),manager.map.SpriteList.get(i).getSpriteImage().getWidth(null)/2, manager.map.SpriteList.get(i).getSpriteImage().getHeight(null)/2);    	
    		g2d.drawImage(manager.map.SpriteList.get(i).getSpriteImage(),npcTransform, null);
    	}
		
	}
	public int pixelsToTiles(int x){
		return (x/50);
		
	}
	public int tilesToPixels(int x){
		return (50*x);
		
	}
	public int getCornerX1(){
		return cornerX1;
	}
	public int getCornerY1(){
		return cornerY1;
	}

}

