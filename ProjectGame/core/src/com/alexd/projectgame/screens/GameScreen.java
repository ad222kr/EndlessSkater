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
    private final int TIME_BETWEEN_PLATFORMS = 7;
    private final Vector2 WORLD_GRAVITY = new Vector2(0, -10);


    /**
     * Vars for fixed timestep
     */
    private final float TIME_STEP = 1 / 300f;
    private float _accumulator = 0f;

    /**
     * Members
     */

    // Physics
    private Game _game;
    private World _world;
    private Runner _runner;
    private Array<Body> _bodies;

    // Cam & rendering
    private OrthographicCamera _camera;
    private Viewport _viewport;
    private Box2DDebugRenderer _debugRenderer;
    private Renderer _renderer;

    // Misc
    private long _lastEnemySpawnTime;
    private float _timeSinceLastPlatform;
    private float _randomNumber;
    private float _enemySpawnY;

    /**
     * Constructors
     * @param game - Instance of the TheGame class
     */
    public GameScreen(Game game) {
        _game = game;

        setUpRunnerAndWorld();
        setupCamera();
        setUpHandlers();

        initiate();
    }

    public GameScreen(){
        _world = new World(WORLD_GRAVITY, true);
    }


    /**
     * Initiates the game. Spawns the first platforms and enemy
     */
    public void initiate(){
        Platform initialPlatform = new Platform(_world);
        initialPlatform.initiate();
        setEnemyPositionY(initialPlatform);
        spawnEnemy();
    }

    /**
     * Creates a physics-world and the runner.
     */
    public void setUpRunnerAndWorld(){
        _world = new World(WORLD_GRAVITY, true);
        _runner = new Runner(_world);
        _bodies = new Array<Body>();
    }

    /**
     * Sets up the handles for contact and input
     */
    public void setUpHandlers(){
        Gdx.input.setInputProcessor(new GameInputHandler(_runner));
        _world.setContactListener(new ContactHandler(_runner));
    }

    /**
     * Sets up rendering
     */
    public void setUpRendering(){
        _renderer = new Renderer(_world, _runner);
        _debugRenderer = new Box2DDebugRenderer();
    }

    /**
     * Sets up the camera and the viewport
     */
    public void setupCamera(){
        _camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        _viewport = new StretchViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, _camera);

        _viewport.apply();
        _camera.position.set(_camera.viewportWidth / 2, _camera.viewportHeight / 2, 0f);
        _camera.update();

    }


    /**
     * Spawns an enemy
     * @return - and instance of the Enemy class
     */
    public Enemy spawnEnemy(){
        _lastEnemySpawnTime = TimeUtils.nanoTime();
        _randomNumber = Helpers.getRandomFloat(1, 4);

        return new Enemy(_world, _enemySpawnY);
    }

    /**
     * Spawns an obstacle
     * @return - an instance of the Obstacle class
     */
    public Obstacle spawnObstacle(){
        _lastEnemySpawnTime = TimeUtils.nanoTime();
        _randomNumber = Helpers.getRandomFloat(1, 4);

        return new Obstacle(_world, _enemySpawnY);
    }

    /**
     * Spawns a platform
     * @return - an instance of the Platform class
     */
    public Platform spawnPlatform(){
        Platform platform = new Platform(_world);

        platform.initiate(42, 0);
        setEnemyPositionY(platform);

        return platform;
    }

    /**
     * Sets the y-position for enemies & obstacles according to the
     * latest spawned platform
     * @param platform - platform to use for y-positioning
     */
    public void setEnemyPositionY(Platform platform){
        _enemySpawnY = platform.getBody().getPosition().y + platform.getHeight() / 2 + Enemy.HEIGHT / 2;
    }

    /**
     * Called when the screen is first shown, set's up rendering
     */
    @Override
    public void show() {
        setUpRendering();
    }

    /**
     * The main "game-loop". Called every .25 seconds (i think)
     * All rendering and logig-method calls happens here. This method
     * also calls the doStep method
     * @param delta - The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        // Checks against last time an enemy spawned
        if ((TimeUtils.nanoTime() - _lastEnemySpawnTime) / 1000000000 > _randomNumber){
            // Random chance for spawning enemy or obstacle
            int random = Helpers.getRandomInt(1, 5);

            if (random > 2){
                spawnEnemy();
            }
            else {
                spawnObstacle();
            }
        }

        _timeSinceLastPlatform += delta;
        if(_timeSinceLastPlatform > TIME_BETWEEN_PLATFORMS){
            spawnPlatform();
            _timeSinceLastPlatform = 0;
        }



        doStep(delta);
        _renderer.render(_camera.combined);
        _debugRenderer.render(_world, _camera.combined);

        if(_runner.getHealth() == 0 || Helpers.isBodyOutOfBounds(_runner.getBody())){
            _game.setScreen(new GameOverScreen(_game));
            // So it doesn't destroy bodies after new screen is set. Don't know why it's called but
            // if no return the app crashes...
            return;
        }
        destroyBodies();
    }

    /**
     * Called when the window is resized, updates the viewport
     * and set's camera position according to that
     * @param width - the new width
     * @param height - the new height
     */
    @Override
    public void resize(int width, int height) {
        _viewport.update(width, height);
        _camera.position.set(_camera.viewportWidth / 2, _camera.viewportHeight / 2, 0);
    }

    /**
     * Called when the application is paused
     * (home-button pressed in android etc)
     */
    @Override
    public void pause() {
        //TODO: Save the states of the game here?
    }

    /**
     * Called when the application is resumed
     */
    @Override
    public void resume() {
        //TODO: Recreate from states here?
    }

    /**
     * Called when the Screen is destroyed. Important
     * to clean up resources here
     */
    @Override
    public void hide() {
        dispose();
    }


    /**
     * This is NOT called automatically. Clean up of
     * resources that the GC won't handle
     */
    @Override
    public void dispose() {
        _world.dispose();
        _renderer.dispose();
        _debugRenderer.dispose();
    }

    /**
     * Advances the physics-world by a fixed timestep
     * @param delta - The time in seconds since the last render.
     */
    public void doStep(float delta) {

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

    /**
     * Destroys the physics-bodies that have gone out of bounds.
     */
    public void destroyBodies(){

        _world.getBodies(_bodies);

        for(Body body : _bodies){
            if(Helpers.isBodyOutOfBounds(body)){
                _world.destroyBody(body);
            }
        }
    }
}
