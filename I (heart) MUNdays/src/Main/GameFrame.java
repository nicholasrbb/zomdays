package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.aplu.xboxcontroller.XboxController;

import Interface.Buttons;
import Interface.MenuButtons;
import Interface.MouseEventListener;
import Interface.myXboxControllerListener;
import Model.ModelManager;
import Model.Player;
import Model.TileMap;
import Model.Weapon;
import View.Animation;
import View.AnimationFrame;
import View.Display;
import events.GameMouseEvents;
/**
 * This class extends JFrame and will be used to contain the 
 * Display class.
 * <p> Various panel are created for menus, focus of the mouse
 * and keyboard listeners are set to the appropriate panel and a given time
 * 
 * @param none
 * 
 *
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
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
	
	public boolean xboxGame = false;
	
	
	/**
	 * 
	 */
		public GameFrame(){
			
			
			MainMenu = new JPanel();
			inGameMenu = new JPanel();
			gameOverMenu = new JPanel();
			winMenu = new JPanel();
			MapList = new ArrayList <TileMap>();
			
			
			DisplayMode dm = new DisplayMode (1280,800,32,60);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			this.setUndecorated(true);
			this.setResizable(false);
			gd.setFullScreenWindow(this);
			gd.setDisplayMode(dm);
			
			
			
			
			
			Dimension size = new Dimension( 800, 600) ;
			MainMenu.setPreferredSize(size) ;
			MainMenu.setOpaque(true) ;
			MainMenu.setBackground(Color.black) ;
			MenuButtons menuButtons = new MenuButtons(this);
			MainMenu.add(menuButtons.NewGame);
			MainMenu.add(menuButtons.Exit);
			MainMenu.add(menuButtons.NewXboxGame);
			
			
			
			MenuButtons ingamemenuButtons = new MenuButtons(this);
			inGameMenu.setPreferredSize(size) ;
			inGameMenu.setOpaque(true) ;
			inGameMenu.setBackground(Color.black) ;
			inGameMenu.add(ingamemenuButtons.Resume);
			inGameMenu.add(ingamemenuButtons.Exit);
			
			MenuButtons winGameMenuButtons = new MenuButtons(this);
			winMenu.setPreferredSize(size) ;
			winMenu.setOpaque(true) ;
			winMenu.setBackground(Color.blue) ;
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
		
		/**
		 * Switch focus to the MainMenu JPanel, setting all other JPanels visibility 
		 * to false and its visibility to true. JButtons are added to the JPanel through
		 * the MenuButtons class in the events package
		 * 
		 * @param none
		 */
		
		public void showMainMenu(){
			inGameMenu.setVisible(false);
			winMenu.setVisible(false);
			this.setContentPane(MainMenu);
			MainMenu.grabFocus();
			MainMenu.setVisible(true);
		}
		
		/**
		 * Switch focus to the InGameMenu JPanel, setting all other JPanels visibility 
		 * to false and its visibility to true. JButtons are added to the JPanel through
		 * the MenuButtons class in the events package
		 * 
		 * @param none
		 */
		public void showInGameMenu(){
			pause = true;
			display.setVisible(false);
			winMenu.setVisible(false);
			MainMenu.setVisible(false);
			this.setContentPane(inGameMenu);
			inGameMenu.setVisible(true);
			inGameMenu.grabFocus();
		}
		
		/**
		 * Switch focus to the WinGameMenu JPanel, setting all other JPanels visibility 
		 * to false and its visibility to true. JButtons are added to the JPanel through
		 * the MenuButtons class in the events package
		 * <p> Play a .wav file when menu is set. 
		 * 
		 * @param none
		 */
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
		
		/**
		 * This allows the user to resume the game from the ingame menu.
		 * <p> This will set the display to visible and set the listener focus.
		 * 
		 * @param none
		 */
		
		public void resumeGame(){
			pause = false;
			MainMenu.setVisible(false);
			winMenu.setVisible(false);
			display.setVisible(true);
			this.setContentPane(display);
			display.grabFocus();
		}
		
		
		/**
		 * This sets up the game TileMaps, and load the images for for the player 
		 * animations and starts the main game loop
		 * 
		 * @param none
		 */
		public void CreateGame(){
			gameMade = true;
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
				display = new Display(manager, playa, this.getWidth(), this.getHeight(), xboxGame) ;
				display.setBackground(Color.BLACK);
				

			
				
				
			//add mouseListener to display
				
				
				//Xbox Controller Listener
				
				if(xboxGame){
					XboxController xc = new XboxController();
					myXboxControllerListener xboxListener = new myXboxControllerListener(playa,this,xc);
					xc.addXboxControllerListener(xboxListener);}
				
				else{
					GameMouseEvents mouse = new GameMouseEvents(display,playa);
					MouseEventListener mouseListener = new MouseEventListener(mouse);
					display.addMouseListener(mouseListener);
					display.addMouseMotionListener(mouseListener);
					
					
				}
				//---------------------------------------------------------------
				
				this.addWindowFocusListener(new WindowAdapter() {
					public void windowGainedFocus(WindowEvent e) {
						display.requestFocusInWindow();
					}
				});
				display.setFocusable(true);
				System.out.println(display.isFocusOwner());
				
				
				//this.requestFocusInWindow(true);
				
				
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
				        	if (loopTime <= 14){
				        		try {
					                Thread.sleep(14);}
						        	catch (InterruptedException ex) { } 
						    }
				        	
				        	
				        	
				        	if (pause != true){
				        		
				        		if (manager.killed >= 100){
				        			showWinGameMenu();
					        	}
				        		manager.switchMap();
				        		display.repaint();
				        		manager.updateAnimations();
				        		manager.manageSprites();
					        	
				        	}
				        	
				        	
				        	
				        }
				    }
				}.start();
	
			}
			
		
		
		
		
		
		
			

}
