package com.alexd.projectgame.gameinterface.shared;

import com.alexd.projectgame.TheGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Alex on 2015-05-17.
 */
public abstract class TextGameButton extends TextButton {
    protected TheGame _game;

    public TextGameButton(String text, TextButtonStyle style, TheGame game) {
        super(text, style);
        _game = game;
        setUp();
    }

    public void setUp(){
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                onClick();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                super.touchDown(event, x, y, pointer, button);
                if (_game.getPrefs().isSoundEnabled()){
                    _game.getAudioManager().playClickSound();
                }
                return true;
            }
        });
    }

    protected abstract void onClick();
}
