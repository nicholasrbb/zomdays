package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class MenuLabel extends JLabel {
	
	public MenuLabel(String text, int font) {
		super(text, JLabel.CENTER);
		
		this.setBackground(Color.BLACK);
		this.setForeground(Color.YELLOW);
		this.setFont(new Font("SansSerif", Font.BOLD, font));
		
	}
	
}