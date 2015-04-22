package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.Helpers;
import com.alexd.projectgame.helpers.Renderer;
import com.alexd.projectgame.gameobjects.*;
import com.alexd.projectgame.handlers.ContactHandler;
import com.alexd.projectgame.handlers.GameInputHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.*;


import java.util.Random;

/**
 * Created by Alex on 2015-04-07.
 */
public class GameScreen implements Screen {
    /**
     * Constants
     */
    private final int VIEWPORT_WIDTH = Helpers.convertToMeters(TheGame.APP_WIDTH);
    private final int VIEWPORT_HEIGHT = Helpers.convertToMeters(TheGame.APP_HEIGHT);
    private final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    private final float TIME_STEP = 1 / 300f;

    /**
     * Members
     */
    private float _accumulator = 0f;

    private Game _game;
    private World _world;
    private Ground _ground;
    private Runner _runner;

    private OrthographicCamera _camera;
    private Viewport _viewport;
    private Box2DDebugRenderer _debugRenderer;
    private Renderer _renderer;

    private long _lastEnemySpawnTime;
    private float _randomNumber;
    private Array<Body> _bodies;

    public GameScreen(Game game) {

        // TODO: Refactor?
        _game = game;
        setUpPhysicsWorld();
        setupCamera();
        setUpRendering();
        setUpHandlers();
        spawnEnemy();

    }

    public GameScreen(){
        _world = new World(WORLD_GRAVITY, true);
    }

    public void setUpPhysicsWorld(){
        _world = new World(WORLD_GRAVITY, true);
        _ground = new Ground(_world);
        _runner = new Runner(_world);
        _bodies = new Array<Body>();

    }

    public void setUpHandlers(){
        Gdx.input.setInputProcessor(new GameInputHandler(_runner));
        _world.setContactListener(new ContactHandler(_runner));
    }

    public void setUpRendering(){
        _renderer = new Renderer(_world, _runner);
        _debugRenderer = new Box2DDebugRenderer();
    }

    public void setupCamera(){
        _camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        _viewport = new StretchViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, _camera);

        _viewport.apply();
        _camera.position.set(_camera.viewportWidth / 2, _camera.viewportHeight / 2, 0f);
        _camera.update();

    }



    public GameObject spawnEnemy(){
        float spawnChance = Helpers.getRandomInt(1, 5);
        _lastEnemySpawnTime = TimeUtils.nanoTime();
        _randomNumber = Helpers.getRandomFloat(1, 4);
        Gdx.app.log("Random number:", ""+spawnChance);

        if (spawnChance < 3){
            return new Enemy(_world);
        }
        else {
            return new Obstacle(_world);
        }
    }

    @Override
    public void show() {

    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        doStep(delta);
        _renderer.render(_camera.combined);
        _debugRenderer.render(_world, _camera.combined);

        if (_runner.getIsJumping() && !_runner.isFalling()){

            _runner.incrementTimeSinceJumpt(delta);
        }




        if(_runner.getHealth() == 0){
            _game.setScreen(new GameOverScreen(_game));
        }
    }

    @Override
    public void resize(int width, int height) {
        _viewport.update(width, height);
        _camera.position.set(_camera.viewportWidth / 2, _camera.viewportHeight / 2, 0);

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
        _world.dispose();
        _renderer.dispose();
        _debugRenderer.dispose();

    }

    public void doStep(float delta) {

        // here or render(gameloop)?
        if ((TimeUtils.nanoTime() - _lastEnemySpawnTime) / 1000000000 > _randomNumber){
            spawnEnemy();
        }

        // if bodies are out of bounds destroy them
        destroyBodies();

        // Stepping the physics-simulation, see https://github.com/libgdx/libgdx/wiki/Box2d#stepping-the-simulation
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);
        _accumulator += frameTime;
        while (_accumulator >= TIME_STEP) {
            _world.step(TIME_STEP, 6, 2);
            _accumulator -= TIME_STEP;
        }

    }

    public void destroyBodies(){
        _world.getBodies(_bodies);

        for(Body body : _bodies){
            if(body.getPosition().x < -1){
                _world.destroyBody(body);
            }


        }
    }


}
