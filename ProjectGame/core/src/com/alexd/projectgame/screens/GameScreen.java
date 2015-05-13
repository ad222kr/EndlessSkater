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

    private Life _life;

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
        setUp();
        initiate();
    }

    private void initiate(){

        GameManager.getInstance().resetDifficulty();
        _platforms.add(new Platform(_world, Constants.PLATFORM_INIT_X, Constants.PLATFORM_INIT_Y,
                Constants.PLATFORM_INIT_WIDTH,
                Constants.PLATFORM_HEIGHT));

        _lastEnemySpawnTime = 0;
        _timeBetweenEnemies = 5;
        _timeForDifficultyChange = 30;
    }

    private void setUp(){

        // Physics & entities
        _world = new World(Constants.WORLD_GRAVITY, true);
        _runner = new Runner(_world, Constants.RUNNER_X, Constants.RUNNER_Y,
                Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT);
        _bodies = new Array<Body>();
        _platforms = new Array<Platform>(4);
        _enemies = new Array<Enemy>(4);
        _obstacles = new Array<Obstacle>(4);



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
    }

    private void spawnObstacle(){
        float x = getCorrectXPos();
        float y = getCorrectYPos(false);

        _obstacles.add(new Obstacle(_world, x, y, Constants.OBSTACLE_WIDTH,
                Constants.OBSTACLE_HEIGHT));
    }

    private void spawnPlatform(){

        Platform platform = new Platform(_world, 42, Helpers.getRandomFloat(0, 2), Constants.PLATFORM_WIDTH,
                Constants.PLATFORM_HEIGHT);
        Gdx.app.log("Platform speed: ", ""+platform.getBody().getLinearVelocity().x);
        _platforms.add(platform);



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
        draw(delta);

        switch (GameManager.getInstance().getState()){
            case RUNNING:
                _batch.setColor(Color.WHITE);

                _lastEnemySpawnTime += delta;
                _totalTime += delta;
                updateDifficulty();

                if (isTimeForPlatformSpawn() && Helpers.getRandomInt(0, 5) == 5){
                    _life = new Life(_world, 35, getCorrectYPos(false) + 3, 0.5f, 0.5f);
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


                break;
        }




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
                    Constants.PLATFORM_SPEED * GameManager.getInstance().getMultiplyer(), 0);
        }
        for (Obstacle obstacle : _obstacles){
            obstacle.getBody().setLinearVelocity(
                    Constants.OBSTACLE_SPEED* GameManager.getInstance().getMultiplyer(), 0);
        }
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
        if (GameManager.getInstance().getState() == GameState.RUNNING){
            _renderer.updateAnimation(delta);
        }
        _batch.begin();

        _renderer.drawBackground(_batch);

        _renderer.drawRunner(_batch, _runner.getBody().getWorldCenter().x - Constants.RUNNER_WIDTH / 2,
                _runner.getBody().getWorldCenter().y - Constants.RUNNER_HEIGHT / 2, _runner.getIsJumping());

        for (Enemy enemy : _enemies){
            _renderer.drawEnemy(_batch, enemy.getBody().getWorldCenter().x - Constants.ENEMY_WIDTH / 2,
                    enemy.getBody().getWorldCenter().y - Constants.ENEMY_HEIGHT / 2);
        }

       /* for (Obstacle obstacle : _obstacles){
            _renderer.drawObstacle(_batch, obstacle.getBody().getWorldCenter().x - Constants.OBSTACLE_WIDTH / 2,
                    obstacle.getBody().getWorldCenter().y - Constants.OBSTACLE_HEIGHT / 2);
        }*/
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

        }
    }

    public Runner getRunner(){ return _runner; }

    public World getWorld(){ return _world; }

    public Game getGame(){ return _game; }

    public Platform getCurrentPlatform(){ return _platforms.get(_platforms.size - 1); }


}
