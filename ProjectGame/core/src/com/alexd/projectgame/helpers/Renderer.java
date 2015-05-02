package com.alexd.projectgame.helpers;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.gameobjects.Enemy;
import com.alexd.projectgame.gameobjects.Obstacle;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

/**
 * Class that will render the game objects. The UI-rendering will be taken
 * care of by Scene2D
 */
public class Renderer implements Disposable{

    private SpriteBatch _batch;
    private GameScreen _screen;
    private Animation _animation;
    private AtlasRegion _enemyRegion;
    private AtlasRegion _obstacleRegion;
    private float _animationElapsed = 0f;


    public Renderer(GameScreen screen){
        _batch = new SpriteBatch();
        _screen = screen;

        // Animation with textureatlas test
        _animation = AssetsManager.getAnimation("runner");
        _enemyRegion = AssetsManager.getAtlasRegion("enemy");
        _obstacleRegion = AssetsManager.getAtlasRegion("obstacle");

    }


    public void render(Matrix4 projectionMatrix, float delta){
        _batch.setProjectionMatrix(projectionMatrix);

        if (_screen.getGameState() == GameState.RUNNING){
            _animationElapsed += delta;
        }


        float x = (_screen.getRunner().getBody().getWorldCenter().x - _screen.getRunner().getWidth() / 2);
        float y = (_screen.getRunner().getBody().getWorldCenter().y - _screen.getRunner().getHeight() / 2);


        _batch.begin();
        _batch.draw(_animation.getKeyFrame(_animationElapsed, true), x, y, 1.5f, 2f);

        for (Enemy enemy : _screen.getEnemies()){
            _batch.draw(_enemyRegion, enemy.getBody().getWorldCenter().x - enemy.getWidth() / 2,
                    enemy.getBody().getWorldCenter().y - enemy.getHeight() / 2, enemy.getWidth(), enemy.getHeight());
        }

        for (Obstacle obstacle : _screen.getObstacles()){
            _batch.draw(_obstacleRegion, obstacle.getBody().getWorldCenter().x - obstacle.getWidth() / 2,
                    obstacle.getBody().getWorldCenter().y - obstacle.getHeight() / 2, obstacle.getWidth(), obstacle.getHeight());
        }

        _batch.end();

    }

    public void dispose(){
        _batch.dispose();
    }



}
