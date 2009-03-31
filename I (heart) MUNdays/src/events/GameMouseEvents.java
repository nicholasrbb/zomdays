package events;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import Interface.MouseListenerInterface;
import Model.Player;
import View.Display;

/**
 * This gets associated with a player and Display to control players
 * actions and bullet collisions with enemey on a given map.
 * 
 * @see Player, Display, MouseEventListener
 *
 */
public class GameMouseEvents implements MouseListenerInterface {
	
	private Display d;
	private Player player;
		
	public GameMouseEvents(Display d, Player player){
		this.d = d;
		this.player = player;
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
		if (player.getXBox() == false){		
			Image image = Toolkit.getDefaultToolkit().createImage("crosshair.png");
			
			Cursor aimer = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(15,15), "crosshairCursor");
			d.setCursor(aimer);
		}else{
			Image blankImage = Toolkit.getDefaultToolkit().createImage("blank.png");
			Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(blankImage, new Point(15,15), "crosshairCursor");
			d.setCursor(blank);
		}
		
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
		
		if (player.getXBox() == false){
			if ( button == 1){
				//player.attack();
				player.attack = true;
			}
		}
	}

	@Override
	public void mouseReleased(int worldX, int worldY, int button) {
		
		
	}

}
