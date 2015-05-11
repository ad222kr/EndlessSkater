package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.utils.GameManager;

import com.badlogic.gdx.Game;


/**
 * Created by Alex on 2015-05-03.
 */
public class PauseButton extends GameButton {


    public PauseButton(Game game, float x, float y, float height, float width, String skinKey) {
        super(game, x, y, height, width, skinKey);
    }

    @Override
    protected void onClick() {
        GameManager.getInstance().setPaused();
    }


}
