package com.tedu.element;

import java.awt.Font;
import java.awt.Graphics;

public class Point extends ElementObj {
	private final int point;

    public Point(int point) {
        setX(0);
        setY(0);
        setW(1350);
        setH(900);
        this.point = point;
    }
	
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.setFont(new Font("微软雅黑", Font.BOLD, 30));
        g.drawString(String.format("总得分："+point), 500, 420);
	}

}
