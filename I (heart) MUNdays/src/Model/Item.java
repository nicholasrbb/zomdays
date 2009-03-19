package Model;

import java.awt.Image;
import java.awt.Toolkit;

public class Item {
	
	Image ammo;
	Image health;
	Image image;
	String type;
	int amount;
	int x,y;
	
	public Item(Image image, String type, int amount, int x, int y){
		this.x = x;
		this.y = y;
		this.type = type;
		this.image = image;
		this.amount = amount;
	}
	public int getX() {
		return x;
	}
	public String getType() {
		return type;
	}

	public int getY() {
		return y;
	}

	public Image getImage(){
		return image;
	}
	public int getAmount(){
		return amount;
	}

}
