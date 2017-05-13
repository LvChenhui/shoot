package com.tedu.lch;

import java.util.Random;

public class Bee extends FlyingObject implements Award{
	private int xSpeed=1,ySpeed=2;
	private int awardType;
	
	public Bee(){
		image = ShootGame.bee;
		Random ran = new Random();
		this.x = ran.nextInt(ShootGame.WIDTH - image.getWidth());
		this.y = -image.getHeight();
		this.width = image.getWidth();
		this.height = image.getHeight();
		awardType = ran.nextInt(2);
	}
	
	public int getAward() {
		return awardType;
	}

	public void step() {
		x+=xSpeed;
		y+=ySpeed;
		if(x>ShootGame.WIDTH-this.width){
			xSpeed = -1;
		}
		if(x<0){
			xSpeed = 1;
		}
	}

	public boolean outOfBounds(){
		return this.y>ShootGame.HEIGHT;
	}	
	

}
