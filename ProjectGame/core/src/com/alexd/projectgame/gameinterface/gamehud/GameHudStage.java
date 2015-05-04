package com.alexd.projectgame.gameinterface.gamehud;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.handlers.GameStateHandler;
import com.alexd.projectgame.screens.GameScreen;
import com.alexd.projectgame.gameinterface.gamehud.actors.Health;
import com.alexd.projectgame.gameinterface.gamehud.actors.PauseButton;
import com.alexd.projectgame.gameinterface.gamehud.actors.Score;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Alex on 2015-05-01.
 */
public class GameHudStage extends Stage {

    private GameScreen _screen;
    private Score _score;
    private Health _health;
    private PauseButton _pauseButton;
    private GameStateHandler _gsh;


    public GameHudStage(GameScreen screen, GameStateHandler gsh){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));

        _screen = screen;
        _gsh = gsh;
        _score = new Score();
        _health = new Health(_screen.getRunner());
        _pauseButton = new PauseButton(_gsh);

        addActor(_score);
        addActor(_health);
        addActor(_pauseButton);




    }

    @Override
    public void draw(){
        super.draw();

    }

    @Override
    public void act(float delta){
        super.act(delta);
    }



















}
