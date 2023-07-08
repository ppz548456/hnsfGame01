package com.tedu.game;

import java.util.Iterator;
import java.util.Random;

import javax.swing.JLabel;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;


public class GameStart {
	/**
	 * 程序的唯一入口
	 */
	public static void main(String[] args) {
		GameJFrame gj = new GameJFrame();
		/** 实例化面板，注入到jframe中 */
		GameMainJPanel jp = new GameMainJPanel();
//		实例化监听
		GameListener listener = new GameListener();
//		实例化主线程
		GameThread th = new GameThread();
		
		
//		注入
		
		gj.setKeyListener(listener);
		gj.setThead(th);
		gj.setjPanel(jp);
		
		gj.start();
		
	}

}
