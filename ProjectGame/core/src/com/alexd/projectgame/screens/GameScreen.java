package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.utils.GameStateManager;
import com.alexd.projectgame.utils.Helpers;
import com.alexd.projectgame.utils.PhysicsConstants;
import com.alexd.projectgame.utils.GameRenderer;
import com.alexd.projectgame.entities.*;
import com.alexd.projectgame.handlers.ContactHandler;
import com.alexd.projectgame.handlers.GameInputHandler;
import com.alexd.projectgame.gameinterface.gamehud.GameHudStage;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.viewport.*;

/**
 * Created by Alex on 2015-04-07.
 */
public class GameScreen implements Screen {


    private boolean isDebug = true; // Set to false to hide debugrender

    private final int VIEWPORT_WIDTH = Helpers.convertToMeters(TheGame.APP_WIDTH);
    private final int VIEWPORT_HEIGHT = Helpers.convertToMeters(TheGame.APP_HEIGHT);


    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0;

    private Game _game;
    private World _world;
    private Runner _runner;
    private Array<Obstacle> _obstacles;
    private Array<Platform> _platforms;
    private Array<Enemy> _enemies;
    private Array<Body> _bodies;


    private OrthographicCamera _camera;
    private Viewport _viewport;
    private Box2DDebugRenderer _debugRenderer;
    private GameRenderer _renderer;
    private GameHudStage _gameHudStage;

    private GameStateManager _gsm;
    private float _lastEnemySpawnTime;
    private float _timeBetweenEnemies;

    public GameScreen(Game game) {
        _game = game;
        setUp();
        initiate();
    }

    private void initiate(){
        _gsm.setState(GameState.RUNNING);

        _platforms.add(new Platform(_world, PhysicsConstants.PLATFORM_INIT_X, PhysicsConstants.PLATFORM_INIT_Y,
                PhysicsConstants.PLATFORM_INIT_WIDTH, PhysicsConstants.PLATFORM_HEIGHT));

        _lastEnemySpawnTime = 0;
        _timeBetweenEnemies = 5;
    }

    private void setUp(){
         _gsm = new GameStateManager();

        // Physics & entities
        _world = new World(PhysicsConstants.WORLD_GRAVITY, true);
        _runner = new Runner(_world, PhysicsConstants.RUNNER_X, PhysicsConstants.RUNNER_Y,
                PhysicsConstants.RUNNER_WIDTH, PhysicsConstants.RUNNER_HEIGHT);
        _bodies = new Array<Body>();
        _platforms = new Array<Platform>(4);
        _enemies = new Array<Enemy>(4);
        _obstacles = new Array<Obstacle>(4);

        // Rendering
        _renderer = new GameRenderer(this, _gsm);
        if (isDebug){
            _debugRenderer = new Box2DDebugRenderer();
        }

        // Cam and HUD
        _camera = new OrthographicCamera();
        _viewport = new StretchViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, _camera);
        _viewport.apply();
        _camera.position.set(_camera.viewportWidth / 2, _camera.viewportHeight / 2, 0f);
        _camera.update();
        _gameHudStage = new GameHudStage(this, _gsm);

        // Input & Contact
        InputProcessor gameInputProcessor = new GameInputHandler(_runner, _gsm);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(_gameHudStage);
        inputMultiplexer.addProcessor(gameInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
        _world.setContactListener(new ContactHandler(_runner));
    }

    private void spawnEnemy(){
        _timeBetweenEnemies = Helpers.getRandomFloat(2, 5);
        float y = getCorrectYPos(true);

        _enemies.add(new Enemy(_world, PhysicsConstants.ENEMY_X, y,
                PhysicsConstants.ENEMY_WIDTH, PhysicsConstants.ENEMY_HEIGHT));
    }

    private void spawnObstacle(){
        float x = getCorrectXPos();
        float y = getCorrectYPos(false);

        _obstacles.add(new Obstacle(_world, x, y, PhysicsConstants.OBSTACLE_WIDTH,
                PhysicsConstants.OBSTACLE_HEIGHT));
    }

    private void spawnPlatform(){


        _platforms.add(new Platform(_world, 42, Helpers.getRandomFloat(0, 2), PhysicsConstants.PLATFORM_WIDTH,
                PhysicsConstants.PLATFORM_HEIGHT));


        if (Helpers.getRandomInt(0, 5 ) <= 1){
            spawnObstacle();
        }

    }

