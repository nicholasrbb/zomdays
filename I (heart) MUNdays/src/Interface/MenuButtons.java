package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Main.Game;
import Main.GameFrame;
public class MenuButtons {

	
	
	
	public JButton NewGame;
	public JButton NewMultiplayerGame;
	public JButton NewXboxGame;
	public JButton Exit;
	public JButton MainMenu;
	public JButton Resume;
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
		
		NewXboxGame = new JButton("New Xbox Game");
		NewXboxGame.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New Xbox Game Clicked");
				game.xboxGame = true;
				game.newGame();
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
