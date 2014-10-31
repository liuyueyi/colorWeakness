package com.july.colorweakness;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

	Menu menu;

	@Override
	public void create() {
		Assert.getInstance().loadTexture();
		menu = new Menu(this);
		setScreen(menu);
	}

	@Override
	public void dispose() {
		menu.dispose();
		Assert.getInstance().dispose();
	}
}
