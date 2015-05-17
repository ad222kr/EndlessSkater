package com.alexd.projectgame.gameinterface.mainmenu;

import com.alexd.projectgame.TheGame;
import com.alexd.projectgame.gameinterface.mainmenu.actors.MusicButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.OutfitsButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.PlayButton;
import com.alexd.projectgame.gameinterface.mainmenu.actors.SoundButton;
import com.alexd.projectgame.utils.AssetsManager;
import com.alexd.projectgame.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.*;


/**
 * Created by Alex on 2015-05-01.
 */
public class MainMenuStage extends Stage{

    private Game _game;
    private PlayButton _playButton;
    private OutfitsButton _outfitsButton;
    private SoundButton _soundButton;
    private MusicButton _musicButton;

    private ImageTextButton _btnTest;




    public MainMenuStage(Game game){
        super(new StretchViewport(TheGame.APP_WIDTH, TheGame.APP_HEIGHT, new OrthographicCamera(TheGame.APP_WIDTH, TheGame.APP_HEIGHT)));

        _game = game;
        //_playButton = new PlayButton(_game, TheGame.APP_WIDTH / 2, TheGame.APP_HEIGHT / 2, 350, 100);

        _soundButton = new SoundButton(_game, 0,0,0,0);
        _musicButton = new MusicButton(_game, 0,0,0,0);


        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-unpressed"));
        style.down = new TextureRegionDrawable(AssetsManager.getSkin().getRegion("greenbutton-pressed"));
        style.font = AssetsManager.getFont();




        _playButton = new PlayButton("PLAY", style, _game);



        Table table = new Table();
        table.setFillParent(true);

        table.add(_playButton).colspan(2).pad(0,0,20,0);
        table.row();
        table.add(_outfitsButton).colspan(2).pad(0,0,20,0);
        table.row();
        table.add(_soundButton).left();
        table.add(_musicButton).right();


        table.setTouchable(Touchable.enabled);



        addActor(table);







    }


    @Override
    public void draw(){
        super.draw();
    }


}
