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
import java.rmi.Remote;
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
import javax.swing.ImageIcon;
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
import View.ChooseMultiplayerMenu;
import View.Display;
import View.InGameMenu;
import View.MultiplayerMenu;
import View.MyMainMenu;
import View.WinMenu;
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
	public MyMainMenu MainMenu;
	public MultiplayerMenu multiplayerMenu;
	public WinMenu NewWinMenu;
	public InGameMenu pauseMenu;
	public ChooseMultiplayerMenu chooseMultiplayerMenu;
	//public JPanel inGameMenu;
	//public JPanel otherMainMenu;
	//public JPanel gameOverMenu;
	//public JPanel winMenu;
	//public JPanel HostMenu;
	//public JPanel JoinMenu;
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
	boolean firstTime = true;
	/**
	 * 
	 */
		public GameFrame(boolean b){
			MainMenu = new MyMainMenu(this);
			multiplayerMenu = new MultiplayerMenu(this);
			pauseMenu = new InGameMenu(this);
			NewWinMenu = new WinMenu(this);
			chooseMultiplayerMenu = new ChooseMultiplayerMenu(this);
			//otherMainMenu = new JPanel();
			//HostMenu = new JPanel();
			//JoinMenu = new JPanel();
			//inGameMenu = new JPanel();
			//gameOverMenu = new JPanel();
			//winMenu = new JPanel();
			MapList = new ArrayList <TileMap>();
			
			multi = b;
			this.setBounds(25, 200, 600, 600);
			this.setPreferredSize(new Dimension(800,600));
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
			
			if ( multi == false){
				showMainMenu();
			}
			
			
		}
		
		/**
		 * Switch focus to the MainMenu JPanel, setting all other JPanels visibility 
		 * to false and its visibility to true. JButtons are added to the JPanel through
		 * the MenuButtons class in the events package
		 * 
		 * @param none
		 */
		
		public void showMainMenu(){
			this.setContentPane(MainMenu);
			MainMenu.grabFocus();
			MainMenu.setVisible(true);
			
			
			if(firstTime == true){
				long elapsedTime = 0;
				long startTime = System.currentTimeMillis();
				for(int i = 0; i < MainMenu.images.size();){
					elapsedTime = System.currentTimeMillis() - startTime;
					if (elapsedTime >= 150){
						MainMenu.interateImage();
						elapsedTime = 0;
						startTime = System.currentTimeMillis();
						MainMenu.repaint();
						System.out.println("changed image");
						i++;
					}
					this.paintComponents(this.getGraphics());
				}
				firstTime = false;
			}
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		public void showMultiplayerMenu(){
			MainMenu.setVisible(false);
			chooseMultiplayerMenu.setVisible(false);
			this.setContentPane(multiplayerMenu);
			multiplayerMenu.grabFocus();
			multiplayerMenu.setVisible(true);
		}
		
		public void showChooseMultiplayerMenu(){
			MainMenu.setVisible(false);
			this.setContentPane(chooseMultiplayerMenu);
			chooseMultiplayerMenu.grabFocus();
			chooseMultiplayerMenu.setVisible(true);
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
			MainMenu.setVisible(false);
			this.setContentPane(pauseMenu);
			pauseMenu.setVisible(true);
			pauseMenu.grabFocus();
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
			//display.setVisible(false);
			MainMenu.setVisible(false);
			this.setContentPane(NewWinMenu);
			NewWinMenu.setVisible(true);
			NewWinMenu.grabFocus();
			
			//winSound.start();
			
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
			display.setVisible(true);
			this.setContentPane(display);
			display.grabFocus();
		}
		
		
		public void NewMultiplayerGame(){
			multi = true;
			
			secondFrame = new GameFrame(true);
			secondFrame.multi = true;
			secondFrame.setBounds(650, 200, 800, 600);
			secondFrame.setResizable(false);
			secondFrame.MainMenu.setVisible(false);
			secondFrame.multiplayerMenu.setVisible(false);
			secondFrame.pauseMenu.setVisible(false);
			secondFrame.NewWinMenu.setVisible(false);
			secondFrame.chooseMultiplayerMenu.setVisible(false);
			secondFrame.display = new Display(game.player2, this.getWidth(), this.getHeight()) ;
			
			GameMouseEvents mouse2 = new GameMouseEvents(secondFrame.display,game.player2);
			MouseEventListener mouseListener2 = new MouseEventListener(mouse2);
			secondFrame.display.addMouseListener(mouseListener2);
			secondFrame.display.addMouseMotionListener(mouseListener2);
			
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
				Naming.bind("rmi://"+realIP+"/Game", game);
				
				System.err.println("Server ready");
			} catch (Exception e) {
				System.err.println("Server exception: " + e.toString());
				e.printStackTrace();
			}
			
			//newGame();
			
			
			
			
		}
		
		public void JoinGame() throws MalformedURLException, RemoteException, NotBoundException{
			String name = JOptionPane.showInputDialog("Enter you Player Name: ", "") ;
			String server = JOptionPane.showInputDialog("Enter the server IP Address: ", "rmi://xxx.xxx.xxx.xxx/Game");
			
			
			Game proxy = null ;
			proxy = (Game) Naming.lookup( server ) ;
			Player player = proxy.player2;
			
			
		//Making a game
			display = new Display(player, this.getWidth(), this.getHeight()) ;
			display.setBackground(Color.BLACK);
						
			GameMouseEvents mouse2 = new GameMouseEvents(display,player);
			MouseEventListener mouseListener2 = new MouseEventListener(mouse2);
			display.addMouseListener(mouseListener2);
			display.addMouseMotionListener(mouseListener2);
			
			Buttons newButton = new Buttons(player, this);
			secondFrame.display.addKeyListener( newButton);
			
			secondFrame.setContentPane(display);
			display.grabFocus();
			
			
			
    	}
		
		
		
		
		
		
		
		
		
		
		
		
		public void newGame(){
			gameMade = true;
			MainMenu.setVisible(false);
			multiplayerMenu.setVisible(false);
			pauseMenu.setVisible(false);
			NewWinMenu.setVisible(false);
			chooseMultiplayerMenu.setVisible(false);
			
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
				        	}
			        	}
			        }
			    }
			}.start();
		}
		
}
