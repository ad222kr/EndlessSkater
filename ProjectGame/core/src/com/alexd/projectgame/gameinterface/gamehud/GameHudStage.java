package com.alexd.projectgame.gameinterface.gamehud;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.gamehud.actors.*;
import com.alexd.projectgame.gameinterface.mainmenu.actors.MusicButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.OutfitsButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.PlayButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.SoundButton;
import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.utils.GameManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.*;

import java.awt.*;

/**
 * Created by Alex on 2015-05-01.
 */
public class GameHudStage extends Stage {

    private GameScreen _screen;
    private Score _score;
    private Health _health;
    private PauseButton _pauseButton;
    private Table _pauseTable;




    public GameHudStage(GameScreen screen){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));

        _screen = screen;

        setupPauseMenu();
        setupGameHud();









    }

    public void setupPauseMenu(){


        TextButton.TextButtonStyle exitStyle = new TextButton.TextButtonStyle();
        exitStyle.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-unpressed"));
        exitStyle.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-pressed"));
        exitStyle.font = AssetsManager.getButtonFont();



        TextButton.TextButtonStyle resumeStyle = new TextButton.TextButtonStyle();
        resumeStyle.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-unpressed"));
        resumeStyle.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-pressed"));
        resumeStyle.font = AssetsManager.getButtonFont();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getButtonFont();

        Label label = new Label("PAUSED", labelStyle);


        _pauseTable = new Table();


        _pauseTable.pad(200, 0, 0, 0);
        _pauseTable.setFillParent(true);

        _pauseTable.add(label).colspan(2);
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

    public void setupGameHud(){
        _score = new Score();
        _health = new Health(_screen.getRunner().getHealth());
        _pauseButton = new PauseButton(_screen.getGame(), 40,650, 40, 40);




        addActor(_score);
        addActor(_health);
        addActor(_pauseButton);

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
