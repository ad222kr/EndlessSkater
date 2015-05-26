package com.alexd.projectgame.desktop;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.utils.DekstopGoogleServicesDummy;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = TheGame.APP_HEIGHT;
		config.width = TheGame.APP_WIDTH;
		config.vSyncEnabled = true;




		new LwjglApplication(new TheGame(new DekstopGoogleServicesDummy()), config);
	}
}
