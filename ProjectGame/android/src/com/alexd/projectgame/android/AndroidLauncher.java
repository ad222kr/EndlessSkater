package com.alexd.projectgame.android;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.alexd.projectgame.utils.IGoogleServices;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.alexd.projectgame.TheGame;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Games;
import com.google.android.gms.search.SearchAuth;
import com.google.example.games.basegameutils.GameHelper;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices, GameHelper.GameHelperListener{
	private final static int REQUEST_CODE_UNUSED = 9002;
	private GameHelper _gameHelper;
	private int _playServiceCode;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		cfg.useGLSurfaceView20API18 = true;
		cfg.useImmersiveMode = true;


		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		//_gameHelper.enableDebugLog(true);
		if (!isPlayServiceAvailable()){
			_gameHelper.setConnectOnStart(false);
		}



		_gameHelper.setup(this);


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			hideVirtualButtons();
		}

		initialize(new TheGame(this), cfg);
	}


	@TargetApi(19)
	private void hideVirtualButtons(){
		// http://www.pixnbgames.com/blog/libgdx/how-to-hide-virtual-buttons-in-android-libgdx/
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			// In KITKAT (4.4) and next released, hide virtual buttons
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
				hideVirtualButtons();
			}
		}
	}


	@Override
	public void signIn() {



		try{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e){
			Gdx.app.log("Mainactivity: ", "Login failed: " + e.getMessage() + ".");
		}



	}

	@Override
	public void signOut() {
		try{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					_gameHelper.signOut();
				}
			});
		}
		catch (Exception e){
			Gdx.app.log("Mainactivity: ", "Signout failed: " + e.getMessage() + ".");
		}

	}

	@Override
	public void rateGame() {
		String str = "https://play.google.com/store/apps/details?id=com.alex.projectgame";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void submitScore(long score) {
		if (isSignedIn()){
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);

		}
	}

	@Override
	public void showScores() {
		if (isSignedIn()){
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		}
		else{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		return _gameHelper.isSignedIn();
	}

	@Override
	protected void onStart(){
		super.onStart();
		_gameHelper.onStart(this);
	}

	@Override
	protected void onStop(){
		super.onStop();
		_gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSignInFailed() {

	}

	@Override
	public void onSignInSucceeded() {

	}

	public boolean isPlayServiceAvailable() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());

		return status == ConnectionResult.SUCCESS;
	}
}
