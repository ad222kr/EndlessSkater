package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Game;

/**
 * Created by Alex on 2015-05-10.
 */
public class PlayButton extends GameButton {

    public PlayButton(Game game,  float x, float y, float width, float height, String skinKey){
        super(game, x, y, width, height, skinKey);
    }

    @Override
    protected void onClick() {
        _game.setScreen(new GameScreen(_game));
    }
}
