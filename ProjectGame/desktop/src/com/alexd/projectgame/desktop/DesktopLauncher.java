package com.alexd.projectgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alexd.projectgame.TheGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = TheGame.APP_HEIGHT;
		config.width = TheGame.APP_WIDTH;
		new LwjglApplication(new TheGame(), config);
	}
}
