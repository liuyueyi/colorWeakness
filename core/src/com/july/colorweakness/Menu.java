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

	SubMenuLabel startSub[];

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
		zen.setX(-zen.getWidth());
		zen.addListener(startSubClickListener);
		stage.addActor(zen);

		normal = new MenuLabel("标准", Assert.getInstance().whiteStyle, 1,
				start.getY());
		normal.setX(-normal.getWidth());
		normal.addListener(startSubClickListener);
		stage.addActor(normal);

		countdown = new MenuLabel("倒计时", Assert.getInstance().blackStyle, 2,
				start.getY());
		countdown.setX(-countdown.getWidth());
		countdown.addListener(startSubClickListener);
		stage.addActor(countdown);

		startSub = new SubMenuLabel[9];
		startSub[0] = new SubMenuLabel("30s\"",
				Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 0,
				zen.getY());
		startSub[1] = new SubMenuLabel("60s\"",
				Assert.getInstance().blackStyle, SubMenuLabel.THREE, 1,
				zen.getY());
		startSub[2] = new SubMenuLabel("90s\"",
				Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 2,
				zen.getY());

		startSub[3] = new SubMenuLabel("4s\"", Assert.getInstance().blackStyle,
				SubMenuLabel.THREE, 0, normal.getY());
		startSub[4] = new SubMenuLabel("6s\"", Assert.getInstance().whiteStyle,
				SubMenuLabel.THREE, 1, normal.getY());
		startSub[5] = new SubMenuLabel("8s\"", Assert.getInstance().blackStyle,
				SubMenuLabel.THREE, 2, normal.getY());

		startSub[6] = new SubMenuLabel("50s\"",
				Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 0,
				countdown.getY());
		startSub[7] = new SubMenuLabel("70s\"",
				Assert.getInstance().blackStyle, SubMenuLabel.THREE, 1,
				countdown.getY());
		startSub[8] = new SubMenuLabel("90s\"",
				Assert.getInstance().whiteStyle, SubMenuLabel.THREE, 2,
				countdown.getY());

		for (int i = 0; i < 9; i++) {
			stage.addActor(startSub[i]);
			System.out.println(i + " x : " + startSub[i].getX() + " y : "
					+ startSub[i].getY() + " width : " + startSub[i].getWidth()
					+ " height : " + startSub[i].getHeight());
		}

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
			if (event.getListenerActor() == zen) {
				for (int i = 0; i < 3; i++)
					startSub[i].setMoveInAction(Constants.RIGHT,
							Gdx.graphics.getDeltaTime() * 10);
			} else if (event.getListenerActor() == normal) {
				for (int i = 3; i < 6; i++)
					startSub[i].setMoveInAction(Constants.RIGHT,
							Gdx.graphics.getDeltaTime() * 5);
			} else if (event.getListenerActor() == countdown) {
				for (int i = 6; i < 9; i++)
					startSub[i].setMoveInAction(Constants.RIGHT, 0);
			}
		}
	};

	private void startSubMoveIn() {
		zen.setMoveInAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 10);
		normal.setMoveInAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 5);
		countdown.setMoveInAction(Constants.LEFT, 0);

	}

	private void startSubMoveOut() {
		zen.setMoveOutAction(Constants.LEFT, 0);
		normal.setMoveOutAction(Constants.LEFT, Gdx.graphics.getDeltaTime() * 5);
		countdown.setMoveOutAction(Constants.LEFT,
				Gdx.graphics.getDeltaTime() * 10);
		// for (int i = 0; i < 3; i++)
		// startSub[i].setMoveOutAction(Constants.RIGHT, 0);
		// for (int i = 3; i < 6; i++)
		// startSub[i].setMoveOutAction(Constants.RIGHT,
		// Gdx.graphics.getDeltaTime() * 5);
		// for (int i = 6; i < 9; i++)
		// startSub[i].setMoveOutAction(Constants.RIGHT,
		// Gdx.graphics.getDeltaTime() * 10);
	}
}
