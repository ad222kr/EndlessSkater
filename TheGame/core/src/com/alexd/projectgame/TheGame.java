package com.alexd.projectgame;

import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Game;

public class TheGame extends Game {
	public static final int APP_WIDTH = 1280;
	public static final int APP_HEIGHT = 720;
	
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
