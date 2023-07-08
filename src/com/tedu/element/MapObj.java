package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class MapObj extends ElementObj {
	public enum Type {
        BRICK, GRASS, IRON, RIVER
    }
	
	private Type type;

    public Type getType() {
        return type;
    }
	
//	墙的血量
	private int hp = 0;
	private String name;// 墙的type 可以用枚举

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}

	@Override
	public ElementObj createElement(String str) {
		String[] arr = str.split(",");
		ImageIcon icon = null;
		switch (arr[0]) {
		case "GRASS":
			icon = new ImageIcon("image/wall/grass.png");
			break;
		case "BRICK":
			icon = new ImageIcon("image/wall/brick.png");
			break;
		case "RIVER":
			icon = new ImageIcon("image/wall/river.png");
			name = "RIVER";
			break;
		case "IRON":
			icon = new ImageIcon("image/wall/iron.png");
			this.hp = 2;
			name = "IRON";
			break;
		}
		this.setH(icon.getIconHeight());
		this.setW(icon.getIconWidth());
		this.setX(Integer.parseInt(arr[1]));
		this.setY(Integer.parseInt(arr[2]));
		this.setIcon(icon);
		return this;
	}

	@Override
	public void setLive(boolean live) {
//		被调用一次，就减少1hp
		if ("RIVER".equals(name)) {
			return;
		}else {
			this.hp--;
			if (this.hp > 0)
				return;
		}
		super.setLive(live);
	}
}
