package View;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class Canvas extends JPanel {

	private int worldWidth ;
	private int worldHeight ;
	private int zoom ;
	private int margin ; 
	
	
	public Canvas( int worldWidth, int worldHeight, int zoom){
		assert worldWidth > 0 ;
		assert worldHeight > 0 ;
		assert zoom > 0 ;
		this.worldWidth = worldWidth ;
		this.worldHeight = worldHeight ;
		this.zoom = zoom ;
		this.margin = zoom/2 ;
		Dimension size = new Dimension( worldWidth * zoom + margin, worldHeight * zoom + margin ) ;
		setMinimumSize(size) ;
		setPreferredSize(size) ;
		
		
		setOpaque(true) ;
		setBackground(Color.white) ;
	}
}