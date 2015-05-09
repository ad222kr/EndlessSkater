package com.alexd.projectgame.utils;

import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.entities.Enemy;
import com.alexd.projectgame.entities.Obstacle;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

/**
 * Class that will render the game objects. The UI-rendering will be taken
 * care of by Scene2D
 */
public class GameRenderer {

    private SpriteAnimation _runnerAnimation;
    private SpriteAnimation _enemyAnimation;
    private AtlasRegion _runnerJumpRegion;
    private AtlasRegion _obstacleRegion;
    private float _animationElapsed = 0f;


    public GameRenderer(){
        // Animation with textureatlas test
        _runnerAnimation = AssetsManager.getAnimation("player");
        _obstacleRegion = AssetsManager.getAtlasRegion("obstacle");
        _runnerJumpRegion = AssetsManager.getAtlasRegion("playerjump");
        _enemyAnimation = AssetsManager.getAnimation("enemy");

    }

    public void updateAnimation(float delta){
        _animationElapsed += delta;
    }

    public void drawRunner(Batch batch, float x, float y, boolean isJumping){
        if (!isJumping){
            _runnerAnimation.draw(_animationElapsed, batch, x, y);
        }
        else {
            batch.draw(_runnerJumpRegion, x, y, Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT);
        }
    }

    public void drawEnemy(Batch batch, float x, float y){
        _enemyAnimation.draw(_animationElapsed, batch,  x, y);
    }

    public void drawObstacle(Batch batch, float x, float y){
        batch.draw(_obstacleRegion, x, y, Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);
    }

    public void dispose(){

    }

}
