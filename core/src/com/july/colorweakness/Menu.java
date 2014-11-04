package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
	String[] txt = { "30", "60", "90", "4", "6", "8", "50", "70", "90" };

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

		LabelStyle[] ls = { Assert.getInstance().whiteStyle,
				Assert.getInstance().blackStyle };
		for (int i = 0; i < 9; i++) {
			startSub[i] = new SubMenuLabel(txt[i] + "s\'", ls[i % 2],
					SubMenuLabel.THREE, i % 3, zen.getY() + i / 3
							* zen.getHeight());
			startSub[i].addListener(playListener);
			stage.addActor(startSub[i]);
		}

		model = new MenuLabel("模式", Assert.getInstance().whiteStyle, 0,
				set.getY());
		model.addListener(setSubClickListener);
		stage.addActor(model);
		switch (Assert.getInstance().playMode) {
		case Constants.NUMBER:
			model.setText("模式: 纯数");
			break;
		case Constants.COLOR:
			model.setText("模式: 纯色");
			break;
		case Constants.MERGE:
			model.setText("模式: 混合");
			break;
		}

		sound = new MenuLabel("声音: 开", Assert.getInstance().blackStyle, 1,
				set.getY());
		sound.addListener(setSubClickListener);
		stage.addActor(sound);
		if (!Assert.getInstance().isSound)
			sound.setText("声音: 关");

		music = new MenuLabel("音乐: 开", Assert.getInstance().whiteStyle, 2,
				set.getY());
		music.addListener(setSubClickListener);
		stage.addActor(music);
		if (!Assert.getInstance().isMusic)
			music.setText("音乐: 关");

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
				if (Assert.getInstance().isSound) {
					Assert.getInstance().isSound = false;
					sound.setText("声音: 关");
				} else {
					Assert.getInstance().isSound = true;
					sound.setText("声音: 开");
				}
			} else if (event.getListenerActor() == music) {
				if (Assert.getInstance().isMusic) {
					Assert.getInstance().isMusic = false;
					music.setText("音乐: 关");
				} else {
					Assert.getInstance().isMusic = true;
					music.setText("音乐: 开");
				}
			} else if (event.getListenerActor() == model) {
				switch (Assert.getInstance().playMode) {
				case Constants.NUMBER:
					Assert.getInstance().playMode = Constants.COLOR;
					model.setText("模式: 纯色");
					break;
				case Constants.COLOR:
					Assert.getInstance().playMode = Constants.MERGE;
					model.setText("模式: 混合");
					break;
				case Constants.MERGE:
					Assert.getInstance().playMode = Constants.NUMBER;
					model.setText("模式: 纯数");
					break;
				}
			}
		}
	};

	// private ClickListener moreSubClickListener = new ClickListener() {
	// @Override
	// public void clicked(InputEvent event, float x, float y) {
	// }
	// };

	private ClickListener playListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			Actor temp = event.getListenerActor();
			startGame(temp);
		}
	};

	/**
	 * 开始进入游戏
	 * 
	 * @param temp
	 */
	private void startGame(Actor temp) {
		for (int i = 0; i < 9; i++) {
			int mode = 0;
			if (temp == startSub[i]) {
				switch (i / 3) {
				case 0:
					mode = Constants.ZEN;
					break;
				case 1:
					mode = Constants.NORMAL;
					break;
				case 2:
					mode = Constants.COUNTDOWN;
					break;
				}
				game.setScreen(new Game(game, mode, Integer.parseInt(txt[i])));
				dispose();
				break;
			}
		}
	}

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
		music.setMoveOutAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 10);
	}
}
