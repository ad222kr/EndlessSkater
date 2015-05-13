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

    private final float Y = 650f;
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

        if (GameManager.getInstance().isPaused()){
            batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        }
        else {
            batch.setColor(Color.WHITE);
        }

        for (boolean isFilled : _hearts){
            if (isFilled){
                batch.draw(_filledHeart, x, Y, 40, 40);

            }
            else {
                batch.draw(_depletedHeart, x, Y, 40, 40);
            }
            x += 40;
        }

    }

}
