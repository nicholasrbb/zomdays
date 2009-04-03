package View;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interface.LabelListener;
import Main.GameFrame;

@SuppressWarnings("serial")
public class MyMainMenu extends JPanel{

Image MUNdays;
public ArrayList<Image> images;
JLabel labelMUN;
int currentImage;
GameFrame game;
	public MyMainMenu(final GameFrame game){
		super();
		this.game = game;
		this.setBackground(Color.BLACK);
		images = new ArrayList<Image>();
		currentImage = 0;
		
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdays.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysBleed1.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysBleed2.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysBleed3.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysBleed4.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysBleed5.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysFade1.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysFade2.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysFade3.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysFade4.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysFade5.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysFade6.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart MUNdaysFade7.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart ZOMdays1.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart ZOMdays2.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart ZOMdays3.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart ZOMdays4.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart ZOMdays5.png"));
		images.add(Toolkit.getDefaultToolkit().createImage("I heart ZOMdays.png"));
		
		//Image image = new Image();
		Image image = Toolkit.getDefaultToolkit().createImage("NEWGAME.png");
		ImageIcon iconimage = new ImageIcon(image);
		Image image2 = Toolkit.getDefaultToolkit().createImage("Multiplayer.png");
		ImageIcon iconimage2 = new ImageIcon(image2);
		Image image3 = Toolkit.getDefaultToolkit().createImage("Exit.png");
		ImageIcon iconimage3 = new ImageIcon(image3);
		JLabel label = new JLabel(iconimage);
		JLabel label2 = new JLabel(iconimage2);
		JLabel label3 = new JLabel(iconimage3);
		MUNdays = images.get(0);
		ImageIcon iconMUN = new ImageIcon(MUNdays);
		labelMUN = new JLabel(iconMUN);
		
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
				game.showChooseMultiplayerMenu();
			}});
		
		
		label3.addMouseListener(new LabelListener(label3) {
			
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
				
			}});
		
		
		
		
		
		
		
		
		this.add(labelMUN, Ctitle);
		this.add(label2, Coptions);
		this.add(label3, Cexit);
		this.add(label,Cnew);
	}
	
	public void startGame(){
		long elapsedTime = 0;
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < images.size();){
			elapsedTime = System.currentTimeMillis() - startTime;
			if (elapsedTime >= 1000){
				ImageIcon icon = new ImageIcon(images.get(i));
				labelMUN.setIcon(icon);
				elapsedTime = 0;
				System.out.println("changed image");
				i++;
			}
			this.repaint();
			
		}
	}
	
	public void interateImage(){
		ImageIcon icon = new ImageIcon(images.get(currentImage));
		labelMUN.setIcon(icon);
		currentImage++;
	}
	
}
