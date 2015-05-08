package com.alexd.projectgame;


import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.screens.MainMenuScreen;
import com.badlogic.gdx.Game;


public class TheGame extends Game {
	public static final int APP_WIDTH = 1280;
	public static final int APP_HEIGHT = 720;
	public static final int PIXELS_TO_METERS = 50;



	public static final int RUNNER_BIT = 0x0001;
	public static final int ENEMY_BIT = 0x0002;
	public static final int PLATFORM_BIT = 0x0004;
	public static final int ENEMY_SENSOR_BIT = 0x0008;
	public static final int RUNNER_SENSOR_BIT = 0x0010;








	@Override
	public void create(){
		AssetsManager.initiate();

		setScreen(new MainMenuScreen(this));

	}

	@Override
	public void dispose(){
		AssetsManager.dispose();
	}





	
}
