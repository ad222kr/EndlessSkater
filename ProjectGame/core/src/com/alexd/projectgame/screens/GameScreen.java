package com.alexd.projectgame.screens;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.enums.GameState;
import com.alexd.projectgame.utils.*;
import com.alexd.projectgame.entities.*;
import com.alexd.projectgame.handlers.GameInputHandler;
import com.alexd.projectgame.gameinterface.gamehud.GameHudStage;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;



/**
 * Created by Alex on 2015-04-07.
 */
public class GameScreen implements Screen {

    private boolean isDebug = false;// Set to false to hide debugrender

    private final int VIEWPORT_WIDTH = Helpers.convertToMeters(TheGame.APP_WIDTH);
    private final int VIEWPORT_HEIGHT = Helpers.convertToMeters(TheGame.APP_HEIGHT);

    private Game _game;
    private EntityManager _entityManager;
    private OrthographicCamera _camera;
    private Viewport _viewport;
    private Box2DDebugRenderer _debugRenderer;
    private GameRenderer _renderer;
    private Batch _batch;
    private GameHudStage _gameHudStage;
    private float _totalTime;
    private float _timeForDifficultyChange = 30;

    public GameScreen(Game game) {
        _game = game;
        GameManager.getInstance().setRunning();
        GameManager.getInstance().resetDifficulty();
        setUp();
    }

    private void setUp(){
        _entityManager = new EntityManager();

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

        // Input. The screen uses one inputprocessor for the controls,
        // The stage uses another for pause etc.
        InputProcessor gameInputProcessor = new GameInputHandler(_entityManager.getRunner());
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(_gameHudStage);
        inputMultiplexer.addProcessor(gameInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {
        // Constructor takes care of initiating things
    }

    @Override
    public void render(float delta) {
        // Main game loop
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (GameManager.getInstance().getState()){
            case RUNNING:
                _batch.setColor(Color.WHITE);
                _totalTime += delta;
                _entityManager.updateTimers(delta);

                updateDifficulty();

                _entityManager.spawnPlatform();
                _entityManager.spawnEnemy();

                if(isGameOver()){
                    GameManager.getInstance().setState(GameState.GAMEOVER);
                }

                _entityManager.destroyBodies();
                _gameHudStage.act(delta);
                _entityManager.doStep(delta);
                break;
            case PAUSED:
                _batch.setColor(0.5f, 0.5f, 0.5f, 1f);
                break;

            case GAMEOVER:
               GamePreferences.getInstance().saveHighScore(_gameHudStage.getScore());
                _game.setScreen(new MainMenuScreen(_game));
                return;
        }
        draw(delta);
    }

    private void updateDifficulty(){

        if (_totalTime > _timeForDifficultyChange && !GameManager.getInstance().isMaxDifficulty()){
            Gdx.app.log("Next diff", "wololo");
            GameManager.getInstance().nextDifficulty();
            _timeForDifficultyChange += 30;
            _entityManager.updateMovingSpeed();
        }
    }

    private void draw(float delta){
        if (GameManager.getInstance().getState() == GameState.RUNNING){
            _renderer.updateAnimation(delta);
        }
        _batch.begin();

        for (Entity entity : _entityManager.getEntities()){
            switch (entity.getGameObjectType()){
                case GROUND:
                    _renderer.drawPlatform(_batch, entity.getPosition().x - entity.getWidth() / 2,
                            entity.getPosition().y - entity.getHeight() / 2, entity.getWidth());
                    break;


                case ENEMY:
                    _renderer.drawEnemy(_batch, entity.getPosition().x - entity.getWidth() / 2,
                            entity.getPosition().y - entity.getHeight() / 2);
                    break;
                case LIFE:
                    _renderer.drawHeart(_batch, entity.getPosition().x - entity.getWidth() / 2,
                            entity.getPosition().y - entity.getHeight() / 2);
                    break;
                case OBSTACLE:
                    _renderer.drawObstacle(_batch, entity.getPosition().x - entity.getWidth() / 2,
                            entity.getPosition().y - entity.getHeight() / 2);
                    break;



            }
        }

        _renderer.drawRunner(_batch, getRunner().getPosition().x - getRunner().getWidth() / 2,
                getRunner().getPosition().y - getRunner().getHeight() / 2, _entityManager.getRunner().getIsJumping());
        _batch.end();

        _gameHudStage.draw();
        if (isDebug ){
            _debugRenderer.render(_entityManager.getWorld(), _camera.combined);
        }
    }

    private boolean isGameOver() {
        return getRunner().getHealth() == 0 || Helpers.isBodyOutOfBounds(_entityManager.getRunner().getBody());
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
        _entityManager.dispose();
        _renderer.dispose();
        if (isDebug){
            _debugRenderer.dispose();
        }
        _batch.dispose();
        _gameHudStage.dispose();
    }

    public Game getGame(){ return _game; }

    public Runner getRunner(){
        return _entityManager.getRunner();
    }
}
