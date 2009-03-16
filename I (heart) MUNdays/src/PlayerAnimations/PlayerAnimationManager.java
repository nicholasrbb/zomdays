package PlayerAnimations;

import java.awt.Image;
import java.awt.Toolkit;


import Model.Player;

public class PlayerAnimationManager{
	
	Player player;
	Image stillImage;
	Image walkImage;
	Image shootImage;
	public enum PlayerAnimationStates {Shooting, Walking, Still};
	PlayerAnimationStates currentState , nextState;
	long startTime;
	long timeChanged;
	
	
	public PlayerAnimationManager(Player player){
		this.player = player;
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
