package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-13.
 */
public class MusicButton extends GameButton{


    public MusicButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);

    }

    @Override
    protected void onClick() {
        if (GameManager.getInstance().isMusicEnabled()){
            GameManager.getInstance().setMusic(false);
        }
        else{
            GameManager.getInstance().setMusic(true);
        }

        setButtonStyle();
    }

    @Override
    protected String getUpDrawableKey() {
        return GameManager.getInstance().isMusicEnabled() ? "musicon-unpressed" : "musicoff-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return GameManager.getInstance().isMusicEnabled() ? "musicon-pressed" : "musicoff-pressed";
    }
}
