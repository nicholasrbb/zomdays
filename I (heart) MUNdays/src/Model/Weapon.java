package Model;

import java.awt.Image;

public class Weapon {
	Image image;
	int range;
	int damage;
	int ammo;
	int magAmmo;
		
	
	public Weapon(Image image, int range, int damage, int ammo){
		this.image = image;
		this.range = range;
		this.damage = damage;
		this.ammo = ammo;
		this.magAmmo = 0;
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getRange(){
		return range;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int getAmmo(){
		return ammo;
	}
	
	public int magAmmo(){
		return magAmmo;
	}
	
	public void reload(){
		int add = 15 - magAmmo;
		if(ammo >= add){
			magAmmo = magAmmo + add;
		}else{
			add = ammo;
			magAmmo = magAmmo + add;
		}
		ammo = ammo - add;
	}
	
	public void updateAmmo(int change){
		magAmmo = magAmmo + change;
	}
	
}
