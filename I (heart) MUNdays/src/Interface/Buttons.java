package Interface;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import Main.GameFrame;
import Model.RemotePlayer;


/**
 * 
 * This adds Keylisteners to the game controls
 * <p> The buttons control the movements of the player.
 *
 */

public class Buttons implements KeyListener{
	
	RemotePlayer player;
	GameFrame game;
	
	public Buttons(RemotePlayer playa, GameFrame game){
		this.game = game;
		player = playa;
	}
	
	
	
		public void keyTyped(KeyEvent ke) {
	    	

		}
	
	
	@Override
	public void keyPressed(KeyEvent ke) {
		
		try{
		switch (ke.getKeyCode()) {
	    	case KeyEvent.VK_W:  
		    	player.setUP(true);
		        break;
		    case KeyEvent.VK_D: 
		    	player.setRIGHT(true);
		    	break;
		    case KeyEvent.VK_A: 
		    	player.setLEFT(true);
		    	break;
		    case KeyEvent.VK_S: 
		    	player.setDOWN(true);
		    	break;
		    case KeyEvent.VK_Q: 
		    	player.changeWeapon();
		    	break;
		    case KeyEvent.VK_R: 
		    	player.reloadWeapon();
		    	break;
		    case KeyEvent.VK_ESCAPE: 
		    	game.showInGameMenu();
		    	break;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		try{
			switch (ke.getKeyCode()) {
		    case KeyEvent.VK_W:  
		    	player.setUP(false);
		        break;
		    case KeyEvent.VK_D: 
		    	player.setRIGHT(false);
		    	break;
		    case KeyEvent.VK_A: 
		    	player.setLEFT(false);
		    	break;
		    case KeyEvent.VK_S: 
		    	player.setDOWN(false);
		    	break;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
