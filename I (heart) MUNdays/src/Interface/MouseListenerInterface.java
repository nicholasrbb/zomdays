package Interface;

/**
 * Interface for creating new mouse listener states 
 *
 */
public interface MouseListenerInterface {

	public abstract void mouseClick( int worldX, int worldY, int button, int clickCount ) ;

	public abstract void mouseEntered();

	public abstract void mouseExited();
	
	public abstract void mousePressed(int worldX, int worldY, int button); 

	public abstract void mouseReleased(int worldX, int worldY, int button);

	public abstract void mouseDragged(int worldX, int worldY) ;

	public abstract void mouseMoved(int worldX, int worldY) ;
}
