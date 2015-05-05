package com.alexd.projectgame.gameinterface.gamehud.actors;

import com.alexd.projectgame.utils.AssetsManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 2015-05-01.
 */
public class Health extends Actor {

    private final float Y = 650f;
    private int _maxHealth;
    private boolean[] _hearts;
    private AtlasRegion _filledHeart;
    private AtlasRegion _depletedHeart;

    public Health(int maxHealth){
        _maxHealth = maxHealth;
        _filledHeart = AssetsManager.getAtlasRegion("heart-filled");
        _depletedHeart = AssetsManager.getAtlasRegion("heart-depleted");
        initHealthArray(_maxHealth);


    }


    private void initHealthArray(int health){
        _hearts = new boolean[health];

        for (Boolean heart : _hearts){
            heart = true;
        }
    }

    public void updateHealthArray(int currentHealth){
        for (int i = 0; i < _hearts.length; i++){
            if (i < currentHealth){
                _hearts[i] = true;
            }
            else {
                _hearts[i] = false;
            }
        }
    }



    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        float x = 100;

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
