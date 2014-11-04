package com.july.colorweakness;

import com.badlogic.gdx.Gdx;

public class Constants {
	public static float width = Gdx.graphics.getWidth();
	public static float height = Gdx.graphics.getHeight();

	public static float wrate = width / 320;
	public static float hrate = height / 480;

	public static float menuHeight = height / 3;
	public static float subMenuHeight = menuHeight / 3;

	public final static int LEFT = -1;
	public final static int CENTER = 0;
	public final static int RIGHT = 1;

	// 三种游戏模式
	public final static int NORMAL = 1;
	public final static int COUNTDOWN = 2;
	public final static int ZEN = 3;

	// play mode
	public final static int NUMBER = 0;
	public final static int COLOR = 1;
	public final static int MERGE = 2;
}
