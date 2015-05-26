package com.alexd.projectgame;


import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.screens.MainMenuScreen;
import com.alexd.projectgame.utils.GamePreferences;
import com.alexd.projectgame.utils.IGoogleServices;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TheGame extends Game {
	public static final int APP_WIDTH = 1280;
	public static final int APP_HEIGHT = 720;
	public static final int PIXELS_TO_METERS = 50;



	private GamePreferences _prefs;
	private SpriteBatch _batch;
	private IGoogleServices _googleServices;

	public TheGame(IGoogleServices googleServices){
		super();
		_googleServices = googleServices;
	}

	@Override
	public void create(){
		AssetsManager.initiate();
		Gdx.graphics.setVSync(true);
		_batch = new SpriteBatch();

		setScreen(new MainMenuScreen(this));

	}

	@Override
	public void dispose(){
		AssetsManager.dispose();
		if (_batch != null){
			_batch.dispose();
		}
	}


	public GamePreferences getPrefs(){
		if(_prefs == null){
			_prefs = new GamePreferences();
		}
		return _prefs;
	}

	public SpriteBatch getBatch(){
		return _batch;
	}


	public IGoogleServices getGoogleServices(){
		return _googleServices;
	}


}
