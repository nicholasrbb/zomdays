package events;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import View.Display;


import Interface.MouseListenerInterface;
import Model.Player;
import Model.TileMap;
import PlayerAnimations.PlayerAnimationManager.PlayerAnimationStates;

public class GameMouseEvents implements MouseListenerInterface {
	
	private Display d;
	private Player player;
	private TileMap map;
		
	public GameMouseEvents(Display d, Player player, TileMap Map){
		this.d = d;
		this.player = player;
		this.map = Map;
	}

	@Override
	public void mouseClick(int worldX, int worldY, int button, int clickCount) {
		
	}

	@Override
	public void mouseDragged(int worldX, int worldY) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered() {
				
		Image image = Toolkit.getDefaultToolkit().createImage("crosshair.jpg");
		
		Cursor aimer = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(15,15), "crosshairCursor");
		d.setCursor(aimer);
	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(int worldX, int worldY) {
		player.mouseX = worldX + d.cornerX1;
		player.mouseY = worldY + d.cornerY1;
	}

	@Override
	public void mousePressed(int worldX, int worldY, int button) {
		// set player animation
		
		int targetX = worldX + d.cornerX1;
		int targetY = worldY + d.cornerY1;
		
		if ( button == 1){
			//player.attack();
			player.attack = true;
		}
	}

	@Override
	public void mouseReleased(int worldX, int worldY, int button) {
		
		
	}

}
