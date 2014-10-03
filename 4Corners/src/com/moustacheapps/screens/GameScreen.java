package com.moustacheapps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.moustacheapps.chelpers.InputHandler;
import com.moustacheapps.gameworld.GameRenderer;
import com.moustacheapps.gameworld.GameWorld;

public class GameScreen implements Screen {
	
	// class decelerations
	private GameWorld world;
	private GameRenderer renderer;
	// variable decelerations
	private float runTime;
	public static int midPointY;
	public static int midPointX;

	// GameScreen class constructor
	public GameScreen() {
		
		// Constructor variable decelerations
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 272;
		float ratio = screenWidth / gameWidth;
		float gameHeight = screenHeight / ratio;
		
		// Initializers
		midPointY = (int) (gameHeight / 2);
		midPointX = (int) (gameWidth / 2);
		world = new GameWorld();
		Gdx.input.setInputProcessor(new InputHandler(world, ratio, ratio));
		renderer = new GameRenderer(world, (int) gameHeight);
		world.setRenderer(renderer);
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
