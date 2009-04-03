package View;

import java.awt.GridBagConstraints;


public class MenuConstraints extends GridBagConstraints {

	public MenuConstraints(int xPos, int yPos, int w, int h, boolean fill) {
		super();
		
		this.weightx = 100;
		this.weighty = 100;
		this.gridx = xPos;
		this.gridy = yPos;
		this.gridwidth = w;
		this.gridheight = h;
		
		if (fill) {
			this.fill = GridBagConstraints.BOTH;
		}
		
	}
	
}