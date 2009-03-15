package Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import events.GameMouseEvents;

import Interface.Buttons;
import Interface.MouseEventListener;
import Model.ModelManager;
import Model.Player;
import Model.TileMap;
import Model.Zombie;
import View.Display;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;
		
		
		public GameFrame(){
			
		//Opening a menu.
			
			
			
			CreateGame();
		}
		
		//Creating a game.
			private void CreateGame(){
			
			//Create Map of Engineering Building Second Floor.
				TileMap map1 = null;
				try {
					map1 = new TileMap(80,80, "EngSecondFloor.txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			// Setting in Player Image.
				Image playerImage = Toolkit.getDefaultToolkit().createImage("newPlayer.jpg");
				Image npcImage = Toolkit.getDefaultToolkit().createImage("zombie.jpg");
				
			//Create Player and Zombie.
				final Player playa = new Player(playerImage, 50, 10, 10, DEFAULT_WIDTH/2-5, DEFAULT_HEIGHT/2-5, 0.2, 0.2, map1);
				Zombie zombay = new Zombie(npcImage, 50, 10, 10, 800, 500, 0.1, 0.1, map1);
				Zombie zombay1 = new Zombie(npcImage, 50, 10, 10, 400, 500, 0.1, 0.1, map1);
				Zombie zombay2 = new Zombie(npcImage, 50, 10, 10, 800, 800, 0.1, 0.1, map1);
				Zombie zombay3 = new Zombie(npcImage, 50, 10, 10, 200, 300, 0.1, 0.1, map1);
				Zombie zombay4 = new Zombie(npcImage, 50, 10, 10, 300, 400, 0.1, 0.1, map1);
				
			//Add player to map SpriteList.
				map1.addPlayer(playa);
				//map1.addSprite(zombay);
				//map1.addSprite(zombay1);
				//map1.addSprite(zombay2);
				//map1.addSprite(zombay3);
				//map1.addSprite(zombay4);
				
			//Initialise the keyboard listener.
				Buttons Button = new Buttons(playa);
							
			//Initialise the Model Manager.
				final ModelManager manager = new ModelManager(map1);
				
			//Initialise the panel.
				final Display display = new Display(map1, playa, 500, 500) ;
				
			//add mouseListener to display
				GameMouseEvents mouse = new GameMouseEvents(display,playa, map1);
				MouseEventListener mouseListener = new MouseEventListener(mouse);
			//---------------------------------------------------------------
				
				display.setLayout( new BorderLayout() ) ;
				display.setPreferredSize( new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT) ) ;
				this.add(display);
				
				//Add keyboard listener to JFrame.
				addKeyListener( Button);
				display.addMouseListener(mouseListener);
				display.addMouseMotionListener(mouseListener);
				
				
			//Start Game Thread 1.
				new Thread() {
				    public void run() {
				    	long startTime = System.currentTimeMillis();
				    	long currentTime = startTime;
				    	while(true){
				        	long loopTime = System.currentTimeMillis() - currentTime;
				        	currentTime += loopTime;
				        	
				        	
				        	//if statement used because update has issues with loop taking less then 5ms
				        	if (loopTime <= 10){
				        		try {
					                Thread.sleep(10);}
						        	catch (InterruptedException ex) { } 
						    }
				        	manager.manageSprites();
				        	manager.updateSprites(loopTime);
				        	display.repaint();
				        	System.out.println(" Health: " + playa.getHealth() + "          Zombies Killed: " + manager.killed);
				        }
				    }
				}.start();
	
				pack() ;
				setVisible(true) ;
				setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
			}
	
			

}
