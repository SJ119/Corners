package com.moustacheapps.corners;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moustacheapps.corners.Corners;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "4Corners";
		cfg.useGL20 = true;
		cfg.height = 1080 / 3;
		cfg.width = 1920 / 3;

		new LwjglApplication(new Corners(), cfg);
	}
}
