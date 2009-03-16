package Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import events.GameMouseEvents;

import Interface.Buttons;
import Interface.MouseEventListener;
import Model.ModelManager;
import Model.Player;
import Model.Sprite;
import Model.TileMap;
import Model.Weapon;
import Model.Zombie;
import View.Display;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;
	public static ArrayList <TileMap> MapList;
		
		
		public GameFrame(){
			
		//Opening a menu.
			
			
			MapList = new ArrayList <TileMap>();
			CreateGame();
		}
		
		//Creating a game.
			private void CreateGame(){
			
			//Create Map of Engineering Building Second Floor.
				TileMap map1 = null;
				try {
					map1 = new TileMap(80,80, "EngSecondFloor.txt");
					MapList.add(map1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				TileMap map2 = null;
				try {
					map2 = new TileMap(80,80, "EngFirstFloor.txt");
					MapList.add(map2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				
				
			// Setting in Player Image.
				Image playerImage = Toolkit.getDefaultToolkit().createImage("newPlayer.jpg");
				Image npcImage = Toolkit.getDefaultToolkit().createImage("zombie.jpg");
				
				
				
			
				
				
			
							
			//Initialise the Model Manager.
				final ModelManager manager = new ModelManager(map1);
				
				//Create Player and Zombie.
				final Player playa = new Player(playerImage, 50, 10, 10, DEFAULT_WIDTH/2-5, DEFAULT_HEIGHT/2-5, 0.2, 0.2, manager);
				final Weapon Gun = new Weapon(npcImage,500,10,1000,15);
				Weapon Knife = new Weapon(npcImage,50,25, -1,-1);
				playa.addWeapon(Gun);
				playa.addWeapon(Knife);	
				
				//Add player to map SpriteList.
				map1.addPlayer(playa);
				
				//Initialise the keyboard listener.
				Buttons Button = new Buttons(playa);
				
				
				
			//Initialise the panel.
				final Display display = new Display(manager, playa, 500, 500) ;
				
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
				        	manager.switchMap();
				        	display.repaint();
				        	
				        	
				        	//System.out.println("map: " + manager.map + " Health: " + playa.getHealth() + "     Zombies Killed: " + manager.killed + "    Mag: " + Gun.magAmmo() + "    Ammo: " + Gun.getAmmo() + "Current Weapon: " + playa.getCurrentWeapon());
				        }
				    }
				}.start();
	
				pack() ;
				setVisible(true) ;
				setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
			}
			
			
			public static TileMap getMap(int mapInt){
				return MapList.get(mapInt);
			}
	
			

}
