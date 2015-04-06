package com.alexd.thegame.desktop;

import com.alexd.thegame.screens.GameScreen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alexd.thegame.TheGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameScreen.WIDTH;
		config.height = GameScreen.HEIGHT;
		new LwjglApplication(new TheGame(), config);
	}
}
