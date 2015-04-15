package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.model.Enemy;
import com.alexd.projectgame.model.Ground;
import com.alexd.projectgame.model.Runner;
import com.alexd.projectgame.handlers.ContactHandler;
import com.alexd.projectgame.handlers.GameInputHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;


import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 2015-04-07.
 */
public class GameScreen implements Screen {

    private final int VIEWPORT_WIDTH = TheGame.APP_WIDTH / 50;
    private final int VIEWPORT_HEIGHT = TheGame.APP_HEIGHT / 50;

    private final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private Game game;
    private World world;
    private Ground ground;
    private Runner runner;


    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private long lastEnemySpawnTime;
    private float randomNumber;


    public GameScreen(Game game) {
        this.game = game;
        world = new World(WORLD_GRAVITY, true);
        ground = new Ground(world);
        runner = new Runner(world);

        spawnEnemy();


        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();

        Gdx.input.setInputProcessor(new GameInputHandler(runner));
        world.setContactListener(new ContactHandler(runner));
    }

    public Enemy spawnEnemy(){
        lastEnemySpawnTime = TimeUtils.nanoTime();

        // Random number for next enemy-spawn
        Random random = new Random();
        randomNumber = random.nextFloat() * ((5 - 1) + 1);
        return new Enemy(world);
    }

    @Override
    public void show() {

    }



    @Override
    public void render(float delta) {


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.render(world, camera.combined);





        if (TimeUtils.nanoTime() - lastEnemySpawnTime > 1500000000){
            spawnEnemy();

        }

        if(runner.getHealth() == 0){
            game.setScreen(new GameOverScreen(game));
        }


        doStep(delta);




    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        this.dispose();

    }

    public void doStep(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

    }

}
