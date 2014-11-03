package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu extends ScreenAdapter {
	MainGame game;

	Stage stage;
	MenuLabel more;
	MenuLabel start;
	MenuLabel set;

	// start sub menu
	MenuLabel normal;
	MenuLabel countdown;
	MenuLabel zen;
	SubMenuLabel startSub[];

	// set sub menu
	MenuLabel sound;
	MenuLabel music;
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

		zen = new MenuLabel("禅", Assert.getInstance().blackStyle, 0,
				start.getY());
		zen.addListener(startSubClickListener);
		stage.addActor(zen);

		normal = new MenuLabel("标准", Assert.getInstance().whiteStyle, 1,
				start.getY());
		normal.addListener(startSubClickListener);
		stage.addActor(normal);

		countdown = new MenuLabel("倒计时", Assert.getInstance().blackStyle, 2,
				start.getY());
		countdown.addListener(startSubClickListener);
		stage.addActor(countdown);

		startSub = new SubMenuLabel[9];
		String[] txt = { "30s\'", "60s\'", "90s\'", "4s\'", "6s\'", "8s\'",
				"50s\'", "70s\'", "90s\'" };
		LabelStyle[] ls = { Assert.getInstance().whiteStyle,
				Assert.getInstance().blackStyle };
		for (int i = 0; i < 9; i++) {
			startSub[i] = new SubMenuLabel(txt[i], ls[i % 2],
					SubMenuLabel.THREE, i % 3, zen.getY() + i / 3
							* zen.getHeight());
			stage.addActor(startSub[i]);
		}
		/*
		 * startSub[0] = new SubMenuLabel("30s\'",
		 * Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 0, zen.getY());
		 * startSub[1] = new SubMenuLabel("60s\'",
		 * Assert.getInstance().blackStyle, SubMenuLabel.THREE, 1, zen.getY());
		 * startSub[2] = new SubMenuLabel("90s\'",
		 * Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 2, zen.getY());
		 * 
		 * startSub[3] = new SubMenuLabel("4s\'",
		 * Assert.getInstance().blackStyle, SubMenuLabel.THREE, 0,
		 * normal.getY()); startSub[4] = new SubMenuLabel("6s\'",
		 * Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 1,
		 * normal.getY()); startSub[5] = new SubMenuLabel("8s\'",
		 * Assert.getInstance().blackStyle, SubMenuLabel.THREE, 2,
		 * normal.getY());
		 * 
		 * startSub[6] = new SubMenuLabel("50s\'",
		 * Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 0,
		 * countdown.getY()); startSub[7] = new SubMenuLabel("70s\'",
		 * Assert.getInstance().blackStyle, SubMenuLabel.THREE, 1,
		 * countdown.getY()); startSub[8] = new SubMenuLabel("90s\'",
		 * Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 2,
		 * countdown.getY());
		 */

		model = new MenuLabel("模式", Assert.getInstance().whiteStyle, 0,
				set.getY());
		model.addListener(setSubClickListener);
		stage.addActor(model);

		sound = new MenuLabel("声音: 开", Assert.getInstance().blackStyle, 1,
				set.getY());
		sound.addListener(setSubClickListener);
		stage.addActor(sound);

		music = new MenuLabel("音乐: 开", Assert.getInstance().whiteStyle, 2,
				set.getY());
		music.addListener(setSubClickListener);
		stage.addActor(music);

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
				setSubMoveIn();
			} else if (event.getListenerActor() == start) {
				// start label clicked
				startSubMoveIn();
			} else if (event.getListenerActor() == more) {
				// more label clicked
				moreSubMoveIn();
			}
		}
	};

	private ClickListener startSubClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if (event.getListenerActor() == zen) {
				zenSubMoveIn();
			} else if (event.getListenerActor() == normal) {
				normalSubMoveIn();
			} else if (event.getListenerActor() == countdown) {
				countdownSubMoveIn();
			}
		}
	};

	private ClickListener setSubClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if (event.getListenerActor() == sound) {
				
			} else if (event.getListenerActor() == music) {
				
			} else if (event.getListenerActor() == model) {
				
			}
		}
	};

	private ClickListener moreSubClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
		}
	};

	private void startSubMoveIn() {
		moreSubMoveOut();
		setSubMoveOut();
		zen.setMoveInAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 10);
		normal.setMoveInAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 5);
		countdown.setMoveInAction(Constants.LEFT, 0);

	}

	private void startSubMoveOut() {
		zenSubMoveOut();
		normalSubMoveOut();
		countdownSubMoveOut();
		zen.setMoveOutAction(Constants.LEFT, 0);
		normal.setMoveOutAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 5);
		countdown.setMoveOutAction(Constants.LEFT,
				Gdx.graphics.getDeltaTime() * 10);
	}

	private void zenSubMoveIn() {
		for (int i = 0; i < 3; i++)
			startSub[i].setMoveInAction(Constants.RIGHT,
					Gdx.graphics.getDeltaTime() * 10);

		normalSubMoveOut();
		countdownSubMoveOut();
	}

	private void zenSubMoveOut() {
		for (int i = 0; i < 3; i++)
			startSub[i].setMoveOutAction(Constants.RIGHT,
					Gdx.graphics.getDeltaTime() * 10);
	}

	private void normalSubMoveIn() {
		for (int i = 3; i < 6; i++)
			startSub[i].setMoveInAction(Constants.RIGHT,
					Gdx.graphics.getDeltaTime() * 10);
		zenSubMoveOut();
		countdownSubMoveOut();
	}

	private void normalSubMoveOut() {
		for (int i = 3; i < 6; i++)
			startSub[i].setMoveOutAction(Constants.RIGHT,
					Gdx.graphics.getDeltaTime() * 10);
	}

	private void countdownSubMoveIn() {
		for (int i = 6; i < 9; i++)
			startSub[i].setMoveInAction(Constants.RIGHT,
					Gdx.graphics.getDeltaTime() * 10);
		zenSubMoveOut();
		normalSubMoveOut();
	}

	private void countdownSubMoveOut() {
		for (int i = 6; i < 9; i++)
			startSub[i].setMoveOutAction(Constants.RIGHT,
					Gdx.graphics.getDeltaTime() * 10);
	}

	private void moreSubMoveIn() {
		startSubMoveOut();
		setSubMoveOut();
	}

	private void moreSubMoveOut() {

	}

	private void setSubMoveIn() {
		startSubMoveOut();
		moreSubMoveOut();
		model.setMoveInAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 10);
		sound.setMoveInAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 5);
		music.setMoveInAction(Constants.LEFT, 0);

	}

	private void setSubMoveOut() {
		model.setMoveOutAction(Constants.LEFT, 0);
		sound.setMoveOutAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 5);
		music.setMoveOutAction(Constants.LEFT,
				Gdx.graphics.getDeltaTime() * 10);
	}
}
