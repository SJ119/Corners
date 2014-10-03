package com.moustacheapps.gameworld;

//import java.util.List;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.moustacheapps.chelpers.AssetLoader;
import com.moustacheapps.chelpers.InputHandler;
import com.moustacheapps.gameobjects.Board;
import com.moustacheapps.screens.GameScreen;
import com.moustacheapps.tweenaccessors.Value;
import com.moustacheapps.tweenaccessors.ValueAccessor;
import com.moustacheapps.ui.SimpleButton;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private static ShapeRenderer shapeRendererOnTouch;

	private SpriteBatch batcher;

	public static boolean a, b, c, d = false;
	public static int ltr, ltg, ltb, lbr, lbg, lbb, rtr, rtg, rtb, rbr, rbg,
			rbb;

	// Game Assets
	private TextureRegion zbLogo, gameOver, highScore, scoreboard, star,
			noStar, retry, lT, lB, rT, rB, lTDown, lBDown, rTDown, rBDown;

	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();

	// Buttons
	private SimpleButton playButton, retryButton, lTButton, lBButton,
	rTButton, rBButton;
	private Color transitionColor;

	public int midPointY = GameScreen.midPointY;
	public int midPointX = GameScreen.midPointX;

	public GameRenderer(GameWorld world, int gameHeight) {

		myWorld = world;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 272, gameHeight);
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRendererOnTouch = new ShapeRenderer();
		shapeRendererOnTouch.setProjectionMatrix(cam.combined);
		transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
		
		initButtons();
		initAssets();
		initValues();
	}

	private void initAssets() {
		gameOver = AssetLoader.gameOver;
		retry = AssetLoader.retry;
		zbLogo = AssetLoader.cornerLogo;
		gameOver = AssetLoader.gameOver;
		highScore = AssetLoader.highScore;
		scoreboard = AssetLoader.scoreboard;
		star = AssetLoader.star;
		noStar = AssetLoader.noStar;
//		lB = AssetLoader.lB;
//		rB = AssetLoader.rB;
//		lT = AssetLoader.lT;
//		rT = AssetLoader.rT;
//		lBDown = AssetLoader.lBDown;
//		rBDown = AssetLoader.rBDown;
//		lTDown = AssetLoader.lTDown;
//		rTDown = AssetLoader.rTDown;
	}
	
	private void initButtons() {
		this.playButton = ((InputHandler) Gdx.input.getInputProcessor())
				.getPlayButton();

		this.retryButton = ((InputHandler) Gdx.input.getInputProcessor())
				.getRetryButton();
		
		this.lTButton = ((InputHandler) Gdx.input.getInputProcessor())
				.getlTButton();
		
		this.rTButton = ((InputHandler) Gdx.input.getInputProcessor())
				.getrTButton();
		
		this.lBButton = ((InputHandler) Gdx.input.getInputProcessor())
				.getlBButton();
		
		this.rBButton = ((InputHandler) Gdx.input.getInputProcessor())
				.getrBButton();
		
	}
	
	private void initValues() {
		ltr = 104;
		ltg = 95;
		ltb = 95;
		lbr = 158;
		lbg = 158;
		lbb = 145;
		rtr = 167;
		rtg = 180;
		rtb = 186;
		rbr = 129;
		rbg = 106;
		rbb = 135;
	}

	private void drawMenuUI() {
		batcher.draw(zbLogo,
				272 / 2 - ((zbLogo.getRegionWidth() / 1.2f) / 2.0f),
				midPointY - 40, zbLogo.getRegionWidth() / 1.2f,
				zbLogo.getRegionHeight() / 1.2f);

		playButton.draw(batcher);
	}

	private void drawScoreboard() {
		batcher.draw(scoreboard, 87, midPointY - 30, 97, 37);
		batcher.draw(noStar, 90, midPointY - 15, 10, 10);
		batcher.draw(noStar, 102, midPointY - 15, 10, 10);
		batcher.draw(noStar, 114, midPointY - 15, 10, 10);
		batcher.draw(noStar, 126, midPointY - 15, 10, 10);
		batcher.draw(noStar, 138, midPointY - 15, 10, 10);

		if (GameWorld.getRound() > 2) {
			batcher.draw(star, 138, midPointY - 15, 10, 10);
		}

		if (GameWorld.getRound() > 17) {
			batcher.draw(star, 126, midPointY - 15, 10, 10);
		}

		if (GameWorld.getRound() > 50) {
			batcher.draw(star, 114, midPointY - 15, 10, 10);
		}

		if (GameWorld.getRound() > 80) {
			batcher.draw(star, 102, midPointY - 15, 10, 10);
		}

		if (GameWorld.getRound() > 120) {
			batcher.draw(star, 90, midPointY - 15, 10, 10);
		}

		int length = ("" + GameWorld.getRound()).length();

		AssetLoader.whiteFont.draw(batcher, "" + GameWorld.getRound(),
				169 - (2 * length), midPointY - 20);

		int length2 = ("" + AssetLoader.getHighScore()).length();
		AssetLoader.whiteFont.draw(batcher, "" + AssetLoader.getHighScore(),
				169 - (2.5f * length2), midPointY - 3);

	}

	private void drawRetry() {
		batcher.draw(retry, 103, midPointY + 10, 66, 14);
		retryButton.draw(batcher);
	}

	private void drawGameOver() {
		batcher.draw(gameOver, 90, midPointY - 50, 92, 14);
	}

	private void drawScore() {
		int length = ("Round: " + GameWorld.getRound()).length();
		AssetLoader.shadow.draw(batcher, "Round: " + GameWorld.getRound(),
				136 - (9 * length / 2), 5);
		AssetLoader.font.draw(batcher, "Round: " + GameWorld.getRound(),
				136 - (9 * length / 2), 5);
	}

	private void drawHighScore() {
		batcher.draw(highScore, 88, midPointY - 50, 96, 14);
	}

	public static void toggleLeftTop() {
		if (a) {
			ltr = 104;
			ltg = 95;
			ltb = 95;
			a = false;
		} else {
			ltr = 58;
			ltg = 53;
			ltb = 53;
			a = true;
		}
	}

	public static void toggleLeftBottom() {
		if (b) {
			lbr = 158;
			lbg = 158;
			lbb = 145;
			b = false;
		} else {
			lbr = 90;
			lbg = 90;
			lbb = 81;
			b = true;
		}
	}

	public static void toggleRightTop() {
		if (c) {
			rtr = 167;
			rtg = 180;
			rtb = 186;
			c = false;
		} else {
			rtr = 96;
			rtg = 106;
			rtb = 111;
			c = true;
		}
	}

	public static void toggleRightBottom() {
		if (d) {
			rbr = 129;
			rbg = 106;
			rbb = 135;
			d = false;
		} else {
			rbr = 72;
			rbg = 60;
			rbb = 75;
			d = true;
		}
	}

	public void render(float delta, float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// DrawTopLeft104,95,95
		shapeRenderer.setColor(ltr / 255.0f, ltg / 255.0f, ltb / 255.0f, 1);
		shapeRenderer.rect(0, 0, midPointX, midPointY);

		// DrawTopLeft158,158,145
		shapeRenderer.setColor(lbr / 255.0f, lbg / 255.0f, lbb / 255.0f, 1);
		shapeRenderer.rect(0, midPointY, midPointX, midPointY);

		// DrawTopLeft167,180,186
		shapeRenderer.setColor(rtr / 255.0f, rtg / 255.0f, rtb / 255.0f, 1);
		shapeRenderer.rect(midPointX, 0, midPointX, midPointY);

		// DrawTopLeft129,106,135
		shapeRenderer.setColor(rbr / 255.0f, rbg / 255.0f, rbb / 255.0f, 1);
		shapeRenderer.rect(midPointX, midPointY, midPointX, midPointY);

		shapeRenderer.end();

		batcher.begin();
		batcher.enableBlending();

		if (!GameWorld.generating) {
		lTButton.draw(batcher);
		lBButton.draw(batcher);
		rTButton.draw(batcher);
		rBButton.draw(batcher);
		}
		
		if (myWorld.isMenu()) {
			drawMenuUI();
		} else if (myWorld.isRunning()) {
			drawScore();
		} else if (myWorld.isGameOver()) {
			drawGameOver();
			drawScoreboard();
			drawRetry();
		} else if (myWorld.isHighScore()) {
			drawHighScore();
			drawScoreboard();
			drawRetry();
		}

		batcher.end();

		drawTransition(delta);

	}

	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0)
				.ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g,
					transitionColor.b, alpha.getValue());
			shapeRenderer.rect(0, 0, 272, 300);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);

		}
	}

}
