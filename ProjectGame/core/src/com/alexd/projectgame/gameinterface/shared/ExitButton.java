package com.alexd.projectgame.gameinterface.shared;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.shared.TextGameButton;
import com.alexd.projectgame.screens.MainMenuScreen;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by Alex on 2015-05-10.
 */
public class ExitButton extends TextGameButton {

    public ExitButton(String text, TextButtonStyle style, TheGame game) {
        super(text, style, game);
    }

    @Override
    protected void onClick() {
        _game.setScreen(new MainMenuScreen(_game));
    }
}
