package View;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {
	private ArrayList <AnimationFrame> frames;
	int currentFrame = 0;
	long elapsedFrameTime = 0;
	long currentTime = 0;
	
	public Animation(ArrayList <AnimationFrame> frames){
		this.frames = frames;
	}
	
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
