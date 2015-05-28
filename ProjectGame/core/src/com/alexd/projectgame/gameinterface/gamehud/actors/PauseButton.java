package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.GameManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Created by Alex on 2015-05-03.
 */
public class PauseButton extends GameButton {

    private final String DRAWABLE_KEY = "pause-button";

    public PauseButton(TheGame game, float x, float y, float width, float height) {
        super(game, x, y, width, height);
        setBounds(x, y, width, height);
    }

    @Override
    protected void onClick() {
        GameManager.getInstance().setPaused();
    }

    @Override
    protected String getUpDrawableKey() {
        return DRAWABLE_KEY;
    }

    @Override
    protected String getDownDrawableKey() {
        return DRAWABLE_KEY;
    }





}
