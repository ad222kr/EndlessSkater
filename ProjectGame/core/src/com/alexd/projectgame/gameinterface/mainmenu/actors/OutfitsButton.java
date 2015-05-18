package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.gameinterface.shared.TextGameButton;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by Alex on 2015-05-13.
 */
public class OutfitsButton extends TextGameButton {


    public OutfitsButton(String text, TextButtonStyle style, Game game) {
        super(text, style, game);
    }

    @Override
    protected void onClick() {
        Gdx.app.log("Outfits pressed", "");
    }


}