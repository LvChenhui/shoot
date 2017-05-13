package com.tedu.lch;

import java.awt.image.BufferedImage;

/**
 * ������(�л����۷䣬�ӵ���Ӣ�ۻ�)
 */
public abstract class FlyingObject {
	protected int x;   
	protected int y;  
	protected int width;  
	protected int height;   
	protected BufferedImage image;  
	public abstract boolean outOfBounds();
	
	public abstract void step();
	
	public boolean shootBy(Bullet bullet){
		int x = bullet.x; 
		int y = bullet.y; 
		return this.x<x && x<this.x+width 
				&& 
				this.y<y && y<this.y+height;
	}

}
