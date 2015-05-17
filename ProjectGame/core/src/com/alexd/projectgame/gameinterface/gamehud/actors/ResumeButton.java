package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-10.
 */
public class ResumeButton extends GameButton {


    public ResumeButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);
    }

    @Override
    protected void onClick() {
        if (GameManager.getInstance().isPaused()){
            GameManager.getInstance().setRunning();
        }
    }

    @Override
    protected String getUpDrawableKey() {
        return "greenbutton-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return "greenbutton-pressed";
    }
}
