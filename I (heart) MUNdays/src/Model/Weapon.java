package Model;

import java.awt.Image;
import java.util.ArrayList;

import View.Animation;

public class Weapon {
	Image image;
	int range;
	int damage;
	int ammo;
	int magAmmo;
	String name;
	public ArrayList <Animation> animations;
	int currentAnimation;	
	
	public Weapon(Image image, int range, int damage, int ammo, int mag, String name){
		this.image = image;
		this.range = range;
		this.damage = damage;
		this.ammo = ammo;
		this.magAmmo = mag;
		this.name = name;
		animations = new ArrayList <Animation>();
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
	
	public String getWeaponName(){
		return name;
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
