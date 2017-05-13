package com.tedu.lch;

import java.awt.image.BufferedImage;

/**
 * 飞行物(敌机，蜜蜂，子弹，英雄机)
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
