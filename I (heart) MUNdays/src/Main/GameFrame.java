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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;






import Interface.Buttons;
import Interface.MenuButtons;
import Interface.MouseEventListener;
import Model.ModelManager;
import Model.Player;
import Model.RemotePlayer;
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
	public JPanel HostMenu;
	public JPanel JoinMenu;
	public Display display;
	public boolean makeGame = false;
	public boolean mainmenu = false;
	public boolean pause = false;
	public boolean gameMade = false;
	private File Win;
	private Clip winSound;
	public Game game;
	GameFrame secondFrame;
	boolean multi = false;
	
	/**
	 * 
	 */
		public GameFrame(){
			MainMenu = new JPanel();
			HostMenu = new JPanel();
			JoinMenu = new JPanel();
			inGameMenu = new JPanel();
			gameOverMenu = new JPanel();
			winMenu = new JPanel();
			MapList = new ArrayList <TileMap>();
			
			this.setBounds(25, 200, 600, 400);
			this.setPreferredSize(new Dimension(600,400));
			this.setResizable(false);
			pack() ;
			setVisible(true) ;
			setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
			
			
			/*
			DisplayMode dm = new DisplayMode (1280,800,32,60);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			this.setUndecorated(true);
			this.setResizable(false);
			gd.setFullScreenWindow(this);
			gd.setDisplayMode(dm);
			*/
			
			//Setting up Main Menu
			Dimension size = new Dimension( 800, 600) ;
			MainMenu.setPreferredSize(size) ;
			MainMenu.setOpaque(true) ;
			MainMenu.setBackground(Color.black) ;
			MenuButtons menuButtons = new MenuButtons(this);
			MainMenu.add(menuButtons.NewGame);
			MainMenu.add(menuButtons.NewMultiplayerGame);
			MainMenu.add(menuButtons.HostMultiplayerGame);
			MainMenu.add(menuButtons.JoinMultiplayerGame);
			MainMenu.add(menuButtons.Exit);
			
			
			//Setting up Host Menu
			MenuButtons HostButtons = new MenuButtons(this);
			HostMenu.setPreferredSize(size) ;
			HostMenu.setOpaque(true) ;
			HostMenu.setBackground(Color.black) ;
			HostMenu.add(HostButtons.StartHost);
			HostMenu.add(HostButtons.Cancel);
			
			//Setting up Join Menu
			MenuButtons JoinButtons = new MenuButtons(this);
			JoinMenu.setPreferredSize(size) ;
			JoinMenu.setOpaque(true) ;
			JoinMenu.setBackground(Color.black) ;
			JoinMenu.add(HostButtons.StartJoin);
			JoinMenu.add(JoinButtons.Cancel);
			
			
			//Adding Buttons to in game menu
			MenuButtons ingamemenuButtons = new MenuButtons(this);
			inGameMenu.setPreferredSize(size) ;
			inGameMenu.setOpaque(true) ;
			inGameMenu.setBackground(Color.black) ;
			inGameMenu.add(ingamemenuButtons.Resume);
			inGameMenu.add(ingamemenuButtons.NewXboxGame);
			inGameMenu.add(ingamemenuButtons.Exit);
			
			//Adding Button to Win Menu
			MenuButtons winGameMenuButtons = new MenuButtons(this);
			winMenu.setPreferredSize(size) ;
			winMenu.setOpaque(true) ;
			winMenu.setBackground(Color.blue) ;
			winMenu.add(winGameMenuButtons.Exit);
			
			//Setting up Win Sound
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
		
		public void showHostMenu(){
			MainMenu.setVisible(false);
			inGameMenu.setVisible(false);
			winMenu.setVisible(false);
			this.setContentPane(HostMenu);
			HostMenu.grabFocus();
			HostMenu.setVisible(true);
		}
		
		public void showJoinMenu(){
			MainMenu.setVisible(false);
			inGameMenu.setVisible(false);
			winMenu.setVisible(false);
			this.setContentPane(JoinMenu);
			JoinMenu.grabFocus();
			JoinMenu.setVisible(true);
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
		
		
		public void NewMultiplayerGame(){
			multi = true;
			
			secondFrame = new GameFrame();
			secondFrame.setBounds(650, 200, 600, 400);
			secondFrame.setResizable(false);
			secondFrame.MainMenu.setVisible(false);
			secondFrame.display = new Display(game.player2, this.getWidth(), this.getHeight()) ;
			
			GameMouseEvents mouse = new GameMouseEvents(secondFrame.display,game.player2);
			MouseEventListener mouseListener = new MouseEventListener(mouse);
			secondFrame.display.addMouseListener(mouseListener);
			secondFrame.display.addMouseMotionListener(mouseListener);
			
			Buttons newButton = new Buttons(game.player2, secondFrame);
			secondFrame.display.addKeyListener( newButton);
			
			secondFrame.setContentPane(secondFrame.display);
			secondFrame.display.grabFocus();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public void HostGame() throws RemoteException{
		
		//Creates the Game	
			try {
				game = new Game();
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			
			
		//Gets the IP Address and asks for a player name.
			InetAddress ip = null;
			try {
				 ip = java.net.InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String ipString = ip.toString();
			int start = ipString.indexOf('/');
			String realIP = ipString.substring(start+1);
			
			String name = JOptionPane.showInputDialog("Your IP Address is " + realIP + ".       Enter you Player Name: ", "") ;
			
			game.player1.name = name;
			System.out.println("Player's name is: " + name);
			
			
		//Tries to run the bat file thats set the RMI stuff
			try {
				Runtime.getRuntime().exec("run-server1.bat");
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		//Registers with rimRegistry
			try {
				//Naming.bind("rmi://localhost/Game", game);
				
				System.err.println("Server ready");
			} catch (Exception e) {
				System.err.println("Server exception: " + e.toString());
				e.printStackTrace();
			}
			
		}
		
		public void JoinGame() throws MalformedURLException, RemoteException, NotBoundException{
			String name = JOptionPane.showInputDialog("Enter you Player Name: ", "") ;
			String server = JOptionPane.showInputDialog("Enter the server IP Address: ", "rmi://xxx.xxx.xxx.xxx/Game");
			
			RemotePlayer proxy = null ;
			proxy = (RemotePlayer) Naming.lookup( name ) ;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		public void newGame(){
			gameMade = true;
			MainMenu.setVisible(false);
			try {
				game = new Game();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			
		//Initialise the panel.
			display = new Display(game.player1, this.getWidth(), this.getHeight()) ;
			display.setBackground(Color.BLACK);
			

		
			
			
		//add mouseListener to display
			
			GameMouseEvents mouse = new GameMouseEvents(display,game.player1);
			MouseEventListener mouseListener = new MouseEventListener(mouse);
			display.addMouseListener(mouseListener);
			display.addMouseMotionListener(mouseListener);
			
			
			
			//---------------------------------------------------------------
			this.addWindowFocusListener(new WindowAdapter() {
				public void windowGainedFocus(WindowEvent e) {
					display.requestFocusInWindow();
				}
			});
			display.setFocusable(true);
			this.setContentPane(display);
			display.grabFocus();
			Buttons Button = new Buttons(game.player1, this);
			display.addKeyListener( Button);
			
			
			
			
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
			        				        	
			        	if (multi == false && pause == true){
			        	
			        	}else{
			        		game.manager.updateSprites(20);
			        		game.manager.switchMap();
			        		game.manager.updateAnimations();
			        		game.manager.manageSprites();
			        		
				        	display.repaint();
				        	if (multi){
				        		secondFrame.display.repaint();
				        		System.out.println("finished displaying second displlay");
				        	}
				        	System.out.println("done displaying");
			        	}
			        }
			    }
			}.start();
		}
		
}
