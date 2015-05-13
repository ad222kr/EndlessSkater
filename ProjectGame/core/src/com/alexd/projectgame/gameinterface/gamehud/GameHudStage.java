package com.alexd.projectgame.gameinterface.gamehud;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.gamehud.actors.*;
import com.alexd.projectgame.gameinterface.mainmenu.actors.MusicButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.SoundButton;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    private HorizontalGroup _pauseGroup;
    private Table _pauseTable;




    public GameHudStage(GameScreen screen){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));

        _screen = screen;
        _score = new Score();
        _health = new Health(_screen.getRunner().getHealth());
        _pauseButton = new PauseButton(_screen.getGame(), 40,650, 40, 40);
        /*_pauseGroup = new HorizontalGroup();
        _pauseGroup.setVisible(false);
        _pauseGroup.addActor(new ExitButton(_screen.getGame(), TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 3, 350, 100));
        _pauseGroup.addActor(new ResumeButton(_screen.getGame(), TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 2, 350, 100));

        _pauseGroup.pad(100f);
        _pauseGroup.setPosition(TheGame.APP_WIDTH / 2 - 800 / 2 , TheGame.APP_HEIGHT / 2 - 200);*/

        _pauseTable = new Table();
        _pauseTable.pad(200,0, 0,0);
        _pauseTable.setFillParent(true);

        _pauseTable.add(new SoundButton(_screen.getGame(), 0,0,0,0)).left().pad(0, 150, 20, 0);
        _pauseTable.add(new MusicButton(_screen.getGame(), 0,0,0,0)).right().pad(0,0,20,150);

        _pauseTable.row();
        _pauseTable.add(new ExitButton(_screen.getGame(), TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 3, 350, 100)).pad(0, 0, 20, 0);
        _pauseTable.add(new ResumeButton(_screen.getGame(), TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 2, 350, 100)).pad(0, 0, 20, 0);




        addActor(_score);
        addActor(_health);
        addActor(_pauseButton);


        addActor(_pauseTable);
    }

    @Override
    public void draw(){
        super.draw();


        switch (GameManager.getInstance().getState()){
            case RUNNING:
                _pauseButton.enable();
                _pauseTable.setVisible(false);
                _pauseTable.setTouchable(Touchable.disabled);
                break;
            case PAUSED:
                _pauseButton.disable();

                _pauseTable.setVisible(true);
                _pauseTable.setTouchable(Touchable.enabled);
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
