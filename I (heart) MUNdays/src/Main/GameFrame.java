package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.aplu.xboxcontroller.XboxController;





import Interface.Buttons;
import Interface.MouseEventListener;
import Interface.myXboxControllerListener;
import Model.TileMap;
import View.ChooseMultiplayerMenu;
import View.Display;
import View.InGameMenu;
import View.LoseMenu;
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
public class GameFrame extends JFrame implements Serializable{
	
	public ArrayList <TileMap> MapList;
	public MyMainMenu MainMenu;
	public MultiplayerMenu multiplayerMenu;
	public WinMenu NewWinMenu;
	public LoseMenu NewLoseMenu;
	public InGameMenu pauseMenu;
	public ChooseMultiplayerMenu chooseMultiplayerMenu;
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
	XboxController xc = new XboxController();
	/**
	 * 
	 */
		public GameFrame(boolean b){
			MainMenu = new MyMainMenu(this);
			multiplayerMenu = new MultiplayerMenu(this);
			pauseMenu = new InGameMenu(this);
			NewWinMenu = new WinMenu(this);
			NewLoseMenu = new LoseMenu(this);
			chooseMultiplayerMenu = new ChooseMultiplayerMenu(this);
			MapList = new ArrayList <TileMap>();
			
			multi = b;
			
			this.setBounds(25, 200, 600, 600);
			this.setPreferredSize(new Dimension(800,600));
			this.setResizable(false);
			pack() ;
			setVisible(true) ;
			setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
			
			/*
			DisplayMode dm = new DisplayMode (800,600,32,60);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			this.setUndecorated(true);
			this.setResizable(false);
			gd.setFullScreenWindow(this);
			gd.setDisplayMode(dm);
			*/
			
			
		
			
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
		public void showLoseGameMenu(){
			pause = true;
			//display.setVisible(false);
			NewLoseMenu.setVisible(false);
			this.setContentPane(NewLoseMenu);
			NewLoseMenu.setVisible(true);
			NewLoseMenu.grabFocus();
			
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
			game.MapList.get(0).addPlayer(game.player2);
			
			
			myXboxControllerListener xboxListener = new myXboxControllerListener(game.player2,secondFrame,xc);
			xc.addXboxControllerListener(xboxListener);
			
			//GameMouseEvents mouse2 = new GameMouseEvents(secondFrame.display,game.player2);
			//MouseEventListener mouseListener2 = new MouseEventListener(mouse2);
			//secondFrame.display.addMouseListener(mouseListener2);
			//secondFrame.display.addMouseMotionListener(mouseListener2);
			
			Buttons newButton = new Buttons(game.player2, secondFrame);
			secondFrame.display.addKeyListener( newButton);
			
			secondFrame.setContentPane(secondFrame.display);
			secondFrame.display.grabFocus();
		}
		
		
		public void restartGame(){
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		public void HostGame() throws RemoteException{
		game = new Game(this);
		//Creates the Game	
			try {
				game = new Game(this);
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
			
			
		
			
		//Registers with rimRegistry
			try {
				java.rmi.registry.LocateRegistry.createRegistry(1099);
				
				
				Naming.rebind("rmi://"+realIP+"/Game", game);
				
				System.err.println("Server ready");
			} catch (Exception e) {
				System.err.println("Server exception: " + e.toString());
				e.printStackTrace();
			}
			
			//newGame();
			
			
			
			
		}
		
		public void JoinGame() throws MalformedURLException, RemoteException, NotBoundException{
			//String name = JOptionPane.showInputDialog("Enter you Player Name: ", "") ;
			String server = JOptionPane.showInputDialog("Enter the server IP Address: ", "rmi://xxx.xxx.xxx.xxx/Game");
			
			
			Remote proxy = null ;
			proxy = Naming.lookup( server ) ;
			GameInterface gameinterface= (GameInterface) proxy;
			System.out.println("connected");
			System.out.println(gameinterface.getPlayer());
				
			
    	}
		
		
		public void newGame(){
			gameMade = true;
			MainMenu.setVisible(false);
			multiplayerMenu.setVisible(false);
			pauseMenu.setVisible(false);
			NewWinMenu.setVisible(false);
			chooseMultiplayerMenu.setVisible(false);
			
			try {
				game = new Game(this);
			} catch (RemoteException e1) {
				System.out.println("exception");
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
			        		game.manager.updateSprites(loopTime);
			        		//game.manager.switchMap();
			        		game.manager.updateAnimations();
			        		game.manager.manageSprites();
			        		game.manager.manageBullets();
			        		
			        		if(game.player1.getPoints() >= 2500){
			        			showWinGameMenu();
			        		}
			        		
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
