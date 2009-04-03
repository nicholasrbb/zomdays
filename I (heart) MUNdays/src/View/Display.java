package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Model.ModelManager;
import Model.Player;
import Model.RemotePlayer;


/**
 * Class that controls the updating of the game by calling paingComponent.
 *
 */

@SuppressWarnings("serial")
public class Display extends JPanel {
	AffineTransform playerOrientation;
	int offsetX, offsetY;
	int screenWidth;
	int screenHeight;
	public RemotePlayer player;
	Image background;
	public int cornerX1;
	public int cornerY1;
	public int cornerX2;
	public int cornerY2;
	
	public int dcornerX1;
	public int dcornerY1;
	
	
	
	
	public Display(RemotePlayer playa, int SW, int SH){
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
		
		if ( cornerX2 >= 25*player.getMap().getWidth() ){
			cornerX1 = 25*player.getMap().getWidth() - screenWidth; 
			cornerX2 = 25*player.getMap().getWidth();
		}
		if ( cornerY2 >= 25*player.getMap().getHeight() ){
			cornerY1 = 25*player.getMap().getHeight() - screenHeight; 
			cornerY2 = 25*player.getMap().getHeight();
		}
		
		dcornerX1 = dcornerX1-cornerX1;
		dcornerY1 = dcornerY1-cornerY1;
		player.setMouseX(player.getMouseX() - dcornerX1); 
		player.setMouseY(player.getMouseY() - dcornerY1);
		
		
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
				Image image = player.getMap().getTile(x, y);
				g2d.drawImage(background,dx - offsetX,dy - offsetY, null);
				g2d.drawImage(image,dx - offsetX,dy - offsetY, null);
				dx = dx+25;
			}
			dy = dy+25;
		}
		
		
		// Print Player 
			player.setPlayerOrientation(cornerX1, cornerY1);
			g2d.drawImage(player.getSpriteImage(),player.getPlayerSpriteOrientation(), null);
			g2d.drawImage(player.getWeapons().get(player.getCurrentWeapon()).getImage(),player.getPlayerSpriteOrientation(), null);
			
    		
			g2d.drawString("Health: " + player.getHealth(),50,300);
    		g2d.drawString("Mag: " + player.getWeapons().get(0).magAmmo(),50,315); 
    		g2d.drawString("Ammo: " + player.getWeapons().get(0).getAmmo(),50,330);
    		g2d.drawString("Current Weapon: " + player.getWeapons().get(player.getCurrentWeapon()).getWeaponName() , 50, 345);
		
    	
		//Paint all the Items
		for ( int i = 0; i < player.getMap().ItemList.size(); i++){
    		g2d.drawImage(player.getMap().ItemList.get(i).getImage(),player.getMap().ItemList.get(i).getX() - cornerX1 -12, player.getMap().ItemList.get(i).getY() - cornerY1 -12, null);
		}
		

		
		
		// Paint all other Players	
		for ( int i = 0; i < player.getMap().PlayerList.size(); i++){	
			if(player.getMap().PlayerList.get(i) != player){
				AffineTransform otherTransform = new AffineTransform();
				otherTransform.setToTranslation(player.getMap().PlayerList.get(i).getX() - cornerX1 - 25, player.getMap().PlayerList.get(i).getY() - cornerY1 - 25);	
				otherTransform.rotate(Math.toRadians(player.getMap().PlayerList.get(i).angle),player.getMap().PlayerList.get(i).getSpriteImage().getWidth(null)/2, player.getMap().PlayerList.get(i).getSpriteImage().getHeight(null)/2);   
				g2d.drawImage(player.getMap().PlayerList.get(i).getSpriteImage(),otherTransform, null);
				g2d.drawImage(((Player) player.getMap().PlayerList.get(i)).getWeapons().get(player.getCurrentWeapon()).getImage(),otherTransform, null);
			}
		}
		
		
		// Print all NPC's
    	for ( int i = 0; i < player.getMap().SpriteList.size(); i++){
    		AffineTransform npcTransform = new AffineTransform();
    		if (player.getMap().SpriteList.get(i).getRandom() == false){
    			npcTransform.setToTranslation(player.getMap().SpriteList.get(i).getX() - cornerX1-55, player.getMap().SpriteList.get(i).getY() - cornerY1-55);	
    			npcTransform.rotate(Math.toRadians(player.getMap().SpriteList.get(i).angle),player.getMap().SpriteList.get(i).getSpriteImage().getWidth(null)/2, player.getMap().SpriteList.get(i).getSpriteImage().getHeight(null)/2);    	
    		}else{
    			npcTransform.setToTranslation(player.getMap().SpriteList.get(i).getX() - cornerX1 - 55, player.getMap().SpriteList.get(i).getY() - cornerY1 - 55);	
    			int angle = 45*(player.getMap().SpriteList.get(i).getNumber());
        		npcTransform.rotate(angle,player.getMap().SpriteList.get(i).getSpriteImage().getWidth(null)/2, player.getMap().SpriteList.get(i).getSpriteImage().getHeight(null)/2);
    		}
    		System.out.println(player.getMap().SpriteList.get(i).);
    		g2d.drawImage(player.getMap().SpriteList.get(i).Blood, npcTransform, null);
    		g2d.drawImage(player.getMap().SpriteList.get(i).Legs,npcTransform, null);
    		g2d.drawImage(player.getMap().SpriteList.get(i).getSpriteImage(),npcTransform, null);
    		
    		

    	}
    	
    	
    	
    	// Load Score Images
		Image Zero = Toolkit.getDefaultToolkit().getImage("Digits/0.png");
		Image One = Toolkit.getDefaultToolkit().getImage("Digits/1.png");
		Image Two = Toolkit.getDefaultToolkit().getImage("Digits/2.png");
		Image Three = Toolkit.getDefaultToolkit().getImage("Digits/3.png");
		Image Four = Toolkit.getDefaultToolkit().getImage("Digits/4.png");
		Image Five = Toolkit.getDefaultToolkit().getImage("Digits/5.png");
		Image Six = Toolkit.getDefaultToolkit().getImage("Digits/6.png");
		Image Seven = Toolkit.getDefaultToolkit().getImage("Digits/7.png");
		Image Eight = Toolkit.getDefaultToolkit().getImage("Digits/8.png");
		Image Nine = Toolkit.getDefaultToolkit().getImage("Digits/9.png");

		// Paint Score
		int spacing = 25;
		int gameScoreInt = player.getPoints();
		String gameScore = Integer.toString(gameScoreInt);
		
		for(int i = 0; i < gameScore.length(); i++){
			if (gameScore.charAt(i) == '0')
				g2d.drawImage(Zero, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '1')
				g2d.drawImage(One, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '2')
				g2d.drawImage(Two, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '3')
				g2d.drawImage(Three, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '4')
				g2d.drawImage(Four, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '5')
				g2d.drawImage(Five, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '6')
				g2d.drawImage(Six, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '7')
				g2d.drawImage(Seven, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '8')
				g2d.drawImage(Eight, 10 + spacing*i, 10, null);
			else if (gameScore.charAt(i) == '9')
				g2d.drawImage(Nine, 10 + spacing*i, 10, null);
			
				
		}
		// paint life bar
		
		double maxAmmo = 400/15;
		g2d.drawRect(30, 440, 400, 25);
		Color color = g2d.getColor();
		g2d.setColor(Color.RED);
		g2d.fillRect(31, 441,player.getHealth()*8-1, 24);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(31, 455,(int) (player.getWeapons().get(player.getCurrentWeapon()).magAmmo()*maxAmmo), 10);
		g2d.setColor(color);
    	
		
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

