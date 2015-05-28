package com.alexd.projectgame.gameinterface.gamehud;

import com.alexd.projectgame.gameinterface.gamehud.actors.*;
import com.alexd.projectgame.gameinterface.shared.*;
import com.alexd.projectgame.screens.BaseScreen;
import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Alex on 2015-05-01.
 */
public class GameHudStage extends BaseStage {

    private Score _score;
    private Health _health;
    private PauseButton _pauseButton;
    private Table _pauseTable;
    private Table _gameOverTable;
    private Label _gameOverScoreLabel;

    public GameHudStage(BaseScreen screen){
        super(screen);
        setupPauseMenu();
        setupGameHud();
        setupGameOver();
    }

    public void setupPauseMenu(){
        _pauseTable = new Table();

        _pauseTable.pad(200, 0, 0, 0);
        _pauseTable.setFillParent(true);

        _pauseTable.add(getLabel("PAUSED", true)).colspan(2).row();
        _pauseTable.add(getSoundButton()).left().pad(0, 200, 20, 0);
        _pauseTable.add(getMusicButton()).right().pad(0, 0, 20, 200).row();
        _pauseTable.add(getMainMenyButton()).left().pad(0, 0, 0, 50);
        _pauseTable.add(getResumeButton()).right().pad(0, 50, 0, 0);
        toggleTable(_pauseTable, false);
        addActor(_pauseTable);
    }

    private void toggleTable(Table table, boolean show){
        table.setVisible(show);
        if (show){
            table.setTouchable(Touchable.enabled);
        }
        else {
            table.setTouchable(Touchable.disabled);
        }
    }

    private ResumeButton getResumeButton(){
        return new ResumeButton("RESUME", getTextButtonStyle("greenbutton-unpressed", "greenbutton-pressed", true),
                _screen.getGame());
    }

    private PauseButton getPauseButton(){
        return new PauseButton(_screen.getGame());
    }

    public void setupGameOver(){
        _gameOverTable = new Table();
        _gameOverTable.pad(100,0,0,0);
        _gameOverTable.setFillParent(true);
        _gameOverScoreLabel = getLabel("SCORE", true);
        _gameOverTable.add(getLabel("GAME OVER", true)).colspan(2).row();
        _gameOverTable.add(_gameOverScoreLabel).pad(50, 0, 160, 0).colspan(2).row();
        _gameOverTable.add(getMainMenyButton()).left().pad(0, 0, 0, 50);
        _gameOverTable.add(getPlayButton("RETRY")).right().pad(0, 50, 0, 0);

        toggleTable(_gameOverTable, false);

        addActor(_gameOverTable);
    }

    public void setupGameHud(){
        _score = new Score();
        _health = new Health(((GameScreen)_screen).getRunner().getHealth());
        _pauseButton = getPauseButton();
        addActor(new Score());
        addActor(_health);
        addActor(_pauseButton);
    }

    public void draw(Batch batch){
        super.draw();

        batch.begin();
        switch (GameManager.getInstance().getState()){
            case RUNNING:
                _pauseButton.enable();

                toggleTable(_gameOverTable, false);
                toggleTable(_pauseTable, false);

                _gameOverTable.draw(batch, 0);
                _pauseButton.draw(batch, 0);
                _score.draw(batch, 0);
                _health.draw(batch, 0);
                break;
            case PAUSED:
                _pauseButton.hide();

                toggleTable(_pauseTable, true);
                toggleTable(_gameOverTable, false);

                _pauseTable.draw(batch, 0);
                break;
            case GAMEOVER:
                _pauseButton.hide();
                _gameOverScoreLabel.setText("SCORE: " + getScore());
                _gameOverTable.draw(batch, 0);

                toggleTable(_pauseTable, false);
                toggleTable(_gameOverTable, true);
                hideGameHUD();
                break;
        }
        batch.end();
    }

    private void hideGameHUD(){
        _pauseButton.hide();
        _health.setVisible(false);
        _score.setVisible(false);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        _health.updateHealthArray(((GameScreen)_screen).getRunner().getHealth());
        _score.act(delta);
    }

    public int getScore(){
        return _score.getScore();
    }

    public void addScore(float amount){
        _score.addScore(amount);
    }
}