package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.gameinterface.shared.TextGameButton;
import com.alexd.projectgame.screens.MainMenuScreen;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-10.
 */
public class ExitButton extends TextGameButton {


    public ExitButton(String text, TextButtonStyle style, Game game) {
        super(text, style, game);
    }

    @Override
    protected void onClick() {
        if (GameManager.getInstance().isPaused()){
            _game.setScreen(new MainMenuScreen(_game));
        }
    }



}
