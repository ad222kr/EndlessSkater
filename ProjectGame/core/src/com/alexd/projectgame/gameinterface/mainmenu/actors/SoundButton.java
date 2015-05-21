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

    private final String SOUND_ON_UP_DRAWABLE_KEY = "soundon-unpressed";
    private final String SOUND_ON_DOWN_DRAWABLE_KEY = "soundon-pressed";
    private final String SOUND_OFF_UP_DRAWABLE_KEY = "soundoff-unpressed";
    private final String SOUND_OFF_DOWN_DRAWABLE_KEY = "soundoff-pressed";

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

        return _game.getPrefs().isSoundEnabled() ? SOUND_ON_UP_DRAWABLE_KEY : SOUND_OFF_UP_DRAWABLE_KEY;
    }

    @Override
    protected String getDownDrawableKey() {
        return _game.getPrefs().isSoundEnabled() ? SOUND_ON_DOWN_DRAWABLE_KEY : SOUND_OFF_DOWN_DRAWABLE_KEY;
    }
}
