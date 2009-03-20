package Interface;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.GameFrame;
import Model.Player;


/**
 * 
 * This adds Keylisteners to the game controls
 * <p> The buttons control the movements of the player.
 *
 */

public class Buttons implements KeyListener{
	
	Player player;
	GameFrame game;
	
	public Buttons(Player p, GameFrame game){
		this.game = game;
		player = p;
	}
	
	
	
		public void keyTyped(KeyEvent ke) {
	    	

		}
	
	
	@Override
	public void keyPressed(KeyEvent ke) {
		

		switch (ke.getKeyCode()) {
	    case KeyEvent.VK_W:  
	    	player.up = true;
	        break;
	    case KeyEvent.VK_D: 
	    	player.right = true;
	    	break;
	    case KeyEvent.VK_A: 
	    	player.left = true;
	    	break;
	    case KeyEvent.VK_S: 
	    	player.down = true;
	    	break;
	    case KeyEvent.VK_Q: 
	    	player.changeWeapon();
	    	break;
	    case KeyEvent.VK_R: 
	    	player.reloadWeapon();
	    	break;
	    case KeyEvent.VK_ESCAPE: 
	    	game.showInGameMenu();
	    	//System.exit(0);
	    	break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		switch (ke.getKeyCode()) {
	    case KeyEvent.VK_W:  
	    	player.up = false;
	        break;
	    case KeyEvent.VK_D: 
	    	player.right = false;
	    	break;
	    case KeyEvent.VK_A: 
	    	player.left = false;
	    	break;
	    case KeyEvent.VK_S: 
	    	player.down = false;
	    	break;
		}
	}
}
