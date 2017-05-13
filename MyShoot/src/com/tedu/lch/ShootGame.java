package com.tedu.lch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel {
	public static final int WIDTH = 420;
	public static final int HEIGHT = 700;
	
	private Timer timer;
	private int intervel = 10;
	
	public static final int START = 0;
	public static final int GAME_OVER = 1;
	public static final int PAUSE = 2;
	public static final int RUNNING = 3;
	private int state = START;
	
	public static BufferedImage airplane;
	public static BufferedImage background;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage gameover;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage start;
	
	private FlyingObject[] flyings = {};
	private Bullet[] bullets = {}; 
	private Hero hero = new Hero();
	
	
	private int score = 0;
	
	static{
		try {
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void paint(Graphics g){
		g.drawImage(background,0,0,null);
		paintHero(g); 
		paintBullets(g);
		paintFlyingObjects(g);
		paintScore(g); 
		paintState(g); 
	}
	
	public void paintHero(Graphics g){
		g.drawImage(hero.image,hero.x,hero.y,null);
	}
	
	public void paintBullets(Graphics g){
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image,b.x,b.y,null);
		}
	}
		
	public void paintFlyingObjects(Graphics g){
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.image,f.x,f.y,null);
		}
	}
	
	public void paintScore(Graphics g){
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22);
		g.setColor(new Color(0x0000FF));
		g.setFont(font);
		g.drawString("分数为："+score, 20, 50);
		g.drawString("生命："+hero.getLife(), 20, 80);
	}
	
	public void paintState(Graphics g){
		switch(state){
		case START:
			g.drawImage(ShootGame.start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(ShootGame.pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(ShootGame.gameover,0,0,null);
			break;
		}
	}
	
	int bulletnum = 0;
	public void bulletdown(){
		bulletnum++;
		if(bulletnum%20 == 0){
			Bullet[] b = hero.shoot();
			bullets =  (Bullet[]) Arrays.copyOf(bullets, bullets.length + b.length);
			System.arraycopy(b, 0 , bullets, bullets.length - b.length,b.length);
		}
	}
	
	int flyingindex = 0;
	public void flyingAciton(){
		flyingindex++;
		if(flyingindex%40==0){
			FlyingObject f = ShootGame.nextOne();
			flyings = (FlyingObject[]) Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = f;
		}
	}
	
	public static FlyingObject nextOne(){
		Random r = new Random();
		if(r.nextInt(10) == 0){
			return new Bee();
		}else{
			return new Airplane();
		}
	}
	
	public void stepAction(){
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			b.step();
		}
		hero.step();
	}
	
	public void bulletBoomAction(){
		for (int i = 0; i < bullets.length; i++) {
			bang(bullets[i]);
		}
	}
	
	public void bang(Bullet b){
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if(f.shootBy(b)){
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = f;
				flyings = (FlyingObject[]) Arrays.copyOf(flyings, flyings.length-1);
				
				if(f instanceof Enemy){
					Enemy e = (Enemy)f;
					score += e.getScore();
				}else{
					Award a = (Award)f;
					switch(a.getAward()){
					case Award.DOUBEL_FIRE:
						hero.doubleFire();
						break;
					case Award.ADDLIFE:
						hero.addLife();
						break;
					}
				}
			}
		}
	}
	
	public void herohitAction(){
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if(hero.hit(f)){
				hero.subtractLife();
				hero.subtractFire();
				
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = f;
				flyings = (FlyingObject[]) Arrays.copyOf(flyings, flyings.length-1);
			}
		}
	}
	
	public void gameoverAction(){
		if(isGameover()){
			state = GAME_OVER;
		}
	}
	
	public boolean isGameover(){
		if(hero.getLife()<=0){
			return true;
		}else{
			return false;
		}
	}
	
	public void outOfBoundsAction(){
		int index = 0;
		Bullet[] b = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			if(!bullets[i].outOfBounds()){
				b[index++] = bullets[i];
			}
		}
		bullets = (Bullet[]) Arrays.copyOf(b, index);
		
		index = 0;
		FlyingObject[] f = new FlyingObject[flyings.length];
		for (int i = 0; i < flyings.length; i++) {
			if(!flyings[i].outOfBounds()){
				f[index++] = flyings[i];
			}
		}
		flyings = (FlyingObject[]) Arrays.copyOf(f, index);
	}
	
	public void action() {
		MouseAdapter l = new MouseAdapter() {
			public void mouseMoved(MouseEvent e){
				if(state == RUNNING){
					hero.x = e.getX()-hero.width/2;
					hero.y = e.getY()-hero.height/2;
				}
			}
			public void mouseClicked(MouseEvent e) {
				if(state == START){
					state = RUNNING;
				}
				if(state == GAME_OVER){
					state = START;
					hero = new Hero();
					bullets = new Bullet[0];
					flyings = new FlyingObject[0];
					score = 0;
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE){
					state = RUNNING;
				}
			}
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING){
					state = PAUSE;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			public void run() {
				if(state == RUNNING){
					flyingAciton();
					bulletdown();
					stepAction();
					bulletBoomAction();
					herohitAction();
					outOfBoundsAction();
					gameoverAction();
				}
				repaint();
				
			}
		},intervel,intervel);
	}
	
	public static void main(String[] args){
		ShootGame game = new ShootGame();
		JFrame jf = new JFrame("fly");
		jf.add(game);
		
		jf.setSize(WIDTH,HEIGHT);
		jf.setAlwaysOnTop(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		
		game.action();
		
		
	}



}
