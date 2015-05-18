package com.alexd.projectgame.android;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.alexd.projectgame.TheGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		cfg.useGLSurfaceView20API18 = true;
		cfg.useImmersiveMode = true;


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			hideVirtualButtons();
		}

		initialize(new TheGame(), cfg);
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


}
