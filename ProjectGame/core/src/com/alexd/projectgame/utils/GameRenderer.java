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
public class GameRenderer implements Disposable{

    private SpriteBatch _batch;
    private GameScreen _screen;
    private GameStateManager _gsh;
    private SpriteAnimation _runnerAnimation;
    private SpriteAnimation _enemyAnimation;
    private AtlasRegion _runnerJumpRegion;
    private AtlasRegion _enemyRegion;
    private AtlasRegion _obstacleRegion;
    private float _animationElapsed = 0f;


    public GameRenderer(GameScreen screen, GameStateManager gsh){
        _batch = new SpriteBatch();
        _screen = screen;
        _gsh = gsh;


        // Animation with textureatlas test
        _runnerAnimation = AssetsManager.getAnimation("player");
        _enemyRegion = AssetsManager.getAtlasRegion("enemy");
        _obstacleRegion = AssetsManager.getAtlasRegion("obstacle");
        _runnerJumpRegion = AssetsManager.getAtlasRegion("playerjump");
        _enemyAnimation = AssetsManager.getAnimation("enemy");

    }


    public void render(Matrix4 projectionMatrix, float delta){
        _batch.setProjectionMatrix(projectionMatrix);

        if (_gsh.getState() == GameState.RUNNING){
            _animationElapsed += delta ;
        }


        float runnerX = _screen.getRunner().getBody().getWorldCenter().x -
                Helpers.convertToMeters((float)_runnerAnimation.getKeyFrame(_animationElapsed, true).getRegionWidth())/ 2 ;
        float runnerY = _screen.getRunner().getBody().getWorldCenter().y -
                Helpers.convertToMeters((float)_runnerAnimation.getKeyFrame(_animationElapsed, true).getRegionHeight()) / 2;


        _batch.begin();
        if (!_screen.getRunner().getIsJumping()){

            _runnerAnimation.draw(_animationElapsed, _batch, runnerX, runnerY);

            /*_batch.draw(_animation.getKeyFrame(_animationElapsed, true), x, y,
                    Helpers.convertToMeters((float)_animation.getKeyFrame(_animationElapsed, true).getRegionWidth()),
                    Helpers.convertToMeters((float)_animation.getKeyFrame(_animationElapsed, true).getRegionHeight()));*/

        }
        else{
            //jumping just using one of animation sprites
            // TODO: FIX new sprite for jumping

            _batch.draw(_runnerJumpRegion,
                    _screen.getRunner().getBody().getWorldCenter().x -
                            Helpers.convertToMeters((float)_runnerJumpRegion.getRegionWidth() / 2),
                    _screen.getRunner().getBody().getWorldCenter().y -
                            Helpers.convertToMeters((float)_runnerJumpRegion.getRegionHeight() / 2),
                    1.5f, 2f);
        }

        for (Enemy enemy : _screen.getEnemies()){


            _enemyAnimation.draw(_animationElapsed, _batch,enemy.getBody().getWorldCenter().x -
                    Helpers.convertToMeters((float)_enemyAnimation.getKeyFrame(_animationElapsed).getRegionWidth() / 2),
                    enemy.getBody().getWorldCenter().y - Helpers.convertToMeters((float)_enemyAnimation.getKeyFrame(_animationElapsed).getRegionHeight() / 2));


        }

        for (Obstacle obstacle : _screen.getObstacles()){
            _batch.draw(_obstacleRegion, obstacle.getBody().getWorldCenter().x - obstacle.getWidth() / 2,
                    obstacle.getBody().getWorldCenter().y - obstacle.getHeight() / 2,
                    obstacle.getWidth(), obstacle.getHeight());
        }




        _batch.end();

    }



    public void dispose(){
        _batch.dispose();
    }



}
