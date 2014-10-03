package com.moustacheapps.corners;

import com.badlogic.gdx.Game;
import com.moustacheapps.chelpers.AssetLoader;
import com.moustacheapps.screens.SplashScreen;

public class Corners extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}