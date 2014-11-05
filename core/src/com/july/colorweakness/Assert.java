package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Assert {
	private static Assert instance;
	BitmapFont numFont;
	BitmapFont whiteFont;
	LabelStyle blackStyle;
	LabelStyle whiteStyle;
	LabelStyle timeStyle;
	LabelStyle levelStyle;

	TextureRegion colors[];
	TextureRegionDrawable trd[];
	TextureAtlas atlas;

	boolean isSound = false;
	boolean isMusic = false;
	int playMode = 0;

	private Assert() {
	}

	public static Assert getInstance() {
		if (instance == null) {
			instance = new Assert();
		}

		return instance;
	}

	public void loadTexture() {
		numFont = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		numFont.setMarkupEnabled(true);
		whiteFont = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
		whiteFont.setMarkupEnabled(true);

		atlas = new TextureAtlas(Gdx.files.internal("gfx/color.pack"));
		colors = new TextureRegion[17];
		for (int i = 0; i < 17; i++)
			colors[i] = atlas.findRegion("c", i);

		trd = new TextureRegionDrawable[2];
		trd[0] = new TextureRegionDrawable(colors[0]); // white
		trd[1] = new TextureRegionDrawable(colors[1]); // black
		blackStyle = new LabelStyle(whiteFont, Color.BLACK);
		whiteStyle = new LabelStyle(whiteFont, Color.WHITE);
		blackStyle.background = trd[0];
		whiteStyle.background = trd[1];

		timeStyle = new LabelStyle(whiteFont, Color.GRAY);
		levelStyle = new LabelStyle(numFont, Color.GRAY);
	}

	public void dispose() {
		numFont.dispose();
		whiteFont.dispose();
		atlas.dispose();
	}
}
