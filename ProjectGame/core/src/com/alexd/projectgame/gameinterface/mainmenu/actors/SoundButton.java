package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.mainmenu.MainMenuStage;
import com.alexd.projectgame.gameinterface.shared.GameButton;

import com.alexd.projectgame.utils.GamePreferences;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-13.
 */
public class SoundButton extends GameButton {

    public SoundButton(TheGame game, float x, float y, float width, float height) {
        super(game, x, y, width, height);
    }

    @Override
    protected void onClick() {
        if (_game.getPrefs().isSoundEnabled()){
            _game.getPrefs().disableSound();
        }
        else{
            _game.getPrefs().enableSound();
        }
        setButtonStyle();
    }

    @Override
    protected String getUpDrawableKey() {

        return _game.getPrefs().isSoundEnabled() ? "soundon-unpressed" :
                "soundoff-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return _game.getPrefs().isSoundEnabled() ? "soundon-pressed"
                : "soundoff-pressed";
    }
}
