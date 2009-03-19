package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import events.GameMouseEvents;
import Interface.Buttons;
import Interface.MenuButtons;
import Interface.MouseEventListener;
import Model.ModelManager;
import Model.Player;
import Model.TileMap;
import Model.Weapon;
import View.Display;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;
	public static ArrayList <TileMap> MapList;
	public JPanel MainMenu;
	public JPanel inGameMenu;
	public Display display;
	public boolean makeGame = false;
	public boolean mainmenu = false;
	public boolean pause = false;
		
		public GameFrame(){
			//MainMenu = new Canvas(500,  500, 1);
			
			
			MainMenu = new JPanel();
			inGameMenu = new JPanel();
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
			
			
			
			
			
			Dimension size = new Dimension( 800, 600) ;
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
			inGameMenu.add(ingamemenuButtons.MainMenu);
			inGameMenu.add(ingamemenuButtons.Resume);
			inGameMenu.add(ingamemenuButtons.Exit);
			
			
			
			showMainMenu();
			
			
			
		}
		
		
		// Create a Menu.
		public void showMainMenu(){
			inGameMenu.setVisible(false);
			this.setContentPane(MainMenu);
			MainMenu.grabFocus();
			MainMenu.setVisible(true);
		}
		
		public void showInGameMenu(){
			pause = true;
			display.setVisible(false);
			MainMenu.setVisible(false);
			this.setContentPane(inGameMenu);
			inGameMenu.setVisible(true);
			inGameMenu.grabFocus();
		}
		
		public void resumeGame(){
			pause = false;
			MainMenu.setVisible(false);
			display.setVisible(true);
			this.setContentPane(display);
			display.grabFocus();
		}
		
		
		//Creating a game.
		public void CreateGame(){
			
			//MainMenu.setFocusable(false);
			MainMenu.setVisible(false);
			System.out.println("game being created");

			//Create Map of Engineering Building Second Floor.
				TileMap map1 = null;
				try {
					map1 = new TileMap(1000,1000, "Engr2ndFloor.txt");
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
				Image playerImage = Toolkit.getDefaultToolkit().createImage("newPlayer.jpg");
				Image npcImage = Toolkit.getDefaultToolkit().createImage("zombie.jpg");
				
			//Initialise the Model Manager.
				final ModelManager manager = new ModelManager(map1);
				
			//Create Player and Zombie.
				final Player playa = new Player(playerImage, 50, 10, 10, 500, 4000, 0.2, 0.2, manager);
				final Weapon Gun = new Weapon(npcImage,600,10,1000,15, "Hand Gun");
				Weapon Knife = new Weapon(npcImage,50,25, -1,-1, "Knife");
				playa.addWeapon(Gun);
				playa.addWeapon(Knife);	
				

			//Add player to map SpriteList.
				map1.addPlayer(playa);
				//map1.setPlayerOnePosition();
				
				
			//Initialise the panel.
				//final Display display = new Display(manager, playa, this.getWidth(), this.getHeight()) ;
				display = new Display(manager, playa, this.getWidth(), this.getHeight()) ;
			
				
				
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
				        	if (pause != true){
				        		manager.manageSprites();
					        	manager.updateSprites(loopTime);
					        	manager.switchMap();
					        	display.repaint();
				        	}
				        	
				        	
				        	
				        }
				    }
				}.start();
	
				//pack() ;
				//setVisible(true) ;
				//setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
			}
			
			
			public static TileMap getMap(int mapInt){
				return MapList.get(mapInt);
			}
	
			

}
