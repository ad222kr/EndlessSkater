package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by Alex on 2015-05-10.
 */
public class PlayButton extends GameButton {


    public PlayButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);
    }

    @Override
    protected void onClick() {

        Gdx.app.log("clicke play!", "");
        _game.setScreen(new GameScreen(_game));
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
