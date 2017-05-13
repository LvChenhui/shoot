package com.tedu.lch;

import java.util.Random;

public class Airplane extends FlyingObject implements Enemy{
	private int speed = 3;
	
	public Airplane(){
		image = ShootGame.airplane;
		this.width = image.getWidth();
		this.height = image.getHeight();
		Random ran = new Random();
		this.x = ran.nextInt(ShootGame.WIDTH - width);
		this.y = - height;
		
	}
	public int getScore() {
		return 10;
	}

	public void step() {
		y+=speed;
	}
	
	public boolean outOfBounds(){
		return this.y>ShootGame.HEIGHT;
	}
	

}
