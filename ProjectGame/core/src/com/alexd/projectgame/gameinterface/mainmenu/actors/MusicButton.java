package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.GamePreferences;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-13.
 */
public class MusicButton extends GameButton {


    public MusicButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);

    }

    @Override
    protected void onClick() {
        if (GamePreferences.getInstance().isMusicEnabled()){
            GamePreferences.getInstance().disableMusic();
        }
        else{
            GamePreferences.getInstance().enableMusic();
        }

        setButtonStyle();
    }

    @Override
    protected String getUpDrawableKey() {
        return GamePreferences.getInstance().isMusicEnabled() ? "musicon-unpressed" : "musicoff-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return GamePreferences.getInstance().isMusicEnabled() ? "musicon-pressed" : "musicoff-pressed";
    }
}
