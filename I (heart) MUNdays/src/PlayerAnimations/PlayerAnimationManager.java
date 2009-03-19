package PlayerAnimations;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;


import Model.Player;

public class PlayerAnimationManager{
	
	private Player player;
	private Image stillImage;
	private Image walkImage;
	private Image shootImage;
	
	private PlayerAnimations playerWalking;
	private PlayerAnimations currentAnimation;
	public enum PlayerAnimationStates {Shooting, Walking, Still};
	PlayerAnimationStates currentState , nextState;
	long startTime;
	long timeChanged;
	
	
	public PlayerAnimationManager(Player player){
		this.player = player;
		playerWalking = new PlayerAnimations(player);
		Image walk0 = Toolkit.getDefaultToolkit().createImage("Player1.png");
		Image walk1 = Toolkit.getDefaultToolkit().createImage("newplayerwalk1.png");
		Image walk2 = Toolkit.getDefaultToolkit().createImage("Player1.png");
		Image walk3 = Toolkit.getDefaultToolkit().createImage("newplayerwalk2.png");
		
		
		playerWalking.addFrame(walk0);
		playerWalking.addFrame(walk1);
		playerWalking.addFrame(walk2);
		playerWalking.addFrame(walk3);
		
		currentAnimation = playerWalking;
		
	}
	public PlayerAnimations getCurrentAnimation(){
		return currentAnimation;
	}
}
		
		
		
		
		
		/*
		currentState = PlayerAnimationStates.Still;
		nextState = PlayerAnimationStates.Still;
		stillImage = Toolkit.getDefaultToolkit().getImage("newPlayer.jpg");
		walkImage = Toolkit.getDefaultToolkit().getImage("newPlayer1.jpg");
		shootImage = Toolkit.getDefaultToolkit().getImage("newPlayerShoot.jpg");
		startTime = System.currentTimeMillis();
		timeChanged = System.currentTimeMillis();
	}
	
	

	public void setImage(){
		startTime = System.currentTimeMillis();
		
		


		if(startTime - timeChanged <= 100){
				return;}
		

		
		else{
			nextState = currentState;
		
		switch(nextState){
		
		case Still:
			player.setSpriteImage(stillImage);
			break;
			
		case Walking:
			player.setSpriteImage(walkImage);
			break;
			
		case Shooting:
			if(player.playersWeapon().magAmmo() > 0){
				player.setSpriteImage(shootImage);	
			}
			timeChanged = System.currentTimeMillis();
			break;

			
		}
		
		}
		
		

		
			}
	public void setState(PlayerAnimationStates state){
		currentState = state;	
		setImage();
	}
}
*/
