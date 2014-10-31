package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu extends ScreenAdapter {
	MainGame game;

	Stage stage;
	MenuLabel more;
	MenuLabel start;
	MenuLabel set;

	MenuLabel normal;
	MenuLabel countdown;
	MenuLabel zen;

	MenuLabel music;
	MenuLabel sound;
	MenuLabel model;

	public Menu(MainGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		set = new MenuLabel("设置", Assert.getInstance().whiteStyle, 0);
		set.addListener(clickListener);
		stage.addActor(set);

		start = new MenuLabel("开始", Assert.getInstance().blackStyle, 1);
		start.addListener(clickListener);
		stage.addActor(start);

		more = new MenuLabel("更多", Assert.getInstance().whiteStyle, 2);
		more.addListener(clickListener);
		stage.addActor(more);

		zen = new SubMenuLabel("禅", Assert.getInstance().blackStyle);
		zen.setPosition(-Constants.width, start.getY());
		zen.addListener(startSubClickListener);
		stage.addActor(zen);

		normal = new SubMenuLabel("标准", Assert.getInstance().whiteStyle);
		normal.setPosition(-Constants.width, zen.getY() + zen.getHeight());
		normal.addListener(startSubClickListener);
		stage.addActor(normal);

		countdown = new SubMenuLabel("倒计时", Assert.getInstance().blackStyle);
		countdown.setPosition(-Constants.width,
				start.getY() + 2 * countdown.getHeight());
		countdown.addListener(startSubClickListener);
		stage.addActor(countdown);

		music = new MenuLabel("音乐", Assert.getInstance().whiteStyle);
		music.setWidth(Constants.width / 2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act();
	}

	private ClickListener clickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			Gdx.app.log("color", "clicked");
			if (event.getListenerActor() == set) {
				// set label clicked
				startSubMoveOut();
			} else if (event.getListenerActor() == start) {
				// start label clicked
				startSubMoveIn();
			} else if (event.getListenerActor() == more) {
				// more label clicked
				startSubMoveOut();
			}
		}
	};

	private ClickListener startSubClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			Gdx.app.log("color", "sub menu clicked");
			if (event.getListenerActor() == zen) {
			} else if (event.getListenerActor() == normal) {
			} else if (event.getListenerActor() == countdown) {
			}
		}
	};

	private void startSubMoveIn() {
		zen.setMoveInAction(MenuLabel.LEFT, Gdx.graphics.getDeltaTime() * 10);
		normal.setMoveInAction(MenuLabel.LEFT, Gdx.graphics.getDeltaTime() * 5);
		countdown.setMoveInAction(MenuLabel.LEFT, 0);
	}

	private void startSubMoveOut() {
		zen.setMoveOutAction(MenuLabel.LEFT, 0);
		normal.setMoveOutAction(MenuLabel.LEFT, Gdx.graphics.getDeltaTime() * 5);
		countdown.setMoveOutAction(MenuLabel.LEFT,
				Gdx.graphics.getDeltaTime() * 10);
	}
}
