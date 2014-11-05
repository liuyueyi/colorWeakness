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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class Game extends ScreenAdapter implements GestureListener {
	MainGame game;
	SpriteBatch batch;
	GestureDetector gestureDector;
	int mode;
	int level = 1;
	float time;
	DecimalFormat fnum = new DecimalFormat("0.00"); // 格式化time
	boolean isStarted = false;

	Stage stage;
	Label timeLabel; // 显示剩余时间
	Label levelLabel; // 显示当前的关卡
	Label scoreLabel; // 显示消灭方块个数
	int score = 0;
	Label infoLabel;
	String info;
	float infoY;
	private ShapeRenderer renderer;

	// 保存方块的数组
	Array<GameCell> array;
	int number; // 方块数的开方
	int chooseColor = (int) (Math.random() * 16 + 1); // 选中的颜色
	int count = 0; // 记录当前剩余的指定颜色方块数
	private float cellX, cellY, cellWidth, cellHeight;

	boolean nextLevel = false; // 当一关结束时，进入下一关
	float outTime = 0;

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
		timeLabel.setSize(Constants.width, 50 * Constants.hrate);
		timeLabel.setPosition(0, Constants.height - 100 * Constants.hrate);
		stage.addActor(timeLabel);

		levelLabel = new Label("level" + level, Assert.getInstance().levelStyle);
		levelLabel.setSize(100 * Constants.wrate, 30 * Constants.hrate);
		levelLabel.setAlignment(Align.center);
		levelLabel.setPosition(Constants.width - 100 * Constants.wrate,
				timeLabel.getY());
		stage.addActor(levelLabel);

		scoreLabel = new Label("0", Assert.getInstance().levelStyle);
		scoreLabel.setAlignment(Align.center);
		scoreLabel.setSize(100 * Constants.wrate, 30 * Constants.hrate);
		scoreLabel.setPosition(0, levelLabel.getY());
		stage.addActor(scoreLabel);

		Assert.getInstance().numFont.setMarkupEnabled(true);
		infoY = Constants.height - 140 * Constants.hrate;
		info = "";
		infoLabel = new Label(info, Assert.getInstance().levelStyle);
		infoLabel.setWidth(Constants.width);
		infoLabel.setHeight(30);
		infoLabel.setAlignment(Align.center);
		stage.addActor(infoLabel);

		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
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
		count = 0;
		for (GameCell cell : array) {
			if (cell.getIndex() == chooseColor)
				++count;
		}

		info = "[BLACK]请点击[" + MyColor.getRGB(chooseColor) + "]"
				+ MyColor.getName(chooseColor) + "[BLACK]方块";
		infoLabel.setText(info);
		infoLabel.clearActions();
		infoLabel.addAction(Actions.moveTo(0, infoY,
				Gdx.graphics.getDeltaTime() * 10));
		infoLabel.setPosition(-Constants.width, infoY);

		nextLevel = false;
		outTime = 0;

		level++;
		levelLabel
				.setText("[" + MyColor.getRGB(chooseColor) + "]level" + level);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (nextLevel) {
			outTime++;
			if (outTime > 9) { // 消去动画播放完毕，开始进入下一关
				init();
			}
		}
		if (!isStarted) {
			batch.begin();
			Assert.getInstance().whiteFont.draw(batch,
					"[" + MyColor.getRGB(chooseColor) + "]click to start", 40,
					infoY / 2);
			batch.end();
		} else {
			time -= Gdx.graphics.getDeltaTime();
			timeLabel.setText(fnum.format(time) + "s\'");
			if (time < 0) {
				// failed;
				time = 0;
			}
		}

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
		if (!isStarted) { // 进入游戏界面，点击后正式开始
			isStarted = true;
			init();
			return false;
		}

		y = Constants.height - y;
		int column = (int) ((x - cellX) / (cellWidth + 1));
		int row = (int) ((y - cellY) / (cellHeight + 1));
		if (row < 0 || column < 0 || row >= number || column >= number)
			return false;

		if (array.get(row * number + column).clicked(chooseColor)) {
			// 点击正确的方块，成绩+1， 判断是否是本关卡的最后一个方块，是，则在消去动画播放完毕后进入下一关
			score++;
			scoreLabel.setText("[" + MyColor.getRGB(chooseColor) + "]" + score);

			if (--this.count == 0) { // 本关结束，进入下一关
				nextLevel = true;
				infoLabel.addAction(Actions.moveTo(Constants.width, infoY,
						Gdx.graphics.getDeltaTime() * 10));
				for (GameCell cell : array)
					cell.setMoveOutAction();
			}
		} else {
			// 点击错误的方块，失败
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
