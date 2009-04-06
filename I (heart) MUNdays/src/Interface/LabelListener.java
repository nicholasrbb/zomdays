package Interface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

/**
 *  Listeners to JLabel in all game menus
 *
 */

public abstract class LabelListener implements MouseListener {
private JLabel label;

	
	public LabelListener(JLabel label) {
		this.label = label;
	}
	
	@Override
	public abstract void mouseClicked(MouseEvent arg0);

	@Override
	public void mouseEntered(MouseEvent arg0) {
	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		//Do nothing.
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Do nothing.
	}

	public JLabel getLabel() {
		return this.label;
	}
	
}