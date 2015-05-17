package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.utils.*;
import com.alexd.projectgame.entities.*;
import com.alexd.projectgame.handlers.ContactHandler;
import com.alexd.projectgame.handlers.GameInputHandler;
import com.alexd.projectgame.gameinterface.gamehud.GameHudStage;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.viewport.*;



/**
 * Created by Alex on 2015-04-07.
 */
public class GameScreen implements Screen {


    private boolean isDebug = false;// Set to false to hide debugrender

    private final int VIEWPORT_WIDTH = Helpers.convertToMeters(TheGame.APP_WIDTH);
    private final int VIEWPORT_HEIGHT = Helpers.convertToMeters(TheGame.APP_HEIGHT);


    private final float TIME_STEP = 1/300f;
    private float accumulator = 0;

    private Game _game;
    private World _world;
    private Runner _runner;
    private Array<Obstacle> _obstacles;
    private Array<Platform> _platforms;
    private Array<Life> _life;
    private Array<Enemy> _enemies;

    private Array<Body> _bodies;
    private EntityManager _entityManager;


    private OrthographicCamera _camera;
    private Viewport _viewport;
    private Box2DDebugRenderer _debugRenderer;
    private GameRenderer _renderer;
    private Batch _batch;
    private GameHudStage _gameHudStage;

    private float _lastEnemySpawnTime;
    private float _timeBetweenEnemies;
    private float _totalTime;
    private float _timeForDifficultyChange; // test difficulty change

    public GameScreen(Game game) {
        _game = game;
        GameManager.getInstance().setRunning();
        _entityManager = new EntityManager();
        setUp();
        initiate();
    }

    private void initiate(){

        GameManager.getInstance().resetDifficulty();
        _platforms.add(new Platform(_world, Constants.PLATFORM_INIT_X, Constants.PLATFORM_INIT_Y,
                Constants.PLATFORM_INIT_WIDTH,
                Constants.PLATFORM_HEIGHT));

        _entityManager.addEntity(_platforms.get(_platforms.size - 1)); // must refactor this later

        _lastEnemySpawnTime = 0;
        _timeBetweenEnemies = 5;
        _timeForDifficultyChange = 30;
    }

    private void setUp(){

        // Physics & entities
        _world = new World(Constants.WORLD_GRAVITY, true);
        _runner = new Runner(_world, Constants.RUNNER_X, Constants.RUNNER_Y,
                Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT);
        _entityManager.addEntity(_runner);
        _bodies = new Array<Body>();
        _platforms = new Array<Platform>(4);
        _enemies = new Array<Enemy>(4);
        _obstacles = new Array<Obstacle>(4);
        _life = new Array<Life>(4);



        // Cam and HUD
        _camera = new OrthographicCamera();
        _viewport = new StretchViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, _camera);
        _viewport.apply();
        _camera.position.set(_camera.viewportWidth / 2, _camera.viewportHeight / 2, 0f);
        _camera.update();
        _gameHudStage = new GameHudStage(this);

        // Rendering
        _renderer = new GameRenderer();
        _batch = new SpriteBatch();
        _batch.setProjectionMatrix(_camera.combined);
        if (isDebug){
            _debugRenderer = new Box2DDebugRenderer();
        }

        // Input & Contact
        InputProcessor gameInputProcessor = new GameInputHandler(_runner);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(_gameHudStage);
        inputMultiplexer.addProcessor(gameInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
        _world.setContactListener(new ContactHandler(_runner));
    }

