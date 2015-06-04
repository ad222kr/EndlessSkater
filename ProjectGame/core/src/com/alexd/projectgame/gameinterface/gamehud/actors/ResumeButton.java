package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.shared.TextGameButton;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;

/**
 * Created by Alex on 2015-05-10.
 */
public class ResumeButton extends TextGameButton {

    public ResumeButton(String text, TextButtonStyle style, TheGame game) {
        super(text, style, game);
    }
    @Override
    protected void onClick() {
        if (GameManager.getInstance().isPaused()){
            GameManager.getInstance().setRunning();
        }
    }
}
