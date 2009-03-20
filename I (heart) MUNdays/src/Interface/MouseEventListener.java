package Interface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/** 
 * interface used to assign Mouse Event Listeners to the Game Display
 *
 */
public class MouseEventListener implements MouseListener, MouseMotionListener{
	
	private MouseListenerInterface mouse;
	
	public MouseEventListener(MouseListenerInterface mouse){
		this.mouse = mouse;
		
	}

	/**
	 * calls mouse clicked in GameMouseEvents, when a full press and release on a single pixel,
	 * and send the x and y values of the location of the mouse in pixels on the GameFrame.	 * 
	 * 
	 * @param MouseEvent e;
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouse.mouseClick(x, y, 1, 0);
		
	}
	
	/**
	 * calls mouse entered in GameMouseEvents, when mouse enters GameFrame,
	 * and send the x and y values of the location of the mouse in pixels on the GameFrame.
	 * 
	 * @param MouseEvent e
	 */

	
	public void mouseEntered(MouseEvent e) {
		mouse.mouseEntered();
	}

	/**
	 * calls mouse exited in GameMouseEvents, when mouse exits GameFrame.
	 * 
	 * @param MouseEvent e
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * calls mouse pressed in GameMouseEvents, when mouse button is pressed on GameFrame,
	 * and send the x and y values of the location of the mouse in pixels on the GameFrame.
	 * 
	 * @param MouseEvent e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouse.mousePressed(x, y,e.getButton());
		
	}

	/**
	 * calls mouse released in GameMouseEvents, when mouse button is pressed on GameFrame,
	 * and send the x and y values of the location of the mouse in pixels on the GameFrame.
	 * 
	 * @param MouseEvent e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouse.mouseReleased(x, y,e.getButton());
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * calls mouse moved in GameMouseEvents, when mouse position changes on GameFrame,
	 * and send the x and y values of the location of the mouse in pixels on the GameFrame.
	 * 
	 * @param MouseEvent e
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouse.mouseMoved(x,y);}

}
