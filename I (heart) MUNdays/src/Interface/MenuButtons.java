package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Main.Game;
import Main.GameFrame;
public class MenuButtons {

	
	
	
	public JButton NewGame;
	public JButton NewMultiplayerGame;
	public JButton HostMultiplayerGame;
	public JButton StartHost;
	public JButton StartJoin;
	public JButton JoinMultiplayerGame;
	public JButton NewXboxGame;
	public JButton Exit;
	public JButton MainMenu;
	public JButton Resume;
	public JButton Cancel;
	GameFrame game;
	
	
	/**
	 * All menu buttons for all of the menu JPanels
	 * 
	 * @param gameframe The main JFrame for the game
	 */
	public MenuButtons(GameFrame gameframe){
		game = gameframe;
		
		NewGame = new JButton("New Game");
		NewGame.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New Game Clicked");
				//game.CreateGame();
				game.newGame();
			}});
		
		NewMultiplayerGame = new JButton("New Multiplayer Game");
		NewMultiplayerGame.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New Game Clicked");
				//game.CreateGame();
				game.newGame();
				game.NewMultiplayerGame();
			}});
		
		HostMultiplayerGame = new JButton("Host Multiplayer Game");
		HostMultiplayerGame.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showHostMenu();
			}});
		
		StartHost = new JButton("Start Hosting Multiplayer Game");
		StartHost.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.HostGame();
				
			}});
		
		StartJoin = new JButton("Start Joining Multiplayer Game");
		StartJoin.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.JoinGame();
				
			}});
		
		JoinMultiplayerGame = new JButton("Join Multiplayer Game");
		JoinMultiplayerGame.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showJoinMenu();
			}});
		
		Cancel = new JButton("Cancel");
		Cancel.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showMainMenu();
			}});
		
		NewXboxGame = new JButton("Change Controls");
		NewXboxGame.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.display.player.getXBox() == false){
					game.display.player.setXBox(true);
				}else{
					game.display.player.setXBox(false);
				}
				
			}});
		
		Exit = new JButton("     Exit     ");
		Exit.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Game Closed");
				System.exit(0);
				
			}});
		
		MainMenu = new JButton("Return to Main Menu");
		MainMenu.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showMainMenu();
				
				
			}});
		
		Resume = new JButton("Return to Game");
		Resume.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.resumeGame();
				
				
			}});
		
		
		
	}
	
	
	
}
