package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.shared.TextGameButton;
import com.alexd.projectgame.screens.OutfitScreen;

/**
 * Created by Alex on 2015-05-13.
 */
public class OutfitsButton extends TextGameButton {


    public OutfitsButton(String text, TextButtonStyle style, TheGame game) {
        super(text, style, game);
    }

    @Override
    protected void onClick() {
        _game.setScreen(new OutfitScreen(_game));
    }


}
