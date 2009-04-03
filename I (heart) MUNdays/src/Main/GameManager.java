package Main;

import javax.swing.SwingUtilities;
 
/**
  * Starts the game Frame
  * 
  * @see GameFrame
  *
  */

public class GameManager {
	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				new GameFrame(false) ; }} ) ;
	}
}
