package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 2015-05-01.
 */
public class Score extends Actor {
    private final int SCORE_MULTIPLIER = 20;
    private final float Y = 690f;
    private final float X = 1160f;

    private BitmapFont _font;
    private float _score;


    public Score(){
        _font = AssetsManager.getSmallFont();
        _score = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        _font.draw(batch, "" + getScore(), X, Y);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        _score += (delta * (SCORE_MULTIPLIER)) * GameManager.getInstance().getMultiplyer();
    }

    public int getScore(){
        return (int)Math.floor(_score);
    }

    public void addScore(float number){
        _score += number;
    }


}
