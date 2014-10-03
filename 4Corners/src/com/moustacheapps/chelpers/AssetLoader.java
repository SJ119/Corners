package com.moustacheapps.chelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture, logoTexture;
	public static TextureRegion logo, cornerLogo, playButtonUp, playButtonDown,
			backButtonUp, backButtonDown, ready, gameOver, highScore,
			scoreboard, star, noStar, retry, lT, lB, rT, rB, lTDown, lBDown, rTDown,
			rBDown;
	public static Sound dead, flap, coin, fall, powerUpSound;
	public static BitmapFont font, shadow, whiteFont;
	public static Preferences prefs;

	public static void load() {

		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 256, 114);

		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		cornerLogo = new TextureRegion(texture, 0, 55, 88, 24);
		cornerLogo.flip(false, true);
		
		lT = new TextureRegion(texture, 0, 0, 26, 26);
		lB = new TextureRegion(texture, 0, 26, 26, 26);
		rT = new TextureRegion(texture, 26, 0, 26, 26);
		rB = new TextureRegion(texture, 26, 26, 26, 26);
		
		lTDown = new TextureRegion(texture, 52, 0, 26, 26);
		lBDown = new TextureRegion(texture, 52, 26, 26, 26);
		rTDown = new TextureRegion(texture, 78, 0, 26, 26);
		rBDown = new TextureRegion(texture, 78, 26, 26, 26);

		playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
		playButtonUp.flip(false, true);
		
		playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
		playButtonDown.flip(false, true);
		
		backButtonUp = new TextureRegion(texture, 0, 99, 29, 16);
		backButtonUp.flip(false, true);
		
		backButtonDown = new TextureRegion(texture, 29, 99, 29, 16);
		backButtonDown.flip(false, true);

		ready = new TextureRegion(texture, 59, 83, 34, 7);
		ready.flip(false, true);

		retry = new TextureRegion(texture, 59, 110, 33, 7);
		retry.flip(false, true);

		gameOver = new TextureRegion(texture, 59, 92, 46, 7);
		gameOver.flip(false, true);

		highScore = new TextureRegion(texture, 59, 101, 48, 7);
		highScore.flip(false, true);
		
		scoreboard = new TextureRegion(texture, 108, 84, 98, 38);
		scoreboard.flip(false, true);

		star = new TextureRegion(texture, 149, 71, 10, 10);
		noStar = new TextureRegion(texture, 162, 71, 10, 10);

		star.flip(false, true);
		noStar.flip(false, true);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));
		powerUpSound = Gdx.audio.newSound(Gdx.files
				.internal("data/powerup.wav"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);

		whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
		whiteFont.setScale(.1f, -.1f);

		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("Corners");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
	}

	public static void setHighScore() {
		prefs.putInteger("highScore", 0);
		prefs.flush();
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void dispose() {
		// We must dispose of the texture when we are finished.
		texture.dispose();
		logoTexture.dispose();

		// Dispose sounds
		dead.dispose();
		flap.dispose();
		coin.dispose();
		fall.dispose();

		font.dispose();
		shadow.dispose();
	}

}