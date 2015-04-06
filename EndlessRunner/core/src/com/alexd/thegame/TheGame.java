package com.alexd.thegame;

import com.alexd.thegame.screens.GameScreen;
import com.badlogic.gdx.Game;


public class TheGame extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}

	@Override
	public void render () {

	}
}
