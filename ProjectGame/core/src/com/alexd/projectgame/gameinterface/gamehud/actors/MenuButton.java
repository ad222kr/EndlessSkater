package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.screens.MainMenuScreen;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-10.
 */
public class MenuButton extends GameButton {
    public MenuButton(Game game, float x, float y, float width, float height, String skinKey) {
        super(game, x, y, width, height, skinKey);

    }

    @Override
    protected void onClick() {
        if (GameManager.getInstance().isPaused()){
            _game.setScreen(new MainMenuScreen(_game));
        }
    }
}
