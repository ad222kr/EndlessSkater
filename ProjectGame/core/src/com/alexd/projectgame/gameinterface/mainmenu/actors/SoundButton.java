package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by Alex on 2015-05-13.
 */
public class SoundButton extends GameButton {


    public SoundButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);

    }

    @Override
    protected void onClick() {
        if (GameManager.getInstance().isSoundEnabled()){
            GameManager.getInstance().setSound(false);
        }
        else{
            GameManager.getInstance().setSound(true);
        }


        setButtonStyle();
    }

    @Override
    protected String getUpDrawableKey() {

        return GameManager.getInstance().isSoundEnabled() ? "soundon-unpressed" : "soundoff-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return GameManager.getInstance().isSoundEnabled() ? "soundon-pressed" : "soundoff-pressed";
    }
}
