package events;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.rmi.RemoteException;

import Interface.MouseListenerInterface;
import Model.RemotePlayer;
import View.Display;

/**
 * This gets associated with a player and Display to control players
 * actions and bullet collisions with enemies on a given map.
 * 
 * @see Player, Display, MouseEventListener
 *
 */
public class GameMouseEvents implements MouseListenerInterface {
	
	private Display d;
	private RemotePlayer player;
		
	public GameMouseEvents(Display d, RemotePlayer playa){
		this.d = d;
		this.player = playa;
	}

	@Override
	public void mouseClick(int worldX, int worldY, int button, int clickCount) {
		try {
			player.setMouseX(worldX + d.cornerX1);
			player.setMouseY(worldY + d.cornerY1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void mouseDragged(int worldX, int worldY) {
		try {
			player.setMouseX(worldX + d.cornerX1);
			player.setMouseY(worldY + d.cornerY1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}

	@Override
	public void mouseEntered() {
		try {
			if (player.getXBox() == false){		
				Image image = Toolkit.getDefaultToolkit().createImage("crosshairDot.png");
				
				Cursor aimer = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(25,25), "crosshairCursor");
				d.setCursor(aimer);
			}else{
				Image blankImage = Toolkit.getDefaultToolkit().createImage("blank.png");
				Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(blankImage, new Point(25,25), "crosshairCursor");
				d.setCursor(blank);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(int worldX, int worldY) {
		try {
			player.setMouseX(worldX + d.cornerX1);
			player.setMouseY(worldY + d.cornerY1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void mousePressed(int worldX, int worldY, int button) {
		try {
			player.setMouseX(worldX + d.cornerX1);
			player.setMouseY(worldY + d.cornerY1);
			if (player.getXBox() == false){
				if ( button == 1){
					//player.attack();
					player.setAttack(true);
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(int worldX, int worldY, int button) {
		try{
			player.setMouseX(worldX + d.cornerX1);
			player.setMouseY(worldY + d.cornerY1);
			player.setAttack(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
