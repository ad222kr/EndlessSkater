package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;

import com.alexd.projectgame.utils.GamePreferences;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-13.
 */
public class SoundButton extends GameButton {


    public SoundButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);

    }

    @Override
    protected void onClick() {
        if (GamePreferences.getInstance().isSoundEnabled()){
            GamePreferences.getInstance().disableSound();
        }
        else{
            GamePreferences.getInstance().enableSound();
        }


        setButtonStyle();
    }

    @Override
    protected String getUpDrawableKey() {

        return GamePreferences.getInstance().isSoundEnabled() ? "soundon-unpressed" : "soundoff-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return GamePreferences.getInstance().isSoundEnabled() ? "soundon-pressed" : "soundoff-pressed";
    }
}
