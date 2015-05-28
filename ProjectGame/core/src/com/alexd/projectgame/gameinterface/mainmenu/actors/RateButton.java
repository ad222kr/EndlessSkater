package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.shared.GameButton;

/**
 * Created by Alex on 2015-05-27.
 */
public class RateButton extends GameButton {

    private final String RATE_UNPRESSED_DRAWABLE_KEY = "rate-unpressed";
    private final String RATE_PRESSED_DRAWABLE_KEY = "rate-pressed";
    public RateButton(TheGame game) {
        super(game);
    }

    @Override
    protected void onClick() {
        _game.getGoogleServices().rateGame();
    }

    @Override
    protected String getUpDrawableKey() {
        return RATE_UNPRESSED_DRAWABLE_KEY;
    }

    @Override
    protected String getDownDrawableKey() {
        return RATE_PRESSED_DRAWABLE_KEY;
    }
}
