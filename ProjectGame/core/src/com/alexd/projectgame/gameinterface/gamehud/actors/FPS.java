package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.utils.AssetsManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 2015-05-20.
 */
public class FPS extends Actor {

    final float Y = 650f;
    final float X = TheGame.APP_WIDTH / 2;
    BitmapFont font;
    float fps;
    public FPS(){
        font = AssetsManager.getSmallFont();
    }


    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), X, Y);
    }
}
