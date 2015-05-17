package com.alexd.projectgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

/**
 * Class that will render the game objects. The UI-rendering will be taken
 * care of by Scene2D
 */
public class GameRenderer {

    private SpriteAnimation _runnerAnimation;
    private SpriteAnimation _enemyAnimation;
    private Sprite _runnerJumpRegion;
    private AtlasRegion _obstacleRegion;
    private AtlasRegion _platformRegion;
    private TextureRegion _heartRegion;
    private float _runnerAnimationElapsed;
    private float _enemyAnimationElapsed;
    private Texture _background;


    public GameRenderer(){
        // Animation with textureatlas test
        _runnerAnimation = AssetsManager.getAnimation("player");
        _obstacleRegion = AssetsManager.getAtlasRegion("obstacle");
        _runnerJumpRegion = new Sprite(AssetsManager.getAtlasRegion("playerjump"));
        _enemyAnimation = AssetsManager.getAnimation("enemy");
        _background = AssetsManager.getBackground();
        _platformRegion = AssetsManager.getAtlasRegion("platform");
        _heartRegion = AssetsManager.getSkin().getRegion("heart-filled");


    }

    public void updateAnimation(float delta){
        _runnerAnimationElapsed += delta * GameManager.getInstance().getMultiplyer();
        _enemyAnimationElapsed += delta * GameManager.getInstance().getMultiplyer();
    }

    public void drawRunner(Batch batch, float x, float y, boolean isJumping){
        if (!isJumping){
            _runnerAnimation.draw(_runnerAnimationElapsed, batch, x, y);
        }
        else {
            batch.draw(_runnerJumpRegion, x, y, Helpers.convertToMeters(_runnerJumpRegion.getWidth()),
                    Helpers.convertToMeters(_runnerJumpRegion.getHeight()));
            _runnerAnimationElapsed = 0;

        }
    }

    public void drawEnemy(Batch batch, float x, float y){
            _enemyAnimation.draw(_enemyAnimationElapsed, batch, x, y);
    }

    public void drawObstacle(Batch batch, float x, float y){
        batch.draw(_obstacleRegion, x, y, Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);
    }

    public void drawPlatform(Batch batch, float x, float y, float platformWidth){
        if (platformWidth == Constants.PLATFORM_WIDTH){
            batch.draw(_platformRegion, x, y, Helpers.convertToMeters(_platformRegion.getRegionWidth()),
                    Helpers.convertToMeters(_platformRegion.getRegionHeight()));
        }
        else {
            batch.draw(_platformRegion, x + Constants.PLATFORM_WIDTH , y,
                    Helpers.convertToMeters(_platformRegion.getRegionWidth()), Helpers.convertToMeters(_platformRegion.getRegionHeight()));

        }

    }

    public void drawHeart(Batch batch, float x, float y){
        batch.draw(_heartRegion, x, y, 1f, 1);
    }

    public void drawBackground(Batch batch){
        batch.draw(_background, 0, 0, Helpers.convertToMeters(1280), Helpers.convertToMeters(720));
    }

    public void dispose(){

    }

}
