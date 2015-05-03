package com.alexd.projectgame.stages.gamehud.actors;

import com.alexd.projectgame.gameobjects.Runner;
import com.alexd.projectgame.helpers.AssetsManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Alex on 2015-05-01.
 */
public class Health extends Actor {

    private final float Y = 650f;
    private Runner _runner;
    private boolean[] _hearts;
    private AtlasRegion _filledHeart;
    private AtlasRegion _depletedHeart;

    public Health(Runner runner){
        _runner = runner;
        _filledHeart = AssetsManager.getAtlasRegion("heart-filled");
        _depletedHeart = AssetsManager.getAtlasRegion("heart-depleted");
        initHealthArray(runner.getHealth());


    }


    private void initHealthArray(int health){
        _hearts = new boolean[health];

        for (Boolean heart : _hearts){
            heart = true;
        }
    }

    private void updateHealthArray(){
        for (int i = 0; i < _hearts.length; i++){
            if (i < _runner.getHealth()){
                _hearts[i] = true;
            }
            else {
                _hearts[i] = false;
            }
        }
    }

    @Override
    public void act(float delta){
        super.act(delta);

        updateHealthArray();
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
