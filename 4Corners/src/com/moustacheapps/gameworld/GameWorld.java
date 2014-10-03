package com.moustacheapps.gameworld;

import com.badlogic.gdx.math.Rectangle;
import com.moustacheapps.gameobjects.Board;
import com.moustacheapps.screens.GameScreen;

public class GameWorld {

	//GameWorld Classes
	private GameRenderer renderer;
	
	//GameWorld Variables
	private static Board board;
	public static Rectangle leftBound, rightBound, leftTop, leftBottom, rightTop, rightBottom;
	public static int score = 0, round = 1;
	public static float runTime = 0;
	public int midPointY = GameScreen.midPointY;
	public int midPointX = GameScreen.midPointX;
	public static boolean generating = true;
	private GameState currentState;
	public enum GameState {
		MENU, START, RUNNING, GAMEOVER, HIGHSCORE
	}

	//GameWorld Constructor
	public GameWorld() {

		//Class Initializers
		currentState = GameState.MENU;
		leftTop = new Rectangle(0, 0, midPointX, midPointY);
		leftBottom = new Rectangle(0, midPointY, midPointX, midPointY);
		rightTop = new Rectangle(midPointX, 0, midPointX, midPointY);
		rightBottom = new Rectangle(midPointX, midPointY, midPointX, midPointY);
		board = new Board(this);
	}
	
	public void update(float delta) {
		// add the time delta to the total time
		runTime += delta;
		// if the game state is START, then we want to be updating the game
		switch (currentState) {
		case START:
			updateRunning(delta);
			break;
		default:
			break;
		}
	}

	// Method to handle the updating of the game board 
	public void updateRunning(float delta) {
		// if the game takes too long to render (over .15f) then cap the delta
		if (delta > .15f) {
			delta = .15f;
		}
		// update the board class
		board.update(delta);
	}
	
	// Method to handle a game restart
	public void restart(int prevState) {
		// depending on the argument passed in, the game either starts over or menu screen
		if (prevState == 0) {
			start();
			round = 0;
			generating = true;
		} else {
			menu();
			round = 0;
			generating = true;
		}
	}

	// returns game round int
	public static int getRound() {
		return round;
	}
	
	// used when rectangle is clicked/tapped
	public static void onClickRectangleCorner(int x) {
		// pass the argument to board
		board.check(x);
	}

	// change the state to START
	public void start() {
		currentState = GameState.START;
		renderer.prepareTransition(0, 0, 0, 1f);
	}

	// change the state to MENU
	public void menu() {
		currentState = GameState.MENU;
		renderer.prepareTransition(0, 0, 0, 1f);
	}

	// change the state to GAMEOVER
	public void gameover() {
		currentState = GameState.GAMEOVER;
		renderer.prepareTransition(0, 0, 0, 1f);
	}

	// checks if gamestate is GAMEOVER
	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	// checks if gamestate is HIGHSCORE
	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	// checks if gamestate is MENU
	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	// checks if gamestate is START
	public boolean isRunning() {
		return currentState == GameState.START;
	}

	// sets the game renderer to gamerenderer argument
	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

}
