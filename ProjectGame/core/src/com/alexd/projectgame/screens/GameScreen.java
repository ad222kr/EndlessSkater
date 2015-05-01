package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.helpers.Helpers;
import com.alexd.projectgame.helpers.PhysicsConstants;
import com.alexd.projectgame.helpers.Renderer;
import com.alexd.projectgame.gameobjects.*;
import com.alexd.projectgame.handlers.ContactHandler;
import com.alexd.projectgame.handlers.GameInputHandler;
import com.alexd.projectgame.stages.gamehud.GameHudStage;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.*;

/**
 * Created by Alex on 2015-04-07.
 */
public class GameScreen implements Screen {

    private boolean isDebug = true; // Set to false to hide debugrender
    /**
     * Constants
     */
    private final int VIEWPORT_WIDTH = Helpers.convertToMeters(TheGame.APP_WIDTH);
    private final int VIEWPORT_HEIGHT = Helpers.convertToMeters(TheGame.APP_HEIGHT);
    private final int TIME_BETWEEN_PLATFORMS = 7;
    private final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    private final float TIME_STEP = 1f / 60f;


    FPSLogger fpsLogger;

    /**
     * Members
     */

    // Physics
    private Game _game;
    private World _world;
    private Runner _runner;
    private Array<Obstacle> _obstacles;
    private Array<Platform> _platforms;
    private Array<Enemy> _enemies;
    private Array<Body> _bodies;

    // Cam & rendering
    private OrthographicCamera _camera;
    private Viewport _viewport;
    private Box2DDebugRenderer _debugRenderer;
    private Renderer _renderer;
    private GameHudStage _gameHudStage;

