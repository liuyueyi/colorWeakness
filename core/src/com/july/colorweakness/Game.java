package com.july.colorweakness;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class Game extends ScreenAdapter implements GestureListener {
	MainGame game;
	SpriteBatch batch;
	GestureDetector gestureDector;
	int mode;
	float time;
	int level = 10;

	String info;
	float infoX, infoY;
	DecimalFormat fnum = new DecimalFormat("##0.00");

	Stage stage;
	Label timeLabel;
	Label levelLabel;
	Label infoLabel;
	private ShapeRenderer renderer;

	// 保存色块的
	Array<GameCell> array;
	int number;
	int chooseColor;
	int count = 0;
	private float cellX, cellY, cellWidth, cellHeight;

	/**
	 * 
	 * @param game
	 * @param mode
	 *            分为 zen, normal, countdown三种模式
	 * @param time
	 */
	public Game(MainGame game, int mode, float time) {
		this.game = game;
		this.mode = mode;
		this.time = time;
		array = new Array<GameCell>();
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage();
		gestureDector = new GestureDetector(this);
		Gdx.input.setInputProcessor(gestureDector);

		timeLabel = new Label(fnum.format(time) + "s\'",
				Assert.getInstance().timeStyle);
		timeLabel.setAlignment(Align.center);
		timeLabel.setWidth(Constants.width);
		timeLabel.setPosition(0, Constants.height - 100 * Constants.hrate);
		stage.addActor(timeLabel);

		levelLabel = new Label("level" + level, Assert.getInstance().levelStyle);
		levelLabel.setAlignment(Align.center);
		levelLabel.setPosition(Constants.width - 100 * Constants.wrate,
				Constants.height - 95 * Constants.hrate);
		stage.addActor(levelLabel);

		Assert.getInstance().numFont.setMarkupEnabled(true);
		infoY = Constants.height - 140 * Constants.hrate;
		info = "[BLACK]请点击[RED]红色[BLACK]方块";
		infoLabel = new Label(info, Assert.getInstance().levelStyle);
		infoLabel.setWidth(Constants.width);
		infoLabel.setAlignment(Align.center);
		infoLabel.setPosition(0, infoY);
		stage.addActor(infoLabel);

		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(batch.getProjectionMatrix());

		init();

	}

	public void init() {
		for (GameCell cell : array) {
			cell.remove();
		}
		array.clear();

		number = 5;
		int max = number * number;
		if (max > 16)
			max = 16;

		cellWidth = Constants.width / (number + 1);
		cellHeight = cellWidth;
		cellX = cellWidth / 2;
		cellY = (infoY - cellHeight * number) / 2;

		for (int i = 0; i < number * number; i++) {
			GameCell cell = Pools.obtain(GameCell.class);
			cell.setIndex((int) (Math.random() * max) + 1);
			cell.setSize(cellWidth, cellHeight);
			cell.setPosition(cellX + (cellWidth + 1) * (i % number), cellY
					+ (cellHeight + 1) * (i / number));
			cell.setMoveInAction();
			array.add(cell);
			stage.addActor(cell);
		}

		chooseColor = array.get((int) (Math.random() * number * number))
				.getIndex();
		info = "[BLACK]请点击[" + MyColor.getRGB(chooseColor) + "]"
				+ MyColor.getName(chooseColor) + "[BLACK]方块";
		infoLabel.setText(info);

		count = 0;
		for (GameCell cell : array) {
			if (cell.getIndex() == chooseColor)
				++count;
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLACK);
		renderer.line(0, infoY, Constants.width, infoY);
		renderer.end();

		stage.draw();
		stage.act();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		y = Constants.height - y;
		int column = (int) ((x - cellX) / (cellWidth + 1));
		int row = (int) ((y - cellY) / (cellHeight + 1));
		System.out.println("y:" + y + " baseY:" + cellY + " height:" + cellHeight);
		if (row < 0 || column < 0 || row >= number || column >= number)
			return false;

		if (array.get(row * number + column).clicked(chooseColor)) {
			if (--this.count == 0) {
				init();
			}
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
}
