package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


public class SubMenuLabel extends MenuLabel{

	public SubMenuLabel(CharSequence text, LabelStyle style) {
		super(text, style);
	}
	
	@Override
	public void setMoveOutAction(int direction, float delayTime) {
		Action a1 = Actions.delay(delayTime);
		Action a2 = Actions.moveTo(getX() + direction * getWidth(), getY(),
				Gdx.graphics.getDeltaTime() * 10);
		Action a3 = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.sequence(a1, Actions.parallel(a2, a3)));
	}

	@Override
	public void setMoveInAction(int direction, float delayTime) {
		Action a1 = Actions.delay(delayTime);
		Action a2 = Actions.moveTo(getX() - direction * getWidth(), getY(),
				Gdx.graphics.getDeltaTime() * 10);
		Action a3 = Actions.fadeIn(Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.sequence(a1, Actions.parallel(a2, a3)));
	}
}
