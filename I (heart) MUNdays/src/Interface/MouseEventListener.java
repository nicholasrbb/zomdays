package Interface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEventListener implements MouseListener, MouseMotionListener{
	
	private MouseListenerInterface mouse;
	
	public MouseEventListener(MouseListenerInterface mouse){
		this.mouse = mouse;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouse.mouseClick(x, y, 1, 0);
		
	}

	public void mouseEntered(MouseEvent e) {
		mouse.mouseEntered();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouse.mousePressed(x, y,e.getButton());
		
	}

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

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouse.mouseMoved(x,y);}

}
