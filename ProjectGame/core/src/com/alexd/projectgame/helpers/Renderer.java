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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Alex on 2015-04-18.
 */
public class Renderer implements Disposable{
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

        batch.setProjectionMatrix(projectionMatrix);
        runnerSprite.setX((runner.getBody().getWorldCenter().x - Runner.WIDTH / 2));
        runnerSprite.setY((runner.getBody().getWorldCenter().y - Runner.WIDTH / 2));


        runnerSprite.setOrigin(runnerSprite.getWidth() / 2, runnerSprite.getHeight() / 2);

        //runnerSprite.setPosition((runner.getBody().getWorldCenter().x * GameScreen.PIXELS_TO_METERS) - (Runner.WIDTH * GameScreen.PIXELS_TO_METERS / 2),
        //        (runner.getBody().getWorldCenter().y * GameScreen.PIXELS_TO_METERS) - (Runner.WIDTH * GameScreen.PIXELS_TO_METERS / 2));



        Gdx.app.log("RunnerX: ", ""+(runner.getBody().getWorldCenter().x - Runner.WIDTH / 2));
        Gdx.app.log("RunnerX: ", ""+(runner.getBody().getWorldCenter().y - Runner.HEIGHT / 2));




        batch.begin();
        batch.draw(runnerSprite, runnerSprite.getX(), runnerSprite.getY(),convertToMeters(runnerSprite.getWidth()), convertToMeters(runnerSprite.getHeight()));
        batch.end();


    }

    public void dispose(){
        img.dispose();
        batch.dispose();
    }


    public float convertToMeters(float pixels){
        return pixels / GameScreen.PIXELS_TO_METERS;
    }
}
