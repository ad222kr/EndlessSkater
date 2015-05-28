package com.alexd.projectgame.gameinterface.shared;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.shared.TextGameButton;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by Alex on 2015-05-10.
 */
public class PlayButton extends TextGameButton {

    public PlayButton(String text, TextButtonStyle style, TheGame game) {
        super(text, style, game);
    }

    @Override
    protected void onClick() {

        _game.setScreen(new GameScreen(_game));
    }


}
