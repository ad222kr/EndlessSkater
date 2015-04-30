package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameObjectType;
import com.alexd.projectgame.helpers.Helpers;
import com.alexd.projectgame.helpers.PhysicsConstants;
import com.alexd.projectgame.helpers.Renderer;
import com.alexd.projectgame.gameobjects.*;
import com.alexd.projectgame.handlers.ContactHandler;
import com.alexd.projectgame.handlers.GameInputHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private final int TIME_BETWEEN_PLATFORMS = 6;
    private final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    /**
     * Vars for fixed timestep
     */
    private final float TIME_STEP = 1 / 300f;
    private float _accumulator = 0f;
    public float _score = 0;

    /**
     * Members
     */

    // Physics
    private Game _game;
    private World _world;
    private Runner _runner;
    private Array<Platform> _platforms;
    private Array<Enemy> _enemies;
    private Array<Obstacle> _obstacles;
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
        _platforms = new Array<Platform>(4);
        _enemies = new Array<Enemy>(4);
        _platforms.add(new Platform(_world, PhysicsConstants.PLATFORM_INIT_X, PhysicsConstants.PLATFORM_INIT_Y,
                PhysicsConstants.PLATFORM_INIT_WIDTH, PhysicsConstants.PLATFORM_HEIGHT));
        setEnemyPositionY(_platforms.get(0));
        spawnEnemy();
    }

    /**
     * Creates a physics-world and the runner.
     */
    public void setUpRunnerAndWorld(){
        _world = new World(WORLD_GRAVITY, true);
        _runner = new Runner(_world, PhysicsConstants.RUNNER_X, PhysicsConstants.RUNNER_Y,
                PhysicsConstants.RUNNER_WIDTH, PhysicsConstants.RUNNER_HEIGHT);
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
        _renderer = new Renderer(this);
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
    public void spawnEnemy(){
        _lastEnemySpawnTime = TimeUtils.nanoTime();
        _randomNumber = Helpers.getRandomFloat(2, 5);

        _enemies.add( new Enemy(_world, PhysicsConstants.ENEMY_X, _enemySpawnY,
                PhysicsConstants.ENEMY_WIDTH, PhysicsConstants.ENEMY_HEIGHT));
    }

    /**
     * Spawns an obstacle
     * @return - an instance of the Obstacle class
     */
    public void spawnObstacle(float x){
        _lastEnemySpawnTime = TimeUtils.nanoTime();
        _randomNumber = Helpers.getRandomFloat(2, 5);

        Obstacle obstacle = new Obstacle(_world, x, _enemySpawnY,
                PhysicsConstants.OBSTACLE_WIDTH, PhysicsConstants.OBSTACLE_HEIGHT);

        if (obstacle.getBody().getPosition().x > _platforms.get(_platforms.size - 1).getBody().getPosition().x + _platforms.get(_platforms.size - 1).getWidth() / 2){
            obstacle.getBody().setTransform(_platforms.get(_platforms.size - 1).getBody().getPosition().x + _platforms.get(_platforms.size - 1).getWidth() / 2 - obstacle.getWidth(), obstacle.getY(), 0);
        }
    }

    /**
     * Spawns a platform
     * @return - an instance of the Platform class
     */
    public void spawnPlatform(){
        float randomY = Helpers.getRandomFloat(0, 2);
        _platforms.add(new Platform(_world, 42, randomY, PhysicsConstants.PLATFORM_WIDTH,
                PhysicsConstants.PLATFORM_HEIGHT));

        // Gets a random number between the last spawned platforms left X and right X coordinates.
        // Add and subtract 5 from those to make sure obstacle is in the bounds of the platform.
        // 1 obstacle / platform feels good but maybe move this to game-loop rather?
        float obstacleX = Helpers.getRandomFloat(5 + (int) (_platforms.get(_platforms.size - 1).getBody().getPosition().x - _platforms.get(_platforms.size - 1).getWidth() / 2),
                (int) (_platforms.get(_platforms.size - 1).getBody().getPosition().x + _platforms.get(_platforms.size - 1).getWidth() / 2) - 5);
        setEnemyPositionY(_platforms.get(_platforms.size - 1));
        if (Helpers.getRandomInt(0, 3) <= 1){
            spawnObstacle(obstacleX);
        }

    }

    /**
     * Sets the y-position for enemies & obstacles according to the
     * latest spawned platform
     * @param platform - platform to use for y-positioning
     */
    public void setEnemyPositionY(Platform platform){
        _enemySpawnY = platform.getBody().getPosition().y + platform.getHeight() / 2 + PhysicsConstants.ENEMY_HEIGHT / 2;
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
        Gdx.gl.glClearColor(1,1,0.5f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.app.log("Mem usage", ""+((Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1000000));

        _score += delta * 5;

        // Checks against last time an enemy spawned

        if ((TimeUtils.nanoTime() - _lastEnemySpawnTime) / 1000000000 > _randomNumber){

            spawnEnemy();

        }

        _timeSinceLastPlatform += delta;
        if(_timeSinceLastPlatform > TIME_BETWEEN_PLATFORMS){
            spawnPlatform();
            _timeSinceLastPlatform = 0;
        }

        doStep(delta);

        _renderer.render(_camera.combined, delta);
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
                Gdx.app.log("Enemies size: ", ""+_enemies.size);
                Gdx.app.log("Platforms size: ", ""+_platforms.size);
                if (((GameObject)body.getUserData()).isExpectedType(GameObjectType.ENEMY)){
                    _enemies.removeValue((Enemy)body.getUserData(), false);
                }
                else if(((GameObject)body.getUserData()).isExpectedType(GameObjectType.GROUND)){
                    _platforms.removeValue((Platform)body.getUserData(), false);
                }

                _world.destroyBody(body);

            }
        }
    }


    public Runner getRunner(){
        return _runner;
    }

    public Array<Platform> getPlatforms(){
        return _platforms;
    }

    public World getWorld(){
        return _world;
    }

}
