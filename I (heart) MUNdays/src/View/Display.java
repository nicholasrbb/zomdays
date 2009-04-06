package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import Model.ModelManager;
import Model.Player;
import Model.RMIInterface;
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
	
	Image healthPack;
	Image ammoPack;
	Image Points10;
	Image Points20;
	Image Points50;
	
	
	
	
	
	public Display(RemotePlayer playa, int SW, int SH){
		super();
		//Buttons Button = new Buttons(playa);
		//this.addKeyListener( Button);
		try{
		
			player = playa;	
			screenWidth = SW;
			screenHeight = SH;
			cornerX1 = player.getX()- screenWidth/2;
			cornerY1 = player.getY()- screenHeight/2;
			cornerX2 = player.getX()+ screenWidth/2;
			cornerY2 = player.getY()+ screenHeight/2;
			background = Toolkit.getDefaultToolkit().createImage("floor.jpg");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		
		
		healthPack = Toolkit.getDefaultToolkit().createImage("BuildImg/health.jpg");
		ammoPack = Toolkit.getDefaultToolkit().createImage("BuildImg/bullet.jpg");
		Points10 = Toolkit.getDefaultToolkit().createImage("Points/Brain3.png");
		Points20 = Toolkit.getDefaultToolkit().createImage("Points/Brain2.png");
		Points50 = Toolkit.getDefaultToolkit().createImage("Points/Brain1.png");
	}
		
		
		@Override public void paintComponent(Graphics g){
		try{
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
					g2d.drawImage(image,dx - offsetX,dy - offsetY, null);
					dx = dx+25;
				}
				dy = dy+25;
			}
			
			if(player.getMap().ItemList.size() > 0){
	    		for (int i = 0; i < player.getMap().ItemList.size();i++ ){
	    			if(player.getMap().ItemList.get(i).getType().equals("health")){
		    			g2d.drawImage(healthPack, player.getMap().ItemList.get(i).getX()- cornerX1, player.getMap().ItemList.get(i).getY()- cornerY1, null);

	    			}
	    			if(player.getMap().ItemList.get(i).getType().equals("ammo")){
		    			g2d.drawImage(ammoPack, player.getMap().ItemList.get(i).getX()- cornerX1, player.getMap().ItemList.get(i).getY()- cornerY1, null);
		    			
		   			}
	    			
	    			if(player.getMap().ItemList.get(i).getType().equals("points10")){
		    			g2d.drawImage(Points10, player.getMap().ItemList.get(i).getX()- cornerX1, player.getMap().ItemList.get(i).getY()- cornerY1, null);
	    			}
	    			
	    			if(player.getMap().ItemList.get(i).getType().equals("points20")){
		    			g2d.drawImage(Points20, player.getMap().ItemList.get(i).getX()- cornerX1, player.getMap().ItemList.get(i).getY()- cornerY1, null);
	    			}
	    			
	    			if(player.getMap().ItemList.get(i).getType().equals("points50")){
		    			g2d.drawImage(Points50, player.getMap().ItemList.get(i).getX()- cornerX1, player.getMap().ItemList.get(i).getY()- cornerY1, null);
	    		}
	    	}
		
			
		
			
			
			// Print Player 
				player.setPlayerOrientation(cornerX1, cornerY1);
				g2d.drawImage(player.getSpriteImage(),player.getPlayerSpriteOrientation(), null);
				g2d.drawImage(player.getWeapons().get(player.getCurrentWeapon()).getImage(),player.getPlayerSpriteOrientation(), null);
				
	    	

			
			
			// Paint all other Players	
			for ( int i = 0; i < player.getMap().PlayerList.size(); i++){	
				if(player.getMap().PlayerList.get(i) != player){
					AffineTransform otherTransform = new AffineTransform();
					otherTransform.setToTranslation(player.getMap().PlayerList.get(i).getX() - cornerX1 - 25, player.getMap().PlayerList.get(i).getY() - cornerY1 - 25);	
					otherTransform.rotate(Math.toRadians(player.getMap().PlayerList.get(i).angle),player.getMap().PlayerList.get(i).getSpriteImage().getWidth(null)/2, player.getMap().PlayerList.get(i).getSpriteImage().getHeight(null)/2);   
					g2d.drawImage(player.getMap().PlayerList.get(i).getSpriteImage(),otherTransform, null);
					//g2d.drawImage(((Player) player.getMap().PlayerList.get(i)).getWeapons().get(player.getCurrentWeapon()).getImage(),otherTransform, null);
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
	    		g2d.drawImage(player.getMap().SpriteList.get(i).Legs,npcTransform, null);
	    		g2d.drawImage(player.getMap().SpriteList.get(i).getSpriteImage(),npcTransform, null);
	    		g2d.drawImage(player.getMap().SpriteList.get(i).Blood, npcTransform, null);
	
	    		
	
	    	}
	    	
	    	// Load HUD Images
	    	Image bullet = Toolkit.getDefaultToolkit().getImage("Hud/9mm.png");
	    	Image gun = Toolkit.getDefaultToolkit().getImage("Hud/gun.png");
	    	Image gun2x =Toolkit.getDefaultToolkit().getImage("Hud/gun2x.png"); 
	    	Image uzi =Toolkit.getDefaultToolkit().getImage("Hud/Uzi.png"); 
	    	Image shotgun =Toolkit.getDefaultToolkit().getImage("Hud/shotgun.png"); 
	    	Image knife =Toolkit.getDefaultToolkit().getImage("Hud/Knife.png"); 
	    	Image bulletUzi = Toolkit.getDefaultToolkit().getImage("Hud/9mmUzi.png");
	    	Image shell = Toolkit.getDefaultToolkit().getImage("Hud/shell.png");
	    	Image reload = Toolkit.getDefaultToolkit().getImage("Hud/Reload.png");
	    	
	    	Image health = Toolkit.getDefaultToolkit().getImage("Hud/healthBar.png");
	    	int hudAmmo = player.getWeapons().get(player.getCurrentWeapon()).magAmmo();
	    	int ammoXPos = 750;
	    	int ammoYPos = 10;
	    	
	    	Image ammoImage;
	    	
	    	int rowLimit;
	    	if (player.getWeapons().get(player.getCurrentWeapon()).getWeaponName().equals("Shotgun")){
	    		g2d.drawImage(shotgun, ammoXPos-shotgun.getWidth(null), ammoYPos, null);
	    		ammoXPos = ammoXPos - shotgun.getWidth(null)- 50;
	    		ammoImage = shell;
	    		rowLimit = 8;
	    	}
	    	
	    	else if (player.getWeapons().get(player.getCurrentWeapon()).getWeaponName().equals("Uzi")){
	    		g2d.drawImage(uzi, ammoXPos-uzi.getWidth(null), ammoYPos, null);
	    		ammoXPos = ammoXPos - uzi.getWidth(null)- 50;
	    		ammoImage = bulletUzi;
	    		rowLimit = 30;
	    	}
	    	
	    	else if (player.getWeapons().get(player.getCurrentWeapon()).getWeaponName().equals("2x Hand Gun")){
	    		g2d.drawImage(gun2x, ammoXPos-gun2x.getWidth(null), ammoYPos, null);
	    		
	    		ammoXPos = ammoXPos - gun.getWidth(null)- 50;
	    		ammoImage = bullet;
	    		rowLimit = player.getWeapons().get(player.getCurrentWeapon()).magAmmo()/2;
	    	}
	    	else if(player.getWeapons().get(player.getCurrentWeapon()).getWeaponName().equals("Hand Gun")){
	    		g2d.drawImage(gun, ammoXPos-gun.getWidth(null), ammoYPos, null);
	    		ammoXPos = ammoXPos - gun.getWidth(null)- 50;
	    		ammoImage = bullet;
	    		rowLimit = 15;
	    	}
	    	else{
	    		
	    		g2d.drawImage(knife, ammoXPos-knife.getWidth(null), ammoYPos, null);
	    		ammoXPos = ammoXPos - knife.getWidth(null)- 50;
	    		ammoImage = bullet;
	    		rowLimit = 1;
	    	}
	    	
	    	int count = 0;
    		int stack = 0;
    		
	    	for(int k = 1; k <=hudAmmo; k++){
	    		
	    		if (count >= rowLimit){
	    			count = 0;
	    			stack++;
	    		}
	    		
	    		 		
	    		g2d.drawImage(ammoImage, ammoXPos-((k-1)*ammoImage.getWidth(null))+(ammoImage.getWidth(null)*rowLimit*stack), ammoYPos + (ammoImage.getHeight(null)*stack), null);
	    		count++;
	    		
	    		
	    	}
	    	
	    	if (hudAmmo == 0){
	    		g2d.drawImage(reload, ammoXPos-reload.getWidth(null), ammoYPos, null);
	    	}
	    	
	    	// Draw all Items
	    	
	    	

	    	
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
			
			//Load Ammo Count Images
			Image ammoBack = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/ammoBack.png");
			Image ZeroSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/0.png");
			Image OneSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/1.png");
			Image TwoSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/2.png");
			Image ThreeSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/3.png");
			Image FourSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/4.png");
			Image FiveSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/5.png");
			Image SixSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/6.png");
			Image SevenSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/7.png");
			Image EightSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/8.png");
			Image NineSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/9.png");
			Image DashSmall = Toolkit.getDefaultToolkit().getImage("Hud/SmallDigits/-.png");
			
						
			//Setup and Paint Ammo Count
			int AmmoCount = player.getWeapons().get(player.getCurrentWeapon()).getAmmo();			
			String ammoCount = Integer.toString(AmmoCount);
			int ammoCountX = ammoXPos+30;
			int ammoCountY = ammoYPos + 80;
			int digitStart = ammoXPos+35;
			g2d.drawImage(ammoBack, ammoXPos+30, ammoYPos + 78, null);
			g2d.drawRect(ammoXPos+30, ammoYPos + 78, 117, 33);
			
			// Pad the Amount of ammo
			
			
			
			
			if(ammoCount.charAt(0) == '-'){
				ammoCount = "0000";
			}
			
			ammoCount = pad(ammoCount,4);
			
			
			
			
			for(int i = 0; i < ammoCount.length(); i++){
				
				
				if (ammoCount.charAt(i) == '0')
					g2d.drawImage(ZeroSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '1')
					g2d.drawImage(OneSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '2')
					g2d.drawImage(TwoSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '3')
					g2d.drawImage(ThreeSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '4')
					g2d.drawImage(FourSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '5')
					g2d.drawImage(FiveSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '6')
					g2d.drawImage(SixSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '7')
					g2d.drawImage(SevenSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '8')
					g2d.drawImage(EightSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '9')
					g2d.drawImage(NineSmall, digitStart + i*25, ammoCountY, null);
				else if (ammoCount.charAt(i) == '-')
					g2d.drawImage(DashSmall, digitStart + i*25, ammoCountY, null);
					
			}
			
	
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
			
			
			g2d.drawImage(health, 30, 540, player.getHealth()*6, 20, null);
			g2d.drawRect(30, 540, 300, 20);
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
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
	
	private String pad(String s, int numDigits){
        StringBuffer sb = new StringBuffer(s);
        int numZeros = numDigits - s.length();
        while(numZeros-- > 0) { 
            sb.insert(0, "0");
        }
		return sb.toString();
        
       
    }

}

