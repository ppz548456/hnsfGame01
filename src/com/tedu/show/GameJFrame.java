package com.tedu.show;

import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tedu.game.GameStart;

/**
 * @说明 游戏窗体 主要实现的功能：关闭，显示，最大最小化
 * @author lyc
 * @功能说明 需要嵌入面板,启动主线程等等
 * @窗体说明 swing awt 窗体大小（记录用户上次使用软件的窗体样式）
 * 
 * @分析 1.面板绑定到窗体 2.监听绑定 3.游戏主线程启动 4.显示窗体
 */
public class GameJFrame extends JFrame {
	public static int GameX = 800;// GAMEX
	public static int GameY = 600;
	private JPanel jPanel = null; // 正在现实的面板
	private KeyListener keyListener = null;// 键盘监听
	private MouseMotionListener mouseMotionListener = null; // 鼠标监听
	private MouseListener mouseListener = null;
	private Thread thead = null; // 游戏主线程
//    private int score=20;
//	private JLabel jl;
//
//	
//    public void setScore(int score) {
//		this.score = score;
//	}
//	public int getScroe() {
//		return score;
//	}
//	private int score;

	public GameJFrame() {
		init();
	}

	public void init() {
		this.setSize(GameX, GameY); // 设置窗体大小
		this.setTitle("坦克大战0.2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置退出并且关闭
		this.setLocationRelativeTo(null);// 屏幕居中显示
	}

	/* 窗体布局: 可以讲 存档，读档。button 给大家扩展的 */
	public void addButton() {
//		this.setLayout(manager);//布局格式，可以添加控件
	}
	
//	public void refresh() {
//		setScore(getScroe()+10);
//	}

	/**
	 * 启动方法
	 */
	public void start() {
		if (jPanel != null) {
//			refresh();
//			jl = new JLabel("分数："+score);
//			jPanel.add(jl);
			this.add(jPanel);
			
		}
		if (keyListener != null) {
			this.addKeyListener(keyListener);
		}
		if (thead != null) {
			thead.start();// 启动线程
		}
		this.setVisible(true);// 显示界面
//		如果jp 是 runnable的 子类实体对象 
//		如果这个判定无法进入就是 instanceof判定为 false 那么 jpanel没有实现runnable接口
		if (this.jPanel instanceof Runnable) {
//			已经做类型判定，强制类型转换不会出错
//			new Thread((Runnable)this.jPanel).start();
			Runnable run = (Runnable) this.jPanel;
			Thread th = new Thread(run);
			th.start();
		}

	}

	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}

	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}

	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public void setThead(Thread thead) {
		this.thead = thead;
	}

}
