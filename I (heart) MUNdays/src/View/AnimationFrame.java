package View;

import java.awt.Image;

/**
 * 
 * Animation frame consists of an image and a duration to display the image
 *  
 */
public class AnimationFrame {
	Image image;
	Long time;
	
	
	/**
	 * sets Image (image) and duration (time)
	 * @param image
	 * @param time
	 */
	public AnimationFrame(Image image, Long time){
		this.image = image;
		this.time = time;
	}
}
