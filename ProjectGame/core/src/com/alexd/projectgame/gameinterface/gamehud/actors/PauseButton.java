package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.GameManager;

import com.badlogic.gdx.Game;


/**
 * Created by Alex on 2015-05-03.
 */
public class PauseButton extends GameButton {


    public PauseButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);
        setBounds(x, y, width, height);
    }

    @Override
    protected void onClick() {
        GameManager.getInstance().setPaused();
    }

    @Override
    protected String getUpDrawableKey() {
        return "pause-button";
    }

    @Override
    protected String getDownDrawableKey() {
        return "pause-button";
    }


}
