package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.GamePreferences;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-13.
 */
public class MusicButton extends GameButton {


    public MusicButton(TheGame game, float x, float y, float width, float height) {
        super(game, x, y, width, height);

    }

    @Override
    protected void onClick() {
        if (_game.getPrefs().isMusicEnabled()){
            _game.getPrefs().disableMusic();
        }
        else{
            _game.getPrefs().enableMusic();
        }

        setButtonStyle();
    }

    @Override
    protected String getUpDrawableKey() {
        return _game.getPrefs().isMusicEnabled() ? "musicon-unpressed" : "musicoff-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return _game.getPrefs().isMusicEnabled() ? "musicon-pressed" : "musicoff-pressed";
    }
}
