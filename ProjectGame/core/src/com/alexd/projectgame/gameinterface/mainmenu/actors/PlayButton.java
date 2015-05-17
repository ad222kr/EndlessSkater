package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.TextGameButton;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by Alex on 2015-05-10.
 */
public class PlayButton extends TextGameButton {


    public PlayButton(String text, TextButtonStyle style, Game game) {
        super(text, style, game);
    }

    @Override
    protected void onClick() {

        Gdx.app.log("clicke play!", "");
        _game.setScreen(new GameScreen(_game));
    }


}