    private float getCorrectXPos(){
        // Helper for calculating obstacles X-pos to keep it inside the bounds of the
        // platform it resides on, otherwise it could be floating in the air
        int min = (int)(getCurrentPlatform().getBody().getPosition().x -
                        getCurrentPlatform().getWidth() / 2) + 5;
        int max = (int)(getCurrentPlatform().getBody().getPosition().x +
                        getCurrentPlatform().getWidth() / 2) - 5;


        return  Helpers.getRandomFloat(min, max);

    }

    private float getCorrectYPos(boolean isEnemy){
        // Helper for calculating the right Y-position for the enemies/obstacles
        // otherwise they are floating (at least obstacles since they are kinematic)
        return getCurrentPlatform().getBody().getPosition().y + getCurrentPlatform().getHeight() / 2 +
              (isEnemy ? PhysicsConstants.ENEMY_HEIGHT : PhysicsConstants.OBSTACLE_HEIGHT) / 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Main game loop
        Gdx.gl.glClearColor(1, 1, 0.5f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        draw(delta);


        Gdx.app.log("Mem usage", "" + ((Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1000000));
        switch (_gsm.getState()){
            case RUNNING:
                _lastEnemySpawnTime += delta;

                if (isTimeForEnemySpawn()){
                    spawnEnemy();
                    _lastEnemySpawnTime = 0;
                }
                if(isTimeForPlatformSpawn()){
                    spawnPlatform();
                }
                if(isGameOver()){
                    _game.setScreen(new MainMenuScreen(_game));
                    return;
                }
                destroyBodies();
                _gameHudStage.act(delta);
                doStep(delta);
                break;
            case PAUSED:
                // Pause menu logic and rendering here
                break;
        }




    }



    private void singleStep(float dt){

    }

    private void doStep(float delta) {
        // Advances the physics world by a fixed timestep

        accumulator += delta;
        while (accumulator >= TIME_STEP * 2){
            _world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
        // Step it again with whats left of the accumulator, this prevents
        // the stuttering
        _world.step(accumulator, 6, 2);
        accumulator = 0;

        //_world.step(1/60f, 8, 3);

    }

    private void draw(float delta){
        _renderer.render(_camera.combined, delta);
        _gameHudStage.draw();
        if (isDebug ){
            _debugRenderer.render(_world, _camera.combined);
        }
    }

    private boolean isGameOver() {
        return _runner.getHealth() == 0 || Helpers.isBodyOutOfBounds(_runner.getBody());
    }

    private boolean isTimeForPlatformSpawn() {
        return (getCurrentPlatform().getBody().getPosition().x + getCurrentPlatform().getWidth() / 2) < 21;

    }

    private boolean isTimeForEnemySpawn() {
        return _lastEnemySpawnTime > _timeBetweenEnemies;
    }

    @Override
    public void resize(int width, int height) {
        _gameHudStage.getViewport().update(width, height, true); // True sets (0, 0) in bottom left corner.
        _viewport.update(width, height);
        _camera.position.set(_camera.viewportWidth / 2, _camera.viewportHeight / 2, 0);
    }

    @Override
    public void pause() {
        _gsm.setState(GameState.PAUSED);
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
        // cleans up resources not handled by the GC
        _world.dispose();
        _renderer.dispose();
        if (isDebug){
            _debugRenderer.dispose();
        }

        _gameHudStage.dispose();
    }

    private void destroyBodies(){
        _world.getBodies(_bodies);

        for(Body body : _bodies){
            if(Helpers.isBodyOutOfBounds(body)){((Entity)body.getUserData()).setFlaggedForDeath(true);}
            if(((Entity)body.getUserData()).getFlaggedForDeath())  {
                removeValueFromGameObjArray((Entity)body.getUserData());
                _world.destroyBody(body);
            }
        }
    }

    private void removeValueFromGameObjArray(Entity object){
        switch (object.getGameObjectType()){
            case  ENEMY:
                _enemies.removeValue((Enemy)object, false);
                break;
            case OBSTACLE:
                _obstacles.removeValue((Obstacle)object, false);
                break;
            case GROUND:
                _platforms.removeValue((Platform)object, false);
                break;
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

    public Platform getCurrentPlatform(){
        return _platforms.get(_platforms.size - 1);
    }


}
