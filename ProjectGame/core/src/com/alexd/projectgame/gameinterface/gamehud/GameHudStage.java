package com.alexd.projectgame.gameinterface.gamehud;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.gamehud.actors.*;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Alex on 2015-05-01.
 */
public class GameHudStage extends Stage {

    private GameScreen _screen;
    private Score _score;
    private Health _health;
    private PauseButton _pauseButton;
    private HorizontalGroup _pauseGroup;



    public GameHudStage(GameScreen screen){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));

        _screen = screen;
        _score = new Score();
        _health = new Health(_screen.getRunner().getHealth());
        _pauseButton = new PauseButton(_screen.getGame(), 50,670, 64, 64, "pause-button");
        _pauseGroup = new HorizontalGroup();
        _pauseGroup.setVisible(false);
        _pauseGroup.addActor(new MenuButton(_screen.getGame(), TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 3, 350, 100, "play-button"));
        _pauseGroup.addActor(new ResumeButton(_screen.getGame(), TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 2, 350, 100, "resume-button"));

        _pauseGroup.space(100f);
        _pauseGroup.setPosition(TheGame.APP_WIDTH / 2 - 800 / 2 , TheGame.APP_HEIGHT / 2 - 200);

        addActor(_score);
        addActor(_health);
        addActor(_pauseButton);
        addActor(_pauseGroup);
    }

    @Override
    public void draw(){
        super.draw();

        switch (GameManager.getInstance().getState()){
            case RUNNING:
                _pauseButton.enable();
                _pauseGroup.setVisible(false);
                _pauseGroup.setTouchable(Touchable.disabled);
                break;
            case PAUSED:
                _pauseButton.disable();

                _pauseGroup.setVisible(true);
                _pauseGroup.setTouchable(Touchable.enabled);
                break;
            case GAMEOVER:

                // Game over draw here
                break;
        }


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
