package com.tedu.manager;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class EnemyLoad {
	public static Map<String, ImageIcon> imgMap;

	static {
		imgMap = new HashMap<>();
		imgMap.put("left", new ImageIcon("image/tank/bot/bot_left.png"));
		imgMap.put("down", new ImageIcon("image/tank/bot/bot_down.png"));
		imgMap.put("right", new ImageIcon("image/tank/bot/bot_right.png"));
		imgMap.put("up", new ImageIcon("image/tank/bot/bot_up.png"));
	}
}
