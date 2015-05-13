package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.GameButton;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by Alex on 2015-05-13.
 */
public class OutfitsButton extends GameButton{
    public OutfitsButton(Game game, float x, float y, float width, float height) {
        super(game, x, y, width, height);
    }

    @Override
    protected void onClick() {
        Gdx.app.log("Outfits pressed", "");
    }

    @Override
    protected String getUpDrawableKey() {
        return "outfits-unpressed";
    }

    @Override
    protected String getDownDrawableKey() {
        return "outfits-pressed";
    }
}
