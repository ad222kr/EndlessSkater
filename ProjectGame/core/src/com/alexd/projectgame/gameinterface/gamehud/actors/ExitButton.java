package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.screens.MainMenuScreen;
import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-10.
 */
public class ExitButton extends GameButton {


    public ExitButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);
    }

    @Override
    protected void onClick() {
        if (GameManager.getInstance().isPaused()){
            _game.setScreen(new MainMenuScreen(_game));
        }
    }

    @Override
    protected String getUpDrawableKey() {
        // TODO: change ALL hardcoded strings to constants later, cba now
        return "standardbutton-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return "standardbutton-pressed";
    }
}
