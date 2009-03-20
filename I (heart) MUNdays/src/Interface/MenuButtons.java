package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import Main.GameFrame;
import Model.TileMap;

public class MenuButtons {

	
	
	
	public JButton NewGame;
	public JButton Exit;
	public JButton MainMenu;
	public JButton Resume;
	GameFrame game;
	
	public MenuButtons(GameFrame gameframe){
		game = gameframe;
		
		NewGame = new JButton("New Game");
		NewGame.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New Game Clicked");
				if ( game.gameMade == true){
					game.CreateNewGame();
				}else{
					game.CreateGame();
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
