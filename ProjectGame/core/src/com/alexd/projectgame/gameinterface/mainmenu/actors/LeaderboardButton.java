package com.alexd.projectgame.gameinterface.mainmenu.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.shared.GameButton;

/**
 * Created by Alex on 2015-05-27.
 */
public class LeaderboardButton extends GameButton {
    private final String LEADERBOARD_PRESSED_DRAWABLE_KEY = "leaderboard-pressed";
    private final String LEADERBOARD_UNPRESSED_DRAWABLE_KEY = "leaderboard-unpressed";

    public LeaderboardButton(TheGame game) {
        super(game);
    }

    @Override
    protected void onClick() {
        _game.getGoogleServices().showScores();
    }

    @Override
    protected String getUpDrawableKey() {
        return LEADERBOARD_UNPRESSED_DRAWABLE_KEY;
    }

    @Override
    protected String getDownDrawableKey() {
        return LEADERBOARD_PRESSED_DRAWABLE_KEY;
    }
}
