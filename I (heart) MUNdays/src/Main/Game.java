package Main;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Model.ModelManager;
import Model.Player;
import Model.TileMap;
import Model.Weapon;
import View.Animation;
import View.AnimationFrame;

/**
 * This sets up the game TileMaps, and load the images for for the player 
 * animations and starts the main game loop
 * 
 */
@SuppressWarnings("serial")
public class Game extends UnicastRemoteObject implements GameInterface, Serializable{
	public ArrayList <TileMap> MapList;
	public ModelManager manager;
	public boolean pause = false;
	public Player player1;
	public Player player2;
	
	public GameFrame game;
	
	
	
	
	/**
	 * This sets up the game TileMaps, and load the images for for the player 
	 * animations and starts the main game loop
	 * 
	 * @param none
	 */
	public Game(GameFrame game) throws RemoteException{
		this.game = game;
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
				
				/*
				TileMap map2 = null;
				try {
					map2 = new TileMap(1000,1000, "Engr4thFloor.txt");
					MapList.add(map2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				*/
				
				
				
				//Setting in Player Image.
				Image playerImage = Toolkit.getDefaultToolkit().createImage("player_nofire.png");
				Image npcImage = Toolkit.getDefaultToolkit().createImage("zombie.png");
				
				//Player animations
				Image walk0 = Toolkit.getDefaultToolkit().createImage("Player/player_shortlegs_right.png");
				Image walk1 = Toolkit.getDefaultToolkit().createImage("Player/player_longlegs_right.png");
				Image walk2 = Toolkit.getDefaultToolkit().createImage("Player/player_shortlegs_left.png");
				Image walk3 = Toolkit.getDefaultToolkit().createImage("Player/player_longlegs_left.png");
		
				
				
				AnimationFrame playaFrame2 = new AnimationFrame(walk0, 125000000L);
				AnimationFrame playaFrame3 = new AnimationFrame(walk1, 125000000L);
				AnimationFrame playaFrame4 = new AnimationFrame(walk0, 125000000L);
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
				
				Animation player2Animation1 = new Animation(frames1);
				Animation player2Animation2 = new Animation(frames2);
				
				
				
				
				
			//Initialise the Model Manager.
				manager = new ModelManager(MapList, game);
				
			//Create Player and Zombie.
				Image gun = Toolkit.getDefaultToolkit().getImage("Player/player_1pistol_noflash.png");
				Image gunFire = Toolkit.getDefaultToolkit().getImage("Player/player_1pistol_flash.png");
				
				
				Image gun2x = Toolkit.getDefaultToolkit().getImage("Player/player_2pistol_noflash.png");
				Image gun2xFire = Toolkit.getDefaultToolkit().getImage("Player/player_2pistol_flash.png");

				Image knifeIn = Toolkit.getDefaultToolkit().getImage("Player/player_knife_in.png");
				Image knifeOut = Toolkit.getDefaultToolkit().getImage("Player/player_knife_Out.png");
				
				Image shotgun = Toolkit.getDefaultToolkit().getImage("Player/player_shotgun_noflash.png");
				Image shotgunFire = Toolkit.getDefaultToolkit().getImage("Player/player_shotgun_flash.png");
				
				Image uzi = Toolkit.getDefaultToolkit().getImage("Player/player_uzi_noflash.png");
				Image uziFire = Toolkit.getDefaultToolkit().getImage("Player/player_uzi_flash.png");
				
				player1 = new Player(playerImage, 50, 10, 10, 1400, 2900, 0.3, 0.3, manager.MapList.get(0),false);
				player2 = new Player(playerImage, 50, 10, 10, 1300, 2900, 0.3, 0.3, manager.MapList.get(0),true);
				
				Weapon Gun = new Weapon(npcImage,15,600,10,30,15,0.2,false, "Hand Gun");
				Weapon Knife = new Weapon(npcImage,0,50,25, -1,-1,0.2,false, "Knife");
				Weapon Gunx2 = new Weapon(npcImage,30,600,20,60,20,.1,false, "2x Hand Gun");
				Weapon Shotgun = new Weapon(npcImage,8,600,4,0,0,.8,false, "Shotgun");
				Weapon Uzi = new Weapon(npcImage,60,600,8,60,30,.08,true, "Uzi");
				
				Weapon player2Gun = new Weapon(npcImage,15,600,10,30,15,0.2,false, "Hand Gun");
				Weapon player2Knife = new Weapon(npcImage,0,50,25, -1,-1,0.2,false, "Knife");
				Weapon player2Gunx2 = new Weapon(npcImage,30,600,20,60,20,.1,false, "2x Hand Gun");
				Weapon player2Shotgun = new Weapon(npcImage,8,600,4,0,0,.8,false, "Shotgun");
				Weapon player2Uzi = new Weapon(npcImage,60,600,8,0,0,.08,true, "Uzi");
				
				player1.addWeapon(Gun);
				player1.addWeapon(Knife);
				player1.addWeapon(Gunx2);
				player1.addWeapon(Shotgun);
				player1.addWeapon(Uzi);
				player2.addWeapon(player2Gun);
				player2.addWeapon(player2Knife);
				player2.addWeapon(player2Gunx2);
				player2.addWeapon(player2Shotgun);
				player2.addWeapon(player2Uzi);
				
				
				/*
				 * Initialising Knife Animation
				 */
				AnimationFrame knifeFrame1 = new AnimationFrame(knifeIn, 12500000L);
				AnimationFrame knifeFrame2 = new AnimationFrame(knifeOut, 325000000L);
				
				ArrayList <AnimationFrame> knifeFrames = new ArrayList <AnimationFrame>();
				knifeFrames.add(knifeFrame2);
				
				ArrayList <AnimationFrame> knifeFrames1 = new ArrayList <AnimationFrame>();
				knifeFrames1.add(knifeFrame1);
				
				Animation knifeAnimation = new Animation(knifeFrames);
				Animation knifeStill = new Animation(knifeFrames1);
				
				
				// Player 2 knife
				
				AnimationFrame knifeFrame12 = new AnimationFrame(knifeIn, 12500000L);
				AnimationFrame knifeFrame22 = new AnimationFrame(knifeOut, 325000000L);
				
				ArrayList <AnimationFrame> knifeFrames2 = new ArrayList <AnimationFrame>();
				knifeFrames.add(knifeFrame2);
				
				ArrayList <AnimationFrame> knifeFrames12 = new ArrayList <AnimationFrame>();
				knifeFrames1.add(knifeFrame1);
				
				Animation knifeAnimation2 = new Animation(knifeFrames);
				Animation knifeStill2 = new Animation(knifeFrames1);
				
				
				
				/*
				 * Initialising Pistol Animation
				 */
				AnimationFrame gunFrame1 = new AnimationFrame(gun, 12500000L);
				AnimationFrame gunFrame2 = new AnimationFrame(gunFire, 32500000L);
								
				ArrayList <AnimationFrame> gunFrames = new ArrayList <AnimationFrame>();
				gunFrames.add(gunFrame2);
				
				ArrayList <AnimationFrame> gunFrames1 = new ArrayList <AnimationFrame>();
				gunFrames1.add(gunFrame1);
				
				
				Animation gunAnimation = new Animation(gunFrames);
				Animation gunStill = new Animation(gunFrames1);
				
				/*
				 * Initialising 2x Pistol Animation
				 */
				AnimationFrame gun2xFrame1 = new AnimationFrame(gun2x,12500000L);
				AnimationFrame gun2xFrame2 = new AnimationFrame(gun2xFire, 32500000L);
				
				ArrayList <AnimationFrame> gun2xFrames = new ArrayList <AnimationFrame>();
				gun2xFrames.add(gun2xFrame2);
				
				ArrayList <AnimationFrame> gun2xFrames1 = new ArrayList <AnimationFrame>();
				gun2xFrames1.add(gun2xFrame1);
				
				Animation gun2xAnimation = new Animation(gun2xFrames);
				Animation gun2xStill = new Animation(gun2xFrames1);
				
				/*
				 * Initialising ShotGun Animation
				 */
				AnimationFrame shotgunFrame1 = new AnimationFrame(shotgun,12500000L);
				AnimationFrame shotgunFrame2 = new AnimationFrame(shotgunFire, 32500000L);
				
				ArrayList <AnimationFrame> shotgunFrames = new ArrayList <AnimationFrame>();
				shotgunFrames.add(shotgunFrame2);
				
				ArrayList <AnimationFrame> shotgunFrames1 = new ArrayList <AnimationFrame>();
				shotgunFrames1.add(shotgunFrame1);
				
				Animation shotgunAnimation = new Animation(shotgunFrames);
				Animation shotgunStill = new Animation(shotgunFrames1);
				
				/*
				 * Initialising Uzi Animation
				 */
				AnimationFrame uziFrame1 = new AnimationFrame(uzi,12500000L);
				AnimationFrame uziFrame2 = new AnimationFrame(uziFire, 12500000L);
				
				ArrayList <AnimationFrame> uziFrames = new ArrayList <AnimationFrame>();
				uziFrames.add(uziFrame2);
				uziFrames.add(uziFrame1);
				
				ArrayList <AnimationFrame> uziFrames1 = new ArrayList <AnimationFrame>();
				uziFrames1.add(uziFrame1);
				
				Animation uziAnimation = new Animation(uziFrames);
				Animation uziStill = new Animation(uziFrames1);
				
				/*
				 * adding animations to game
				 */
				
				Gun.animations.add(gunStill);
				Gun.animations.add(gunAnimation);
				
				player2Gun.animations.add(gunStill);
				player2Gun.animations.add(gunAnimation);
				
				Gunx2.animations.add(gun2xStill);
				Gunx2.animations.add(gun2xAnimation);
				
				player2Gunx2.animations.add(gun2xStill);
				player2Gunx2.animations.add(gun2xAnimation);
				
				Shotgun.animations.add(shotgunStill);
				Shotgun.animations.add(shotgunAnimation);
				
				player2Shotgun.animations.add(shotgunStill);
				player2Shotgun.animations.add(shotgunAnimation);
				
				Uzi.animations.add(uziStill);
				Uzi.animations.add(uziAnimation);
				
				player2Uzi.animations.add(uziStill);
				player2Uzi.animations.add(uziAnimation);
				
				Knife.animations.add(knifeStill);
				Knife.animations.add(knifeAnimation);
				
				player2Knife.animations.add(knifeStill);
				player2Knife.animations.add(knifeAnimation);
				
				
							
				player1.animations.add(playerAnimation1);
				player1.animations.add(playerAnimation2);
				
				player2.animations.add(player2Animation1);
				player2.animations.add(player2Animation2);
				

			//Add player to map SpriteList.
				map1.addPlayer(player1);
				//map2.addPlayer(player2);
			    //map1.setPlayerOnePosition();
				
				
			
	}
			
			
		
		
		
		
		
	public Player getPlayer(){
		return player2;
	}
		
		
	}
	
	
	

