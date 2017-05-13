package com.tedu.lch;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Hero extends FlyingObject {
	private BufferedImage[] images;
	private int index = 0;
	private int life;
	private int fireWork;
	
	public Hero(){
		image = ShootGame.hero0;
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
		width = image.getWidth();
		height = image.getHeight();
		this.x = 150;
		this.y = 400;
		life = 3;
		fireWork = 0;	
	}
	
	public void step() {
		if(images.length>0){
			image = images[index++/10%images.length];
		}
	}
	
	public int getLife(){
		return life;
	}
	
	public Bullet[] shoot(){   
		int xStep = width/4;  
		int yStep = 20;  
		if(fireWork>0){  
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet(x+xStep,y-yStep);  
			bullets[1] = new Bullet(x+3*xStep,y-yStep);
			return bullets;
		}else{     
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet(x+2*xStep,y-yStep);  
			return bullets;
		}
	}
	
	public void addLife(){
		life++;
	}
	public void doubleFire(){
		fireWork+=30;
	}
	
	public boolean hit(FlyingObject other){
		int x1 = other.x - this.width/2; 
		int x2 = other.x + this.width/2 + other.width;
		int y1 = other.y - this.height/2;  
		int y2 = other.y + this.height/2 + other.height;
		int herox = this.x + this.width/2;
		int heroy = this.y + this.height/2;
		
		return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;
	}

	
	public void subtractLife(){
		this.life--;
	}
	
	public void subtractFire(){
		this.fireWork = 0;
	}

	public boolean outOfBounds() {
		return false;
	}
}
