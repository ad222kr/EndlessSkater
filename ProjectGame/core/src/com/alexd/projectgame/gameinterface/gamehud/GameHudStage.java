package com.alexd.projectgame.gameinterface.gamehud;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.gamehud.actors.*;
import com.alexd.projectgame.gameinterface.mainmenu.actors.MusicButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.SoundButton;
import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import javafx.scene.control.Tab;

/**
 * Created by Alex on 2015-05-01.
 */
public class GameHudStage extends Stage {

    private GameScreen _screen;
    private Score _score;
    private Health _health;
    private PauseButton _pauseButton;
    private Table _pauseTable;
    private Table _gameOverTable;
    private Label _gameOverScoreLabel; // need to have ref to this
    FPS fps;

    public GameHudStage(GameScreen screen){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));

        _screen = screen;

        setupPauseMenu();
        setupGameHud();
        setupGameOver();

    }

    public void setupPauseMenu(){

        TextButton.TextButtonStyle exitStyle = new TextButton.TextButtonStyle();
        exitStyle.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-unpressed"));
        exitStyle.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-pressed"));
        exitStyle.font = AssetsManager.getLargeFont();

        TextButton.TextButtonStyle resumeStyle = new TextButton.TextButtonStyle();
        resumeStyle.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-unpressed"));
        resumeStyle.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-pressed"));
        resumeStyle.font = AssetsManager.getLargeFont();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getLargeFont();
        _pauseTable = new Table();

        _pauseTable.pad(200, 0, 0, 0);
        _pauseTable.setFillParent(true);

        _pauseTable.add(new Label("PAUSED", labelStyle)).colspan(2);
        _pauseTable.row();
        _pauseTable.add(new SoundButton(_screen.getGame(), 0,0,0, 0)).left().pad(0, 200, 20, 0);
        _pauseTable.add(new MusicButton(_screen.getGame(), 0, 0,0, 0)).right().pad(0, 0, 20, 200);

        _pauseTable.row();
        _pauseTable.add(new ExitButton("EXIT", exitStyle, _screen.getGame())).left().pad(0, 0, 0, 50);

        _pauseTable.add(new ResumeButton("RESUME", resumeStyle, _screen.getGame())).right().pad(0, 50, 0, 0);
        addActor(_pauseTable);

        _pauseTable.setVisible(false);
        _pauseTable.setTouchable(Touchable.disabled);
    }

    public void setupGameOver(){
        Label.LabelStyle gameoverStyle = new Label.LabelStyle();
        gameoverStyle.font = AssetsManager.getLargeFont();

        Label.LabelStyle scoreStyle = new Label.LabelStyle();
        scoreStyle.font = AssetsManager.getSmallFont();

        TextButton.TextButtonStyle exitStyle = new TextButton.TextButtonStyle();
        exitStyle.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-unpressed"));
        exitStyle.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-pressed"));
        exitStyle.font = AssetsManager.getLargeFont();

        _gameOverScoreLabel = new Label("SCORE: "+ getScore(), scoreStyle);
        _gameOverTable = new Table();
        _gameOverTable.pad(100,0,0,0);
        _gameOverTable.setFillParent(true);
        _gameOverTable.add(new Label("GAME OVER", gameoverStyle)).row();
        _gameOverTable.add(_gameOverScoreLabel).pad(50, 0, 0, 0).row();
        _gameOverTable.add(new ExitButton("MENU", exitStyle, _screen.getGame())).pad(50, 0, 0, 0);

        _gameOverTable.setVisible(false);
        _gameOverTable.setTouchable(Touchable.enabled);

        addActor(_gameOverTable);
    }

    public void setupGameHud(){
        _score = new Score();
        _health = new Health(_screen.getRunner().getHealth());
        _pauseButton = new PauseButton(_screen.getGame(), 40,650, 40, 40);
        fps = new FPS();
        addActor(_score);
        addActor(_health);
        addActor(_pauseButton);
        addActor(fps);
    }

    public void draw(Batch batch){
        super.draw();

        batch.begin();
        switch (GameManager.getInstance().getState()){
            case RUNNING:

                _gameOverTable.setVisible(false);
                _gameOverTable.setTouchable(Touchable.enabled);
                _pauseButton.enable();
                _pauseTable.setVisible(false);
                _pauseTable.setTouchable(Touchable.disabled);

                _gameOverTable.draw(batch, 0);
                _pauseButton.draw(batch, 0);
                _score.draw(batch, 0);
                _health.draw(batch, 0);
                fps.draw(batch, 0);
                break;
            case PAUSED:
                _pauseButton.disable();
                _gameOverTable.setVisible(false);
                _gameOverTable.setTouchable(Touchable.enabled);
                _pauseTable.setVisible(true);
                _pauseTable.setTouchable(Touchable.enabled);

                _pauseTable.draw(batch, 0);

                break;
            case GAMEOVER:
                _pauseButton.disable();
                _health.setVisible(false);
                _score.setVisible(false);
                _gameOverScoreLabel.setText("SCORE: " + getScore());
                _gameOverTable.setVisible(true);
                _gameOverTable.setTouchable(Touchable.enabled);
                _gameOverTable.draw(batch, 0);


                break;
        }
        batch.end();

    }

    @Override
    public void act(float delta){
        super.act(delta);

        _health.updateHealthArray(_screen.getRunner().getHealth());
    }

    public int getScore(){
        return _score.getScore();
    }




























}
