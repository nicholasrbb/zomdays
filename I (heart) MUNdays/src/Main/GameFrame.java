package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.DoubleBuffer;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import events.GameMouseEvents;
import Interface.Buttons;
import Interface.MenuButtons;
import Interface.MouseEventListener;
import Model.ModelManager;
import Model.Player;
import Model.TileMap;
import Model.Weapon;
import View.Animation;
import View.AnimationFrame;
import View.Display;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;
	public ArrayList <TileMap> MapList;
	public JPanel MainMenu;
	public JPanel inGameMenu;
	public JPanel gameOverMenu;
	public JPanel winMenu;
	public Display display;
	public boolean makeGame = false;
	public boolean mainmenu = false;
	public boolean pause = false;
	public ModelManager manager;
	public boolean gameMade = false;
	private File Win;
	private Clip winSound;
	
		public GameFrame(){
			
			
			MainMenu = new JPanel();
			inGameMenu = new JPanel();
			gameOverMenu = new JPanel();
			winMenu = new JPanel();
			MapList = new ArrayList <TileMap>();
			
			//this.setFocusable(false);
			//pack() ;
			//this.setResizable(false);
			//this.setBounds(240, 90, 800, 600);
			//setVisible(true) ;
			//setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
			
			DisplayMode dm = new DisplayMode (800,600,32,60);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			this.setUndecorated(true);
			this.setResizable(false);
			gd.setFullScreenWindow(this);
			gd.setDisplayMode(dm);
			
			
			
			
			
			Dimension size = new Dimension( 1200, 800) ;
			MainMenu.setPreferredSize(size) ;
			MainMenu.setOpaque(true) ;
			MainMenu.setBackground(Color.black) ;
			MenuButtons menuButtons = new MenuButtons(this);
			MainMenu.add(menuButtons.NewGame);
			MainMenu.add(menuButtons.Exit);
			
			
			
			MenuButtons ingamemenuButtons = new MenuButtons(this);
			inGameMenu.setPreferredSize(size) ;
			inGameMenu.setOpaque(true) ;
			inGameMenu.setBackground(Color.black) ;
			//inGameMenu.add(ingamemenuButtons.MainMenu);
			inGameMenu.add(ingamemenuButtons.Resume);
			inGameMenu.add(ingamemenuButtons.Exit);
			
			MenuButtons winGameMenuButtons = new MenuButtons(this);
			winMenu.setPreferredSize(size) ;
			winMenu.setOpaque(true) ;
			winMenu.setBackground(Color.blue) ;
			//winMenu.add(winGameMenuButtons.NewGame);
			winMenu.add(winGameMenuButtons.Exit);
			
			Win = new File("dark2.wav");

			
			try {
				AudioInputStream winSound1 = AudioSystem.getAudioInputStream(Win);
				winSound = AudioSystem.getClip();
				winSound.open(winSound1);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
					e.printStackTrace();}
			
			
			
			
			
			
			
			showMainMenu();
			
			
			
		}
		
		
		// Create a Menu.
		public void showMainMenu(){
			inGameMenu.setVisible(false);
			winMenu.setVisible(false);
			this.setContentPane(MainMenu);
			MainMenu.grabFocus();
			MainMenu.setVisible(true);
		}
		
		public void showInGameMenu(){
			pause = true;
			display.setVisible(false);
			winMenu.setVisible(false);
			MainMenu.setVisible(false);
			this.setContentPane(inGameMenu);
			inGameMenu.setVisible(true);
			inGameMenu.grabFocus();
		}
		
		public void showWinGameMenu(){
			pause = true;
			display.setVisible(false);
			MainMenu.setVisible(false);
			inGameMenu.setVisible(false);
			this.setContentPane(winMenu);
			winMenu.setVisible(true);
			winMenu.grabFocus();
			
			winSound.start();
			
		}
		
		public void resumeGame(){
			pause = false;
			MainMenu.setVisible(false);
			winMenu.setVisible(false);
			display.setVisible(true);
			this.setContentPane(display);
			display.grabFocus();
		}
		
		
		//Creating a game.
		public void CreateGame(){
			gameMade = true;
			//MainMenu.setFocusable(false);
			MainMenu.setVisible(false);
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
					map2 = new TileMap(1000,1000, "Engr3rdFloor.txt");
					MapList.add(map2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				
				
			// Setting in Player Image.
				Image playerImage = Toolkit.getDefaultToolkit().createImage("player_nofire.png");
				Image npcImage = Toolkit.getDefaultToolkit().createImage("zombie.png");
				
				//Testing new animations
				Image walk0 = Toolkit.getDefaultToolkit().createImage("player_nofire.png");
				Image walk1 = Toolkit.getDefaultToolkit().createImage("newplayerwalk1.png");
				Image walk2 = Toolkit.getDefaultToolkit().createImage("newplayerwalk2.png");
				//Image walk3 = Toolkit.getDefaultToolkit().createImage("PlayerWalk1.jpg");
				//Image walk4 = Toolkit.getDefaultToolkit().createImage("PlayerStill.jpg");
				//Image walk5 = Toolkit.getDefaultToolkit().createImage("PlayerWalk3.jpg");
				//Image walk6 = Toolkit.getDefaultToolkit().createImage("PlayerWalk4.jpg");
				//Image walk7 = Toolkit.getDefaultToolkit().createImage("PlayerWalk3.jpg");
				
				
				AnimationFrame playaFrame1 = new AnimationFrame(walk0, 125000000L);
				AnimationFrame playaFrame2 = new AnimationFrame(walk1, 125000000L);
				AnimationFrame playaFrame3 = new AnimationFrame(walk2, 125000000L);
				//AnimationFrame playaFrame4 = new AnimationFrame(walk3, 125000000L);
				//AnimationFrame playaFrame5 = new AnimationFrame(walk4, 125000000L);
				//AnimationFrame playaFrame6 = new AnimationFrame(walk5, 125000000L);
				//AnimationFrame playaFrame7 = new AnimationFrame(walk6, 125000000L);
				//AnimationFrame playaFrame8 = new AnimationFrame(walk7, 125000000L);
				
				ArrayList <AnimationFrame> frames1 = new ArrayList <AnimationFrame>();
				frames1.add(playaFrame1);
				
				ArrayList <AnimationFrame> frames2 = new ArrayList <AnimationFrame>();
				frames2.add(playaFrame1);
				//frames2.add(playaFrame2);
				//frames2.add(playaFrame3);
				//frames2.add(playaFrame4);
				//frames2.add(playaFrame5);
				//frames2.add(playaFrame6);
				//frames2.add(playaFrame7);
				//frames2.add(playaFrame8);
				
				Animation playerAnimation1 = new Animation(frames1);
				Animation playerAnimation2 = new Animation(frames2);
				
				
				
				
				
			//Initialise the Model Manager.
				manager = new ModelManager(MapList);
				
			//Create Player and Zombie.
				Image gun = Toolkit.getDefaultToolkit().createImage("player_nofire.png");
				Image gunFire = Toolkit.getDefaultToolkit().createImage("player_fire.png");
				final Player playa = new Player(playerImage, 50, 10, 10, 1400, 2900, 0.3, 0.3, manager);
				final Weapon Gun = new Weapon(npcImage,600,10,1000,15, "Hand Gun");
				Weapon Knife = new Weapon(npcImage,50,25, -1,-1, "Knife");
				playa.addWeapon(Gun);
				playa.addWeapon(Knife);	
				
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
				
				playa.animations.add(playerAnimation1);
				playa.animations.add(playerAnimation2);
				
				

			//Add player to map SpriteList.
				map1.addPlayer(playa);
				//map1.setPlayerOnePosition();
				
				
			//Initialise the panel.
				//final Display display = new Display(manager, playa, this.getWidth(), this.getHeight()) ;
				display = new Display(manager, playa, this.getWidth(), this.getHeight()) ;
				display.setBackground(Color.BLACK);
				

			
				
				
			//add mouseListener to display
				GameMouseEvents mouse = new GameMouseEvents(display,playa, map1);
				MouseEventListener mouseListener = new MouseEventListener(mouse);
			//---------------------------------------------------------------
				
				this.addWindowFocusListener(new WindowAdapter() {
					public void windowGainedFocus(WindowEvent e) {
						display.requestFocusInWindow();
					}
				});
				display.setFocusable(true);
				System.out.println(display.isFocusOwner());
				
				
				//this.requestFocusInWindow(true);
				display.addMouseListener(mouseListener);
				display.addMouseMotionListener(mouseListener);
				
				
				System.out.println("showing display");
				
				this.setContentPane(display);
				display.grabFocus();
				Buttons Button = new Buttons(playa, this);
				display.addKeyListener( Button);
				//DoubleBuffer buffer = new DoubleBuffer();
				
				
				
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
				        		
				        		if (manager.killed >= 100){
				        			showWinGameMenu();
					        	}
				        		display.repaint();
				        		//manager.updateSprites(loopTime);
				        		manager.updateAnimations();
				        		manager.manageSprites();
					        	//manager.switchMap();
					        	
					        	//display.paintComponent(display.getGraphics());
				        	}
				        	
				        	
				        	
				        }
				    }
				}.start();
	
				//pack() ;
				//setVisible(true) ;
				//setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
			}
			
		
		
		public void CreateNewGame(){
			
		}
		
		
		
			

}
