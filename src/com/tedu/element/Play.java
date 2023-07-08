package com.tedu.element;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Play extends ElementObj /* implements Comparable<Play>*/{
	/**
	 * 移动属性:
	 * 1.单属性  配合  方向枚举类型使用; 一次只能移动一个方向 
	 * 2.双属性  上下 和 左右    配合boolean值使用 例如： true代表上 false 为下 不动？？
	 *                      需要另外一个变来确定是否按下方向键
	 *                约定    0 代表不动   1代表上   2代表下  
	 * 3.4属性  上下左右都可以  boolean配合使用  true 代表移动 false 不移动
	 * 						同时按上和下 怎么办？后按的会重置先按的
	 */
	private boolean left=false; //左
	private boolean up=false;   //上
	private boolean right=false;//右
	private boolean down=false; //下
	

//	变量专门用来记录当前主角面向的方向,默认为是up
	private String fx="up";
	private boolean pkType=false;//攻击状态 true 攻击  false停止
	
	public Play() {}
	public Play(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
	}
	
	@Override
	public ElementObj createElement(String str) {
		String[] arrs = str.split(",");
		this.setX(Integer.parseInt(arrs[0]));
		this.setY(Integer.parseInt(arrs[1]));
		ImageIcon icon2 = GameLoad.imgMap.get(arrs[2]);
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		this.fx = arrs[2];
		return this;
	}
	
	/**
	 * 面向对象中第1个思想： 对象自己的事情自己做
	 */
	@Override
	public void showElement(Graphics g) {
//		绘画图片
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	/*
	 * @说明 重写方法： 重写的要求：方法名称 和参数类型序列 必须和父类的方法一样
	 * @重点 监听的数据需要改变状态值
	 */
	@Override   // 注解 通过反射机制，为类或者方法或者属性 添加的注释(相当于身份证判定)
	public void keyClick(boolean bl,int key) { //只有按下或者鬆開才會 调用这个方法
//		System.out.println("测试："+key);
		if(bl) {//按下
			switch(key) {  //怎么优化 大家中午思考;加 监听会持续触发；有没办法触发一次
			case 37: 
				this.down=false;this.up=false;
				this.right=false;this.left=true; this.fx="left"; break;
			case 38: 
				this.right=false;this.left=false;
				this.down=false; this.up=true;   this.fx="up"; break;
			case 39: 
				this.down=false;this.up=false;
				this.left=false; this.right=true; this.fx="right";break;
			case 40: 
				this.right=false;this.left=false;
				this.up=false; this.down=true;  this.fx="down";break;
			case 32:
				this.pkType=true;break;//开启攻击状态
			}
		}else {
			switch(key) {
			case 37: this.left=false;  break;
			case 38: this.up=false;    break;
			case 39: this.right=false; break;
			case 40: this.down=false;  break;
			case 32: this.pkType=false; break;//关闭攻击状态
			}
		//a a
		}	
	}
	

	
	
//	@Override
//	public int compareTo(Play o) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	@Override
	public void move() {
		if (this.left && this.getX()>0) {
			this.setX(this.getX() - 2);
		}
		if (this.up  && this.getY()>0) {
			this.setY(this.getY() - 2);
		}
		if (this.right && this.getX()<800-this.getW()) {  //坐标的跳转由大家来完成
			this.setX(this.getX() + 2);
		}
		if (this.down && this.getY()<610-this.getH()) {
			this.setY(this.getY() + 2);
		}
	}
	@Override
	protected void updateImage() {
//		ImageIcon icon=GameLoad.imgMap.get(fx);
//		System.out.println(icon.getIconHeight());//得到图片的高度
//		如果高度是小于等于0 就说明你的这个图片路径有问题
		this.setIcon(GameLoad.imgMap.get(fx));
	}
	/**
	 * @重写规则：1.重写方法的方法名称和返回值 必须和父类的一样
	 * 			  2.重写的方法的传入参数类型序列，必须和父类的一样
	 *            3.重写的方法访问修饰符 只能 比父类的更加宽泛。
	 *               比方说：父类的方法是受保护的，但是现在需要在非子类调用
	 *                      可以直接子类继承，重写并super.父类方法。public方法
	 *            4.重写的方法抛出的异常 不可以比父类更加宽泛
	 * 子弹的添加 需要的是 发射者的坐标位置，发射者的方向  如果你可以变换子弹(思考，怎么处理？)
	 */
	private long filetime=0;
//	filefime 和传入的时间 gameTime 进行比较，赋值等操作运算，控制子弹间隔
//	这个控制代码 自己写
	@Override   //添加子弹
	public void add(long gameTime) {//有啦时间就可以进行控制
		if(!this.pkType) {//如果是不发射状态 就直接return
			return;
		}
		this.pkType=false;//按一次，发射一个子弹。拼手速(也可以增加变量来控制)
//		new PlayFile(); // 构造一个类 需要做比较多的工作  可以选择一种方式，使用小工厂
//		将构造对象的多个步骤进行封装成为一个方法，返回值直接是这个对象
//		传递一个固定格式   {X:3,y:5,f:up} json格式
		ElementObj element = new PlayFile().createElement(this.toString());
//		装入到集合中
		ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
	}
	
	@Override
	public String toString() {// 这里是偷懒，直接使用toString；建议自己定义一个方法
		//  {X:3,y:5,f:up,t:A} json格式
		int x=this.getX();
		int y=this.getY();
		switch(this.fx) { // 子弹在发射时候就已经给予固定的轨迹。可以加上目标，修改json格式
		case "up": x+=13;break;  
		// 一般不会写20等数值，一般情况下 图片大小就是显示大小；一般情况下可以使用图片大小参与运算
		case "left": y+=13;break;
		case "right": x+=35;y+=13;break;
		case "down": y+=35;x+=13; break;
		}
		return "x:"+x+",y:"+y+",f:"+this.fx;
	}
}





