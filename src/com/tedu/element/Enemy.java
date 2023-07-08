package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.EnemyLoad;
import com.tedu.manager.GameLoad;

public class Enemy extends ElementObj{
//	方向默认为up
	private String fx = "up";
	private int hp = 1;
	
//	public Enemy(int x, int y, int w, int h, ImageIcon icon, String fx) {
//		super(x, y, w, h, icon);
//		this.fx = fx;
//	}
	
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	
	@Override
	public ElementObj createElement(String str) {
		Random ran=new Random();
		int x=ran.nextInt(800);
		int y=ran.nextInt(500);
		this.fx = str;
		this.setX(x);
		this.setY(y);
		ImageIcon icon2 = GameLoad.imgMap.get(str);
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}
	
	//由用户自己调节方向
//	public ElementObj createElement02(String str) {
//		if(str != "")
//			fx = str;
//		Random ran=new Random();
//		int x=ran.nextInt(800);
//		int y=ran.nextInt(500);
//		this.setX(x);
//		this.setY(y);
//		this.setW(50);
//		this.setH(50);
//		this.setIcon(new ImageIcon("image/tank/bot/bot_right.png"));
//		return this;
//	}
	
	@Override
	public void move() {
		if (fx == "left" && this.getX() > 0) {
			this.setX(this.getX() - 1);
		} else if (fx == "left" && this.getX() <= 0) {
			fx = "right";
		}

		if (fx == "right" && this.getX() < 800 - this.getW()) {
			this.setX(this.getX() + 1);
		} else if (fx == "right" && this.getX() >= 800 - this.getW()) {
			fx = "left";
		}

		if (fx == "up" && this.getY() > 0) {
			this.setY(this.getY() - 1);
		} else if (fx == "up" && this.getY() <= 0) {
			fx = "down";
		}

		if (fx == "down" && this.getY() < 600 - this.getH()) {
			this.setY(this.getY() + 1);
		} else if (fx == "down" && this.getY() >= 600 - this.getH()) {
			fx = "up";
		}
	}

	@Override
	protected void updateImage() {
		this.setIcon(EnemyLoad.imgMap.get(fx));
	}
	
//	@Override
//	public boolean isLive() {
//		this.hp = this.hp - 1;
//		if(hp>0)
//		    return true;
//		else 
//			return false;
//	}
	
}
