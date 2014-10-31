package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class MenuLabel extends Label {
	final static int LEFT = -1;
	final static int CENTER = 0;
	final static int RIGHT = 1;

	/**
	 * ����˵���ǩ
	 * 
	 * @param text
	 *            �˵���ʾ������
	 * @param style
	 *            �׵׺��� �� �ڵװ�������
	 * @param type
	 *            �˵������ͣ���һ���˵����Ƕ����˵� [MENU, SUBMENU]
	 */
	public MenuLabel(CharSequence text, LabelStyle style, int type) {
		super(text, style);
		setType(type);
		setAlignment(Align.center);
	}

	final static int MENU = 1;
	final static int SUBMENU = 2;

	public void setType(int type) {
		if (type == 1) {
			// ����˵�
			setSize(Constants.width, Constants.menuHeight);
		} else {
			// �Ӳ˵�
			setSize(Constants.width, Constants.subMenuHeight);
		}
	}

	public void setMoveOutAction(int direction, float delayTime) {
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
