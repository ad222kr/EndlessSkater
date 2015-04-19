package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.helpers.GameObjectType;
import com.alexd.projectgame.helpers.Renderer;
import com.alexd.projectgame.model.*;
import com.alexd.projectgame.handlers.ContactHandler;
import com.alexd.projectgame.handlers.GameInputHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 2015-04-07.
 */
public class GameScreen implements Screen {

    public static final int PIXELS_TO_METERS = 50;
    private static final int VIEWPORT_WIDTH = TheGame.APP_WIDTH / PIXELS_TO_METERS;
    private final int VIEWPORT_HEIGHT = TheGame.APP_HEIGHT / PIXELS_TO_METERS;

    private final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private Game game;
    private World world;
    private Ground ground;
    private Runner runner;


    private OrthographicCamera camera;
    private Viewport viewport;
    private Box2DDebugRenderer debugRenderer;
    private Renderer renderer;

    private long lastEnemySpawnTime;
    private float randomNumber;
    private GameObjectType[] enemies;



    public GameScreen(Game game) {

        // TODO: Refactor?
        this.game = game;
        world = new World(WORLD_GRAVITY, true);
        ground = new Ground(world);
        runner = new Runner(world);
        debugRenderer = new Box2DDebugRenderer();

        setupCamera();
        renderer = new Renderer(world, runner);

        Gdx.input.setInputProcessor(new GameInputHandler(runner));
        world.setContactListener(new ContactHandler(runner));
        enemies = new GameObjectType[]{ GameObjectType.ENEMY, GameObjectType.OBSTACLE };

        spawnEnemy();






    }


    public GameScreen(){
        world = new World(WORLD_GRAVITY, true);
    }

    public void setupCamera(){
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        //viewport = new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }



    public GameObject spawnEnemy(){
        lastEnemySpawnTime = TimeUtils.nanoTime();

        GameObjectType type = enemies[new Random().nextInt(enemies.length)];
        GameObject retObj = null;

        switch (type){
            case ENEMY:
                retObj = new Enemy(world);
                break;
            case OBSTACLE:
                retObj = new Obstacle(world);
                break;

        }

        return retObj;

    }

    @Override
    public void show() {

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        doStep(delta);

        renderer.render(camera.combined);
        debugRenderer.render(world, camera.combined);





        if (TimeUtils.nanoTime() - lastEnemySpawnTime > 2000000000){
            spawnEnemy();

        }

        if(runner.getHealth() == 0){

            game.setScreen(new GameOverScreen(game));
        }







    }



    @Override
    public void resize(int width, int height) {
        //viewport.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        renderer.dispose();
        debugRenderer.dispose();

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