    private void spawnEnemy(){
        _timeBetweenEnemies = Helpers.getRandomFloat(GameManager.getInstance().getEnemyMinSeconds(),
                GameManager.getInstance().getEnemyMaxSeconds());
        float y = getCorrectYPos(true);
        Enemy enemy = new Enemy(_world, Constants.ENEMY_X, y,
                Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
        Gdx.app.log("Enemy speed: ", ""+enemy.getBody().getLinearVelocity().x);

        _enemies.add(enemy);
        _entityManager.addEntity(enemy);
    }

    private void spawnObstacle(){
        float x = getCorrectXPos();
        float y = getCorrectYPos(false);

        _obstacles.add(new Obstacle(_world, x, y, Constants.OBSTACLE_WIDTH,
                Constants.OBSTACLE_HEIGHT));
        _entityManager.addEntity(_obstacles.get(_obstacles.size - 1));
    }

    private void spawnPlatform(){

        Platform platform = new Platform(_world, 42, Helpers.getRandomFloat(0, 2), Constants.PLATFORM_WIDTH,
                Constants.PLATFORM_HEIGHT);
        Gdx.app.log("Platform speed: ", ""+platform.getBody().getLinearVelocity().x);
        _platforms.add(platform);
        _entityManager.addEntity(platform);



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
              (isEnemy ? Constants.ENEMY_HEIGHT : Constants.OBSTACLE_HEIGHT) / 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Main game loop
        Gdx.gl.glClearColor(1, 1, 0.5f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        switch (GameManager.getInstance().getState()){
            case RUNNING:
                _batch.setColor(Color.WHITE);

                _lastEnemySpawnTime += delta;
                _totalTime += delta;
                updateDifficulty();

                if (isTimeForPlatformSpawn() && Helpers.getRandomInt(0, 5) == 5){
                    _life.add(new Life(_world, 35, getCorrectYPos(false) + 3, 0.5f, 0.5f));
                    _entityManager.addEntity(_life.get(_life.size - 1));
                }


                if (isTimeForEnemySpawn()){
                    spawnEnemy();
                    _lastEnemySpawnTime = 0;
                }
                if(isTimeForPlatformSpawn()){
                    spawnPlatform();
                }
                if(isGameOver()){

                    GameManager.getInstance().setState(GameState.GAMEOVER);

                }
                destroyBodies();
                _gameHudStage.act(delta);
                doStep(delta);
                break;
            case PAUSED:
                _batch.setColor(0.5f, 0.5f, 0.5f, 1f);
                break;
            case GAMEOVER:
                Gdx.app.log("Game over ", "yo");
                _game.setScreen(new MainMenuScreen(_game));
                return;
        }

        draw(delta);


    }

    private void updateDifficulty(){

        if (_totalTime > _timeForDifficultyChange && !GameManager.getInstance().isMaxDifficulty()){
            GameManager.getInstance().nextDifficulty();
            Gdx.app.log("Multiplyer:", "" + GameManager.getInstance().getMultiplyer());
            Gdx.app.log("Difficulty: ", ""+GameManager.getInstance().getDifficulty());
            _timeForDifficultyChange += 30;
            updateMovingSpeed();

        }
    }

    private void updateMovingSpeed(){
        // On difficultychange, updatres speed of the platforms that are
        // already on screen
        for (Platform platform : _platforms){
            platform.getBody().setLinearVelocity(
                    GameManager.getInstance().getStaticObjectSpeed(), 0);
        }
        for (Obstacle obstacle : _obstacles){
            obstacle.getBody().setLinearVelocity(
                    GameManager.getInstance().getStaticObjectSpeed(), 0);
        }

        for (Life life : _life){
            life.getBody().setLinearVelocity(GameManager.getInstance().getStaticObjectSpeed(), 0);
        }
    }

    private void doStep(float delta) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float framteTime = Math.min(delta, 0.25f);
        accumulator += framteTime;
        while (accumulator >= TIME_STEP){
            _entityManager.saveCurrentPosition();
            _entityManager.updateEntities();
            _world.step(TIME_STEP,6, 2);
            accumulator -= TIME_STEP;
            _entityManager.interpolate(accumulator / TIME_STEP);
        }
    }

    private void draw(float delta){
        if (GameManager.getInstance().getState() == GameState.RUNNING){
            _renderer.updateAnimation(delta);
        }
        _batch.begin();



        for (Platform platform : _platforms){
            _renderer.drawPlatform(_batch, platform.getPosition().x - platform.getWidth() / 2, platform.getPosition().y - platform.getHeight() / 2, platform.getWidth());
        }


        _renderer.drawRunner(_batch, _runner.getPosition().x - _runner.getWidth() / 2, _runner.getPosition().y - _runner.getHeight() / 2, _runner.getIsJumping());

        if (_enemies.size > 0){
            for (Enemy enemy : _enemies){
                _renderer.drawEnemy(_batch, enemy.getPosition().x - enemy.getWidth() / 2, enemy.getPosition().y - enemy.getHeight() / 2);
            }

        }



        if (_life.size > 0){
            for (Life life : _life){


                _renderer.drawHeart(_batch, life.getPosition().x - life.getWidth() / 2, life.getPosition().y - life.getHeight() / 2);
            }
        }



        if (_obstacles.size > 0){
            for (Obstacle obstacle : _obstacles) {


                _renderer.drawObstacle(_batch, obstacle.getPosition().x - obstacle.getWidth() / 2, obstacle.getPosition().y - obstacle.getHeight() / 2);
            }
        }

        _batch.end();

        _gameHudStage.draw();
        if (isDebug ){
            _debugRenderer.render(_world, _camera.combined);
        }
    }

    private boolean isGameOver() {
        return _runner.getHealth() == 0 || Helpers.isBodyOutOfBounds(_runner.getBody());
    }

    private boolean isTimeForPlatformSpawn() {
        return (getCurrentPlatform().getBody().getPosition().x + getCurrentPlatform().getWidth() / 2) < 22;
    }

    private boolean isTimeForEnemySpawn() {
        // Second condition checks so that the enemy has a platform to stand on
        return (_lastEnemySpawnTime > _timeBetweenEnemies) && (
                getCurrentPlatform().getBody().getPosition().x + getCurrentPlatform().getWidth() / 2) > Constants.ENEMY_X;
    }

    @Override
    public void resize(int width, int height) {
        _gameHudStage.getViewport().update(width, height, true); // True sets (0, 0) in bottom left corner.
        _viewport.update(width, height);
        _camera.position.set(_camera.viewportWidth / 2, _camera.viewportHeight / 2, 0);
    }

    @Override
    public void pause() {
        GameManager.getInstance().setState(GameState.PAUSED);
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
        _batch.dispose();
        _gameHudStage.dispose();
    }

    private void destroyBodies(){
        _world.getBodies(_bodies);

        for(Body body : _bodies){
            // First update the body, check if it needs removing
            if(Helpers.isBodyOutOfBounds(body)){
                ((Entity)body.getUserData()).setFlaggedForDeath(true);
            }
            // Then remove it from the world
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
            case LIFE:
                _life.removeValue((Life)object, false);
                break;

        }

        _entityManager.removeEntity(object);
    }

    public Runner getRunner(){ return _runner; }

    public World getWorld(){ return _world; }

    public Game getGame(){ return _game; }

    public Platform getCurrentPlatform(){ return _platforms.get(_platforms.size - 1); }


}
