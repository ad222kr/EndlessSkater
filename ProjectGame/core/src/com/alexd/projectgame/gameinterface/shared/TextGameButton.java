package com.alexd.projectgame.gameinterface.shared;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Alex on 2015-05-17.
 */
public abstract class TextGameButton extends TextButton {
    protected Game _game;

    public TextGameButton(String text, TextButtonStyle style, Game game) {
        super(text, style);
        _game = game;
        setUp();
    }


    public void setUp(){
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                onClick();
            }
        });


    }


    protected abstract void onClick();


}
