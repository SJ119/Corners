package com.moustacheapps.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Interpolation.Exp;
import com.moustacheapps.chelpers.AssetLoader;
import com.moustacheapps.gameworld.GameRenderer;
import com.moustacheapps.gameworld.GameWorld;

public class Board {

	// classes
	private GameWorld gameworld;

	// variables
	private Random r;
	private List<Integer> sequence = new ArrayList<Integer>();
	private int corner, position, position2, x, difficulty;
	private float threshold = (float) 1.5, threshold2 = (float) 1,
			timeSinceGenerate = 0, counter = 0;
	private boolean isOn = false;

	public Board(GameWorld gameworld) {
		// Initializers
		position = -1;
		position2 = -1;
		this.gameworld = gameworld;
		difficulty = 0;
		r = new Random();

		// generate a random sequence
		generateSequence();
	}

	public void generateSequence() {
		difficulty += 1;
		GameWorld.generating = true;
		// generate a random sequence of numbers of length 'difficulty'
		// containing integers from 0 to 3
		for (int i = 0; i < difficulty; i++) {
			position += 1;
			position2 += 1;
			corner = r.nextInt(4);
			// System.out.println("position: " + position);
			// System.out.println("position2: " + position2);
			sequence.add(corner);
		}
	}

	// update board
	public void update(float delta) {
		// add time delta to the time since the last generated sequence
		timeSinceGenerate += delta;
		// if time since last generate is greater than 1, then add time delta to
		// counter
		if (timeSinceGenerate > 1) {
			counter += delta;
			// if the sequence is greater than -1 and toggle is off and counter
			// is greater than threshold, then update board
			System.out.println(isOn);
			System.out.println(position2);
			System.out.println(counter);
			System.out.println(threshold);
			if (position2 >= 0 && isOn == false && (counter > threshold)) {
				// set counter back to 0
				counter = 0;
				// get what the last integer of the sequence was
				x = sequence.get(position2);
				// toggle the corresponding corner color
				switch (x) {
				case 0:
					GameRenderer.toggleLeftTop();
					break;
				case 1:
					GameRenderer.toggleLeftBottom();
					break;
				case 2:
					GameRenderer.toggleRightTop();
					break;
				default:
					GameRenderer.toggleRightBottom();
					break;
				}
				// toggle isOn
				isOn = true;
			}
			
			// if counter is now over the second threshold, and the toggle is on then update the board
			if (counter > threshold2 && isOn == true) {
				// reset counter to 0
				counter = 0;
				// toggle back the appropriate rectangle
				switch (x) {
				case 0:
					GameRenderer.toggleLeftTop();
					break;
				case 1:
					GameRenderer.toggleLeftBottom();
					break;
				case 2:
					GameRenderer.toggleRightTop();
					break;
				default:
					GameRenderer.toggleRightBottom();
					break;
				}
				// subtract one from position2 of the sequence
				position2 -= 1;
				// toggle isOn to false
				isOn = false;
			}

			// if position is less than 0, then set generating to false and allow user input
			if (position2 < 0) {
				GameWorld.generating = false;
			} else {
				GameWorld.generating = true;
			}

			// if the sequence is empty then regenerate the sequence, adding difficulty
			if (sequence.isEmpty()) {
				generateSequence();
				AssetLoader.coin.play((float) 0.5);
				timeSinceGenerate = 0;
				GameWorld.round += 1;
				threshold = (float) (1 / Math.log(GameWorld.round));
				threshold2 = (float) (1 / Math.log(GameWorld.round + 1));
			}
		}
	}

	// check if user input is the correct one
	public void check(int x) {
		// if user input is equal to the value of the sequence at 'position' then remove that element, if not then end the game
		if (sequence.get(position) == x) {
			sequence.remove(position);
			position -= 1;
			AssetLoader.flap.play();
		} else {
			AssetLoader.dead.play();
			sequence.clear();
			position = -1;
			difficulty = 0;
			if (GameWorld.round > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(GameWorld.round);
			}
			gameworld.gameover();
		}
	}
}
