package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;


import Interface.Buttons;
import Model.ModelManager;
import Model.Player;
import Model.TileMap;


/**
 * Class that controls the updating of the game by calling paingComponent.
 *
 */
@SuppressWarnings("serial")
public class Display extends JPanel{
	AffineTransform playerOrientation;
	public ModelManager manager;
	int offsetX, offsetY;
	int screenWidth;
	int screenHeight;
	Player player;
	Image background;
	public int cornerX1;
	public int cornerY1;
	public int cornerX2;
	public int cornerY2;
	
	public int dcornerX1;
	public int dcornerY1;
	
	
	
	public Display(ModelManager Manager, Player playa, int SW, int SH){
		super();
		//Buttons Button = new Buttons(playa);
		//this.addKeyListener( Button);

		manager = Manager;
		player = playa;	
		screenWidth = SW;
		screenHeight = SH;
		cornerX1 = player.getX()- screenWidth/2;
		cornerY1 = player.getY()- screenHeight/2;
		cornerX2 = player.getX()+ screenWidth/2;
		cornerY2 = player.getY()+ screenHeight/2;
		background = Toolkit.getDefaultToolkit().createImage("floor.jpg");
		
		
	}
	
	
	
	@Override public void paintComponent(Graphics g){
		manager.updateSprites(20);
		
		Graphics2D g2d = (Graphics2D) g;
		
		//g.drawImage(background, 0, 0, null);
		
		
		//Correction the position of the display relative to player.
		dcornerX1 = cornerX1;
		dcornerY1 = cornerY1;
		
		if ( cornerX1 > (player.getX() - 200) ){
			cornerX1 = player.getX() - 200; 
			cornerX2 = (player.getX() - 200) + screenWidth;
			
		}
		if ( cornerY1 > player.getY() - 200 ){
			cornerY1 = player.getY() - 200; 
			cornerY2 = (player.getY() - 200) + screenHeight;
		}
		if ( cornerX2 < (player.getX() + 200) ){
			cornerX1 = cornerX1 + Math.abs(cornerX2 - (player.getX() + 200)); 
			cornerX2 = cornerX1 + screenWidth;
		}
		if ( cornerY2 < (player.getY() + 200) ){
			cornerY1 = (player.getY() + 200) - screenHeight; 
			cornerY2 = player.getY() + 200;
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
		
		if ( cornerX2 >= 25*manager.map.getWidth() ){
			cornerX1 = 25*manager.map.getWidth() - screenWidth; 
			cornerX2 = 25*manager.map.getWidth();
		}
		if ( cornerY2 >= 25*manager.map.getHeight() ){
			cornerY1 = 25*manager.map.getHeight() - screenHeight; 
			cornerY2 = 25*manager.map.getHeight();
		}
		
		dcornerX1 = dcornerX1-cornerX1;
		dcornerY1 = dcornerY1-cornerY1;
		player.mouseX = player.mouseX - dcornerX1;
		player.mouseY = player.mouseY - dcornerY1;
		
		
		offsetX = cornerX1%25;
		offsetY = cornerY1%25;
		//System.out.println("offsetX:  " + offsetX + "   offsetY:  " + offsetY);
		
		
		// setting tile view parameters
		int firstTileX = pixelsToTiles(cornerX1-offsetX);
		int lastTileX = pixelsToTiles(cornerX2 + offsetX) + 1;
		int firstTileY  = pixelsToTiles(cornerY1-offsetY);
		int lastTileY = pixelsToTiles(cornerY2 + offsetY) + 1;	
		
		//System.out.println("PlayerX:   " + manager.map.PlayerList.get(0).getX() + "lastTileX:  "  + lastTileX + "    lastTileY:    " + offsetY);
	
		// Print all the visible Tiles
		int dy = 0;
		for(int y = firstTileY; y < lastTileY; y++ ){
			int dx = 0;
			for(int x = firstTileX; x < lastTileX; x++ ){
				Image image = manager.map.getTile(x, y);
				g2d.drawImage(background,dx - offsetX,dy - offsetY, null);
				g2d.drawImage(image,dx - offsetX,dy - offsetY, null);
				dx = dx+25;
			}
			dy = dy+25;
		}
		
		// Print Player 
		//System.out.println("cornerX1:  " + cornerX1 + "   cornerY1:  " + cornerY1 + "   cornerX2:  " + cornerX2 + "   cornerY2:  " + cornerY2);
		player.setPlayerSpriteOrientation(cornerX1, cornerY1);
		g2d.drawImage(player.getSpriteImage(),player.getSpriteOrientation(), null);
		g2d.drawImage(player.WeaponList.get(player.getCurrentWeapon()).getImage(),player.getSpriteOrientation(), null);
    	
		
		
		//Paint all the Items
		for ( int i = 0; i < manager.map.ItemList.size(); i++){
    		//System.out.println("playerX: " + player.getX() + "PlayerY: " + player.getY() + " Item type: " +manager.map.ItemList.get(i).getType() +  " Item Image = " + manager.map.ItemList.get(i).getImage() + " " + manager.map.ItemList.get(i).getX()+ " " + manager.map.ItemList.get(i).getY());
    		g2d.drawImage(manager.map.ItemList.get(i).getImage(),manager.map.ItemList.get(i).getX() - cornerX1 -12, manager.map.ItemList.get(i).getY() - cornerY1 -12, null);
		}
		

			
		
		
		// Paint all other Players	
		for ( int i = 0; i < manager.map.PlayerList.size(); i++){	
			if(manager.map.PlayerList.get(i) != player)
				g2d.drawImage(manager.map.PlayerList.get(i).getSpriteImage(),playerOrientation, null);
				Player playdizzle = (Player) manager.map.PlayerList.get(i);
        		g2d.drawString("Health: " + manager.map.PlayerList.get(i).getHealth(),50,400);
        		g2d.drawString("Zombies Killed: " + manager.killed,50,415);
        		g2d.drawString("Mag: " + playdizzle.WeaponList.get(0).magAmmo(),50,430); 
        		g2d.drawString("Ammo: " + playdizzle.WeaponList.get(0).getAmmo(),50,445);
        		g2d.drawString("Current Weapon: " + playdizzle.WeaponList.get(playdizzle.getCurrentWeapon()).getWeaponName() , 50, 460);
        		//g2d.drawString("Coins Collected: " + playdizzle.coinCount,50,475);
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
		return (x/25);
		
	}
	public int tilesToPixels(int x){
		return (25*x);
		
	}
	public int getCornerX1(){
		return cornerX1;
	}
	public int getCornerY1(){
		return cornerY1;
	}

}

