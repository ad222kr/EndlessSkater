package com.alexd.projectgame.stages.gamehud.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.handlers.GameStateHandler;
import com.alexd.projectgame.helpers.AssetsManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;



/**
 * Created by Alex on 2015-05-03.
 */
public class PauseButton extends Button {
    private final int Y = 650;
    private final int X = 20;
    private final int WIDTH = 64;
    private final int HEIGHT = WIDTH;

    private AtlasRegion _pauseTexture;
    private GameStateHandler _gsh;
    public PauseButton(GameStateHandler gsh){
        _gsh = gsh;
        _pauseTexture = AssetsManager.getAtlasRegion("pause-button");
        ButtonStyle style = new ButtonStyle();
        style.up = new TextureRegionDrawable(_pauseTexture);
        setStyle(style);


        setBounds(X, Y, WIDTH, HEIGHT);

        addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (_gsh.getState() == GameState.RUNNING) {
                    _gsh.setState(GameState.PAUSED);
                }
                else {
                    _gsh.setState(GameState.RUNNING);
                }


                return true;
            }
        });


    }

}
