package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class SubMenuLabel extends Label {
	static final int THREE = 3;
	static final int TWO = 2;

	private int index;

	public SubMenuLabel(CharSequence text, LabelStyle style, int type,
			int index, float y) {
		super(text, style);
		this.index = index;
		setAlignment(Align.center | Align.top);

		setSize(Constants.width / type, Constants.menuHeight / 3);
		switch (index) {
		case 0:
			setPosition(-getWidth(), y);
			break;
		case 1:
			setPosition(Constants.width + 100, y);
			setSize(0, 0);
			break;
		case 2:
			setPosition(Constants.width, y);
			break;
		}

	}

	public void setMoveOutAction(int direction, float delayTime) {
		if (getX() < -10 || getX() > Constants.width - 10)
			return;

		Action a1 = Actions.delay(delayTime);
		float x = 0;
		switch (index) {
		case 0:
			x = -getWidth();
			break;
		case 1:			
		case 2:
			x = Constants.width;
			break;
		}
		Action a2 = Actions.moveTo(x, getY(), Gdx.graphics.getDeltaTime() * 10);
		Action a3 = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.sequence(a1, Actions.parallel(a2, a3)));
	}

	public void setMoveInAction(int direction, float delayTime) {
		Action a1 = Actions.delay(delayTime);
		Action a2;
		if (index == 1) {
			setPosition(Constants.width / 3, getY());
			a2 = Actions.sizeTo(Constants.width / 3, Constants.menuHeight / 3,
					Gdx.graphics.getDeltaTime() * 10);
		} else {
			a2 = Actions.moveTo(getWidth() * index, getY(),
					Gdx.graphics.getDeltaTime() * 10);
		}
		Action a3 = Actions.fadeIn(Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.sequence(a1, Actions.parallel(a2, a3)));
	}
}
