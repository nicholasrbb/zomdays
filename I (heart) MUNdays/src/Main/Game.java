package Main;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import Model.ModelManager;
import Model.Player;
import Model.TileMap;
import Model.Weapon;
import View.Animation;
import View.AnimationFrame;

public class Game {
	public ArrayList <TileMap> MapList;
	public ModelManager manager;
	public boolean pause = false;
	Player player1;
	Player player2;
	
	public Game(){
		MapList = new ArrayList <TileMap>();
		System.out.println("game being created");

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
				Image walk0 = Toolkit.getDefaultToolkit().createImage("player_nofire.png");
		
				
				
				AnimationFrame playaFrame1 = new AnimationFrame(walk0, 125000000L);
				
				
				ArrayList <AnimationFrame> frames1 = new ArrayList <AnimationFrame>();
				frames1.add(playaFrame1);
				
				ArrayList <AnimationFrame> frames2 = new ArrayList <AnimationFrame>();
				frames2.add(playaFrame1);
				
				
				Animation playerAnimation1 = new Animation(frames1);
				Animation playerAnimation2 = new Animation(frames2);
				
				
				
				
				
			//Initialise the Model Manager.
				manager = new ModelManager(MapList);
				
			//Create Player and Zombie.
				Image gun = Toolkit.getDefaultToolkit().createImage("player_nofire.png");
				Image gunFire = Toolkit.getDefaultToolkit().createImage("player_fire.png");
				player1 = new Player(playerImage, 50, 10, 10, 1400, 2900, 0.3, 0.3, manager);
				player2 = new Player(playerImage, 50, 10, 10, 1400, 2900, 0.3, 0.3, manager);
				final Weapon Gun = new Weapon(npcImage,600,10,1000,15, "Hand Gun");
				Weapon Knife = new Weapon(npcImage,50,25, -1,-1, "Knife");
				player1.addWeapon(Gun);
				player1.addWeapon(Knife);	
				player2.addWeapon(Gun);
				player2.addWeapon(Knife);
				
				
				AnimationFrame gunFrame1 = new AnimationFrame(gun, 125000000L);
				AnimationFrame gunFrame2 = new AnimationFrame(gunFire, 325000000L);
				
				ArrayList <AnimationFrame> gunFrames = new ArrayList <AnimationFrame>();
				gunFrames.add(gunFrame2);
				
				ArrayList <AnimationFrame> gunFrames1 = new ArrayList <AnimationFrame>();
				gunFrames1.add(gunFrame1);
				
				Animation gunAnimation = new Animation(gunFrames);
				Animation gunStill = new Animation(gunFrames1);
				Gun.animations.add(gunStill);
				Gun.animations.add(gunAnimation);
				Knife.animations.add(gunStill);
				Knife.animations.add(gunAnimation);
				
				player1.animations.add(playerAnimation1);
				player1.animations.add(playerAnimation2);
				
				player2.animations.add(playerAnimation1);
				player2.animations.add(playerAnimation2);
				

			//Add player to map SpriteList.
				map1.addPlayer(player1);
				map1.addPlayer(player2);
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
	
	
	

