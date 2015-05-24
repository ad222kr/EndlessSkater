package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.utils.GameManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 2015-05-01.
 */
public class Health extends Actor {

    private final float Y = 630f;
    private int _maxHealth;
    private boolean[] _hearts;
    private TextureRegion _filledHeart;
    private TextureRegion _depletedHeart;

    public Health(int maxHealth){
        _maxHealth = maxHealth;
        _filledHeart = AssetsManager.getSkin().getRegion("heart-filled");
        _depletedHeart = AssetsManager.getSkin().getRegion("heart-depleted");
        initHealthArray(_maxHealth);
    }

    private void initHealthArray(int health){
        _hearts = new boolean[health];

        for (Boolean heart : _hearts){
            heart = true;
        }
    }

    public void updateHealthArray(int currentHealth){
        // This is kinda custom act method, though it doesn't need
        // delta time and uses current health of runner as arg instead
        for (int i = 0; i < _hearts.length; i++){
            if (i < currentHealth){
                _hearts[i] = true;
            }
            else {
                _hearts[i] = false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        float x = 100;

        for (boolean isFilled : _hearts){
            batch.draw(isFilled ? _filledHeart : _depletedHeart, x, Y, 60, 60);
            x += 60;
        }

    }

}
