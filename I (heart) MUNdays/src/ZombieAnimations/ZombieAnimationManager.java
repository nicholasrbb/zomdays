package ZombieAnimations;

import java.awt.Image;
import java.awt.Toolkit;

import Model.Sprite;


public class ZombieAnimationManager{
	
	
	Sprite sprite;
	Image MovingImage;
	Image DamageImage;
	Image AttackImage;
	public enum ZombieAnimationStates {Damage, Walking, Attacking};
	ZombieAnimationStates currentState , nextState;
	long startTime;
	long timeChanged;
	
	
	public ZombieAnimationManager(Sprite sprite){
		this.sprite = sprite;
		currentState = ZombieAnimationStates.Walking;
		nextState = ZombieAnimationStates.Walking;
		MovingImage = Toolkit.getDefaultToolkit().getImage("zombie.jpg");
		DamageImage = Toolkit.getDefaultToolkit().getImage("zombieDamage.jpg");
		AttackImage = Toolkit.getDefaultToolkit().getImage("zombieAttack.jpg");
		startTime = System.currentTimeMillis();
		timeChanged = System.currentTimeMillis();
	}

	public void setImage(){
		
		startTime = System.currentTimeMillis();
		
		System.out.println("State Changed To " + nextState);
		System.out.println(startTime + "  " + timeChanged);

		System.out.println("State Changed To " + nextState);


		if(startTime - timeChanged <= 100){
				return;}
		

		
		else{
			nextState = currentState;
		
		System.out.println("State Changed To " + nextState);

			switch(nextState){
		
			case Walking:
				sprite.setSpriteImage(MovingImage);
				break;
			
			case Attacking:
				sprite.setSpriteImage(AttackImage);
				timeChanged = System.currentTimeMillis();
				break;
			
			case Damage:
				sprite.setSpriteImage(DamageImage);
				timeChanged = System.currentTimeMillis();
				break;
				

			}
		}
	}
			
			
			
	
			
	
	

		
			

	public void setState(ZombieAnimationStates state) {
		currentState = state;
		setImage();
		
	}

	

}
