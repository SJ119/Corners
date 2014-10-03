package com.moustacheapps.chelpers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.moustacheapps.gameworld.GameWorld;
import com.moustacheapps.screens.GameScreen;
import com.moustacheapps.ui.SimpleButton;

public class InputHandler implements InputProcessor {
	
	// classes
	private GameWorld myWorld;

	// variables
	private SimpleButton playButton, retryButton, leftTopTap, leftBottomTap, rightTopTap, rightBottomTap;
	private float scaleFactorX;
	private float scaleFactorY;
	public static int state = 1;
	int midPointY = GameScreen.midPointY;
	int midPointX = GameScreen.midPointX;

	public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
		// initializers
		this.myWorld = myWorld;
		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;
		playButton = new SimpleButton(272 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2), midPointY + 35, 29, 16, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
		leftTopTap = new SimpleButton(0, 0, midPointX, midPointY, AssetLoader.lT, AssetLoader.lTDown);
		leftBottomTap = new SimpleButton(0, midPointY, midPointX, midPointY, AssetLoader.lB, AssetLoader.lBDown);
		rightTopTap = new SimpleButton(midPointX, 0, midPointX, midPointY, AssetLoader.rT, AssetLoader.rTDown);
		rightBottomTap = new SimpleButton(midPointX, midPointY, midPointX, midPointY, AssetLoader.rB, AssetLoader.rBDown);
		retryButton = new SimpleButton(272 / 2 - (AssetLoader.backButtonUp.getRegionWidth() / 2),midPointY + 35, 29, 16, AssetLoader.backButtonUp, AssetLoader.backButtonDown);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		
		if (myWorld.isMenu()) {
			// set playbutton to be true if it is touched down when the gamestate is MENU
			playButton.isTouchDown(screenX, screenY);
		} else if (myWorld.isRunning() && !(GameWorld.generating)) {
			// set any of the four corners to true when it is touched down when the gamestate is RUNNING
			// also makes sure that users cannot input when the game is generating the sequence
			leftTopTap.isTouchDown(screenX, screenY);
			leftBottomTap.isTouchDown(screenX, screenY);
			rightTopTap.isTouchDown(screenX, screenY);
			rightBottomTap.isTouchDown(screenX, screenY);
		} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
			// sets retry button to true when touched down.
			retryButton.isTouchDown(screenX, screenY);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		
		if (myWorld.isMenu()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				// start the game
				AssetLoader.flap.play(1);
				myWorld.start();
				state = 0;
				return true;
			}
		} else if (myWorld.isRunning() && !(GameWorld.generating)) {
			// make sure input cannot be put in if the board is generating
			if (leftTopTap.isTouchUp(screenX, screenY)) {
				// check the left top rectangle
				GameWorld.onClickRectangleCorner(0);
			}
			if (leftBottomTap.isTouchUp(screenX, screenY)) {
				// check the left bottom rectangle
				GameWorld.onClickRectangleCorner(1);
			}
			if (rightTopTap.isTouchUp(screenX, screenY)) {
				// check the right top rectangle
				GameWorld.onClickRectangleCorner(2);
			}
			if (rightBottomTap.isTouchUp(screenX, screenY)) {
				// check the right bottom rectangle
				GameWorld.onClickRectangleCorner(3);
			}
		} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
			if (retryButton.isTouchUp(screenX, screenY)) {
				AssetLoader.dead.play();
				state = 1;
				// restart the game back to the menu
				myWorld.restart(state);
				return true;
			} else {
				AssetLoader.flap.play();
				state = 0;
				// reset the game
				myWorld.restart(state);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

		// spacebar starts and restarts the game
		if (keycode == Keys.SPACE) {

			if (myWorld.isMenu()) {
				myWorld.start();
			}

			if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.restart(state);
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	public SimpleButton getPlayButton() {
		return playButton;
	}

	public SimpleButton getRetryButton() {
		return retryButton;
	}
	
	public SimpleButton getlTButton() {
		return leftTopTap;
	}
	
	public SimpleButton getrTButton() {
		return rightTopTap;
	}
	
	public SimpleButton getlBButton() {
		return leftBottomTap;
	}
	
	public SimpleButton getrBButton() {
		return rightBottomTap;
	}
	

}
