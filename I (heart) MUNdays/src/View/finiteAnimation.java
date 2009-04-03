package View;

import java.awt.Image;
import java.util.ArrayList;

public class finiteAnimation extends Animation{
	
	

	public finiteAnimation(ArrayList<AnimationFrame> frames) {
		super(frames);
	}
	
	public Image getAnimationImage(){
		elapsedFrameTime = elapsedFrameTime + (System.nanoTime() - currentTime);
		if (elapsedFrameTime >= frames.get(currentFrame).time) {
			elapsedFrameTime = 0;
			if(currentFrame < frames.size()-1 ){
				currentFrame = currentFrame + 1;}
			
		}
		currentTime = System.nanoTime();
		return frames.get(currentFrame).image;
	}
	
	public void startAnimation(){
		System.out.print(currentFrame);
		currentFrame = 0;
	}

}
