package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Main.GameFrame;

public class MenuButtons {

	
	
	
	public JButton NewGame;
	public JButton Exit;
	public JButton MainMenu;
	GameFrame game;
	
	public MenuButtons(GameFrame gameframe){
		game = gameframe;
		
		NewGame = new JButton("New Game");
		NewGame.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New Game Clicked");
				game.CreateGame();
				//game.makeGame = true;;
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
		
		
		
	}
	
	
	
}
