package com.tedu.lch;

public class Bullet extends FlyingObject{
	private int speed = 3;
	
	public Bullet(int x,int y){
		image = ShootGame.bullet;
		this.x = x;
		this.y = y;
	}
	public void step() {
		y-=speed;
	}
	
	public boolean outOfBounds(){
		return y<-this.height;
	}
	
}
