package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.gameinterface.gamehud.GameHudStage;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.utils.GameStateManager;
import com.alexd.projectgame.utils.AssetsManager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
    public PauseButton(){

        _pauseTexture = AssetsManager.getAtlasRegion("pause-button");
        ButtonStyle style = new ButtonStyle();
        style.up = new TextureRegionDrawable(_pauseTexture);
        setStyle(style);


        setBounds(X, Y, WIDTH, HEIGHT);

        addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (GameManager.getInstance().getState() == GameState.RUNNING) {
                    GameManager.getInstance().setState(GameState.PAUSED);
                }
                else {
                    GameManager.getInstance().setState(GameState.RUNNING);
                }


                return true;
            }
        });


    }

}
