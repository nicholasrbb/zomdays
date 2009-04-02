package Main;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Model.ModelManager;
import Model.Player;
import Model.TileMap;
import Model.Weapon;
import View.Animation;
import View.AnimationFrame;

public class Game extends UnicastRemoteObject{
	public ArrayList <TileMap> MapList;
	public ModelManager manager;
	public boolean pause = false;
	Player player1;
	Player player2;
	
	
	
	
	
	/**
	 * This sets up the game TileMaps, and load the images for for the player 
	 * animations and starts the main game loop
	 * 
	 * @param none
	 */
	public Game() throws RemoteException{
		MapList = new ArrayList <TileMap>();
		System.out.println("game being set up");

			//Create Map of Engineering Building Second Floor.
				TileMap map1 = null;
				try {
					map1 = new TileMap(400,500, "Engr3rdFloor.txt");
					MapList.add(map1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				TileMap map2 = null;
				try {
					map2 = new TileMap(1000,1000, "Engr4thFloor.txt");
					MapList.add(map2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				
				
				//Setting in Player Image.
				Image playerImage = Toolkit.getDefaultToolkit().createImage("player_nofire.png");
				Image npcImage = Toolkit.getDefaultToolkit().createImage("zombie.png");
				
				//Player animations
				Image walk0 = Toolkit.getDefaultToolkit().createImage("Player/player_shortlegs_right.png");
				Image walk1 = Toolkit.getDefaultToolkit().createImage("Player/player_longlegs_right.png");
				Image walk2 = Toolkit.getDefaultToolkit().createImage("Player/player_shortlegs_left.png");
				Image walk3 = Toolkit.getDefaultToolkit().createImage("Player/player_longlegs_left.png");
				//Image walk4 = Toolkit.getDefaultToolkit().createImage("blank.png");
		
				
				
				//AnimationFrame playaFrame1 = new AnimationFrame(walk4, 125000000L);
				AnimationFrame playaFrame2 = new AnimationFrame(walk0, 125000000L);
				AnimationFrame playaFrame3 = new AnimationFrame(walk1, 125000000L);
				AnimationFrame playaFrame4 = new AnimationFrame(walk0, 125000000L);
				//AnimationFrame playaFrame5 = new AnimationFrame(walk4, 125000000L);
				AnimationFrame playaFrame6 = new AnimationFrame(walk2, 125000000L);
				AnimationFrame playaFrame7 = new AnimationFrame(walk3, 125000000L);
				AnimationFrame playaFrame8 = new AnimationFrame(walk2, 125000000L);
				
				
				ArrayList <AnimationFrame> frames1 = new ArrayList <AnimationFrame>();
				frames1.add(playaFrame2);
				
				ArrayList <AnimationFrame> frames2 = new ArrayList <AnimationFrame>();
				
				//frames2.add(playaFrame1);
				frames2.add(playaFrame2);
				frames2.add(playaFrame3);
				frames2.add(playaFrame4);
				//frames2.add(playaFrame5);
				frames2.add(playaFrame6);
				frames2.add(playaFrame7);
				frames2.add(playaFrame8);
				
				Animation playerAnimation1 = new Animation(frames1);
				Animation playerAnimation2 = new Animation(frames2);
				
				
				
				
				
			//Initialise the Model Manager.
				manager = new ModelManager(MapList);
				
			//Create Player and Zombie.
				Image gun = Toolkit.getDefaultToolkit().createImage("Player/player_1pistol_noflash.png");
				Image gunFire = Toolkit.getDefaultToolkit().createImage("Player/player_1pistol_flash.png");
				
				player1 = new Player(playerImage, 50, 10, 10, 1400, 2900, 0.3, 0.3, manager.MapList.get(0));
				player2 = new Player(playerImage, 50, 10, 10, 1300, 2900, 0.3, 0.3, manager.MapList.get(1));
				
				Weapon Gun = new Weapon(npcImage,600,10,1000,15,0.2, "Hand Gun");
				Weapon Knife = new Weapon(npcImage,50,25, -1,-1,0.0, "Knife");
				
				Weapon player2Gun = new Weapon(npcImage,600,10,1000,15,0.5, "Hand Gun");
				Weapon player2Knife = new Weapon(npcImage,50,25, -1,-1,0.0, "Knife");
				
				player1.addWeapon(Gun);
				player1.addWeapon(Knife);	
				player2.addWeapon(player2Gun);
				player2.addWeapon(player2Knife);
				
				
				AnimationFrame gunFrame1 = new AnimationFrame(gun, 12500000L);
				AnimationFrame gunFrame2 = new AnimationFrame(gunFire, 32500000L);
				
				ArrayList <AnimationFrame> gunFrames = new ArrayList <AnimationFrame>();
				//gunFrames.add(gunFrame1);
				gunFrames.add(gunFrame2);
				//gunFrames.add(gunFrame1);
				
				ArrayList <AnimationFrame> gunFrames1 = new ArrayList <AnimationFrame>();
				gunFrames1.add(gunFrame1);
				
				Animation gunAnimation = new Animation(gunFrames);
				Animation gunStill = new Animation(gunFrames1);
				
				Gun.animations.add(gunStill);
				Gun.animations.add(gunAnimation);
				
				Knife.animations.add(gunStill);
				Knife.animations.add(gunAnimation);
				
				player2Gun.animations.add(gunStill);
				player2Gun.animations.add(gunAnimation);
				
				player2Knife.animations.add(gunStill);
				player2Knife.animations.add(gunAnimation);
				
				player1.animations.add(playerAnimation1);
				player1.animations.add(playerAnimation2);
				
				player2.animations.add(playerAnimation1);
				player2.animations.add(playerAnimation2);
				

			//Add player to map SpriteList.
				map1.addPlayer(player1);
				map2.addPlayer(player2);
				//map1.setPlayerOnePosition();
				
				
			
	}
				
		public void gameLoop(){	
				
			//Start Game Thread 1.
				new Thread() {
				    public void run() {
				    	
				    	long startTime = System.currentTimeMillis();
				    	long currentTime = startTime;
				    	while(true){
				        	long loopTime = System.currentTimeMillis() - currentTime;
				        	currentTime += loopTime;
				        	
				        	
				        	//if statement used because update has issues with loop taking less then 5ms
				        	if (loopTime <= 20){
				        		try {
					                Thread.sleep(20);}
						        	catch (InterruptedException ex) { } 
						    }
				        	
				        	
				        	
				        	if (pause != true){
				        		
				        		manager.switchMap();
				        		manager.updateAnimations();
				        		manager.manageSprites();
					        	
				        	}
				        	
				        	
				        	
				        }
				    }
				}.start();
	
			}
			
		
		
		
		
		
		
		
		
	}
	
	
	

