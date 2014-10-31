package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class SubMenuLabel extends Label {
	static final int THREE = 3;
	static final int TWO = 2;

	private int type;
	private int index;

	/**
	 * 
	 * @param text
	 * @param style
	 * @param type
	 *            HALF 表示是左半边的子菜单(如声音等）， FOUR和SIX表示是右半边用于设置的子菜单
	 * @param index
	 *            在右半边的序号，左半边的无意义
	 * @param y
	 *            y坐标
	 */
	public SubMenuLabel(CharSequence text, LabelStyle style, int type,
			int index, float y) {
		super(text, style);
		this.type = type;
		this.index = index;
		setAlignment(Align.center);

		if (type == THREE && index == 2) {
			setSize(0, 0);
			setPosition(Constants.width / 3, y);
		} else {
			setSize(Constants.width / type, Constants.menuHeight / 3);
			if (index == 0)
				setPosition(-getWidth(), y);
			else if (index == 2 || (index == 1 && type == TWO))
				setPosition(Constants.width + getWidth(), y);
		}
	}

	
	public void setMoveOutAction(int direction, float delayTime) {
		Action a1 = Actions.delay(delayTime);
		Action a2;
		if (type == THREE && index == 2) {
			a2 = Actions.scaleTo(0, 0, Gdx.graphics.getDeltaTime() * 10);
		}
		a2 = Actions.moveTo(getX() + direction * Constants.width / 2, getY(),
				Gdx.graphics.getDeltaTime() * 10);
		Action a3 = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.sequence(a1, Actions.parallel(a2, a3)));
	}

	
	public void setMoveInAction(int direction, float delayTime) {
		Action a1 = Actions.delay(delayTime);
		Action a2;
		if (type == THREE && index == 2) {
			a2 = Actions.scaleTo(Constants.width / 3, Constants.menuHeight / 3,
					Gdx.graphics.getDeltaTime() * 10);
		}
		a2 = Actions.moveTo(getX() - direction * Constants.width / 2, getY(),
				Gdx.graphics.getDeltaTime() * 10);
		Action a3 = Actions.fadeIn(Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.sequence(a1, Actions.parallel(a2, a3)));
	}
}
