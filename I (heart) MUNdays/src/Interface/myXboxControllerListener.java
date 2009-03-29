package Interface;

import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerListener;
import Main.GameFrame;
import Model.Player;

public class myXboxControllerListener implements XboxControllerListener{

	private GameFrame game;
	private Player p;
	private XboxController xc;
	
	private double prevAngle = 0;
	
	private int RTcount = 0;
	private double RTmag = 0;
	
	private int LTcount = 0;
	private double LTmag = 0;
	
	public myXboxControllerListener(Player p, GameFrame game, XboxController xc){
		this.xc = xc;
		this.game = game;
		this.p = p;
	}
	@Override
	public void back(boolean arg0) {

		if (arg0 == true){
			if (game.pause)
				System.exit(0);
			System.out.println("back");
		}
	}

	@Override
	public void buttonA(boolean arg0) {
		if (arg0 == true){
			System.out.println("A");}
		
		
	}

	@Override
	public void buttonB(boolean arg0) {
		if (arg0 == true){
			p.reloadWeapon();}		
	}

	@Override
	public void buttonX(boolean arg0) {
		if (arg0 == true){
			System.out.println("X");}		
	}

	@Override
	public void buttonY(boolean arg0) {
		if (arg0 == true){
			p.changeWeapon();}		
	}

	@Override
	public void dpad(int arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isConnected(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftShoulder(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftThumb(boolean arg0) {
		if (arg0){
			p.setXSpeed(0.4);
			p.setYSpeed(0.4);
		}
		else{
			p.setXSpeed(0.2);
			p.setYSpeed(0.2);
		}
	}

	@Override
	public void leftThumbDirection(double arg0) {
		if (LTmag >= 0.3 ){
			// Move UP
			if(arg0 < 22.5 && arg0 >= 0 || arg0 > 337.5 && arg0 <= 360){
				p.up = true;
				p.down = false;
				p.left = false;
				p.right = false;}	
			
			//Move UP-RIGHT	
			if (arg0 >= 22.5 && arg0 <= 67.5){
				p.up = true;
				p.down = false;
				p.left = false;
				p.right = true;}
			
			//Move RIGHT
			if(arg0 < 112.5 && arg0 > 67.5){
				p.right = true;
				p.down = false;
				p.left = false;
				p.up = false;}
			
			//Move DOWN-RIGHT
			if(arg0 >= 112.5 && arg0 <=157.5){
				p.right = true;
				p.down = true;
				p.left = false;
				p.up = false;}
			
			//Move DOWN
			if(arg0 > 157.5 && arg0 < 202.5){
				p.right = false;
				p.down = true;
				p.left = false;
				p.up = false;}
			
			//Move DOWN-LEFT
			if(arg0 >= 202.5 && arg0 <= 247.5){
				p.right = false;
				p.down = true;
				p.left = true;
				p.up = false;}
			
			//Move LEFT
			if(arg0 > 247.5 && arg0 < 292.5){
				p.right = false;
				p.down = false;
				p.left = true;
				p.up = false;}
			
			//Move UP-LEFT
			if(arg0 >= 292.5 && arg0 <= 337.5){
				p.right = false;
				p.down = false;
				p.left = true;
				p.up = true;}
						
			}
		
		else{
			p.right = false;
			p.down = false;
			p.left = false;
			p.up = false;}
	
		
		
						
		
	}

	@Override
	public void leftThumbMagnitude(double arg0) {
		LTmag = arg0;		
	}

	@Override
	public void leftTrigger(double arg0) {
				
	}

	@Override
	public void rightShoulder(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightThumb(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightThumbDirection(double arg0) {
		if (RTmag > .3){
			if (Math.abs(prevAngle - arg0) > 1)
				p.TSangle = (int) arg0;	
			prevAngle = arg0;
		}
	}

	@Override
	public void rightThumbMagnitude(double arg0) {
		RTmag = arg0;
	}

	@Override
	public void rightTrigger(double arg0) {
		
		if (arg0 < 0.5 && RTcount == 1)
			RTcount = 0;
		
		
		if(arg0 >= 0.5 && RTcount == 0){
			p.attack = true;
			RTcount = 1;
		}		
	}

	@Override
	public void start(boolean arg0) {
		if (arg0 == true){
			if (game.MainMenu.isVisible())
				game.CreateGame();
			else 
				game.showInGameMenu();
		}
    
	}
	
	

}
