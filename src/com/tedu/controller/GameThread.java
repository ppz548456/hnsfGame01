package com.tedu.controller;

import java.rmi.server.LoaderHandler;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.tedu.element.Point;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.MapObj;
import com.tedu.element.Play;
import com.tedu.element.PlayFile;
import com.tedu.game.GameStart;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

/**
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，游戏运行时自动化 游戏判定；游戏地图切换 资源释放和重新读取。。。
 * @author lyc
 * @继承 使用继承的方式实现多线程(一般建议使用接口实现)
 */
public class GameThread extends Thread {
	private ElementManager em;

	private int point = 0;
	private int level = 0;

	private static int mapId = 5;
	private boolean bl = false;

	public void setPoint(int i) {
		this.point = i;
	}

	public int getPoint() {
		return this.point;
	}

	public void setBl(boolean bl) {
		this.bl = bl;
	}

	public boolean getBl() {
		return this.bl;
	}

	public GameThread() {
		em = ElementManager.getManager();
	}

	@Override
	public void run() {// 游戏的run方法 主线程
		while (true) {
			// Before Start (Loading, Progress)
			gameLoad();
			// Runtime
			gameRun();
			
			em.clearAll();
			Point point1 = new Point(point);
			em.addElements(GameElement.DIE, point1);
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			em.clearElement(GameElement.DIE);

			if (level == 2)
				System.exit(0);
		}
	}

	/**
	 * 游戏的加载
	 */
	private void gameLoad() {
		GameLoad.loadImg();
		GameLoad.MapLoad(mapId);
		mapId++;
//		load();
		if (level == 0) {
			GameLoad.loadPlay();
			GameLoad.loadEnemy("right");
		}
		if (level == 1) {
			GameLoad.loadPlay();
			GameLoad.loadEnemy("up");
		}

//		GameLoad.loadEnemy("up");
	}

	/**
	 * @说明 游戏进行时
	 * @任务说明 游戏过程中需要做的事情：1.自动化玩家的移动，碰撞，死亡 2.新元素的增加(NPC死亡后出现道具) 3.暂停等等。。。。。 先实现主角的移动
	 */

	private void gameRun() {
		long gameTime = 0L;// 给int类型就可以啦
		while (true) {// 预留扩展 true可以变为变量，用于控制管关卡结束等
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
			List<ElementObj> files = em.getElementsByKey(GameElement.PLAYFILE);
			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
			moveAndUpdate(all, gameTime);// 游戏元素自动化方法

//			ElementPK(enemys, files);
//			ElementPK(files, maps);
			collideDetect();

			if (point == 50 && level == 0) {
				level++;
				break;
			}

			if (point == 100 && level == 1) {
				level++;
				break;
			}

			gameTime++;// 唯一的时间控制
			try {
				sleep(10);// 默认理解为 1秒刷新100次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

//	public void ElementPK(List<ElementObj> listA, List<ElementObj> listB) {
////		请大家在这里使用循环，做一对一判定，如果为真，就设置2个对象的死亡状态
//		for (int i = 0; i < listA.size(); i++) {
//			ElementObj enemy = listA.get(i);
//			for (int j = 0; j < listB.size(); j++) {
//				ElementObj file = listB.get(j);
//				if (enemy.pk(file)) {
//					enemy.setLive(false);
////					if(!enemy.isLive()) {
////						setPoint(getPoint()+10);
////					}
//					file.setLive(false);
//					break;
//				}
//			}
//		}
//
//	}

//	游戏元素自动化方法
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime) {
//		GameElement.values();//隐藏方法  返回值是一个数组,数组的顺序就是定义枚举的顺序
		for (GameElement ge : GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for (int i = list.size() - 1; i >= 0; i--) {
				ElementObj obj = list.get(i);// 读取为基类
				if (!obj.isLive()) {// 如果死亡
					obj.die();// 需要大家自己补充
					list.remove(i);
					continue;
				}
				obj.model(gameTime);// 调用的模板方法 不是move
			}
		}
	}

	/** 游戏切换关卡 */
	private void gameOver() {

	}

	private void collideDetect() {
		List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
		List<ElementObj> bullets = em.getElementsByKey(GameElement.PLAYFILE);
		List<ElementObj> mapItem = em.getElementsByKey(GameElement.MAPS);
		ElementObj player = em.getElementsByKey(GameElement.PLAY).get(0);

		for (ElementObj enemy : enemies) {
			for (ElementObj bullet : bullets) {
				if (enemy.pk(bullet)) {
					if (bullet instanceof PlayFile) {
						enemy.setLive(false);
						point += 10;
						System.out.println("分数：" + point);
					} else {
						enemy.setLive(false);
					}
					bullet.setLive(false);
					break;
				}
			}
		}

		for (ElementObj mapItems : mapItem) {
			for (ElementObj bullet : bullets) {
				if (mapItems.pk(bullet)) {
					if (bullet instanceof PlayFile) {
						mapItems.setLive(false);
					} else {
						mapItems.setLive(false);
					}
					bullet.setLive(false);
					break;
				}
			}
		}

//		List<MapObj> items =  mapItem.stream()
//                .filter(e -> e instanceof MapObj)
//                .map(e -> (MapObj) e)
//                .filter(e -> e.getType() == MapObj.Type.BRICK || e.getType() == MapObj.Type.IRON)
//                .collect(Collectors.toList());
//		
//		for (MapObj item : items) {
//            if (item.pk(player)) {
//                player.rollback();
//            }
//            for (ElementObj enemy : enemies) {
//                if (item.pk(enemy)) {
//                    enemy.rollback();
//                }
//            }
//            for (ElementObj bullet : bullets) {
//                if (item.pk(bullet)) {
//                    bullet.setLive(false);
//                }
//            }
//        }
	}

}
