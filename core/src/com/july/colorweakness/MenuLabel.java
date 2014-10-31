package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class MenuLabel extends Label {
	public MenuLabel(CharSequence text, LabelStyle style, int index) {
		super(text, style);
		setAlignment(Align.center);
		setSize(Constants.width, Constants.menuHeight);
		setPosition(0, index * getHeight());
	}

	public MenuLabel(CharSequence text, LabelStyle style, int index, float y) {
		super(text, style);
		setAlignment(Align.center);
		setSize(Constants.width, Constants.menuHeight / 3);
		setPosition(-Constants.width, y + index * getHeight());
	}

	public void setMoveOutAction(int direction, float delayTime) {
		if (getX() < -10 || getX() > Constants.width - 10)
			return;
		
		Action a1 = Actions.delay(delayTime);
		Action a2 = Actions.moveTo(getX() + direction * getWidth(), getY(),
				Gdx.graphics.getDeltaTime() * 10);
		Action a3 = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.sequence(a1, Actions.parallel(a2, a3)));
	}

	public void setMoveInAction(int direction, float delayTime) {
		Action a1 = Actions.delay(delayTime);
		Action a2 = Actions.moveTo(getX() - direction * getWidth(), getY(),
				Gdx.graphics.getDeltaTime() * 10);
		Action a3 = Actions.fadeIn(Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.sequence(a1, Actions.parallel(a2, a3)));
	}
}
