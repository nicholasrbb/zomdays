package View;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interface.LabelListener;
import Main.GameFrame;

public class LoseMenu extends JPanel {


	GameFrame game;
		public LoseMenu(final GameFrame game){
			super();
			this.game = game;
			this.setBackground(Color.BLACK);
			
			
			
			
			//Image image = new Image();
			Image Title = Toolkit.getDefaultToolkit().createImage("YouLose.png");
			ImageIcon iconTitle = new ImageIcon(Title);
			Image image = Toolkit.getDefaultToolkit().createImage("NEWGAME.png");
			ImageIcon iconimage = new ImageIcon(image);
			Image image2 = Toolkit.getDefaultToolkit().createImage("Multiplayer.png");
			ImageIcon iconimage2 = new ImageIcon(image2);
			Image image3 = Toolkit.getDefaultToolkit().createImage("Exit.png");
			ImageIcon iconimage3 = new ImageIcon(image3);
			
			JLabel TitleLabel = new JLabel(iconTitle);
			JLabel label = new JLabel(iconimage);
			JLabel label2 = new JLabel(iconimage2);
			JLabel label3 = new JLabel(iconimage3);
			
			GridBagLayout gbl = new GridBagLayout();
			this.setLayout(gbl);
			
			
			MenuConstraints Ctitle = new MenuConstraints(0,0,3,1,true);
			MenuConstraints Cnew = new MenuConstraints(0,1,1,1,false);
			MenuConstraints Coptions = new MenuConstraints(1,1,1,1,false);
			MenuConstraints Cexit = new MenuConstraints(2,1,1,1,false);
			
			label.addMouseListener(new LabelListener(label) {
				
				public void mouseClicked(MouseEvent arg0) {
					game.newGame();
					
				}});
			
			label2.addMouseListener(new LabelListener(label2) {
				
				public void mouseClicked(MouseEvent arg0) {
					game.showMultiplayerMenu();
				}});
			
			
			label3.addMouseListener(new LabelListener(label3) {
				
				public void mouseClicked(MouseEvent arg0) {
					System.exit(0);
				}});
			
			
			
			
			
			
			
			
			this.add(TitleLabel, Ctitle);
			this.add(label2, Coptions);
			this.add(label3, Cexit);
			this.add(label,Cnew);
	
	
	
		}
}
