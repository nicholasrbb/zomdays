package Model;

import java.awt.Image;

/**
 *Used to create in-game Items 
 *
 */
public class Item {
	
	
	String type;
	int amount;
	int x,y;
	
	/**
	 * Creates an Item with an Image, type, and a x,y coordinate.
	 * String type only takes the name of the Item, ex. "ammo" or "health"
	 *  
	 * @param image
	 * @param type
	 * @param amount
	 * @param x
	 * @param y
	 */
	public Item(String type, int amount, int x, int y){
		this.x = x;
		this.y = y;
		this.type = type;
		this.amount = amount;
	}
	
	/**
	 *Returns Items x,y world coordinates in pixels  
	 *
	 *@return Int x
	 *
	 */
	public int getX() {
		return x;
	}
	
	/**
	 *Returns Items name  
	 *
	 *@return String type
	 *
	 */
	public String getType() {
		return type;
	}

	/**
	 *Returns Items x,y world coordinates in pixels  
	 *
	 *@return Int y
	 *
	 */
	public int getY() {
		return y;
	}

	/**
	 *Returns Items Image   
	 *
	 *@return Image image
	 *
	 */
	
	
	/**
	 *Returns Items amount of ammo or health.  
	 *
	 *@return Int ammount
	 *
	 */
	public int getAmount(){
		return amount;
	}

}
