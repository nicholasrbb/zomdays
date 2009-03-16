package Interface;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Player;




public class Buttons implements KeyListener{
	
	Player player;
		
	public Buttons(Player p){
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