    // Misc
    private GameState _gameState;
    private long _lastEnemySpawnTime;
    private float _timeSinceLastPlatform;
    private float _randomNumber;
    private float _enemySpawnY;
    private float _score;

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
        _gameState = GameState.RUNNING;
        _platforms = new Array<Platform>(4);
        _enemies = new Array<Enemy>(4);
        _obstacles = new Array<Obstacle>(4);
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
        _gameHudStage = new GameHudStage(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_WIDTH), this);
        if (isDebug){
            _debugRenderer = new Box2DDebugRenderer();
        }
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
     */
    public void spawnEnemy(){
        _lastEnemySpawnTime = TimeUtils.nanoTime();
        _randomNumber = Helpers.getRandomFloat(2, 5);

        _enemies.add(new Enemy(_world, PhysicsConstants.ENEMY_X, _enemySpawnY,
                PhysicsConstants.ENEMY_WIDTH, PhysicsConstants.ENEMY_HEIGHT));


    }

    /**
     * Spawns an obstacle
     */
    public void spawnObstacle(){
        _lastEnemySpawnTime = TimeUtils.nanoTime();
        _randomNumber = Helpers.getRandomFloat(2, 5);
        float x = getObstacleX();

        Obstacle obstacle = new Obstacle(_world, x, _enemySpawnY, PhysicsConstants.OBSTACLE_WIDTH, PhysicsConstants.OBSTACLE_HEIGHT);
        Platform platform = _platforms.get(_platforms.size - 1);

        fixObstaclePosition(obstacle, platform);

        _obstacles.add(obstacle);
    }

    /**
     * Spawns a platform.
     */
    public void spawnPlatform(){

        _platforms.add(new Platform(_world, 42, Helpers.getRandomFloat(0, 2), PhysicsConstants.PLATFORM_WIDTH,
                PhysicsConstants.PLATFORM_HEIGHT));

        setEnemyPositionY(_platforms.get(_platforms.size - 1));
        if (Helpers.getRandomInt(0, 5) <= 1){
            spawnObstacle();
        }

    }

    /**
     * Fixes the obstacle position. In other words, makes sure that it's
     * withing the bounds of the platform it's on, preventing floating
     * obstacles.
     * @param obstacle - obstacle to apply fix to
     * @param platform - platform which the obstacle resides on
     */
    public void fixObstaclePosition(Obstacle obstacle, Platform platform){
        if (obstacle.getBody().getPosition().x > platform.getBody().getPosition().x + platform.getWidth() / 2){

            obstacle.getBody().setTransform(platform.getBody().getPosition().x +
                    platform.getWidth() / 2 - obstacle.getWidth(), obstacle.getY(), 0);
        }
    }



    /**
     *  Gets a random number between the last spawned platforms left X and right X coordinates.
        Add and subtract 5 from those to make sure obstacle is in the bounds of the platform.
     * @return - the X-position for the obstacle
     */
    public float getObstacleX(){

        int min = (int)(_platforms.get(_platforms.size - 1).getBody().getPosition().x -
                        _platforms.get(_platforms.size - 1).getWidth() / 2) + 5;
        int max = (int)(_platforms.get(_platforms.size - 1).getBody().getPosition().x +
                        _platforms.get(_platforms.size - 1).getWidth() / 2) - 5;


        return  Helpers.getRandomFloat(min, max);

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
        Gdx.gl.glClearColor(1, 1, 0.5f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.app.log("Mem usage", "" + ((Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1000000));
        switch (_gameState){
            case RUNNING:

                _score += delta * 5;
                _timeSinceLastPlatform += delta;

                if (isTimeForEnemySpawn()){
                    spawnEnemy();

                }
                if(isTimeForPlatformSpawn()){
                    spawnPlatform();
                    _timeSinceLastPlatform = 0;
                }
                if(isGameOver()){
                    _game.setScreen(new GameOverScreen(_game));
                    return;
                }

                _renderer.render(_camera.combined, delta);
                _gameHudStage.act(delta);
                _gameHudStage.draw();
                _debugRenderer.render(_world, _camera.combined);

                destroyBodies();
                doStep(delta);

                break;
            case PAUSED:
                // Pause menu logic and rendering here
                break;
        }
    }

    private boolean isGameOver() {
        return _runner.getHealth() == 0 || Helpers.isBodyOutOfBounds(_runner.getBody());
    }

    private boolean isTimeForPlatformSpawn() {
        return _timeSinceLastPlatform > TIME_BETWEEN_PLATFORMS;
    }

    private boolean isTimeForEnemySpawn() {
        return (TimeUtils.nanoTime() - _lastEnemySpawnTime) / 1000000000 > _randomNumber;
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
        _gameHudStage.dispose();

    }

    /**
     * Advances the physics-world by a fixed timestep
     * @param delta - The time in seconds since the last render.
     */
    public void doStep(float delta) {

        // Stepping the physics-simulation, see https://github.com/libgdx/libgdx/wiki/Box2d#stepping-the-simulation
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        // stutters a little w/o interpolation...

        /*
            FIXED TIME STEP
        float frameTime = Math.min(delta, 0.25f);
        _accumulator += frameTime;
        while (_accumulator >= TIME_STEP){
            _world.step(TIME_STEP, 6, 2);
            _accumulator -= TIME_STEP;

        }*/

        _world.step(TIME_STEP, 6, 2);
    }

    /**
     * Destroys the physics-bodies that have gone out of bounds.
     * Removes the gameobj connected to body from it's list as well.
     */
    public void destroyBodies(){

        GameObject obj;

        _world.getBodies(_bodies);

        for(Body body : _bodies){
            if(Helpers.isBodyOutOfBounds(body)){
                obj = ((GameObject)body.getUserData());

                switch (obj.getGameObjectType()){
                    case  ENEMY:
                        _enemies.removeValue((Enemy)obj, false);
                        break;
                    case OBSTACLE:
                        _obstacles.removeValue((Obstacle)obj, false);
                        break;
                    case GROUND:
                        _platforms.removeValue((Platform)obj, false);
                        break;

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

    public Array<Enemy> getEnemies(){
        return _enemies;
    }

    public Array<Obstacle> getObstacles(){
        return _obstacles;
    }

    public World getWorld(){
        return _world;
    }

    public Game getGame(){
        return _game;
    }

    public float getScore(){
        return _score;
    }

}
