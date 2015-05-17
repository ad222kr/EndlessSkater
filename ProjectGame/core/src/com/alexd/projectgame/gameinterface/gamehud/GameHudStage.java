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
    private ResumeButton _resumeButton;
    private SoundButton _soundButton;
    private MusicButton _musicButton;
    private PlayButton _playButton;
    private OutfitsButton _outfitsButton;
    private HorizontalGroup _pauseGroup;
    private Table _pauseTable;




    public GameHudStage(GameScreen screen){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));

        _screen = screen;
        _soundButton = new SoundButton(_screen.getGame(), 0,0,0,0);
        _musicButton = new MusicButton(_screen.getGame(), 0,0,0,0);


        setupPauseMenu();



        setupGameHud();



    }

    public void setupPauseMenu(){



        TextButton.TextButtonStyle exitStyle = new TextButton.TextButtonStyle();
        exitStyle.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-unpressed"));
        exitStyle.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("standardbutton-pressed"));
        exitStyle.font = AssetsManager.getFont();



        TextButton.TextButtonStyle resumeStyle = new TextButton.TextButtonStyle();
        resumeStyle.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-unpressed"));
        resumeStyle.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-pressed"));
        resumeStyle.font = AssetsManager.getFont();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = AssetsManager.getFont();
        labelStyle.font.setScale(1);
        Label label = new Label("PAUSED", labelStyle);


        _pauseTable = new Table();
        _pauseTable.pad(0, 0, 0, 0);
        _pauseTable.setFillParent(true);

        _pauseTable.add(label).center().pad(0,0,25,0).colspan(2);
        _pauseTable.row();
        _pauseTable.add(new SoundButton(_screen.getGame(), 0,0,0,0)).left().pad(0, 250, 25, 0);
        _pauseTable.add(new MusicButton(_screen.getGame(), 0,0,0, 0)).right().pad(0, 0, 25, 250);

        _pauseTable.row();
        _pauseTable.add(new ExitButton("EXIT", exitStyle, _screen.getGame())).left().pad(0,0,0,50);

        _pauseTable.add(new ResumeButton("RESUME", resumeStyle, _screen.getGame())).right().pad(0,50,0,0);
        addActor(_pauseTable);



    }

    public void setupGameHud(){
        _score = new Score();
        _health = new Health(_screen.getRunner().getHealth());
        _pauseButton = new PauseButton(_screen.getGame(), 30,650, 40, 40);




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
