package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import Model.ModelManager;
import Model.Player;


/**
 * Class that controls the updating of the game by calling paingComponent.
 *
 */

@SuppressWarnings("serial")
public class Display extends JPanel{
	AffineTransform playerOrientation;
	int offsetX, offsetY;
	int screenWidth;
	int screenHeight;
	public Player player;
	Image background;
	public int cornerX1;
	public int cornerY1;
	public int cornerX2;
	public int cornerY2;
	
	public int dcornerX1;
	public int dcornerY1;
	
	
	
	
	public Display(Player playa, int SW, int SH){
		super();
		//Buttons Button = new Buttons(playa);
		//this.addKeyListener( Button);

		
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
		//manager.updateSprites(20);
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
		
		if ( cornerX2 >= 25*player.map.getWidth() ){
			cornerX1 = 25*player.map.getWidth() - screenWidth; 
			cornerX2 = 25*player.map.getWidth();
		}
		if ( cornerY2 >= 25*player.map.getHeight() ){
			cornerY1 = 25*player.map.getHeight() - screenHeight; 
			cornerY2 = 25*player.map.getHeight();
		}
		
		dcornerX1 = dcornerX1-cornerX1;
		dcornerY1 = dcornerY1-cornerY1;
		player.mouseX = player.mouseX - dcornerX1;
		player.mouseY = player.mouseY - dcornerY1;
		
		
		offsetX = cornerX1%25;
		offsetY = cornerY1%25;
		

		
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
				Image image = player.map.getTile(x, y);
				g2d.drawImage(background,dx - offsetX,dy - offsetY, null);
				g2d.drawImage(image,dx - offsetX,dy - offsetY, null);
				dx = dx+25;
			}
			dy = dy+25;
		}
		
		// Print Player 
		
			player.setPlayerOrientation(cornerX1, cornerY1);
			g2d.drawImage(player.getSpriteImage(),player.getPlayerSpriteOrientation(), null);
			g2d.drawImage(player.WeaponList.get(player.getCurrentWeapon()).getImage(),player.getPlayerSpriteOrientation(), null);
			
    		
			g2d.drawString("Health: " + player.getHealth(),50,300);
    		g2d.drawString("Mag: " + player.WeaponList.get(0).magAmmo(),50,315); 
    		g2d.drawString("Ammo: " + player.WeaponList.get(0).getAmmo(),50,330);
    		g2d.drawString("Current Weapon: " + player.WeaponList.get(player.getCurrentWeapon()).getWeaponName() , 50, 345);
			
		
		//Paint all the Items
		for ( int i = 0; i < player.map.ItemList.size(); i++){
    		g2d.drawImage(player.map.ItemList.get(i).getImage(),player.map.ItemList.get(i).getX() - cornerX1 -12, player.map.ItemList.get(i).getY() - cornerY1 -12, null);
		}
		

		
		
		// Paint all other Players	
		for ( int i = 0; i < player.map.PlayerList.size(); i++){	
			if(player.map.PlayerList.get(i) != player){
				
				
				AffineTransform otherTransform = new AffineTransform();
				otherTransform.setToTranslation(player.map.PlayerList.get(i).getX() - cornerX1 - 25, player.map.PlayerList.get(i).getY() - cornerY1 - 25);	
				otherTransform.rotate(Math.toRadians(player.map.PlayerList.get(i).angle),player.map.PlayerList.get(i).getSpriteImage().getWidth(null)/2, player.map.PlayerList.get(i).getSpriteImage().getHeight(null)/2);   
				g2d.drawImage(player.map.PlayerList.get(i).getSpriteImage(),otherTransform, null);
				
				
				
			}
		}
		// Print all NPC's
    	for ( int i = 0; i < player.map.SpriteList.size(); i++){
    		AffineTransform npcTransform = new AffineTransform();
    		npcTransform.setToTranslation(player.map.SpriteList.get(i).getX() - cornerX1 - 25, player.map.SpriteList.get(i).getY() - cornerY1 - 25);	
    		npcTransform.rotate(Math.toRadians(player.map.SpriteList.get(i).angle),player.map.SpriteList.get(i).getSpriteImage().getWidth(null)/2, player.map.SpriteList.get(i).getSpriteImage().getHeight(null)/2);    	
    		g2d.drawImage(player.map.SpriteList.get(i).getSpriteImage(),npcTransform, null);
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

