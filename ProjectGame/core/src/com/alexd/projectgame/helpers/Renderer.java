package com.alexd.projectgame.helpers;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameobjects.Runner;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Alex on 2015-04-18. Placeholder for the class that will render everything later
 * Will probably use Scene2D for rendering the UI later.
 */
public class Renderer implements Disposable{

    private SpriteBatch _batch;
    private Sprite _runnerSprite;
    private Texture _img;
    private World _world;
    private Runner _runner;
    private BitmapFont _font;

    public Renderer(World world, Runner runner){
        _world = world;
        _runner = runner;
        _batch = new SpriteBatch();
        _img = new Texture("badlogic.jpg");
        _runnerSprite = new Sprite(_img);
        _font = new BitmapFont();
        _font.setColor(Color.RED);

    }


    public void render(Matrix4 projectionMatrix){

        _batch.setProjectionMatrix(projectionMatrix);
        _runnerSprite.setX((_runner.getBody().getWorldCenter().x - Runner.WIDTH / 2));
        _runnerSprite.setY((_runner.getBody().getWorldCenter().y - Runner.WIDTH / 2));

        _font.setScale(0.2f, 0.2f);

        _runnerSprite.setOrigin(_runnerSprite.getWidth() / 2, _runnerSprite.getHeight() / 2);

        _batch.begin();
        _batch.draw(_runnerSprite, _runnerSprite.getX(), _runnerSprite.getY(),
                Helpers.convertToMeters(_runnerSprite.getWidth()),
                Helpers.convertToMeters(_runnerSprite.getHeight()));

        _font.draw(_batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 1, 13);

        _batch.end();

    }

    public void dispose(){
        _img.dispose();
        _batch.dispose();
    }



}
