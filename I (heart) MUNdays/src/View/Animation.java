package View;

import java.awt.Image;
import java.util.ArrayList;

/**
 * Consists of an Array of AnimationFrames and iterates 
 * through the array based on the frames duration
 *
 */
public class Animation {
	private ArrayList <AnimationFrame> frames;
	int currentFrame = 0;
	long elapsedFrameTime = 0;
	long currentTime = 0;
	
	/**
	 * creates an Animation object with ArrayList frames
	 * @param frames
	 */
	public Animation(ArrayList <AnimationFrame> frames){
		this.frames = frames;
	}
	
	/**
	 * Returns the current Animation Image 
	 * 
	 * @return Image
	 */
	public Image getAnimationImage(){
		elapsedFrameTime = elapsedFrameTime + (System.nanoTime() - currentTime);
		if (elapsedFrameTime >= frames.get(currentFrame).time) {
			elapsedFrameTime = 0;
			if(currentFrame < frames.size()-1 ){
				currentFrame = currentFrame + 1;
			}else{
				currentFrame = 0;
			}
		}
		currentTime = System.nanoTime();
		return frames.get(currentFrame).image;
	}
}
