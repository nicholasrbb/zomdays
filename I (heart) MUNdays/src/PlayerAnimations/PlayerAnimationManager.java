package PlayerAnimations;

import java.awt.Image;
import java.awt.Toolkit;

import Model.Player;

public class PlayerAnimationManager {
	
	Player player;
	Image stillImage;
	Image walkImage;
	Image shootImage;
	public enum AnimationStates {Shooting, Walking, Still};
	AnimationStates currentState ;
	
	
	public PlayerAnimationManager(Player player){
		this.player = player;
		currentState = AnimationStates.Still;
		stillImage = Toolkit.getDefaultToolkit().getImage("newPlayer.jpg");
		walkImage = Toolkit.getDefaultToolkit().getImage("newPlayer1.jpg");
		shootImage = Toolkit.getDefaultToolkit().getImage("newPlayerShoot.jpg");
	}

	private void setImage(){
		switch(currentState){
		
		case Still:
			player.setSpriteImage(stillImage);
			break;
			
		case Walking:
			player.setSpriteImage(walkImage);
			break;
			
		case Shooting:
			if(player.playersWeapon().magAmmo() > 0)
				player.setSpriteImage(shootImage);			
			
			System.out.println(player.playersWeapon().magAmmo());
		}
		
		
		

		
			}
	
	public void setState(AnimationStates state){
		currentState = state;	
		System.out.println("State Changed To " + currentState);
		setImage();
	}
}
