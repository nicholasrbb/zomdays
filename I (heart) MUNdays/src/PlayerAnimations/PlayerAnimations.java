package PlayerAnimations;

import java.awt.Image;
import java.util.ArrayList;

import Model.Player;

public class PlayerAnimations {
	
	private ArrayList<Image> frames;
	private long animationDuration;
	private long currentAnimationTime;
	private int currentFrame;
	private Player player;

	
	private long frameRate = 100;

	public PlayerAnimations(Player player) {
		this.player = player;
		frames = new ArrayList<Image>();
		animationDuration = 0;
	}
	
	public void start(){
		currentAnimationTime = 0;
		currentFrame = 0;
	}
	
	
	public void addFrame(Image image){
		frames.add(image);
		animationDuration += frameRate;}
	
	public Image getFrame(int i){		
		return frames.get(i);}
	
	 public synchronized void update(long elapsedTime) {
		 
		 System.out.println(player.getSpriteImage() + "  " + currentFrame);

	        if (frames.size() > 1) {
	        	currentAnimationTime += elapsedTime;



	            if (currentAnimationTime >= animationDuration || currentFrame == 8 ) {
	            	//System.out.println("resetting");

	            	currentAnimationTime = currentAnimationTime % animationDuration;
	            	currentFrame = 0;
	            }
	   		 player.setSpriteImage(frames.get(currentFrame));


	            if (currentAnimationTime > frameRate*currentFrame) {
	            	//System.out.println(currentAnimationTime  + "   " + frameRate);

	            	currentFrame++;
	            }
	        }
	    }
	
	
	
	

}
