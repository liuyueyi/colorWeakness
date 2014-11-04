package com.july.colorweakness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GameCell extends Actor {
	private int index; // color index
	private boolean over = false;
	private float time = 0;

	public GameCell() {
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return MyColor.getName(index);
	}

	public String getRGB() {
		return MyColor.getRGB(index);
	}

	public boolean clicked(int currentColor) {
		System.out.println(getName());
		if (currentColor == index && !over) {
			setMoveOutAction();
			return true;
		}
		return false;
	}

	public void setMoveInAction() {
		setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
		Action a3 = Actions.moveBy(-getWidth() / 2, -getHeight() / 2,
				Gdx.graphics.getDeltaTime() * 10);
		setScale(0, 0);
		Action a1 = Actions.scaleTo(1, 1, Gdx.graphics.getDeltaTime() * 10);
		Action a2 = Actions.fadeIn(Gdx.graphics.getDeltaTime() * 10);
		addAction(Actions.parallel(a1, a2, a3));
	}

	public void setMoveOutAction() {
		over = true;
		Action a1 = Actions.scaleTo(0, 0, Gdx.graphics.getDeltaTime() * 100);
		Action a2 = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 100);
		addAction(Actions.parallel(a1, a2));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (over) {
			time += Gdx.graphics.getDeltaTime();
			if (time > Gdx.graphics.getDeltaTime() * 10) {
				this.remove();
				return;
			}
		}
		Color c = batch.getColor();
		batch.setColor(getColor());
		batch.draw(Assert.getInstance().colors[index], getX(), getY(),
				getWidth() * getScaleX(), getHeight() * getScaleY());
		batch.setColor(c);
	}
}
