package Model;

import java.awt.Image;
import java.util.ArrayList;

import View.Animation;

/**
 * Class that holds all weapons, and determines range, damage and amount of ammo remaining for
 *  each weapon.
 */
public class Weapon {
	Image image;
	int range;
	int damage;
	int ammo;
	int magAmmo;
	int capacity;
	double rate;
	String name;
	public ArrayList <Animation> animations;
	int currentAnimation;	
	public boolean auto;
	
	public Weapon(Image image, int capacity, int range, int damage, int ammo, int mag, double rate,boolean auto, String name){
		this.image = image;
		this.range = range;
		this.damage = damage;
		this.ammo = ammo;
		this.magAmmo = mag;
		this.name = name;
		this.rate = rate;
		this.auto = auto;
		this.capacity = capacity;
		animations = new ArrayList <Animation>();
	}
	
	/**
	 * get the weapon's image  
	 * @return image
	 */
	public Image getImage(){
		return image;
	}
	
	/**
	 * get the weapon's range  
	 * @return range
	 */
	public int getRange(){
		return range;
	}
	
	/**
	 * get the weapon's damage  
	 * @return damage
	 */
	public int getDamage(){
		return damage;
	}
	
	/**
	 * get the weapons firing rate
	 * @return rate
	 */
	public double getRate(){
		return rate;
	}
	
	/**
	 * get the weapon's ammo  
	 * @return ammo
	 */
	public int getAmmo(){
		return ammo;
	}
	
	/**
	 * get the weapon's magAmmo  
	 * @return magAmmo
	 */
	public int magAmmo(){
		return magAmmo;
	}
	
	/**
	 * get the weapon's name  
	 * @return name
	 */
	public String getWeaponName(){
		return name;
	}
	
	/**
	 * Reloads the weapon by changing the magAmmo to 15, and adjusting the total
	 * ammo accordingly.  
	 */
	public void reload(){
		int add = capacity - magAmmo;
		if(ammo >= add){
			magAmmo = magAmmo + add;
		}else{
			add = ammo;
			magAmmo = magAmmo + add;
		}
		ammo = ammo - add;
	}
	
	/**
	 * Updates the weapon's ammo  
	 */
	public void updateAmmo(){
		if (magAmmo >0){
			magAmmo--;
			if (capacity == 30)
				magAmmo--;
		}
		
	}
	
}
