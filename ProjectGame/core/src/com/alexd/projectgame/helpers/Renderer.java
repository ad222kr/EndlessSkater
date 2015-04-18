package com.alexd.projectgame.helpers;

import com.alexd.projectgame.model.Enemy;
import com.alexd.projectgame.model.Runner;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 2015-04-18.
 */
public class Renderer {
    private SpriteBatch batch;

    private Sprite runnerSprite;
    private Texture img;
    private World world;
    private Runner runner;
    private OrthographicCamera camera;


    public Renderer(World world, Runner runner){
        this.world = world;
        this.runner = runner;



        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

        runnerSprite = new Sprite(img);




    }

    public void render(Matrix4 projectionMatrix){


        runnerSprite.setPosition((runner.getBody().getWorldCenter().x * GameScreen.PIXELS_TO_METERS) - runnerSprite.getWidth() / 2,
                (runner.getBody().getWorldCenter().y * GameScreen.PIXELS_TO_METERS) - runnerSprite.getHeight() / 2);



        batch.begin();
        batch.draw(runnerSprite, runnerSprite.getX(), runnerSprite.getY());
        batch.end();

    }

    public void dispose(){
        img.dispose();
        batch.dispose();
    }
}
