package com.alexd.projectgame;


import com.alexd.projectgame.helpers.AssetsManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


public class TheGame extends Game {
	public static final int APP_WIDTH = 1280;
	public static final int APP_HEIGHT = 720;
	public static final int PIXELS_TO_METERS = 50;
	public static final int SCORE_MULTIPLUER = 5;

	public static final int RUNNER_BIT = 0x0001;
	public static final int ENEMY_BIT = 0x0002;
	public static final int PLATFORM_BIT = 0x0003;






	@Override
	public void create(){
		Gdx.graphics.setVSync(true);
		AssetsManager.initiate();

		setScreen(new GameScreen(this));

	}

	@Override
	public void dispose(){
		AssetsManager.dispose();
	}





	
}
