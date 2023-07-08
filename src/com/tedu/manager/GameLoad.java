package com.tedu.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.MapObj;
import com.tedu.element.Play;

/**
 * @说明 加载器(工具：用户读取配置文件的工具)工具类,大多提供的是static方法
 * @author lyc
 *
 */

public class GameLoad {
	private static ElementManager em = ElementManager.getManager();
	
	public static Map<String, ImageIcon> imgMap = new HashMap<>();
	
	public static Map<String, List<ImageIcon>> imgMaps;

//	static {
//		imgMap = new HashMap<>();
//		imgMap.put("left", new ImageIcon("image/tank/play1/player1_left.png"));
//		imgMap.put("down", new ImageIcon("image/tank/play1/player1_down.png"));
//		imgMap.put("right", new ImageIcon("image/tank/play1/player1_right.png"));
//		imgMap.put("up", new ImageIcon("image/tank/play1/player1_up.png"));
//	}
	
//	用户读取文件的类
	private static Properties pro = new Properties();
	
	/**
	 * @说明 传入地图ID,由加载方法依据文件规则自动生成地图文件名称,加载文件
	 * @param mapId 文件编号 文件id
	 */
	public static void MapLoad(int mapId) {
		String mapName = "com/tedu/text/"+mapId+".map";//文件路径
//		使用IO流来获取文件对象
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream maps = classLoader.getResourceAsStream(mapName);
		if(maps == null) {
			System.out.println("配置文件读取异常");
			return;
		}
		
		try {
			pro.clear();
			pro.load(maps);
			Enumeration<?> names = pro.propertyNames();
//			可以动态的获取所有的key，有key就可以获取value
			while(names.hasMoreElements()) {
				String key = names.nextElement().toString();
				String[] arrs = pro.getProperty(key).split(";");
				for(int i=0;i<arrs.length;i++) {
					em.addElement(new MapObj().createElement(key+","+arrs[i]), GameElement.MAPS);
				}
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void loadLevel(int id, ElementManager manager) {
        String path = "com/tedu/text/level" + id + ".lev";
        Properties level = new Properties();
        InputStream stream = GameLoad.class.getClassLoader().getResourceAsStream(path);
        try {
            level.load(stream);
            //load player
            String playerStr = level.getProperty("PLAYER");
            ElementObj player = new Play().createElement(playerStr);
            manager.addElements(GameElement.PLAY, player);

            // load enemies
            String[] enemyStr = level.getProperty("ENEMY").split(";");
            Arrays.stream(enemyStr)
                    .map(s -> new Enemy().createElement(s))
                    .forEach(e -> manager.addElements(GameElement.ENEMY, e));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static void loadImg() {
		String texturl = "com/tedu/text/GameData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object o:set) {
				String url = pro.getProperty(o.toString());
				imgMap.put(o.toString(), new ImageIcon(url));
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	/**
	 * 加载玩家
	 */
	public static void loadPlay() {
		String playStr = "500,500,up";
		ElementObj play = new Play().createElement(playStr);
		em.addElement(play, GameElement.PLAY);
	}
	/**
	 * 加载敌人
	 */
	public static void loadEnemy(String str) {
		for(int i=0;i<5;i++) {
			ElementObj enemy = new Enemy().createElement(str);
			em.addElement(enemy, GameElement.ENEMY);
		}
	}
	
//	用于测试
//	public static void main(String[] args) {
//		MapLoad(5);
//	}
}
