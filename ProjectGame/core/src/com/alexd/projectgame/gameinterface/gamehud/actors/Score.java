package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.helpers.AssetsManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 2015-05-01.
 */
public class Score extends Actor {
    private final int SCORE_MULTIPLYER = 5;
    private final float Y = 690f;
    private final float X = 1160f;

    private BitmapFont _font;
    private float _score;


    public Score(){
        _font = AssetsManager.getFont();
        _score = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        _font.setScale(0.90f, 0.90f);

        _font.draw(batch, "" + (int) Math.floor(_score), X, Y);

    }

    @Override
    public void act(float delta){
        super.act(delta);
        _score += (delta * SCORE_MULTIPLYER);
    }


}
