package com.alexd.projectgame.stages.gamehud;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.screens.GameScreen;
import com.alexd.projectgame.stages.gamehud.actors.Health;
import com.alexd.projectgame.stages.gamehud.actors.Score;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Alex on 2015-05-01.
 */
public class GameHudStage extends Stage {

    private GameScreen _screen;
    private Score _score;
    private Health _health;


    public GameHudStage(GameScreen screen){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));
        _screen = screen;

        _score = new Score();
        _health = new Health(_screen.getRunner());

        addActor(_score);
        addActor(_health);
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
