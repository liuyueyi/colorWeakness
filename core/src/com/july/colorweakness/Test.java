package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Test extends ScreenAdapter {
	public GameCell gc;
	MainGame game;
	Stage stage;
	Image im;

	public Test(MainGame game) {
		this.game = game;
		gc = new GameCell();
		gc.setIndex(1);
		gc.setBounds(20, 100, 200, 200);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		stage.addActor(gc);
		gc.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("clicked!");
				gc.setBounds(20, 100, 200, 200);
			}
		});
		
		im = new Image(Assert.getInstance().colors[2]);
		im.setBounds(10, 10, 80, 80);
		im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				im.setScale(2, 2);
				im.addAction(Actions.scaleTo(0.5f, 0.5f, Gdx.graphics.getDeltaTime() * 100));
			}
		});
		stage.addActor(im);
	}

	int tag = 0;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (tag < 3) {
			tag++;
			System.out.println(delta);
		}

		stage.draw();
		stage.act();
	}
}
